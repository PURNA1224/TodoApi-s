package com.talentXp.todoApplication.securityConfig;


import java.io.IOException;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.talentXp.todoApplication.constants.SecurityConstants;
import com.talentXp.todoApplication.entity.UserEntity;
import com.talentXp.todoApplication.repository.UsersRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthorizationFilter extends BasicAuthenticationFilter{

	UsersRepository usersRepository;
	public AuthorizationFilter(AuthenticationManager authenticationManager, UsersRepository usersRepository) {
		super(authenticationManager);
		this.usersRepository = usersRepository;
	}
	
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String authorizationHeader = request.getHeader(SecurityConstants.HEADER_TOKEN_KEY);
		if(authorizationHeader == null || !authorizationHeader.startsWith(SecurityConstants.HEADER_TOKEN_PREFIX)) {
			chain.doFilter(request, response);
			return;
		}
		UsernamePasswordAuthenticationToken token = getAuthentication(authorizationHeader.substring(7).trim());
		SecurityContextHolder.getContext().setAuthentication(token);
		chain.doFilter(request, response);
	}
	
	private UsernamePasswordAuthenticationToken getAuthentication(String token) {
		String commonKey = SecurityConstants.getCommonKey();
		if(commonKey == null)
			return null;
		
		SecretKey key = Keys.hmacShaKeyFor(commonKey.getBytes());
		
		Claims claims = Jwts.parser()
							.verifyWith(key)
							.build()
							.parseSignedClaims(token)
							.getPayload();
		
		String email = claims.getSubject();
		UserEntity entity = usersRepository.findByEmail(email);
		
		if(entity == null ) return null;
		
		if(email != null) {
			UserPrincipal principal = new UserPrincipal(entity);
			return new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
		}
		return null;
	}

}
