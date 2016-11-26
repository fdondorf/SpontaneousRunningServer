package org.spontaneous.server.auth.logic.api;

import org.spontaneous.server.usermanagement.api.User;

/**
 * Service provides method for authentication of users
 *
 * @author hahmad
 */
public interface AuthenticationService {

  /**
   * Check if the given user is still a valid user.
   *
   * @param user - an logged in User
   * @return Authentication object
   */
  Authentication isValid(User user);

  /**
   * Check if the given user is a valid user.
   *
   * @param username The username of the user
   * @param password the password of the user
   * @return Authentication object
   */
  Authentication isAuthenticated(String username, String password);
}
