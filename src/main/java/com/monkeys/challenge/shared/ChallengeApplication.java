package com.monkeys.challenge.shared;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Main class of the application.
 * @author  Santi
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.monkeys.challenge")
public class ChallengeApplication {

	//* Main method of the application.
	public static void main(String[] args) {
		SpringApplication.run(ChallengeApplication.class, args);
	}

}
