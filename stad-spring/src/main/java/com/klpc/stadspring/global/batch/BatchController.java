package com.klpc.stadspring.global.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BatchController {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job; // 이는 구성해야 할 배치 작업을 참조합니다.

    @PostMapping("/start-batch")
    public ResponseEntity<?> startBatchJob() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(job, jobParameters);
            return ResponseEntity.ok().body("{\"status\":\"Batch job has been started.\"}");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("{\"error\":\"Failed to start batch job.\"}");
        }
    }
}
