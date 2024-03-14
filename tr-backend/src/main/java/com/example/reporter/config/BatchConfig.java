package com.example.reporter.config;

import java.math.BigDecimal;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.reporter.entities.Operation;
import com.example.reporter.entities.Remittance;
import com.example.reporter.entities.OperationType;

@Configuration
public class BatchConfig {
  private PlatformTransactionManager platformTransactionManager;
  // Jobs sao baseados em maquinas de estado - o metadado para cada step fica armazenado em jobRepository
  private JobRepository jobRepository;

  public BatchConfig(PlatformTransactionManager platformTransactionManager, JobRepository jobRepository) {
    this.platformTransactionManager = platformTransactionManager;
    this.jobRepository = jobRepository;
  }

  // Construindo o Job - o incrementer foi utilizado com proposito de testar a
  // aplicacao.
  @Bean
  Job job(Step step) {
    return new JobBuilder("job", jobRepository)
        .start(step)
        .incrementer(new RunIdIncrementer())
        .build();
  }

  // Fracionando o Job em steps
  @Bean
  Step step(
      ItemReader<Remittance> itemReader,
      ItemProcessor<Remittance, Operation> itemProcessor,
      ItemWriter<Operation> itemWriter) {
    return new StepBuilder("step", jobRepository)
        .<Remittance, Operation>chunk(1000, platformTransactionManager)
        .reader(itemReader)
        .processor(itemProcessor)
        .writer(itemWriter)
        .listener(new LoggingStepStartStopListener())
        .build();
  }

  // Logica para ler os arquivos de remessa

  // Injetei o 'cnabFile' de jobParameters no resource. Para que os parametros so
  // sejam injetados quando disponiveis, utilzei o StepScope
  // Diferente de outros formatos como xml, FlatFiles permitem que sejam montados
  // no formato que seja conveniente. Para isso, o FlatFileItemReaderBuilder
  @StepScope
  @Bean
  FlatFileItemReader<Remittance> reader(@Value("#{jobParameters['cnabFile']}") Resource resource) {
    return new FlatFileItemReaderBuilder<Remittance>()
        .name("reader")
        .resource(resource)
        .fixedLength()
        .columns(
            new Range(1, 1), new Range(2, 9),
            new Range(10, 19), new Range(20, 30),
            new Range(31, 42), new Range(43, 48),
            new Range(49, 62), new Range(63, 80))
        .names(
            "type", "date", "value", "cnpj",
            "card", "hour", "storeOwner", "storeName")
        .targetType(Remittance.class)
        .build();
  }

  // Logica para o processamento (opcional - definido por regra de negocio) e a
  // normalizacao, passando de um arquivo de remessa (remittance) para um arquivo
  // de retorno (operation) - como indica o padrao CNAB
  // Como definido na regra de negocio, deve-se dividir por 100 o valor recebido
  // para obter o valor final a ser mostrado no relatorio. Alem disso, como estamos
  // trabalhando com records (imutaveis) precisamos de uma maneira de "alterar"
  // algumas propriedades. Para isso, utilizei o pattern Wither, que cria uma copia do
  // objeto, alterando apenas os valores necessarios

  @Bean
  ItemProcessor<Remittance, Operation> processor() {
    return item -> {
      var operationType = OperationType.findByType(item.type());
      var withNormalizedValue = item.value()
          .divide(new BigDecimal(100))
          .multiply(operationType.getSinal());
      // Wither pattern
      var operation = new Operation(
          null,
          item.type(),
          null,
          withNormalizedValue,
          item.cnpj(),
          item.card(),
          null,
          item.storeOwner().trim(),
          item.storeName().trim())
          .withDate(item.date()) // Wither em data
          .withHour(item.hour()); // Wither em horas
      return operation;

    };
  }

  // Logica para escrita - geracao de relatorio
  // Priorizando a performance, SQL puro

  @Bean
  JdbcBatchItemWriter<Operation> writer(DataSource dataSource) {
    return new JdbcBatchItemWriterBuilder<Operation>()
        .dataSource(dataSource)
        .sql("""
            INSERT INTO operation (
                op_type, op_date, op_value, op_cnpj, op_card,
                op_hour, op_store_owner, op_store_name
            ) VALUES (
                :opType, :opDate, :opValue, :opCnpj, :opCard, :opHour, :opStoreOwner, :opStoreName
            )
            """)
        //Adicionei espaços reservados com o mesmo nome para usar beanMapped e preencher automaticamente o valor da operação recebida
        .beanMapped()
        .build();
  }

  // Customizando o jobLauncher para ser executado de forma assincrona
  @Bean
  JobLauncher jobLauncherAsync(JobRepository jobRepository) throws Exception {
    var jobLauncher = new TaskExecutorJobLauncher();
    jobLauncher.setJobRepository(jobRepository);
    jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
    jobLauncher.afterPropertiesSet();
    return jobLauncher;
  }

}