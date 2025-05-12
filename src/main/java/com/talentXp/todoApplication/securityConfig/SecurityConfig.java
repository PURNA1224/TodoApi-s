package com.talentXp.todoApplication.securityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.talentXp.todoApplication.repository.UsersRepository;
import com.talentXp.todoApplication.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Bean
	protected SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
		
		AuthenticationManagerBuilder authenticationManagerBuilder = security.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
		AuthenticationManager authenticationManager = authenticationManagerBuilder.build();
		
		return security
			.csrf(request -> request.disable())
			.authorizeHttpRequests(request -> 
				request
					.requestMatchers("/user/**").permitAll()
					.requestMatchers("/h2-console/**").permitAll()
					.requestMatchers("/todo/**").permitAll()
					.requestMatchers("/home").permitAll()
					.anyRequest().authenticated())
			.addFilter(new AuthenticationFilter(authenticationManager))
			.authenticationManager(authenticationManager)
			.addFilter(new AuthorizationFilter(authenticationManager, usersRepository))
			.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.headers((header)->header.frameOptions((frameOptions)->frameOptions.sameOrigin()))
		    .build();	
	}
}
