package org.spontaneous.server.auth;

import org.spontaneous.server.auth.logic.impl.UserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The type Auth config.
 */
@Configuration
public class AuthConfig {
	
	  /**
	   * Defines the userDetailsService bean.
	   *
	   * @return the userDetailsService bean
	   */
	  @Bean
	  public UserDetailsService userDetailsService() {
	    return new UserDetailsService();
	  }

}
