package org.spontaneous.server.common.data;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.cxf.helpers.IOUtils;
import org.spontaneous.server.usermanagement.api.Gender;
import org.spontaneous.server.usermanagement.entity.RoleEntity;
import org.spontaneous.server.usermanagement.entity.UserEntity;

public class UserEntityBuilder {

	private UserEntity entity = new UserEntity();

	private static Random random = new Random();
	
	private UserEntityBuilder() {

	}

	public static UserEntityBuilder aDefaultUserEntity(RoleEntity role) {
		UserEntityBuilder userEntityBuilder = new UserEntityBuilder();
		userEntityBuilder.entity.setFirstName("Jonny");
		userEntityBuilder.entity.setLastName("Olsen");
		userEntityBuilder.entity.setEmail("test" + random.nextInt() + "@test.de");
		userEntityBuilder.entity.setPassword("test");
		userEntityBuilder.entity.setGender(Gender.MALE);

		byte [] image = loadResource("/images/profile-image.jpg");
		userEntityBuilder.entity.setImage(image);
		
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
	  
	  private static byte [] loadResource(String file) {

	    try (InputStream is = UserEntityBuilder.class.getResourceAsStream(file)) {
	      return IOUtils.readBytesFromStream(is);
	    } catch (final IOException e) {
	      return null;
	    }

	  }
}
