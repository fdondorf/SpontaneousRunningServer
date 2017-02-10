package org.spontaneous.server.client.service.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spontaneous.server.client.service.RegisteredUserResult;
import org.spontaneous.server.usermanagement.dao.RoleRepository;
import org.spontaneous.server.usermanagement.dao.UserRepository;
import org.spontaneous.server.usermanagement.entity.RoleEntity;
import org.spontaneous.server.usermanagement.entity.UserEntity;
import org.spontaneous.server.usermanagement.to.UserTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController extends AbstractClientController {

	private static final Logger LOG = LoggerFactory.getLogger(RegisterController.class);

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	
	/**
	   * Method for providing a rest interface for register a new user
	   *
	   * @param userTO The user to register
	   * @return Returns a ResponseEntity
	   */
	@RequestMapping(value = "/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegisteredUserResult> registerUser(@RequestBody UserTO userTO) {
		LOG.debug("Calling Controller 'registerUser'");

		//TODO: Validation and Check der App-Version
		
		UserEntity user = new UserEntity(userTO.getFirstname(), userTO.getLastname(), 
				userTO.getEmail(), userTO.getPassword(), Arrays.asList(roleRepository.findByName("ROLE_USER")));
		UserEntity savedUser = userRepository.save(user);
		
		RegisteredUserResult result = new RegisteredUserResult(savedUser.getId());
		
		ResponseEntity<RegisteredUserResult> response = new ResponseEntity<RegisteredUserResult>(result, HttpStatus.OK);
		
		return response;
    }

}
