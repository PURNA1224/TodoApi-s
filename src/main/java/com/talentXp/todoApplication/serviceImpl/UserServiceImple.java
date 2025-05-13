package com.talentXp.todoApplication.serviceImpl;

import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.talentXp.todoApplication.Pojo.UserDto;
import com.talentXp.todoApplication.entity.RoleEntity;
import com.talentXp.todoApplication.entity.UserEntity;
import com.talentXp.todoApplication.exceptionHandler.EmailAlreadyExistException;
import com.talentXp.todoApplication.exceptionHandler.UserServiceException;
import com.talentXp.todoApplication.repository.RolesRepository;
import com.talentXp.todoApplication.repository.UsersRepository;
import com.talentXp.todoApplication.securityConfig.UserPrincipal;
import com.talentXp.todoApplication.service.UserService;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImple implements UserService{

	@Autowired
	private UsersRepository usersRepo;
	
	@Autowired
	private RolesRepository rolesRepository;
	
	@Autowired
	ModelMapper mapper;

	@Autowired
	BCryptPasswordEncoder bcrypt;
	
	@Override
	public UserDto createUser(UserDto userDto, String password) throws EmailAlreadyExistException{
		
		UserEntity checkEmail = usersRepo.findByEmail(userDto.getEmail().toLowerCase());
		if(checkEmail != null)
			throw new EmailAlreadyExistException("User with email "+ userDto.getEmail() + " already exist, please use another email");
		userDto.setUserId(UUID.randomUUID().toString());
		userDto.setEncryptedPassword(bcrypt.encode(password));
		userDto.setEmail(userDto.getEmail().toLowerCase());
		Collection<RoleEntity> userRoleEntity = new HashSet<>(); 
		for(String role: userDto.getRoles()) {
			RoleEntity roleEntity = rolesRepository.findByName(role);
			if(roleEntity != null) {
				userRoleEntity.add(roleEntity);
			}
		}
		
		UserEntity user = mapper.map(userDto, UserEntity.class);
		user.setRoles(userRoleEntity);
		usersRepo.save(user);
		return userDto;
	}
	
	@Override
	public UserDto getUserDetailsByEmail(String username) {
		UserEntity userEntity = usersRepo.findByEmail(username.toLowerCase());
		return new ModelMapper().map(userEntity, UserDto.class);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity entity =	usersRepo.findByEmail(email.toLowerCase());
		
		if(entity == null) throw new UsernameNotFoundException("Email not found");
		
		return new UserPrincipal(entity);
	}
	

	@Override
	@Transactional
	public String deleteUser(String id) throws UserServiceException{
		UserEntity deletedEntity = usersRepo.getByUserId(id);
		if(deletedEntity == null) throw new UserServiceException("User not found here");
		usersRepo.deleteByUserId(id);
		return id;
	}

	@Override
	public UserDto getUserByUserId(String userId) {
		UserEntity entity = usersRepo.findByUserId(userId);
		if(entity == null) throw new UserServiceException("User not found here");
		return new ModelMapper().map(entity, UserDto.class);
		
	}
	
}
