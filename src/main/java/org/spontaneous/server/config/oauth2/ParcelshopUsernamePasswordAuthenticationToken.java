package org.spontaneous.server.config.oauth2;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * We use our own UsernamePasswordAuthenticationToken to avoid confusion with the UsernamePasswordAuthenticationToken.
 * <p>
 * It is used in our own TokenGranter.
 *
 * @author Horst Jilg
 */
public class ParcelshopUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {

  private static final long serialVersionUID = 5402656074762957854L;

  /**
   * Instantiates a new Parcelshop username password authentication token.
   *
   * @param principal the principal
   * @param credentials the credentials
   */
  public ParcelshopUsernamePasswordAuthenticationToken(Object principal, Object credentials) {
    super(principal, credentials);
  }

  /**
   * Instantiates a new Parcelshop username password authentication token.
   *
   * @param principal the principal
   * @param credentials the credentials
   * @param authorities the authorities
   */
  public ParcelshopUsernamePasswordAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
    super(principal, credentials, authorities);
  }

}
