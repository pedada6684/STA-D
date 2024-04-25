package com.klpc.stadspring.global.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job updateStatsJob;

    @Scheduled(fixedDelay = 200000)
    /**
     * 1시간마다 실행
     */
//    @Scheduled(cron = "0 0 0/1 * * *")
    public void runJob() throws JobExecutionException {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis(), true)
                .toJobParameters();
        jobLauncher.run(updateStatsJob, jobParameters);
    }
}
