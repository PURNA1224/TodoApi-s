package com.talentXp.todoApplication.controller;


import java.util.Arrays;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.talentXp.todoApplication.Pojo.CreateUserRequestModel;
import com.talentXp.todoApplication.Pojo.CreateUserResponseModel;
import com.talentXp.todoApplication.Pojo.UserDto;
import com.talentXp.todoApplication.Pojo.UserResponseModel;
import com.talentXp.todoApplication.repository.UsersRepository;
import com.talentXp.todoApplication.service.UserService;
import com.talentXp.todoApplication.shared.Roles;
import com.talentXp.todoApplication.shared.Utils;

import jakarta.validation.Valid;

@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	UsersRepository userRepository;
	
	@Autowired
	ModelMapper mapper;
	
	@Autowired
	Utils utils;
	
//	@PostAuthorize("hasRole('ADMIN') or returnObject.body.userId == principal.userId")
	@PostMapping("/create")
	public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserRequestModel requestModel) {
		UserDto userDto = mapper.map(requestModel, UserDto.class);
		userDto.setRoles(Arrays.asList(Roles.ROLE_USER.name()));
		UserDto createdUserDto = userService.createUser(userDto, requestModel.getPassword());
		CreateUserResponseModel response = mapper.map(createdUserDto, CreateUserResponseModel.class);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
	}
	
	@PreAuthorize("hasRole('ADMIN') or #userId == principal.userId")
	@GetMapping("/getUser/{userId}")
	public ResponseEntity<UserResponseModel> getUserById(@PathVariable String userId){
		UserDto userDto = userService.getUserByUserId(userId);
		UserResponseModel response = null;
		if(userDto != null) 
			response =  mapper.map(userDto, UserResponseModel.class);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
	}
	
//	@Secured("ROLE_ADMIN")
	@PreAuthorize("hasRole('ADMIN') or #id == principal.userId")
	@DeleteMapping("/delete/{id}")
	public String deleteUser(@PathVariable String id) {
		return userService.deleteUser(id);
	}
	
	@PostMapping("/regenerateToken")
	public String regenerateToken(@RequestParam String userId) {
		return utils.regenerateToken(userId);
	}
}
