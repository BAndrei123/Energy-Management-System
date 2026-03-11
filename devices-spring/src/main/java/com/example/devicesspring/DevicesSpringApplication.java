package com.example.devicesspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class DevicesSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(DevicesSpringApplication.class, args);
	}

}
