package org.spontaneous.server.client.service.rest;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spontaneous.server.auth.logic.api.AuthenticatedUser;
import org.spontaneous.server.client.service.CustomerResult;
import org.spontaneous.server.client.service.Header;
import org.spontaneous.server.client.service.UserInfoResult;
import org.spontaneous.server.usermanagement.dao.CustomerRepository;
import org.spontaneous.server.usermanagement.dao.UserRepository;
import org.spontaneous.server.usermanagement.entity.Customer;
import org.spontaneous.server.usermanagement.entity.UserEntity;
import org.spontaneous.server.usermanagement.to.CustomerTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserManagementController extends AbstractClientController {

	private static final Logger LOG = LoggerFactory.getLogger(UserManagementController.class);
	
	@Autowired
	private CustomerRepository crudRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	/**
	 * Returns UserInfo, wrapped in a ResponseEntity. Will send a request against PartnerPortal to get required information.
	   *
	   * @param headerData the HeaderData
	   * @param principal the requested Principal
	   * @return UserInfo wrapped in a ResponseEntitiy
	   */
	  @RequestMapping(value = "/userinfo", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	  public ResponseEntity<UserInfoResult> getUserInfo(@RequestBody Header headerData, Principal principal) {
		
		  LOG.debug("Calling Controller 'getUserInfo'");
			
		  // TODO: Validate Params and Security, AppVersion

		  // TODO: Exception werfen wenn nicht gefunden
		  AuthenticatedUser authUser = getAuthUser(principal);
		  UserEntity user = userRepository.findByEmail(authUser.getUsername());
		  if (user != null) {
			  UserInfoResult userInfo = new UserInfoResult();
			  userInfo.setEmail(user.getEmail());
			  userInfo.setFirstName(user.getFirstName());
			  userInfo.setLastName(user.getLastName());
			  
			  for (GrantedAuthority authority : authUser.getAuthorities()) {
				  userInfo.getRoles().add(authority.getAuthority());
			  }

			  LOG.debug("userinfo: " + userInfo);
			  
			  return new ResponseEntity<UserInfoResult>(userInfo, HttpStatus.OK);
		  } else {
			  LOG.error("No user found.");
		  }

		  return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	  }
	  
	  
	@RequestMapping("/spontaneous/customer")
    public ResponseEntity<CustomerResult> customer() {
		LOG.debug("calling Controller 'customer'");
		
		CustomerResult result = new CustomerResult();
		
		List<Customer> list = (List<Customer>) crudRepository.findAll();
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
