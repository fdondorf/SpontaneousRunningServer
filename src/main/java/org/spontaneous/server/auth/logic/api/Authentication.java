package org.spontaneous.server.auth.logic.api;


/**
 * Describes the authentication status
 *
 * @author hahmad
 */
public interface Authentication {

  /**
   * Returns time in s when password expires
   *
   * @return time in s when password expires
   */
  public int getExpiresIn();

  /**
   * Returns error object or null if there is no error
   *
   * @return error object or null if there is no error
   */
  public Error getError();

  /**
   * Check if error exists
   *
   * @return Check if error exists
   */
  public boolean hasError();

}
