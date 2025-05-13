package com.talentXp.todoApplication.singletonClasses;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.talentXp.todoApplication.SpringApplicationContext;

@Configuration
public class SingletonClasses {
	
	@Bean
	ModelMapper getModelMapper() {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return mapper;
	}
	
	@Bean
	BCryptPasswordEncoder getBcrypt() {
		return new BCryptPasswordEncoder(12);
	}
	
	@Bean
	SpringApplicationContext getApplicationContext() {
		return new SpringApplicationContext();
	}
}
