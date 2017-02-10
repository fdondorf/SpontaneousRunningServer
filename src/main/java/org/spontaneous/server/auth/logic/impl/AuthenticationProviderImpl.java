package org.spontaneous.server.auth.logic.impl;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spontaneous.server.auth.logic.api.AuthenticatedUser;
import org.spontaneous.server.config.oauth2.ParcelshopUsernamePasswordAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

public class AuthenticationProviderImpl implements AuthenticationProvider {

  private static final Logger LOG = LoggerFactory.getLogger(AuthenticationProviderImpl.class);

  @Autowired
  private UserDetailsService userDetailsService;
  
  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    
	LOG.debug("Authenticate user");
	
	if (authentication.getDetails() != null && authentication.getDetails() instanceof WebAuthenticationDetails) {
      return null;
    }
    // Determine username
    String username = (authentication.getPrincipal() == null) ? "NONE_PROVIDED" : authentication.getName();

    // Determine password
    if (authentication.getCredentials() == null) {
      LOG.debug("Authentication failed: no credentials provided");
      throw new BadCredentialsException("Bad credentials");
    }
    String password = authentication.getCredentials().toString();
    
    // Check if user exists in db
    AuthenticatedUser user = (AuthenticatedUser) userDetailsService.loadUserByUsername(username);
    
    //AuthenticatedUser user = new AuthenticatedUser(username, password, Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
    ParcelshopUsernamePasswordAuthenticationToken result =
        new ParcelshopUsernamePasswordAuthenticationToken(user, authentication.getCredentials(), user.getAuthorities());
            //Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
    result.setDetails(authentication.getDetails());

    return result;
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return (ParcelshopUsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
  }

  @PostConstruct
  public void checkConfig() {
    LOG.warn("Using AuthenticationProviderImpl.");
  }

}
