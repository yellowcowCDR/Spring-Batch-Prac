package com.example.batchprac;

import com.example.batchprac.entity.People;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Collection;

@RequiredArgsConstructor
@Configuration
public class JpaItemReaderWriterJobConfiguration {
    private final EntityManagerFactory entityManagerFactory;
    private final PlatformTransactionManager transactionManager;
    private final JobRepository jobRepository;

    private static final int chunkSize = 3;

    @Bean
    public FlatFileItemReader<People> reader() {
        return new FlatFileItemReaderBuilder<People>()
                .name("personItemReader")
                .resource(new ClassPathResource("sample-data.csv"))
                .delimited()
                .names(new String[]{"firstName", "lastName"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<People>() {{
                    setTargetType(People.class);
                }})
                .build();
    }

    @Bean
    public PeopleItemProcessor processor() {
        return new PeopleItemProcessor();
    }

//    @Bean
//    public JdbcBatchItemWriter<Person> writer(DataSource dataSource) {
//        return new JdbcBatchItemWriterBuilder<Person>()
//                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
//                .sql("INSERT INTO people (first_name, last_name) VALUES (:firstName, :lastName)")
//                .dataSource(dataSource)
//                .build();
//    }

    @Bean
    public Job jpaItemWriterJob() {
        return new JobBuilder("jpaItemWriterJob", jobRepository)
                .start(jpaItemWriterStep())
                .build();
    }

    @Bean
    public Step jpaItemWriterStep() {
        return new StepBuilder("jpaItemWriterStep", jobRepository)
                .<People, People>chunk(chunkSize, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(jpaItemWriter())
                .build();
    }

    @Bean
    public JpaItemWriter<People> jpaItemWriter() {
        JpaItemWriter<People> jpaItemWriter = new JpaItemWriter<>();
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
        return jpaItemWriter;
    }
}
