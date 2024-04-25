package com.klpc.stadspring.global.batch.config;

import com.klpc.stadspring.domain.log.entity.AdvertClickLog;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDateTime;
import java.util.Collections;

@Configuration
public class BatchConfig {

    @Bean
    public Job exampleJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("exampleJob", jobRepository)
                .start(exampleStep(jobRepository, transactionManager))
                .build();
    }

    @Bean
    public Step exampleStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("exampleStep", jobRepository)
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
                        System.out.println("Step executed at: " + LocalDateTime.now());
                        return RepeatStatus.FINISHED;
                    }
                }, transactionManager)
                .build();
    }

//    @Bean
//    public Step advertClickCountStep() {
//        return stepBuilderFactory.get("advertClickCountStep")
//                .<AdvertClickLog, AdvertClickStats>chunk(10)  // 처리할 항목 수 설정
//                .reader(advertClickLogReader())
//                .processor(advertClickLogProcessor())
//                .writer(advertClickLogWriter())
//                .build();
//    }
//
//    @Bean
//    public ItemReader<AdvertClickLog> advertClickLogReader() {
//        return new RepositoryItemReaderBuilder<AdvertClickLog>()
//                .name("advertClickLogReader")
//                .repository(advertClickLogRepository)
//                .methodName("findAll")  // JpaRepository의 findAll 사용
//                .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
//                .build();
//    }
//
//    @Bean
//    public ItemProcessor<AdvertClickLog, AdvertClickStats> advertClickLogProcessor() {
//        return advertClickLog -> new AdvertClickStats(advertClickLog.getAdvertId(), 1);  // 클릭 수를 집계
//    }
//
//    @Bean
//    public ItemWriter<AdvertClickStats> advertClickLogWriter() {
//        return items -> items.forEach(item -> System.out.println("Processed: " + item));  // 콘솔에 결과 출력
//    }
}
