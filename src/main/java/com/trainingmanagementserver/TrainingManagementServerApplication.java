package com.trainingmanagementserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class TrainingManagementServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrainingManagementServerApplication.class, args);
	}

	@GetMapping(path = "/")
	public static String hello() {
		return "Hello World!";
	}
}
