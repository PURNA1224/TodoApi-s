package com.talentXp.todoApplication.constants;

import com.talentXp.todoApplication.SpringApplicationContext;
import com.talentXp.todoApplication.securityConfig.AppProperties;


public final class SecurityConstants {
	public static final String HEADER_TOKEN_KEY = "Authorization";
	public static final String HEADER_TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_USERID = "userId";
	
	public static String getCommonKey() {
		return ((AppProperties)SpringApplicationContext.getBean("appProperties")).getCommonKey();
	}
	
	public static String getExprirationTime() {
		return ((AppProperties)SpringApplicationContext.getBean("appProperties")).getExpireTime();
	}
}
