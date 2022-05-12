package com.games.dicegame;

import com.games.dicegame.model.domain.AppUser;
import com.games.dicegame.model.domain.Role;
import com.games.dicegame.model.service.UserService;
import com.games.dicegame.model.util.AppUserInfo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Calendar;
import java.util.Date;

@SpringBootApplication
public class DiceGameApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiceGameApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}



	/*@Bean
	CommandLineRunner runner(UserService userService){


		return args -> {

			userService.saveRole(new Role(null, "ROLE_USER"));
			userService.saveRole(new Role(null, "ROLE_ADMIN"));

			userService.saveUser((new AppUserInfo("luis@gmail.com", "123456","Luis")));

			userService.addRoleToUser("luis@gmail.com", "ROLE_USER");
			userService.addRoleToUser("luis@gmail.com", "ROLE_ADMIN");
		};
	}*/

}
