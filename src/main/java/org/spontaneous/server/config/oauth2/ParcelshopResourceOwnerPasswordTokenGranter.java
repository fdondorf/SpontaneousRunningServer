package org.spontaneous.server.config.oauth2;

import java.util.Map;

import org.spontaneous.server.client.service.exception.InvalidGrantException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

/**
 * This a special ResourceOwnerPasswordTokenGranter to handle our own Tokens and
 * to enable individual error messages and other http status codes than defined in the OAuth2 standard.
 *
 * @author Horst Jilg
 */
public class ParcelshopResourceOwnerPasswordTokenGranter extends ResourceOwnerPasswordTokenGranter {

  private static final String BAD_CREDENTIALS = "Bad credentials";
  private final AuthenticationManager authenticationManager;

  /**
   * Instantiates a new Parcelshop resource owner password token granter.
   *
   * @param authenticationManager the authentication manager
   * @param tokenServices the token services
   * @param clientDetailsService the client details service
   * @param requestFactory the request factory
   */
  public ParcelshopResourceOwnerPasswordTokenGranter(AuthenticationManager authenticationManager, AuthorizationServerTokenServices tokenServices,
      ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
    super(authenticationManager, tokenServices, clientDetailsService, requestFactory);
    this.authenticationManager = authenticationManager;
  }

  @Override
  protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {

    Map<String, String> parameters = tokenRequest.getRequestParameters();
    String username = parameters.get("username");
    String password = parameters.get("password");

    Authentication userAuth = new ParcelshopUsernamePasswordAuthenticationToken(username, password);
    try {
      userAuth = authenticationManager.authenticate(userAuth);
    } catch (AccountStatusException ase) {
      // covers expired, locked, disabled cases (mentioned in section 5.2, draft 31)
      throw new InvalidGrantException(BAD_CREDENTIALS);
    } catch (BadCredentialsException e) {
      throw new InvalidGrantException(BAD_CREDENTIALS);
    } catch (InvalidGrantException ed) {
      throw new InvalidGrantException(BAD_CREDENTIALS);
    }
    if (userAuth == null || !userAuth.isAuthenticated()) {
      throw new InvalidGrantException(BAD_CREDENTIALS);
    }

    OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
    return new OAuth2Authentication(storedOAuth2Request, userAuth);
  }

}
