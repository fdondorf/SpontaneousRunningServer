package org.spontaneous.server.common.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.spontaneous.server.usermanagement.entity.RoleEntity;
import org.spontaneous.server.usermanagement.entity.UserEntity;

public class UserEntityBuilder {

	private static Random r = new Random();

	private UserEntity entity = new UserEntity();

	private UserEntityBuilder() {
		//entity.setId(UUID.randomUUID().getMostSignificantBits());
	}

	public static UserEntityBuilder aDefaultUserEntity() {
		UserEntityBuilder userEntityBuilder = new UserEntityBuilder();
		userEntityBuilder.entity.setFirstName("Jonny");
		userEntityBuilder.entity.setLastName("Olsen");
		userEntityBuilder.entity.setEmail("test@test.de");
		userEntityBuilder.entity.setPassword("test");
		
		List<RoleEntity> roles = new ArrayList<RoleEntity>();
		roles.add(new RoleEntity("ROLE_USER"));
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
