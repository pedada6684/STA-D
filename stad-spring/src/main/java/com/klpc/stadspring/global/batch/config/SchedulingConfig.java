package com.klpc.stadspring.global.batch.config;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class SchedulingConfig {
<<<<<<< HEAD
//
//    private final JobLauncher jobLauncher;
//    private final Job logCountJob; // 로그 카운트 Job
//
//    // 30초마다 작업 실행
//    @Scheduled(fixedDelay = 300000)
//    public void runBatchJob() {
//        try {
//            JobParameters params = new JobParametersBuilder()
//                    .addLong("time", System.currentTimeMillis())
//                    .toJobParameters();
//            jobLauncher.run(logCountJob, params);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
=======

    private final JobLauncher jobLauncher;
    private final Job logCountJob; // 로그 카운트 Job

    // 30초마다 작업 실행
    @Scheduled(fixedDelay = 300000)
    public void runBatchJob() {
        try {
            JobParameters params = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(logCountJob, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
>>>>>>> 390e18d (feat: Log 집계 테이블 entity 추가)
}
