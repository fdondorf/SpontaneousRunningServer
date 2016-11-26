package org.spontaneous.server.config;

import org.spontaneous.server.config.auth.WebSecurityConfiguration;
import org.spontaneous.server.config.oauth2.AuthorizationServerConfiguration;
import org.spontaneous.server.config.oauth2.ResourceServerConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Umbrella configuration for the configurations in the config package.
 */
@Configuration
@Import({ //
    ProductionConfig.class, //
    WebSecurityConfiguration.class, //
    AuthorizationServerConfiguration.class,//
    ResourceServerConfiguration.class//
    //WebMvcConfiguration.class,//
    //WebServiceConfig.class 
    })
public class ConfigConfig {
  // nothing in here just imports....
}
