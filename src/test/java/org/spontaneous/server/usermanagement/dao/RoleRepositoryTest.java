package org.spontaneous.server.usermanagement.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.spontaneous.server.AbstractSpontaneousIntegrationTest;
import org.spontaneous.server.usermanagement.entity.RoleEntity;

/**
 * Unit Test for the Role-Repository
 * @author fdondorf
 *
 */
public class RoleRepositoryTest extends AbstractSpontaneousIntegrationTest {
	
	@Before
	public void setupTestData() {
		;
	}
	 

	@Test
	public void testFindRoleByName() {
		
		RoleEntity roleUser = roleRepository.findByName("ROLE_USER");
		Assert.assertEquals("ROLE_USER", roleUser.getName());
		
		RoleEntity roleAdmin = roleRepository.findByName("ROLE_ADMIN");
		Assert.assertEquals("ROLE_ADMIN", roleAdmin.getName());
		
		RoleEntity roleSuperAdmin = roleRepository.findByName("ROLE_SUPERADMIN");
		Assert.assertEquals("ROLE_SUPERADMIN", roleSuperAdmin.getName());
		
	}

}
