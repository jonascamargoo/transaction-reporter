package com.example.pagamentos.job;

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

import com.example.pagamentos.entities.TransactionType;
import com.example.pagamentos.entities.Transaction;
import com.example.pagamentos.entities.Remittance;

@Configuration
public class BatchConfig {
    private PlatformTransactionManager platformTransactionManager;
    // Jobs are based on state machines. The metadata for each step resides in the jobRepository.
    private JobRepository jobRepository;

    public BatchConfig(PlatformTransactionManager platformTransactionManager, JobRepository jobRepository) {
        this.platformTransactionManager = platformTransactionManager;
        this.jobRepository = jobRepository;
    }

    @Bean
    Job job(Step step) {
        return new JobBuilder("job", jobRepository)
                // first step to be executed
                .start(step)
                // The incrementer is used to allow the job to run more than once during this testing phase
                .incrementer(new RunIdIncrementer())
                .build();
    }

    // Breaking down the processing piece by piece and defining what we want - Batch works with large data and we gonna count transaction by transaction
    @Bean
    Step step(
            ItemReader<Remittance> itemReader,
            ItemProcessor<Remittance, Transaction> itemProcessor,
            ItemWriter<Transaction> itemWriter) {
        return new StepBuilder("step", jobRepository)
                .<Remittance, Transaction>chunk(1000, platformTransactionManager)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();
    }

    // Logic to read the remittance file

    @StepScope
    // Retrieving job parameters using brackets. In other words, obtained from
    // jobParameters
    // and automatically injected into the Resource. Injection will only occur when
    // the
    // parameters are available - for that, StepScope
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
                        "type", "date", "value", "cpf",
                        "card", "hour", "storeOwner", "storeName")
                .targetType(Remittance.class)
                .build();
    }

    // Logic to process the remittance file into a return file

    @Bean
    ItemProcessor<Remittance, Transaction> processor() {
        // Converting .REM to .RET
        // Here we will configure the processor that will handle a
        // Remittance into a Transaction. However, as we are dealing with
        // records (which are immutable), we will use the Wither Pattern -> I create a
        // method that
        // recreates the transaction, changing only the property I want to modify.
        // Therefore,
        // we will create a Transaction with all values the same, changing only the
        // value property.
        return item -> {
            var transactionType = TransactionType.findByType(item.type());
            // I divided by 100 due to the challenge specification. I will do the same for
            // date and time
            var withNormalizedValue = item.value()
                    .divide(new BigDecimal(100))
                    .multiply(transactionType.getSinal());
            // Wither pattern
            var transaction = new Transaction(
                    null, item.type(), null,
                    withNormalizedValue, item.cpf(), item.card(),
                    null, item.storeOwner().trim(), item.storeName().trim())
                    .withDate(item.date())
                    .withHour(item.hour());
            return transaction;

        };
    }

    @Bean
    JdbcBatchItemWriter<Transaction> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Transaction>()
                .dataSource(dataSource)
                // In batch processing, we always aim for high performance, so I will use raw
                // SQL
                .sql("""
                        INSERT INTO transaction (
                            type, date, value, cpf, card,
                            hour, store_owner, store_name
                        ) VALUES (
                            :type, :date, :value, :cpf, :card, :hour, :storeOwner, :storeName
                        )
                        """)
                // I added placeholders with the same name to use beanMapped for
                // automatically filling in the value of the received transaction
                .beanMapped()
                .build();
    }

    // ENTENDER!
    @Bean
    JobLauncher jobLauncherAsync(JobRepository jobRepository) throws Exception  {
        var jobLauncher = new TaskExecutorJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
        jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
        jobLauncher.afterPropertiesSet();
        return jobLauncher;
    }

}
