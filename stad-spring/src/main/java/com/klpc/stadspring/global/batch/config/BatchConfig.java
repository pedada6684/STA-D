package com.klpc.stadspring.global.batch.config;

import com.klpc.stadspring.domain.advert.repository.AdvertRepository;
import com.klpc.stadspring.domain.advertVideo.repository.AdvertVideoRepository;
import com.klpc.stadspring.domain.log.entity.AdvertClickLog;
import com.klpc.stadspring.domain.log.entity.AdvertVideoLog;
import com.klpc.stadspring.domain.log.repository.AdvertClickLogRepository;
import com.klpc.stadspring.domain.log.repository.AdvertVideoLogRepository;
import com.klpc.stadspring.domain.log.repository.OrderLogRepository;
import com.klpc.stadspring.domain.log.repository.OrderReturnLogRepository;
import com.klpc.stadspring.global.response.ErrorCode;
import com.klpc.stadspring.global.response.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

import java.util.ArrayList;
import java.util.HashMap;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class BatchConfig {

    private final AdvertClickLogRepository advertClickLogRepository;
    private final AdvertVideoLogRepository advertVideoLogRepository;
    private final OrderLogRepository orderLogRepository;
    private final OrderReturnLogRepository orderReturnLogRepository;
    private final AdvertRepository advertRepository;
    private final AdvertVideoRepository advertVideoRepository;

    @Bean
    public Job LogCountJob(JobRepository jobRepository,
                           Step advertClickCount,
                           Step advertVideoCount,
                           Step orderCount,
                           Step orderReturnCount
    ) {
        return new JobBuilder("LogCountJob", jobRepository)
                .start(advertClickCount)
                .next(advertVideoCount)
                .next(orderCount)
                .next(orderReturnCount)
                .build();
    }
    @Bean
    public Step advertClickCount(JobRepository jobRepository, Tasklet advertClickCountTasklet, PlatformTransactionManager platformTransactionManager){
        return new StepBuilder("advertClickCountStep", jobRepository)
                .tasklet(advertClickCountTasklet, platformTransactionManager)
                .build();
    }

    @Bean
    public Step advertVideoCount(JobRepository jobRepository, Tasklet advertVideoCountTasklet, PlatformTransactionManager platformTransactionManager){
        return new StepBuilder("advertVideoCountStep", jobRepository)
                .tasklet(advertVideoCountTasklet, platformTransactionManager)
                .build();
    }

    @Bean
    public Step orderCount(JobRepository jobRepository, Tasklet orderCountTasklet, PlatformTransactionManager platformTransactionManager){
        return new StepBuilder("orderCountStep", jobRepository)
                .tasklet(orderCountTasklet, platformTransactionManager)
                .build();
    }

    @Bean
    public Step orderReturnCount(JobRepository jobRepository, Tasklet orderReturnCountTasklet, PlatformTransactionManager platformTransactionManager){
        return new StepBuilder("orderReturnCountStep", jobRepository)
                .tasklet(orderReturnCountTasklet, platformTransactionManager)
                .build();
    }
    @Bean
    public Tasklet advertClickCountTasklet(){
        return ((contribution, chunkContext) -> {
            ArrayList<Long> advertIdList = new ArrayList<>(advertRepository.findAllAdvertIds());
            HashMap<Long, Long> clickCountList = new HashMap<>();

            for (Long advertId : advertIdList) {
                Long clickCount = advertClickLogRepository.countAddClickLogByAdvertId(advertId)
                        .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

                clickCountList.put(advertId, clickCount);
            }
            log.info("clickCountList: "+clickCountList);

            /**
             * db 에 저장하는 로직 필요
             */

            return RepeatStatus.FINISHED;
        });
    }

    @Bean
    public Tasklet advertVideoCountTasklet(){
        return ((contribution, chunkContext) -> {
            ArrayList<Long> advertIdList = new ArrayList<>(advertRepository.findAllAdvertIds());
            HashMap<Long, Long> advertVideoCountList = new HashMap<>();

            for (Long advertId : advertIdList) {
                Long videoCount = advertVideoLogRepository.countAdvertVideoLog(advertId)
                        .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

                advertVideoCountList.put(advertId, videoCount);
            }
            log.info("advertVideoCountList: "+advertVideoCountList);

            /**
             * db 에 저장하는 로직 필요
             */

            return RepeatStatus.FINISHED;
        });
    }

    @Bean
    public Tasklet orderCountTasklet(){
        return ((contribution, chunkContext) -> {
            ArrayList<Long> advertVideoIdList = new ArrayList<>(advertVideoRepository.findAllAdvertVideoIds());
            HashMap<Long, Long> orderCountList = new HashMap<>();

            for (Long advertVideoId : advertVideoIdList) {
                Long orderCount = orderLogRepository.countOrderLog(advertVideoId)
                        .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

                orderCountList.put(advertVideoId, orderCount);
            }
            log.info("orderCountList: "+orderCountList);

            /**
             * db 에 저장하는 로직 필요
             */

            return RepeatStatus.FINISHED;
        });
    }


    @Bean
    public Tasklet orderReturnCountTasklet(){
        return ((contribution, chunkContext) -> {
            ArrayList<Long> advertIdList = new ArrayList<>(advertRepository.findAllAdvertIds());
            HashMap<Long, Long> orderReturnCountList = new HashMap<>();

            for (Long advertId : advertIdList) {
                Long orderReturnCount = orderReturnLogRepository.countOrderReturnLog(advertId)
                        .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));

                orderReturnCountList.put(advertId, orderReturnCount);
            }
            log.info("orderReturnCountList: "+orderReturnCountList);

            /**
             * db 에 저장하는 로직 필요
             */


            return RepeatStatus.FINISHED;
        });
    }
//
//    @Bean
//    public Step simpleStep2(JobRepository jobRepository, Tasklet testTasklet, PlatformTransactionManager platformTransactionManager){
//        return new StepBuilder("simpleStep2", jobRepository)
//                .tasklet(testTasklet2(), platformTransactionManager).build();
//    }
//
//    @Bean
//    public Tasklet testTasklet2(){
//        return ((contribution, chunkContext) -> {
//
//            AnnotationBasedAOP.map.clear();
//            // 클리어 해주기
//            return RepeatStatus.FINISHED;
//        });
//    }

}
