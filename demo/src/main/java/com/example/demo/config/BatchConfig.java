package com.example.demo.config;

import com.example.demo.model.RouteAnalytics;
import jakarta.persistence.EntityManagerFactory;
import lombok.Data;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Configuration
public class BatchConfig {

    @Bean
    public ItemReader<RouteAnalytics> reader(MongoTemplate mongoTemplate) {
        return new ItemReader<RouteAnalytics>() {
            private List<RouteAnalytics> data;
            private int index = 0;

            @Override
            public RouteAnalytics read() {
                if (data == null) {
                    Aggregation aggregation = newAggregation(
                            group("parcelId")
                                    .avg("speed").as("avgSpeed")
                                    .count().as("totalRecords"));

                    AggregationResults<RouteDoc> results = mongoTemplate.aggregate(
                            aggregation, "route_logs", RouteDoc.class);

                    data = results.getMappedResults().stream()
                            .map(doc -> new RouteAnalytics(doc.get_id(), doc.getAvgSpeed(), doc.getTotalRecords(),
                                    LocalDateTime.now()))
                            .collect(Collectors.toList());
                }

                if (index < data.size()) {
                    return data.get(index++);
                }
                return null;
            }
        };
    }

    @Bean
    public JpaItemWriter<RouteAnalytics> writer(EntityManagerFactory entityManagerFactory) {
        return new JpaItemWriterBuilder<RouteAnalytics>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager,
            ItemReader<RouteAnalytics> reader, JpaItemWriter<RouteAnalytics> writer) {
        return new StepBuilder("step1", jobRepository)
                .<RouteAnalytics, RouteAnalytics>chunk(100, transactionManager)
                .reader(reader)
                .writer(writer)
                .build();
    }

    @Bean
    public Job routeJob(JobRepository jobRepository, Step step1) {
        return new JobBuilder("routeJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(step1)
                .end()
                .build();
    }

    @Data
    public static class RouteDoc {
        private String _id;
        private double avgSpeed;
        private long totalRecords;
    }
}
