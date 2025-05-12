package com.talentXp.todoApplication.securityConfig;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.talentXp.todoApplication.entity.AuthoritiesEntity;
import com.talentXp.todoApplication.entity.RoleEntity;
import com.talentXp.todoApplication.entity.UserEntity;

public class UserPrincipal implements UserDetails{
	
	private static final long serialVersionUID = 8739591637087661174L;
	
	UserEntity userEntity;
	private String userId;
	
	public UserPrincipal(UserEntity userEntity) {
		super();
		this.userEntity = userEntity;
		this.userId = userEntity.getUserId();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> grantedAuthority = new HashSet<>();
		Collection<AuthoritiesEntity> authorities = new HashSet<>();
		
		Collection<RoleEntity> userRoles = userEntity.getRoles();
		if(userRoles == null) return grantedAuthority;
		
		userRoles.forEach(role -> {
			grantedAuthority.add(new SimpleGrantedAuthority(role.getName()));
			authorities.addAll(role.getAuthorities());
		});
		
		authorities.forEach(auth -> grantedAuthority.add(new SimpleGrantedAuthority(auth.getName())));
		
		return grantedAuthority;
		
		
	}

	@Override
	public String getPassword() {
		return userEntity.getEncryptedPassword();
	}

	@Override
	public String getUsername() {
		return userEntity.getEmail();
	}

	public boolean isAccountNonExpired() {
		return true;
	}
	
	public boolean isAccountNonLocked() {
		return true;
	}
	
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	public String getUserId() {
		return this.userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
