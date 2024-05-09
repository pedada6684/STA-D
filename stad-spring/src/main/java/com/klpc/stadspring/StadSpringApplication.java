package com.klpc.stadspring;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableBatchProcessing // 배치 기능 활성화
@EnableScheduling
@EnableCaching
public class StadSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(StadSpringApplication.class, args);
	}

}
