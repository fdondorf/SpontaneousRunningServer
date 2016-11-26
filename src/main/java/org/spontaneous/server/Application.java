package org.spontaneous.server;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spontaneous.server.auth.AuthConfig;
import org.spontaneous.server.auth.logic.api.AuthenticationService;
import org.spontaneous.server.auth.logic.impl.AuthenticationServiceImpl;
import org.spontaneous.server.client.ClientConfig;
import org.spontaneous.server.common.CommonConfig;
import org.spontaneous.server.config.ConfigConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

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
