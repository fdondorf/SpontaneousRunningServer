package org.spontaneous.server.common.data;

import java.util.ArrayList;
import java.util.List;

import org.spontaneous.server.usermanagement.entity.RoleEntity;
import org.spontaneous.server.usermanagement.entity.UserEntity;

public class UserEntityBuilder {

	private UserEntity entity = new UserEntity();

	private UserEntityBuilder() {

	}

	public static UserEntityBuilder aDefaultUserEntity(RoleEntity role) {
		UserEntityBuilder userEntityBuilder = new UserEntityBuilder();
		userEntityBuilder.entity.setFirstName("Jonny");
		userEntityBuilder.entity.setLastName("Olsen");
		userEntityBuilder.entity.setEmail("test@test.de");
		userEntityBuilder.entity.setPassword("test");
		
		List<RoleEntity> roles = new ArrayList<RoleEntity>();
		roles.add(role);
		userEntityBuilder.entity.setRoles(roles);

		return userEntityBuilder;
	  }

	  public UserEntityBuilder withFirstname(String firstname) {
		  entity.setFirstName(firstname);
		  return this;
	  }
	  
	  public UserEntityBuilder withLastname(String lastname) {
		  entity.setLastName(lastname);
		  return this;
	  }
	  
	  public UserEntityBuilder withEmail(String email) {
		  entity.setEmail(email);
		  return this;
	  }

	  public UserEntityBuilder withPassword(String password) {
		  entity.setPassword(password);
		  return this;
	  }
	  
	  public UserEntityBuilder withRole(RoleEntity role) {
		  if (entity.getRoles() == null) {
			  entity.setRoles(new ArrayList<RoleEntity>());
		  }
		  entity.getRoles().add(role);
		  return this;
	  }
	  
	  public UserEntity build() {
		    return this.entity;
		  }
}
