package org.spontaneous.server.usermanagement.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.spontaneous.server.usermanagement.entity.RoleEntity;
import org.spontaneous.server.usermanagement.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Unit Test for the User-Repository
 * @author fdondorf
 *
 */
public class UserRepositoryTest extends AbstractJPATest {
	
	@Autowired
	private UserRepository userRepository;
	
	@Before
	public void setupTestData() {
		// given
		userRepository.deleteAll();
	}
	  
	@Test
	public void testCreateUser() {
		
		List<RoleEntity> roles = null;
		UserEntity user = new UserEntity("Florian", "Dondorf", "f.dondorf@googlemail.com", 
				"passwort", roles);
		UserEntity savedUser = userRepository.save(user);
		Assert.assertNotNull(savedUser.getId());
	}

}
