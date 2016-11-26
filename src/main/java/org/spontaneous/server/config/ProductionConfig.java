package org.spontaneous.server.config;

import org.spontaneous.server.auth.logic.api.UserService;
import org.spontaneous.server.auth.logic.impl.AuthenticationProviderImpl;
import org.spontaneous.server.auth.logic.impl.UserDetailsService;
import org.spontaneous.server.auth.logic.impl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;

/**
 * The "production" profile connects to the database for user authentication.
 
 * @author fdondorf
 */
@Configuration
@Profile("production")
public class ProductionConfig {

//  @Value("${mail.smtp.host}")
//  private String mailSmtpHost;
//
//  @Value("${mail.smtp.port}")
//  private int mailSmtpPort;

  /**
   * Defines the userService bean.
   *
   * @return the userService bean
   */
  @Bean
  public UserService userService() {
    return new UserServiceImpl();
  }
  
  /**
   * Defines the userDetailsService bean.
   *
   * @return the userDetailsService bean
   */
  @Bean
  public UserDetailsService userDetailsService() {
    return new UserDetailsService();
  }
  
//  /**
//   * Defines the authenticationService bean.
//   *
//   * @return the authenticationService bean
//   */
//  @Bean
//  public AuthenticationService authenticationService() {
//    return new AuthenticationServiceImpl();
//  }

  /**
   * Defines the authenticationProvider bean.
   *
   * @return the authenticationProvider bean
   */
  @Bean
  public AuthenticationProvider authenticationProvider() {
    //LiferayAuthenticationProvider authProvider = new LiferayAuthenticationProvider();
    AuthenticationProviderImpl authProvider =  new AuthenticationProviderImpl();
	//authProvider.setAuthenticationService(authenticationService());
    return authProvider;
  }

//  /**
//   * Defines the javaMailSender bean.
//   *
//   * @return the javaMailSender bean
//   */
//  @Bean
//  public JavaMailSender javaMailSender() {
//    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//    mailSender.setHost(mailSmtpHost);
//    mailSender.setPort(mailSmtpPort);
//    return mailSender;
//  }

}
