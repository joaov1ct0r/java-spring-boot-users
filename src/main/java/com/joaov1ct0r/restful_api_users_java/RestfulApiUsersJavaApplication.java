package com.joaov1ct0r.restful_api_users_java;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "REST API Usuários",
				description = "API REST crud de usuários com Java utilizando Spring Boot",
				version = "1"
		)
)
public class RestfulApiUsersJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestfulApiUsersJavaApplication.class, args);
	}

}
