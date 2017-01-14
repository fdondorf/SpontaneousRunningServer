package org.spontaneous.server.usermanagement.dao;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.spontaneous.server.AbstractSpontaneousIntegrationTest;
import org.spontaneous.server.usermanagement.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Unit Test for the Crud-Repository
 * @author fdondorf
 *
 */
public class CustomerRepositoryTest extends AbstractSpontaneousIntegrationTest {
	
	@Autowired
	private CustomerRepository customerRepository;
	
    private static final long MEGABYTE = 1024L * 1024L;

    public static long bytesToMegabytes(long bytes) {
            return bytes / MEGABYTE;
    }
    
	@Before
	public void setupTestData() {
		// given
		customerRepository.deleteAll();
		createTestData(200);
	}
	
	@After
	public void tearDownTestData() {
		//customerRepository.deleteAll();
	}
	
	@Test
	public void testSaveCustomer() {
		
		Customer customer = new Customer("Florian","Dondorf");
		Customer savedCustomer = customerRepository.save(customer);
		Assert.assertNotNull(savedCustomer.getId());
	}
	
	@Test
	public void testFindByLastname() {
		
		List<Customer> customerList = customerRepository.findByLastName("Testnachname");
		
		// Get the Java runtime
        Runtime runtime = Runtime.getRuntime();
        // Run the garbage collector
        runtime.gc();
        // Calculate the used memory
        long memory = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Used memory is bytes: " + memory);
        System.out.println("Used memory is megabytes: "
                        + bytesToMegabytes(memory));
        
		Assert.assertEquals(200, customerList.size());
	}
	
//	@Test
//	public void testFindAllCustomer() {
//		
//		long count = 0;
//		List<Customer> customerList;
//		try (Stream<Customer> customers = customerRepository.findByLastname("Testnachname")) {
//			 //count = customers.count(); //ma.filter(…).map(…).collect(…);
//			 customerList = customers.filter(c -> c.getLastName().startsWith("Test"))
//					 .collect(Collectors.toList());
////			    .map(String::toUpperCase)
////			    .sorted()
////			    .forEach(System.out::println);
//		}
//		
//		// Get the Java runtime
//        Runtime runtime = Runtime.getRuntime();
//        // Run the garbage collector
//        runtime.gc();
//        // Calculate the used memory
//        long memory = runtime.totalMemory() - runtime.freeMemory();
//        System.out.println("Used memory is bytes: " + memory);
//        System.out.println("Used memory is megabytes: "
//                        + bytesToMegabytes(memory));
//        
//		Assert.assertEquals(20, customerList.size());
//		//Assert.assertEquals(20, count);
//	}
	
	private void createTestData (int count) {
		
		Customer customer;
		for (int i = 0; i < count; i++) {
			customer = new Customer("Test" + i,"Testnachname");
			customerRepository.save(customer);
		}
	}

}
