package org.spontaneous.server.client.service.rest;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spontaneous.server.auth.logic.api.AuthenticatedUser;
import org.spontaneous.server.client.service.Header;
import org.spontaneous.server.client.service.UserInfoResult;
import org.spontaneous.server.usermanagement.dao.UserRepository;
import org.spontaneous.server.usermanagement.entity.UserEntity;
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
public class UserManagementController extends AbstractClientAuthController {

	private static final Logger LOG = LoggerFactory.getLogger(UserManagementController.class);
	
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
			  userInfo.setUserId(user.getId());
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
	  
}
