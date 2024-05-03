package com.klpc.stadspring.global.batch;

import com.klpc.stadspring.domain.advert.repository.AdvertRepository;
import com.klpc.stadspring.domain.log.entity.AdvertStatistics;
import com.klpc.stadspring.domain.log.repository.AdvertClickLogRepository;
import com.klpc.stadspring.domain.log.repository.AdvertVideoLogRepository;
import com.klpc.stadspring.domain.log.repository.OrderLogRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
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

            LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);

            for (Long advertId : advertIdList) {
                Long clickCount = advertClickLogRepository.countAddClickLogByAdvertId(advertId,oneHourAgo)
                        .orElse(0L);

                Long videoCount = advertVideoLogRepository.countAdvertVideoLog(advertId,oneHourAgo)
                        .orElse(0L);

                Long orderCount = orderLogRepository.countOrderLog(advertId,oneHourAgo)
                        .orElse(0L);

                Long orderCancelCount = orderLogRepository.countOrderCancelLog(advertId,oneHourAgo)
                        .orElse(0L);

                Long revenue = orderLogRepository.sumClicksByAdvertIdAndDateRange(advertId,oneHourAgo)
                        .orElse(0L);



                logList.add(AdvertStatistics.createNewAdvertStatistics(
                        advertId,
                        videoCount,
                        clickCount,
                        orderCount,
                        orderCancelCount,
                        revenue
                ));
            }

            for (AdvertStatistics statistics : logList) {
                log.info("statistics: " +statistics);
                updateAdvertStatistic(statistics);
            }
            entityManager.flush();
            entityManager.clear();

            return RepeatStatus.FINISHED;
        });
    }
    @Transactional
    public void updateAdvertStatistic(AdvertStatistics advertStatistics) {
        AdvertStatistics existing = entityManager.createQuery(
                        "SELECT a FROM AdvertStatistics a WHERE a.advertId = :advertId AND a.date = :date", AdvertStatistics.class)
                .setParameter("advertId", advertStatistics.getAdvertId())
                .setParameter("date", LocalDate.now())
                .getResultStream().findFirst().orElse(null);

        log.info("existing: " + existing);

        if (existing != null) {
            // 기존 데이터 업데이트
            existing.updateCounts(advertStatistics.getAdvertClickCount(), advertStatistics.getAdvertVideoCount(),
                    advertStatistics.getOrderCount(), advertStatistics.getOrderCancelCount(), advertStatistics.getRevenue());
            entityManager.merge(existing);
        } else {
            // 새 데이터 삽입
            entityManager.persist(advertStatistics);
        }
    }


}
