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
	public UserDto createUser(UserDto userDto, String password) {
		
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
		UserEntity userEntity = usersRepo.getByEmail(username);
		return new ModelMapper().map(userEntity, UserDto.class);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity entity =	usersRepo.getByEmail(email);
		
		if(entity == null) throw new UsernameNotFoundException("Email not found");
		
		return new UserPrincipal(entity);
//		return new User(entity.getEmail(), entity.getEncryptedPassword(), true, true, true, true, new ArrayList<>());
	}
	

	@Override
	@Transactional
	public String deleteUser(String id) {
		UserEntity deletedEntity = usersRepo.getByUserId(id);
		if(deletedEntity == null) return "-1";
		usersRepo.deleteByUserId(id);
		return id;
	}

	@Override
	public UserDto getUserByUserId(String userId) {
		UserEntity entity = usersRepo.findByUserId(userId);
		if(entity == null) return null;
		return new ModelMapper().map(entity, UserDto.class);
		
	}
	
}
