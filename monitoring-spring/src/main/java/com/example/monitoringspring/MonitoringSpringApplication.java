package com.example.monitoringspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class MonitoringSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonitoringSpringApplication.class, args);
	}

}
