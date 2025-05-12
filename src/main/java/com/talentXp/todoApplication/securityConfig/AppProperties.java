package com.talentXp.todoApplication.securityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class AppProperties {
	@Autowired
	private Environment environment;
	
	public String getCommonKey() {
		return environment.getProperty("token.commonKey");
	}
	
	public String getExpireTime() {
		return environment.getProperty("time.expiration");
	}
}
