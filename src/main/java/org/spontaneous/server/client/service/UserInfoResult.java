package org.spontaneous.server.client.service;

import java.util.ArrayList;
import java.util.List;

public class UserInfoResult {

	private Long userId;
	private String firstName;
	private String lastName;
	private String email;
	private String gender;
	private byte [] image;
	private List<String> roles;
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

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
	
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public List<String> getRoles() {
		if (this.roles == null) {
			this.roles = new ArrayList<String>();
		}
		return roles;
	}
	
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

}
