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

/*
 * The InitialSetUp class is used to insert a default admin user into the database if one does 
 * not already exist.
 */
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
	
	
	/* 
	 * EventListener:
	 * This method runs when the application is fully started and ready to serve requests.
	 *
	 * Transactional:
	 * Marks the method as transactional to ensure that all database operations within it
	 * are executed as a single unit — either all succeed or all are rolled back in case of failure.
	 * (Here multiple entities mapping with each other like Users, Roles and Authorities)
	 */
	@EventListener
	@Transactional
	public void onApplicationCall(ApplicationReadyEvent applicationReadyEvent) {
		
		/* 
		 * Sending the authorities READ_AUTHORITY, WRITE_AUTHORITY, and DELETE_AUTHORITY 
		 * separately to the createAuthority() method.
		 */
		AuthoritiesEntity readAuthority = createAuthority("READ_AUTHORITY");
		AuthoritiesEntity writeAuthority = createAuthority("WRITE_AUTHORITY");
		AuthoritiesEntity deleteAuthority = createAuthority("DELETE_AUTHORITY");
		
		/* 
		 * Creating the USER role and assigning it READ and WRITE authorities.
		 * Creating the ADMIN role and assigning it READ, WRITE, and DELETE authorities.
		 * These roles will be saved to the database with their respective permissions.
		 */
		createRole(Roles.ROLE_USER.name(), Arrays.asList(readAuthority, writeAuthority));
		RoleEntity adminRoleEntity = createRole(Roles.ROLE_ADMIN.name(), Arrays.asList(readAuthority, writeAuthority, deleteAuthority));
		
		/*
		 * This is a safety check to verify whether adminRoleEntity is null. If it is, 
		 * the execution is stopped; otherwise, the process continues.
		 */
		if(adminRoleEntity == null)
			return;
		
		/*
		 * Creating a default admin user with predefined details:
 		 * - Sets first name, last name, and email
         * - Encrypts the password before storing
 		 * - Generates a unique user ID using our utils class
 		 * - Assigns the ADMIN role with appropriate authorities 
 		 *   (like READ_AUTHORITY, WRITE_AUTHORITY, and DELETE_AUTHORITY)
		 */
		UserEntity admin = new UserEntity();
		admin.setFirstName("Purna");
		admin.setLastName("Sekhar");
		admin.setEmail("purna@talentxp.com");
		admin.setEncryptedPassword(bCryptPasswordEncoder.encode("Purna@123"));
		admin.setUserId(utils.generateUserId(30));
		admin.setRoles(Arrays.asList(adminRoleEntity));
		
		/*
		 * Check if a user with the specified email already exists in the database.
		 * If not found, save the newly created admin user to prevent duplicate entries.
		 */
		UserEntity presentEntity = usersRepository.findByEmail("purna@talentxp.com");
		if(presentEntity == null)
			usersRepository.save(admin);
		
		
	}

	/*
	 * Stores the authority in the database if it does not already exist.
	 * Prevents duplicate entries by checking for existing authority before saving.
	 */
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
	
	/*
	 * Creates a new role with the given name and associated authorities.
	 * If the role already exists in the database, it returns the existing role.
	 * Otherwise, it creates and saves a new RoleEntity with the provided authorities.
	 */
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
