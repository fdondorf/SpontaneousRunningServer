package org.spontaneous.server.config.oauth2;

import org.spontaneous.server.auth.logic.impl.TimeToLiveTokenServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

/**
 * Configuration for the OAuth2 AuthorizationServer.
 * <p>
 * The AuthorizationServer uses an InMemoryTokenStore and the {@link TimeToLiveTokenServices}. The AuthenticationManager needs to be set for the
 * AuthorizationServerEndpoint, otherwise you cannot use the "password" grant. It uses the URL "/pshop-client/auth/token" for the
 * AuthorizationServerEndpoint for the token-request.
 *
 * @author Horst Jilg
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

  @Value("spontaneous")
  private String resourceId;

  @Value("spontaneous-client")
  private String clientId;

  @Value("spontaneous-secret")
  private String clientSecret;

  @Autowired
  @Qualifier("authenticationManagerBean")
  private AuthenticationManager authenticationManager;

  private TokenStore tokenStore = new InMemoryTokenStore();

  @Autowired
  private ClientDetailsService clientDetailsService;

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients
    	.inMemory()
    	.withClient(clientId)
    	.secret(clientSecret)
    	.authorizedGrantTypes("password")
    	.authorities("ROLE_USER")
    	.scopes("tracking")
    	.resourceIds(resourceId);
  }

  @Bean
  public TokenStore tokenStore() {
      return new InMemoryTokenStore();
  }
  
  @Override
  public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
    super.configure(oauthServer);
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    endpoints
        .tokenStore(tokenStore())
        .authenticationManager(authenticationManager)
        .tokenServices(getTokenServices())
        .tokenGranter(
            new ParcelshopResourceOwnerPasswordTokenGranter(authenticationManager, endpoints.getTokenServices(), clientDetailsService,
                new DefaultOAuth2RequestFactory(clientDetailsService))).pathMapping("/oauth/token", "/spontaneous/auth/token");
  }

  private TimeToLiveTokenServices getTokenServices() {
    TimeToLiveTokenServices tokenServices = new TimeToLiveTokenServices();
    tokenServices.setTokenStore(tokenStore());
    tokenServices.setSupportRefreshToken(false);
    return tokenServices;
  }

  /**
   * The type Parcel o auth 2 authentication entry point.
   */
  
  public static class ParcelOAuth2AuthenticationEntryPoint extends OAuth2AuthenticationEntryPoint {

  }

}
