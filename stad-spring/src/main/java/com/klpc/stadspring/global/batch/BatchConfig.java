package com.klpc.stadspring.global.batch;

import com.klpc.stadspring.domain.advert.repository.AdvertRepository;
import com.klpc.stadspring.domain.advertVideo.repository.AdvertVideoRepository;
import com.klpc.stadspring.domain.log.entity.AdvertStatistics;
import com.klpc.stadspring.domain.log.repository.AdvertClickLogRepository;
import com.klpc.stadspring.domain.log.repository.AdvertVideoLogRepository;
import com.klpc.stadspring.domain.log.repository.OrderLogRepository;
import com.klpc.stadspring.domain.log.repository.OrderReturnLogRepository;
import com.klpc.stadspring.global.response.ErrorCode;
import com.klpc.stadspring.global.response.exception.CustomException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Configuration
@RequiredArgsConstructor
public class BatchConfig {

    private final AdvertClickLogRepository advertClickLogRepository;
    private final AdvertVideoLogRepository advertVideoLogRepository;
    private final OrderLogRepository orderLogRepository;
    private final AdvertRepository advertRepository;
    private static ArrayList<Long> advertIdList;

    @PersistenceContext
    private final EntityManager entityManager;

    @Bean
    public Job LogCountJob(JobRepository jobRepository,
                           Step LogSave
    ) {
        return new JobBuilder("LogCountJob", jobRepository)
                .start(LogSave)
                .build();
    }
    @Bean
    public Step LogSave(JobRepository jobRepository,
                                 Tasklet advertClickCountTasklet,
                                 Tasklet advertVideoCountTasklet,
                                 Tasklet orderCountTasklet,
                                 PlatformTransactionManager platformTransactionManager){
        return new StepBuilder("advertClickCountStep", jobRepository)
                .tasklet(advertClickCountTasklet, platformTransactionManager)
                .tasklet(advertVideoCountTasklet, platformTransactionManager)
                .tasklet(orderCountTasklet, platformTransactionManager)
                .build();
    }

    @Bean
    public Tasklet advertClickCountTasklet(){
        return ((contribution, chunkContext) -> {
            advertIdList = new ArrayList<>(advertRepository.findAllAdvertIds());

            List<AdvertStatistics> logList = new ArrayList<>();

            for (Long advertId : advertIdList) {
                Long clickCount = advertClickLogRepository.countAddClickLogByAdvertId(advertId)
                        .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

                Long videoCount = advertVideoLogRepository.countAdvertVideoLog(advertId)
                        .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

                Long orderCount = orderLogRepository.countOrderLog(advertId)
                        .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

                Long orderCancelCount = orderLogRepository.countOrderCancelLog(advertId)
                        .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));


                logList.add(AdvertStatistics.createNewAdvertStatistics(
                        advertId,
                        clickCount,
                        videoCount,
                        orderCount,
                        orderCancelCount
                ));
            }

            for (AdvertStatistics statistics : logList) {
                entityManager.persist(statistics);
            }
            entityManager.flush();
            entityManager.clear();

            return RepeatStatus.FINISHED;
        });
    }
}
