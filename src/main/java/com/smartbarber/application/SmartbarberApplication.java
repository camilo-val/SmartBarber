package com.smartbarber.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.smartbarber")
@EnableScheduling
@EnableR2dbcRepositories(
        basePackages = "com.smartbarber.infrastructure.drivenadapter.postgres.data"
)
public class SmartbarberApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartbarberApplication.class, args);
	}

}
