package com.talentXp.todoApplication.shared;

import java.security.SecureRandom;
import java.util.Random;

import org.springframework.stereotype.Component;

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
}
