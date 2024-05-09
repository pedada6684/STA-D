package com.klpc.stadstream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class StadStreamApplication {

	public static void main(String[] args) {
		SpringApplication.run(StadStreamApplication.class, args);
	}

}
