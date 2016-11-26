package org.spontaneous.server.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration which defines very basic beans of the common component.
 * These beans will also be included in all component test configurations which derive
 * from AbstractParcelshopBackendComponentTest. This configuration will also define
 * the locations für loading the tenant specific property files ("classpath:/application.tenants.properties",
 * "file:config/application.tenants.properties")
 * 
 * @author ohecker
 *
 */
@Configuration
//@PropertySource(value = { "classpath:/application.tenants.properties", "file:config/application.tenants.properties" }, ignoreResourceNotFound = true)
public class CommonBaseConfig {

  /**
   * Defines the {@link TimeService} bean.
   * 
   * @return the timeService bean
   */
  @Bean
  public TimeService timeService() {
    return new DefaultTimeService();
  }

  /**
   * Defines the global dozer bean mapper bean.
   * 
   * @return a dozerBeanMapper
   */
//  @Bean
//  public Mapper mapper() {
//    return new DozerBeanMapper();
//  }

}
