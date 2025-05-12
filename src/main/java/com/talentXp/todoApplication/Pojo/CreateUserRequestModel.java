package com.talentXp.todoApplication.Pojo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateUserRequestModel {
	
	@NotNull(message="First Name can't be null")
	@Size(min = 2, message="enter minimum 2 letters for firstName")
	private String firstName;
	
	@NotNull(message="Last Name can't be null")
	@Size(min = 2, message="enter minimum 2 letters for lastName")
	private String lastName;
	
	@NotNull(message="Email can't be null")
	@Email
	private String email;
	
	@NotNull(message="password can't be null")
	@Size(min = 8, max = 15, message="enter minimum 8 letters and maximum 15 for password")
	private String password;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
