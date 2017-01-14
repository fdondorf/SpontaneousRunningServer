package org.spontaneous.server.usermanagement.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.spontaneous.server.AbstractSpontaneousIntegrationTest;
import org.spontaneous.server.common.data.UserEntityBuilder;
import org.spontaneous.server.usermanagement.entity.RoleEntity;
import org.spontaneous.server.usermanagement.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Unit Test for the User-Repository
 * @author fdondorf
 *
 */
public class UserRepositoryTest extends AbstractSpontaneousIntegrationTest {
	
	@Autowired
	private UserRepository userRepository;
	
	@Before
	public void setupTestData() {
		// given
		userRepository.deleteAll();
	}
	  
	@Test
	public void testCreateUser() {
		
		RoleEntity role = null;
		UserEntity user = UserEntityBuilder.aDefaultUserEntity().withRole(role).build();
		UserEntity savedUser = userRepository.save(user);
		Assert.assertNotNull(savedUser.getId());
	}

}
