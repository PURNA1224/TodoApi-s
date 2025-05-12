package com.talentXp.todoApplication.service;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.talentXp.todoApplication.Pojo.UserDto;

public interface UserService extends UserDetailsService{
	public UserDto createUser(UserDto userDto, String password);
	public UserDto getUserDetailsByEmail(String username);
	public String deleteUser(String id);
	public UserDto getUserByUserId(String userId);
}

