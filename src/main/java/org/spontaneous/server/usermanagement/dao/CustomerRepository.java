package org.spontaneous.server.usermanagement.dao;

import java.util.List;

import org.spontaneous.server.usermanagement.entity.Customer;
import org.springframework.data.repository.CrudRepository;


public interface CustomerRepository extends CrudRepository<Customer, Long>{

	 List<Customer> findByLastName(String lastName);
	 
}
