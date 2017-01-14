package org.spontaneous.server.client.service.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spontaneous.server.client.service.CustomerResult;
import org.spontaneous.server.usermanagement.dao.CustomerRepository;
import org.spontaneous.server.usermanagement.entity.Customer;
import org.spontaneous.server.usermanagement.to.CustomerTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

	private static final Logger LOG = LoggerFactory.getLogger(CustomerController.class);

	@Autowired
	private CustomerRepository customerRepository;
	
	@RequestMapping("/customer")
    public ResponseEntity<CustomerResult> customer() {
		LOG.debug("Calling Controller 'customer'");
		
		CustomerResult result = new CustomerResult();
		
		List<Customer> list = (List<Customer>) customerRepository.findAll();
		for(Customer customer : list) {
			CustomerTO cTo = new CustomerTO();
			cTo.setId(customer.getId());
			cTo.setFirstName(customer.getFirstName());
			cTo.setLastName(customer.getLastName());
			result.addCustomer(cTo);
		}
		ResponseEntity<CustomerResult> response = new ResponseEntity<CustomerResult>(result, HttpStatus.OK);
		
		return response;
    }

}
