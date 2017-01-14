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
 * Superclass for all client controllers used for the mobile client requests not under oauth control..
 * <p>
 * Common functionality to get a logged in user and to validate header data.
 *
 * @author fdondorf
 */
@RequestMapping("/spontaneous")
public class AbstractClientController {

	private static final Logger LOG = LoggerFactory.getLogger(AbstractClientController.class);
	 
}
