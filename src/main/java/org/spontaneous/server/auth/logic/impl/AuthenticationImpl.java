package org.spontaneous.server.auth.logic.impl;

import org.spontaneous.server.auth.logic.api.Authentication;

/**
 * Implements {@link Authentication}
 *
 * @author hahmad
 */
public class AuthenticationImpl implements Authentication {

  private final int expiresIn;

  private final Error error;

  /**
   * Creates a AuthenticationImpl object
   *
   * @param expiresIn Epiration time in seconds
   * @param error Error that occured
   */
  public AuthenticationImpl(int expiresIn, Error error) {
    this.expiresIn = expiresIn;
    this.error = error;
  }

  @Override
  public int getExpiresIn() {
    return expiresIn;
  }

  @Override
  public Error getError() {
    return error;
  }

  @Override
  public boolean hasError() {
    return error != null;
  }

}
