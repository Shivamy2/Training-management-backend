package com.trainingmanagementserver;

import com.trainingmanagementserver.entity.Role;
import com.trainingmanagementserver.entity.UserCredentialsEntity;
import com.trainingmanagementserver.service.UserCredentialsService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@RestController
public class TrainingManagementServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrainingManagementServerApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*");
			}
		};
	}

	@Bean
	public CommandLineRunner run(UserCredentialsService userCredentialsService) {
		return args -> {
			userCredentialsService.roleSave(new Role("ROLE_TRAINER"));
			userCredentialsService.roleSave(new Role("ROLE_TRAINEE"));
			userCredentialsService.userCredentialsSave(new UserCredentialsEntity
					("testuser1","secret", "testuser1@gmail.com", new ArrayList<>())
			);
			userCredentialsService.userCredentialsSave(new UserCredentialsEntity
					("testuser2","secret", "testuser2@gmail.com", new ArrayList<>())
			);
			userCredentialsService.userCredentialsSave(new UserCredentialsEntity
					("testuser3","secret", "testuser3@gmail.com", new ArrayList<>())
			);
			userCredentialsService.userCredentialsSave(new UserCredentialsEntity
					("testuser4","secret", "testuser4@gmail.com", new ArrayList<>())
			);
			userCredentialsService.userCredentialsSave(new UserCredentialsEntity
					("testuser5","secret", "testuser5@gmail.com", new ArrayList<>())
			);
			userCredentialsService.userCredentialsSave(new UserCredentialsEntity
					("testuser6","secret", "testuser6@gmail.com", new ArrayList<>())
			);
			userCredentialsService.userCredentialsSave(new UserCredentialsEntity
					("testuser7","secret", "testuser7@gmail.com", new ArrayList<>())
			);
			userCredentialsService.addRoleToUser("ROLE_TRAINER", "testuser1");
			userCredentialsService.addRoleToUser("ROLE_TRAINEE", "testuser2");
			userCredentialsService.addRoleToUser("ROLE_TRAINEE", "testuser3");
			userCredentialsService.addRoleToUser("ROLE_TRAINER", "testuser4");
			userCredentialsService.addRoleToUser("ROLE_TRAINEE", "testuser4");
			userCredentialsService.addRoleToUser("ROLE_TRAINEE", "testuser5");
			userCredentialsService.addRoleToUser("ROLE_TRAINER", "testuser6");
		};
	}
}
