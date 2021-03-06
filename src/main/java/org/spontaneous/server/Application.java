package org.spontaneous.server;

import java.util.Arrays;

import org.apache.catalina.connector.Connector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spontaneous.server.auth.AuthConfig;
import org.spontaneous.server.auth.logic.api.AuthenticationService;
import org.spontaneous.server.auth.logic.impl.AuthenticationServiceImpl;
import org.spontaneous.server.client.ClientConfig;
import org.spontaneous.server.common.CommonConfig;
import org.spontaneous.server.config.ConfigConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

//@SpringBootApplication
@Import({ //
    // Import all components of the application
	AuthConfig.class,
	ClientConfig.class,
	CommonConfig.class,
	ConfigConfig.class
})
@EnableAutoConfiguration
public class Application {

	private static final Logger LOG = LoggerFactory.getLogger(Application.class);

	@Value("${spontaneous.version}")
	private String spontaneousVersion;

	@Value("${spontaneous.client.apiVersion}")
	private String spontaneousApiVersion;

	@Value("${spontaneous.client.validApiVersions}")
	private String spontaneousValidApiVersions;

	@Value("${spontaneous.client.android.validAppVersions}")
	private String spontaneousAndroidValidAppVersions;
	
	@Value("${tomcat.ajp.port}")
	int ajpPort;

	@Value("${tomcat.ajp.remoteauthentication}")
	String remoteAuthentication;

	@Value("${tomcat.ajp.enabled}")
	boolean tomcatAjpEnabled;
	
	public static void main(String[] args) {
		
		LOG.info("Start ApplicationContext...");
		
		ApplicationContext ctx = SpringApplication.run(Application.class);
		
		String[] beanNames = ctx.getBeanDefinitionNames();
	    Arrays.sort(beanNames);
	    for (String beanName : beanNames) {
	    	System.out.println(beanName);
	    }
	        
	}
	
	  /**
	   * Defines the authenticationService bean.
	   *
	   * @return the authenticationService bean
	   */
	  @Bean
	  public AuthenticationService authenticationService() {
	    return new AuthenticationServiceImpl();
	  }
	  
	  /**
	   * Define the javax.validation Validator bean.
	   * <p>
	   * You can autowire a Validator instance in any class to use this validator bean.
	   *
	   * @return a LocalValidatorFactoryBean
	   */
	  @Bean
	  public javax.validation.Validator localValidatorFactoryBean() {
	    return new LocalValidatorFactoryBean();
	  }

	  
	  @Bean
	  public EmbeddedServletContainerFactory servletContainer() {

	      TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
	      if (tomcatAjpEnabled)
	      {
	          Connector ajpConnector = new Connector("AJP/1.3");
	          ajpConnector.setProtocol("AJP/1.3");
	          ajpConnector.setPort(ajpPort);
	          ajpConnector.setSecure(false);
	          ajpConnector.setAllowTrace(false);
	          ajpConnector.setScheme("http");
	          tomcat.addAdditionalTomcatConnectors(ajpConnector);
	      }

	      return tomcat;
	  }
	  
//    @Bean
//    CommandLineRunner init(final UserRepository userRepository) {
//      
//      return new CommandLineRunner() {
//
//        @Override
//        public void run(String... arg0) throws Exception {
//        	List<RoleEntity> roles = new ArrayList<RoleEntity>();
//        	roles.add(new RoleEntity("ROLE_USER"));
//        	userRepository.save(new UserEntity("fdondorf", "password", roles));
//          
//        }
//        
//      };
//
//    }
    
}

//@Configuration
//class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {
//
//  @Autowired
//  UserRepository userRepository;
//
//  @Override
//  public void init(AuthenticationManagerBuilder auth) throws Exception {
//    auth.userDetailsService(userDetailsService());
//  }
//
//  @Bean
//  UserDetailsService userDetailsService() {
//    return new UserDetailsService() {
//
//      @Override
//      public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        UserEntity user = userRepository.findByUsername(username);
//        if(user != null) {
//        return new User(user.getUsername(), user.getPassword(), true, true, true, true,
//                AuthorityUtils.createAuthorityList("USER"));
//        } else {
//          throw new UsernameNotFoundException("could not find the user '"
//                  + username + "'");
//        }
//      }
//      
//    };
//  }
//}
//
//@EnableWebSecurity
//@Configuration
//class WebSecurityConfig extends WebSecurityConfigurerAdapter {
// 
//  @Override
//  protected void configure(HttpSecurity http) throws Exception {
//    http.authorizeRequests().anyRequest().fullyAuthenticated().and().
//    httpBasic().and().
//    csrf().disable();
//  }
//  
//}
