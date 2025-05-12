package com.talentXp.todoApplication.entity;

import java.util.Collection;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
public class RoleEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	private String name;
	
	@ManyToMany(mappedBy = "roles")
	private Collection<UserEntity> users;
	
	@ManyToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.EAGER)
	@JoinTable(name="roles_authorities",
			   joinColumns = @JoinColumn(name="roles_id", referencedColumnName = "id"),
			   inverseJoinColumns = @JoinColumn(name="authorities_id", referencedColumnName = "id"))
	private Collection<AuthoritiesEntity> authorities;
	

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Collection<AuthoritiesEntity> getAuthorities() {
		return authorities;
	}
	public void setAuthorities(Collection<AuthoritiesEntity> authorities) {
		this.authorities = authorities;
	}
	
	
}
