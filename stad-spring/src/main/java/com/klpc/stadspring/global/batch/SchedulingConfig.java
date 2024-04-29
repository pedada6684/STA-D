package com.klpc.stadspring.global.batch;

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

    private final JobLauncher jobLauncher;
    private final Job logCountJob; // 로그 카운트 Job

    // 매 시간마다 작업 실행
    @Scheduled(cron = "0 0 * * * ?")
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
}
