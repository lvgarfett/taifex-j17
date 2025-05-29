package com.oneonetrade.fx.config;

import com.oneonetrade.fx.batch.FxProcessor;
import com.oneonetrade.fx.batch.FxReader;
import com.oneonetrade.fx.batch.FxWriter;
import com.oneonetrade.fx.entity.CollectionEntity;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

@Configuration
public class FxBatchConfig {

    @Bean
    public Job fxSearchEngineJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("fxSearchEngineJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(chunkStep(jobRepository, transactionManager))
                .build();
    }

    @Bean
    public Step chunkStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("chunkStep", jobRepository)
                .<CollectionEntity, CollectionEntity>chunk(1, transactionManager)
                .reader(fxReader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    @StepScope
    public ItemWriter<CollectionEntity> writer() {
        return new FxWriter();
    }

    @Bean
    @StepScope
    public ItemProcessor<CollectionEntity, CollectionEntity> processor() {
        CompositeItemProcessor<CollectionEntity, CollectionEntity> processor = new CompositeItemProcessor<>();
        processor.setDelegates(List.of(new FxProcessor()));
        return processor;
    }

    @Bean
    @StepScope
    public ItemReader<CollectionEntity> fxReader() {
        return new FxReader();
    }
    @Bean
    @StepScope
    public FlatFileItemReader<CollectionEntity> reader() {
        return new FlatFileItemReaderBuilder<CollectionEntity>()
                .name("fxReader")
                .resource(new ClassPathResource("fx_data.csv"))
                .delimited()
                .names(new String[] {"date", "ccy", "rate"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(CollectionEntity.class);
                }})
                .build();
    }

}
