package com.talentXp.todoApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * The @SpringBootApplication annotation is a convenience annotation that combines three core annotations:
 * 1. @EnableAutoConfiguration: Enables Spring Boot's auto-configuration feature, automatically configuring beans based on classpath settings.
 * 2. @ComponentScan: Tells Spring to scan for components, configurations, and services in the current package and its sub-packages.
 * 3. @Configuration: Marks the class as a source of bean definitions for the application context.
 * It is typically used on the main class to bootstrap a Spring Boot application.
*/
@SpringBootApplication
public class TodoApplication {

	/*
	 * The main method serves as the entry point for the Spring Boot application.
	 * It uses SpringApplication.run() to launch the application, 
	 * initializing the Spring context and starting the embedded web server (e.g., Tomcat).
	 * TodoApplication.class is passed as an argument, specifying the configuration class.
	 */
	public static void main(String[] args) {
		SpringApplication.run(TodoApplication.class, args);
	}

}
