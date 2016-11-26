package org.spontaneous.server.client.service.rest;


import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller to revoke tokens.
 * <p>
 * Only the logged in user can revoke his own token.
 *
 * @author Horst Jilg
 */
@RestController
public class RevokeTokenController extends AbstractClientController {
  private static final Logger LOG = LoggerFactory.getLogger(RevokeTokenController.class);

  /**
   * Revokes the existing token for the logged in user.
   *
   * @param principal the principal
   * @return the response entity
   */
  @RequestMapping(value = "/auth/revoke", method = RequestMethod.POST)
  public ResponseEntity<Void> revokeToken(Principal principal) {
	  LOG.info("Calling revoke token");

    if (principal instanceof OAuth2Authentication) {
      OAuth2Authentication authentication = (OAuth2Authentication) principal;
      OAuth2AccessToken principalToken = tokenStore.getAccessToken(authentication);//authorizationServerTokenServices.getAccessToken(authentication);
      
      boolean revokeResult = consumerTokenServices.revokeToken(principalToken.getValue());
      if (revokeResult) {
        LOG.debug("Token {} revoked.", principalToken.getValue());
        return new ResponseEntity<>(HttpStatus.OK);
      } else {
        LOG.error("Token {} is already revoked.", principalToken.getValue());
        return new ResponseEntity<>(HttpStatus.OK);
      }

    }
    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
  }

}
