package org.spontaneous.server.client.service.rest;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spontaneous.server.auth.logic.api.AuthenticatedUser;
import org.spontaneous.server.auth.logic.api.AuthenticationService;
import org.spontaneous.server.auth.logic.impl.TimeToLiveTokenServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedClientException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Superclass for all client controllers used for the mobile client requests.
 * <p>
 * Common functionality to get a logged in user and to validate header data.
 *
 * @author fdondorf
 */
@RequestMapping("/spontaneous")
public class AbstractClientController {

	private static final Logger LOG = LoggerFactory.getLogger(AbstractClientController.class);
	
	  @Autowired
	  protected AuthenticationService authenticationService;

	  @Autowired
	  protected ConsumerTokenServices consumerTokenServices;
	  
	  @Autowired
	  protected TokenStore tokenStore;
	  
//	  @Autowired
//	  @Qualifier("authServerTokenServiceBean")
//	  protected AuthorizationServerTokenServices authorizationServerTokenServices;
	  
	  /**
	   * Get the authenticated user.
	   * 
	   * @param principal - a spring-security principal
	   * @return an authenticated {@link AuthenticatedUser}
	   */
	 public AuthenticatedUser getAuthUser(Principal principal) {
		  
		 LOG.info("Calling getAuthUser...");
		  
		 AuthenticatedUser user = null;
		 if (principal instanceof OAuth2Authentication) {
			 OAuth2Authentication authentication = (OAuth2Authentication) principal;
			 if (authentication.getPrincipal() instanceof AuthenticatedUser) {
				 user = (AuthenticatedUser) authentication.getPrincipal();
			 }
		 }

		 if (user == null) {
			 throw new UnauthorizedClientException("No logged in user found");
		 }
		 return user;
	  }
	 
}
