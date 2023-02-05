package com.monkeys.challenge.shared;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Main class of the application.
 * @author  Santi
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.monkeys.challenge")
@SecurityScheme(type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER, name="Bearer Token", bearerFormat = "JWT", scheme = "bearer")
@OpenAPIDefinition(info = @Info(
		title = "Monkeys Challenge API",
		version = "1.0",
		description = "Clean solution to the Agile Monkey CRM challenge",
		contact = @Contact(name = "Santiago García Monsalve", email = "santi.gamo@gmail.com")
), security = { @SecurityRequirement(name = "Bearer Token")})
public class ChallengeApplication {

	//* Main method of the application.
	public static void main(String[] args) {
		SpringApplication.run(ChallengeApplication.class, args);
	}

}
