package org.spontaneous.server.usermanagement.dao;

import java.util.Iterator;

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
	
	private static final String EMAIL_USER_1 = "test1@test.de";
	private static final String EMAIL_USER_2 = "test2@test.de";
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Before
	public void setupTestData() {
		// given
		userRepository.deleteAll();
	}
	  
	@Test
	public void testCreateUser() {
		
		UserEntity user1 = UserEntityBuilder
				.aDefaultUserEntity(roleRepository.findByName("ROLE_USER"))
				.withEmail(EMAIL_USER_1)
				.build();
		UserEntity savedUser1 = userRepository.save(user1);
		Assert.assertNotNull(savedUser1.getId());
		Assert.assertEquals(3, getCountofRoles());
		
		UserEntity user2 = UserEntityBuilder
				.aDefaultUserEntity(roleRepository.findByName("ROLE_USER"))
				.withEmail(EMAIL_USER_2)
				.withRole(roleRepository.findByName("ROLE_ADMIN"))
				.build();
		UserEntity savedUser2 = userRepository.save(user2);
		Assert.assertNotNull(savedUser2.getId());
		Assert.assertEquals(3, getCountofRoles());
	}

	
	private int getCountofRoles() {
		
		int result = 0;
		Iterator<RoleEntity> iter = roleRepository.findAll().iterator();
		while (iter.hasNext()) {
			iter.next();
			result ++;
		}
		return result;
	}
}
