package com.talentXp.todoApplication.securityConfig;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.talentXp.todoApplication.SpringApplicationContext;
import com.talentXp.todoApplication.Pojo.LoginRequestModel;
import com.talentXp.todoApplication.Pojo.UserDto;
import com.talentXp.todoApplication.constants.SecurityConstants;
import com.talentXp.todoApplication.serviceImpl.UserServiceImple;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	
	public AuthenticationFilter(AuthenticationManager manager) {
		super(manager);
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException{
		
		try {
			LoginRequestModel creds = new ObjectMapper().readValue(request.getInputStream(), LoginRequestModel.class);
			return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		String userEmail = ((UserPrincipal)authResult.getPrincipal()).getUsername();
		UserDto userDto = ((UserServiceImple)SpringApplicationContext.getBean("userServiceImple")).getUserDetailsByEmail(userEmail);
		
		String commonKey = SecurityConstants.getCommonKey();
	
		SecretKey secretKey = Keys.hmacShaKeyFor(commonKey.getBytes());
		
		String token = Jwts.builder()
			.claim("scope", authResult.getAuthorities())
			.subject(userDto.getEmail())
			.expiration(Date.from(Instant.now().plusMillis(Long.parseLong(SecurityConstants.getExprirationTime()))))
			.issuedAt(Date.from(Instant.now()))
			.signWith(secretKey)
			.compact();

		response.addHeader(SecurityConstants.HEADER_TOKEN_KEY, SecurityConstants.HEADER_TOKEN_PREFIX + token);
		response.addHeader(SecurityConstants.HEADER_USERID, userDto.getUserId());
		
	}


}
