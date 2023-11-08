package com.UserModel.UserModel;

import com.UserModel.UserModel.Roles.Roles;
import com.UserModel.UserModel.Roles.RolesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class UserModelApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserModelApplication.class, args);
	}

}
