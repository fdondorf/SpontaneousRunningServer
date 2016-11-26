package org.spontaneous.server.auth.logic.api;

/**
 * Interface to access authenticated users.
 *
 * @author fdondorf
 */
public interface UserService {

  /**
   * Add additional user infos to an authenticated user.
   *
   * @param user - an AuthenticatedUser
   * @return the same AuthenticatedUser with additional user infos like features etc.
   */
  AuthenticatedUser addUserinfo(AuthenticatedUser user);

}
