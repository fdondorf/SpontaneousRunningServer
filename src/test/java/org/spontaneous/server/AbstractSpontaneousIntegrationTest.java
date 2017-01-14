package org.spontaneous.server;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

import java.nio.charset.Charset;

import javax.servlet.Filter;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Abstract class for Unit Tests for the Crud-Repository
 * 
 * @author fdondorf
 * @param <springSecurityFilterChain>
 *
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Application.class, initializers = ConfigFileApplicationContextInitializer.class)
@TestPropertySource(locations="classpath:application.properties")
@WebAppConfiguration
public abstract class AbstractSpontaneousIntegrationTest extends AbstractSpontaneousTest{
	
	protected MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

	
	protected MockMvc mockMvc;
	
    @Autowired
    protected WebApplicationContext webApplicationContext;
    
    @Autowired
    private Filter springSecurityFilterChain;
    
	@Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).addFilter(springSecurityFilterChain).build();
	}
	
	public String getToken(String username, String password) throws Exception {
		ResultActions resultLogin = this.mockMvc.perform(MockMvcRequestBuilders.post("/spontaneous/secure/auth/token").with(httpBasic("spontaneous-client","spontaneous-secret"))
				.param("client_id", "spontaneous-client")
				.param("client_secret", "spontaneous-secret")
				.param("grant_type", "password")
				.param("username", username)
				.param("password", password)
                .contentType(contentType));
		
		// Assertions
		resultLogin
			.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("bearer")))
			.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("access_token")))
			.andExpect(MockMvcResultMatchers.status().isOk());
		
		// Get token
		String tokenRepsonse = resultLogin.andReturn().getResponse().getContentAsString();
		String token = tokenRepsonse.substring(tokenRepsonse.indexOf(':') + 2, tokenRepsonse.indexOf(',') - 1);
		
		return token;
		
	}

	public void revokeToken(String token) throws Exception {
		ResultActions resultLogin = this.mockMvc.perform(MockMvcRequestBuilders.post("/spontaneous/secure/auth/revoke")
				.with(bearerToken(token))
				.param("token", token)
                .contentType(contentType));
		
		// Assertions
		resultLogin.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	public RequestPostProcessor bearerToken(final String token) {
        return mockRequest -> {
            mockRequest.addHeader("Authorization", "Bearer " + token);
            return mockRequest;
        };
    }

	
}
