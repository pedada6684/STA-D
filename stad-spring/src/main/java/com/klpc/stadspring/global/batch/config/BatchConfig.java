package com.klpc.stadspring.global.batch.config;

import com.klpc.stadspring.domain.advert.repository.AdvertRepository;
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

    @Bean
    public Job LogCountJob(JobRepository jobRepository,
                           Step AdvertClickCount,
                           Step simpleStep2
    ) {
        return new JobBuilder("LogCountJob", jobRepository)
                .start(AdvertClickCount)
                .next(simpleStep2)
                .build();
    }
    @Bean
    public Step AdvertClickCount(JobRepository jobRepository, Tasklet advertClickCountTasklet, PlatformTransactionManager platformTransactionManager){
        return new StepBuilder("advertClickCountStep", jobRepository)
                .tasklet(advertClickCountTasklet, platformTransactionManager)
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
            log.info("list: "+clickCountList);

            // TODO: 2023/06/12 이곳에 movieNameMap을 파일/디비로 저장하는 로직이 필요하다.
            //  현재는 로깅하도록 하자

//            for (String s : names.keySet()) {
//                log.info("영화 이름 :" + s + ", 호출 횟수 :" + names.get(s));
//            }

//            AnnotationBasedAOP.map.clear();

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
