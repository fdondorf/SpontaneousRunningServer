package org.spontaneous.server.client;

import org.spontaneous.server.client.service.rest.RegisterController;
import org.spontaneous.server.client.service.rest.RevokeTokenController;
import org.spontaneous.server.client.service.rest.UserManagementController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The type Client config.
 */
@Configuration
//@ComponentScan(basePackages = { "com.dhl.parcelshop.backend.client" }, useDefaultFilters = false, includeFilters = { @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,//
//value = { TenantSpecificClientConfig.class }) })
public class ClientConfig {

//  /**
//   * Defines the androidClientProperties bean.
//   *
//   * @return the androidClientProperties bean
//   */
//  @Bean
//  @ConfigurationProperties(prefix = "parcelshop.client.android")
//  public ClientProperties androidClientProperties() {
//    return new ClientProperties();
//  }
	 
  /**
   * Defines the revokeTokenController bean.
   *
   * @return the revokeTokenController bean
   */
  @Bean
  public RevokeTokenController revokeTokenController() {
    return new RevokeTokenController();
  }

  /**
   * Defines the userManagementController bean.
   *
   * @return the userManagementController bean
   */
  @Bean
  public UserManagementController userManagementController() {
    return new UserManagementController();
  }

  /**
   * Defines the registerController bean.
   *
   * @return the registerController bean
   */
  @Bean
  public RegisterController registerController() {
    return new RegisterController();
  }
  
}