package org.spontaneous.server.auth.logic.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spontaneous.server.auth.logic.api.Authentication;
import org.spontaneous.server.auth.logic.api.AuthenticationService;
import org.spontaneous.server.usermanagement.api.User;
import org.spontaneous.server.usermanagement.dao.UserRepository;

/**
 * Implements {@link AuthenticationService}
 *
 * @author fdondorf
 */
public class AuthenticationServiceImpl implements AuthenticationService {

  private static final Logger LOG = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

  private UserRepository userDAO;
  
  @Override
  public Authentication isValid(User user) {
//    Authentication auth = AuthenticationEntityTOMapper.authenticationMapper(authenticationDao.verifyAuthentication(user.getId()));
//    LOG.debug("User '{}' verified: {}", user.getId(), auth);
//    return auth;
	return new AuthenticationImpl(10000, null);
  }

  @Override
  public Authentication isAuthenticated(String username, String password) {
//    Authentication auth = AuthenticationEntityTOMapper.authenticationMapper(userDAO.findByEmail(username));
//    LOG.debug("User '{}' authenticated: {}", username, auth.getExpiresIn());
//    return auth;
    return new AuthenticationImpl(10000, null);
  }

}
