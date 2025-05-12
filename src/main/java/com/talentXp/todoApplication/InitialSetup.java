package com.talentXp.todoApplication;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.talentXp.todoApplication.entity.AuthoritiesEntity;
import com.talentXp.todoApplication.entity.RoleEntity;
import com.talentXp.todoApplication.entity.UserEntity;
import com.talentXp.todoApplication.repository.AuthoritiesRepository;
import com.talentXp.todoApplication.repository.RolesRepository;
import com.talentXp.todoApplication.repository.UsersRepository;
import com.talentXp.todoApplication.shared.Roles;
import com.talentXp.todoApplication.shared.Utils;

import jakarta.transaction.Transactional;

@Component
public class InitialSetup {
	
	@Autowired
	AuthoritiesRepository authoritiesRepository;
	
	@Autowired
	RolesRepository rolesRepository;
	
	@Autowired
	UsersRepository usersRepository;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	Utils utils;
	
	@EventListener
	@Transactional
	public void onApplicationCall(ApplicationReadyEvent applicationReadyEvent) {
		AuthoritiesEntity readAuthority = createAuthority("READ_AUTHORITY");
		AuthoritiesEntity writeAuthority = createAuthority("WRITE_AUTHORITY");
		AuthoritiesEntity deleteAuthority = createAuthority("DELETE_AUTHORITY");
		
		createRole(Roles.ROLE_USER.name(), Arrays.asList(readAuthority, writeAuthority));
		RoleEntity adminRoleEntity = createRole(Roles.ROLE_ADMIN.name(), Arrays.asList(readAuthority, writeAuthority, deleteAuthority));
		
		if(adminRoleEntity == null)
			return;
		
		UserEntity admin = new UserEntity();
		admin.setFirstName("Purna");
		admin.setLastName("Sekhar");
		admin.setEmail("purna@talentxp.com");
		admin.setEncryptedPassword(bCryptPasswordEncoder.encode("Purna@123"));
		admin.setUserId(utils.generateUserId(30));
		admin.setRoles(Arrays.asList(adminRoleEntity));
		
		UserEntity presentEntity = usersRepository.findByEmail("purna@talentxp.com");
		
		if(presentEntity == null)
			usersRepository.save(admin);
		
		
	}

	@Transactional
	private AuthoritiesEntity createAuthority(String name) {
		
		AuthoritiesEntity authoritiesEntity = authoritiesRepository.findByName(name);
		
		if(authoritiesEntity == null) {
			authoritiesEntity = new AuthoritiesEntity();
			authoritiesEntity.setName(name);
			authoritiesRepository.save(authoritiesEntity);
		}
		
		return authoritiesEntity;
	}
	
	@Transactional
	private RoleEntity createRole(String name, Collection<AuthoritiesEntity> authorities) {
		
		RoleEntity roleEntity = rolesRepository.findByName(name);
		
		if(roleEntity == null) {
			roleEntity = new RoleEntity();
			roleEntity.setName(name);
			roleEntity.setAuthorities(authorities);
			rolesRepository.save(roleEntity);
		}
		
		return roleEntity;
	}
}
