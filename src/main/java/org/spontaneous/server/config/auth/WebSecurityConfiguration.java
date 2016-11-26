package org.spontaneous.server.config.auth;

import org.spontaneous.server.auth.logic.impl.AuthenticationProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

/**
 * Spring WebSecurity configuration.
 * <p>
 * The AuthenticationManager uses the LiferayAuthenticationProvider as AuthenticationProvider to check users.
 *
 * @author Horst Jilg
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  private AuthenticationProvider authenticationProvider;
  
  /**
   * Defines the authenticationManagerBean bean.
   * 
   * @return the authenticationManagerBean bean
   */
  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
      return new StandardPasswordEncoder();
  }
  
  /**
   * Defines the authenticationProvider bean.
   *
   * @return the authenticationProvider bean
   */
  @Bean
  public AuthenticationProvider authenticationProvider() {
    AuthenticationProviderImpl authProvider = new AuthenticationProviderImpl();
    return authProvider;
  }
  
  @Override
  protected void configure(HttpSecurity http) throws Exception {
	http.headers().addHeaderWriter(new StaticHeadersWriter("Server", "Spontaneous Running Backend"));
    http.requestMatchers().antMatchers("/spontaneous/**");
    http.csrf().disable();
  }

  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
    	.authenticationProvider(authenticationProvider);
  }

}
