package org.spontaneous.server.usermanagement.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.spontaneous.server.usermanagement.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Unit Test for the Crud-Repository
 * @author fdondorf
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CrudRepositoryTest {
	
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
