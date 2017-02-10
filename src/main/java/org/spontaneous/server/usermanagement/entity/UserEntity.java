package org.spontaneous.server.usermanagement.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.spontaneous.server.usermanagement.api.User;

@Entity
@Table(name="USER")
public class UserEntity implements User, Serializable {
	
	private static final long serialVersionUID = -7447873502288352919L;

	@Id
	@GeneratedValue
	private Long id;
	
	private String firstName;
	private String lastName;
	
	@Column(unique=true)
	private String email;
	private String password;
	
	@ManyToMany(fetch = FetchType.EAGER) //, cascade=CascadeType.ALL)
	private List<RoleEntity> roles;

	public UserEntity() {
		;
	}

	public UserEntity(String firstname, String lastname, String email, String password, List<RoleEntity> roles) {
		this.firstName = firstname;
		this.lastName = lastname;
		this.email = email;
		this.password = password;
		this.roles = roles;
	}

	public Long getId() {
		return id;
	}
	
	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<RoleEntity> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleEntity> roles) {
		this.roles = roles;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstname) {
		this.firstName = firstname;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastname) {
		this.lastName = lastname;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
