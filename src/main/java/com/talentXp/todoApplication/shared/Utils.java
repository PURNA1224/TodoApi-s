package com.talentXp.todoApplication.shared;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Date;
import java.util.Random;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.talentXp.todoApplication.constants.SecurityConstants;

import ch.qos.logback.core.subst.Token;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class Utils {
	
	String charactersSource = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890#."; 
	Random random = new SecureRandom();
	
	public String generateUserId(int length) {
		
		return generateUniqueId(length);
	}
	
	private String generateUniqueId(int length) {
		StringBuilder returnValue = new StringBuilder(); 
		for(int i = 0; i < length; i++) {
			returnValue.append(charactersSource.charAt(random.nextInt(charactersSource.length())));
		}
		return returnValue.toString();
	}
	
	public String regenerateToken(String userId) {
		
		String commonKey = SecurityConstants.getCommonKey();
		SecretKey secretKey = Keys.hmacShaKeyFor(commonKey.getBytes());
		return Jwts.builder()
						   .subject(userId)
						   .expiration(Date.from(Instant.now().plusMillis(Long.parseLong(SecurityConstants.getExprirationTime()))))
						   .issuedAt(Date.from(Instant.now()))
						   .signWith(secretKey)
						   .compact();
		
	}
	
	public boolean isTokenExpired(String token) {
		
		boolean returnValue = false;
		try {
			
			String commonKey = SecurityConstants.getCommonKey();
			
			if(commonKey == null) returnValue = true;
			
			SecretKey secretKey = Keys.hmacShaKeyFor(commonKey.getBytes());
			
			Claims claims = Jwts.parser()
								.verifyWith(secretKey)
								.build()
								.parseSignedClaims(token)
								.getPayload();
			
			Date expiration = claims.getExpiration();
			Date present = new Date();
			returnValue = expiration.before(present);
		}catch (Exception e) {
			returnValue = true;
		}
		return returnValue;
	}
}
