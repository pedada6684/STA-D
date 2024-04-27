package com.klpc.stadspring.global.batch.config;

import com.klpc.stadspring.domain.advert.repository.AdvertRepository;
import com.klpc.stadspring.domain.advertVideo.repository.AdvertVideoRepository;
import com.klpc.stadspring.domain.log.entity.AdvertClickLog;
import com.klpc.stadspring.domain.log.entity.AdvertStatistics;
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
import org.springframework.batch.item.database.JpaPagingItemReader;
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
    private static ArrayList<Long> advertIdList;
//    private static AdvertStatistics advertStatistics = new AdvertStatistics();

//    @Bean
//    public Job LogCountJob(JobRepository jobRepository,
//                           Step advertClickCount,
//                           Step advertVideoCount,
//                           Step orderCount,
//                           Step orderReturnCount
//    ) {
//        return new JobBuilder("LogCountJob", jobRepository)
//                .start(advertClickCount)
//                .next(advertVideoCount)
//                .next(orderCount)
//                .next(orderReturnCount)
//                .build();
//    }
//    @Bean
//    public Step advertClickCount(JobRepository jobRepository, Tasklet advertClickCountTasklet, PlatformTransactionManager platformTransactionManager){
//        return new StepBuilder("advertClickCountStep", jobRepository)
//                .tasklet(advertClickCountTasklet, platformTransactionManager)
//                .build();
//    }
//
//    @Bean
//    public Step advertVideoCount(JobRepository jobRepository, Tasklet advertVideoCountTasklet, PlatformTransactionManager platformTransactionManager){
//        return new StepBuilder("advertVideoCountStep", jobRepository)
//                .tasklet(advertVideoCountTasklet, platformTransactionManager)
//                .build();
//    }
//
//    @Bean
//    public Step orderCount(JobRepository jobRepository, Tasklet orderCountTasklet, PlatformTransactionManager platformTransactionManager){
//        return new StepBuilder("orderCountStep", jobRepository)
//                .tasklet(orderCountTasklet, platformTransactionManager)
//                .build();
//    }
//
//    @Bean
//    public Step orderReturnCount(JobRepository jobRepository, Tasklet orderReturnCountTasklet, PlatformTransactionManager platformTransactionManager){
//        return new StepBuilder("orderReturnCountStep", jobRepository)
//                .tasklet(orderReturnCountTasklet, platformTransactionManager)
//                .build();
//    }
//    @Bean
//    public Tasklet advertClickCountTasklet(){
//        return ((contribution, chunkContext) -> {
//            advertIdList = new ArrayList<>(advertRepository.findAllAdvertIds());
//            HashMap<Long, Long> clickCountList = new HashMap<>();
//
//            for (Long advertId : advertIdList) {
//                Long clickCount = advertClickLogRepository.countAddClickLogByAdvertId(advertId)
//                        .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
//
//                clickCountList.put(advertId, clickCount);
//            }
//            log.info("clickCountList: "+clickCountList);
//
//            /**
//             * db 에 저장하는 로직 필요
//             */
//
//            return RepeatStatus.FINISHED;
//        });
//    }
//
//    @Bean
//    public Tasklet advertVideoCountTasklet(){
//        return ((contribution, chunkContext) -> {
//            HashMap<Long, Long> advertVideoCountList = new HashMap<>();
//
//            for (Long advertId : advertIdList) {
//                Long videoCount = advertVideoLogRepository.countAdvertVideoLog(advertId)
//                        .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
//
//                advertVideoCountList.put(advertId, videoCount);
//            }
//            log.info("advertVideoCountList: "+advertVideoCountList);
//
//            /**
//             * db 에 저장하는 로직 필요
//             */
//
//            return RepeatStatus.FINISHED;
//        });
//    }
//
//    @Bean
//    public Tasklet orderCountTasklet(){
//        return ((contribution, chunkContext) -> {
//            ArrayList<Long> advertIdList = new ArrayList<>(advertRepository.findAllAdvertIds());
//            HashMap<Long, Long> orderCountList = new HashMap<>();
//            HashMap<Long, Long> orderCancelCountList = new HashMap<>();
//
//            for (Long advertId : advertIdList) {
//                Long orderCount = orderLogRepository.countOrderLog(advertId)
//                        .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
//
//                Long orderCancelCount = orderLogRepository.countOrderCancelLog(advertId)
//                        .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
//
//                orderCountList.put(advertId, orderCount);
//                orderCancelCountList.put(advertId, orderCancelCount);
//            }
//            log.info("orderCountList: "+orderCountList);
//            log.info("orderCancelCountList: "+orderCancelCountList);
//
//            /**
//             * db 에 저장하는 로직 필요
//             */
//
//            return RepeatStatus.FINISHED;
//        });

//
//        return ((contribution, chunkContext) -> {
//            // 광고 ID와 해당 광고에 대한 주문 수와 취소된 주문 수를 담을 맵 생성
//            Map<Long, Map<String, Long>> orderInfoMap = new HashMap<>();
//
//            // 모든 광고 ID에 대한 주문 수와 취소된 주문 수를 한 번의 쿼리로 가져옴
//            List<Object[]> orderInfoList = orderLogRepository.findOrderAndCancelCountByAdvertIds();
//
//            // 결과를 orderInfoMap에 저장
//            for (Object[] row : orderInfoList) {
//                Long advertId = (Long) row[0];
//                Long orderCount = (Long) row[1];
//                Long cancelCount = (Long) row[2];
//
//                Map<String, Long> orderCounts = new HashMap<>();
//                orderCounts.put("orderCount", orderCount);
//                orderCounts.put("cancelCount", cancelCount);
//
//                orderInfoMap.put(advertId, orderCounts);
//            }
//
//            log.info("orderInfoMap: " + orderInfoMap);
//
//            /**
//             * db에 저장하는 로직 필요
//             */
//
//            return RepeatStatus.FINISHED;
//    }
}
