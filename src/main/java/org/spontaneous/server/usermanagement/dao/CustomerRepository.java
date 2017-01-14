package org.spontaneous.server.usermanagement.dao;

import java.util.List;
import java.util.stream.Stream;

import org.spontaneous.server.usermanagement.entity.Customer;
import org.springframework.data.repository.CrudRepository;


public interface CustomerRepository extends CrudRepository<Customer, Long>{

	 List<Customer> findByLastName(String lastName);
	 
	 //Stream<Customer> findByLastname(String lastName);
}
