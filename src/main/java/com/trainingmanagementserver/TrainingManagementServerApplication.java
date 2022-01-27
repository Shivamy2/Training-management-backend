package com.trainingmanagementserver;

import com.trainingmanagementserver.entity.Role;
import com.trainingmanagementserver.entity.TraineesTrainer;
import com.trainingmanagementserver.entity.UserCredentialsEntity;
import com.trainingmanagementserver.entity.UserDetailsEntity;
import com.trainingmanagementserver.repository.TraineesTrainerRepository;
import com.trainingmanagementserver.service.UserCredentialsService;
import com.trainingmanagementserver.service.UserDetailService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@RestController
public class TrainingManagementServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrainingManagementServerApplication.class, args);
	}

	@GetMapping("/")
	public String check() {
		return "Hello. I am running!";
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

	// @Bean
	// public CommandLineRunner run(UserCredentialsService userCredentialsService,
	// UserDetailService userDetailService,
	// TraineesTrainerRepository traineesTrainerRepository) {
	// return args -> {
	// userCredentialsService.deleteUser(1);
	// userCredentialsService.roleSave(new Role("ROLE_TRAINER"));
	// userCredentialsService.roleSave(new Role("ROLE_TRAINEE"));
	// userCredentialsService.userCredentialsSave(new UserCredentialsEntity
	// ("anonymous","secret", "anonymous@gmail.com", new ArrayList<>())
	// );
	// userCredentialsService.userCredentialsSave(new UserCredentialsEntity
	// ("testuser2","secret", "testuser2@gmail.com", new ArrayList<>())
	// );
	// userCredentialsService.userCredentialsSave(new UserCredentialsEntity
	// ("testuser3","secret", "testuser3@gmail.com", new ArrayList<>())
	// );
	// userCredentialsService.userCredentialsSave(new UserCredentialsEntity
	// ("testuser4","secret", "testuser4@gmail.com", new ArrayList<>())
	// );
	// userCredentialsService.userCredentialsSave(new UserCredentialsEntity
	// ("testuser5","secret", "testuser5@gmail.com", new ArrayList<>())
	// );
	// userCredentialsService.userCredentialsSave(new UserCredentialsEntity
	// ("testuser6","secret", "testuser6@gmail.com", new ArrayList<>())
	// );
	// userCredentialsService.userCredentialsSave(new UserCredentialsEntity
	// ("testuser7","secret", "testuser7@gmail.com", new ArrayList<>())
	// );
	// userCredentialsService.userCredentialsSave(new UserCredentialsEntity
	// ("testuser8","secret", "testuser8@gmail.com", new ArrayList<>())
	// );
	//
	// userCredentialsService.addRoleToUser("ROLE_TRAINER", "anonymous");
	// userCredentialsService.addRoleToUser("ROLE_TRAINEE", "testuser8");
	// userCredentialsService.addRoleToUser("ROLE_TRAINEE", "testuser2");
	// userCredentialsService.addRoleToUser("ROLE_TRAINEE", "testuser3");
	// userCredentialsService.addRoleToUser("ROLE_TRAINER", "testuser4");
	// userCredentialsService.addRoleToUser("ROLE_TRAINEE", "testuser5");
	// userCredentialsService.addRoleToUser("ROLE_TRAINER", "testuser6");
	// userDetailService.save(new UserDetailsEntity(3, "Shivam", "Yadav", "02",
	// "08", "2000", "H-292 Hindalco Colony", "Renukoot", "Sonbhadra", 231217,
	// "7007723257", "MALE"));
	// userDetailService.save(new UserDetailsEntity(4, "Test", "User 1", "23", "12",
	// "2001", "HH-4 Hindalco Colony", "Renukoot", "Sonbhadra", 231217, "889723257",
	// "FEMALE"));
	// userDetailService.save(new UserDetailsEntity(5, "Test", "User 2", "21", "11",
	// "2002", "L-232 Hindalco Colony", "Renukoot", "Sonbhadra", 231217,
	// "6547723257", "FEMALE"));
	// traineesTrainerRepository.save(new TraineesTrainer(10, 3));
	// traineesTrainerRepository.save(new TraineesTrainer(4, 3));
	// traineesTrainerRepository.save(new TraineesTrainer(5, 6));
	// traineesTrainerRepository.save(new TraineesTrainer(7, 8));
	// };
	// }
}
