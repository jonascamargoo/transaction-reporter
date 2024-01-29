package com.example.pagamentos;

import java.math.BigDecimal;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {
    private PlatformTransactionManager platformTransactionManager;
    private JobRepository jobRepository;

    public BatchConfig(PlatformTransactionManager platformTransactionManager, JobRepository jobRepository) {
        this.platformTransactionManager = platformTransactionManager;
        this.jobRepository = jobRepository;
    }

    @Bean
    Job job(Step step) {
        return new JobBuilder("job", jobRepository)
                .start(step)
                .incrementer(new RunIdIncrementer())
                .build();
    }

    // dividindo pedaço a pedaço do processamento e definir o que queremos
    @Bean
    Step step(ItemReader<TransacaoCNAB> itemReader, ItemProcessor<TransacaoCNAB, Transacao> itemProcessor, ItemWriter<Transacao> itemWriter) {
        return new StepBuilder("step", jobRepository)
                .<TransacaoCNAB, Transacao>chunk(1000, platformTransactionManager)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();
    }

    // logica de leitura que ira retornar um objeto preenchido, que sera processado
    @Bean
    FlatFileItemReader<TransacaoCNAB> reader() {
        return new FlatFileItemReaderBuilder<TransacaoCNAB>()
            .name("reader")
            .resource(new FileSystemResource("files/CNAB.txt"))
            .fixedLength()
            .columns(
                new Range(1, 1), new Range(2, 9),
                new Range(10, 19), new Range(20, 30),
                new Range(31, 42), new Range(43, 48),
                new Range(49, 62), new Range(63, 80)
            )
            .names(
                "tipo", "data", "valor", "cpf",
                "cartao", "hora", "donoDaLoja", "nomeDaLoja"
            )
            .targetType(TransacaoCNAB.class)
            .build();
            
    }

    @Bean
    ItemProcessor<TransacaoCNAB, Transacao> processor() {
        // aqui iremos configurar o processador que processara uma transacaoCNAB em uma transacao. Mas como estamos record (que sao imutaveis). Para isso, utilizaremos o Wither Pattern -> crio um metodo que recria a transacao, mudando apenas a prorpiedade que quero mudar. Portanto, iremos criar uma Transacao com todos valores iguais, mudando apenas a propriedade valor.
        
        return item -> {
            var transacao = new Transacao(null, item.tipo(), null, null, item.cpf(), item.cartao(), null, item.donoDaLoja().trim(), item.nomeDaLoja().trim())
            .withValor(
                item.valor().divide(BigDecimal.valueOf(100)))
                // multipliquei por 100 pela especificaca do desafio. Farei o msm para data e hora
            .withData(item.data())
            .withHora(item.hora());

            return transacao;
            
        };
    }

    @Bean
    JdbcBatchItemWriter<Transacao> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Transacao>()
            .dataSource(dataSource)
            // em batch sempre queremos uma performance alta, entao utilizarei sql puro
            .sql("""
                    INSERT INTO transacao (
                        tipo, data, valor, cpf, cartao,
                        hora, dono_loja, nome_loja
                    ) VALUES (
                        :tipo, :data, :valor, :cpf, :cartao, :hora, :donoDaLoja, :nomeDaLoja
                    )
                    """)
                    //adicionei os placeholders com o mesmo nome, para utilizar o beanMapped para preencher automaticamente o valor da transacao recebida
                    .beanMapped()
                    .build();
        
    }


}
