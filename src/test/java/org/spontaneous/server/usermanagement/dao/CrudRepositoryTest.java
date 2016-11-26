package org.spontaneous.server.usermanagement.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.spontaneous.server.usermanagement.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Unit Test for the Crud-Repository
 * @author fdondorf
 *
 */
public class CrudRepositoryTest extends AbstractJPATest {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Before
	public void setupTestData() {
	  // given
		customerRepository.deleteAll();
	}
	  
	@Test
	public void testSaveCustomer() {
		
		Customer customer = new Customer("Florian","Dondorf");
		Customer savedCustomer = customerRepository.save(customer);
		Assert.assertNotNull(savedCustomer.getId());
	}

}
