package org.spontaneous.server.usermanagement.controller;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONParser;
import org.spontaneous.server.Application;
import org.spontaneous.server.usermanagement.dao.CustomerRepository;
import org.spontaneous.server.usermanagement.dao.UserRepository;
import org.spontaneous.server.usermanagement.to.UserTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


/**
 * @author Flo Dondorf
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class, initializers = ConfigFileApplicationContextInitializer.class)
@TestPropertySource(locations="classpath:application.properties")
@WebAppConfiguration
public class UserManagementControllerTest {

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private WebApplicationContext webApplicationContext;
    
    private HttpMessageConverter mappingJackson2HttpMessageConverter;
    

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
            .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
            .findAny()
            .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }
    
    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        // Delete all user and customer
        this.userRepository.deleteAll();
        this.customerRepository.deleteAll();

    }

    @Test
    public void registerTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/register")
                .content(this.json(new UserTO("Flo", "Dondorf", "test@test.de", "password")))
                .contentType(contentType))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
//    @Test
//    public void getUserInfoTest() throws Exception {
//    	
//    	// Register Testuser
//    	List<RoleEntity> rolesToAdd = new ArrayList<RoleEntity>();
//		rolesToAdd.add(new RoleEntity("ROLE_USER"));
//    	UserEntity user = userRepository.save(new UserEntity("Flo", "Dondorf", 
//    			"test@test.de", "password", rolesToAdd));
//    	UserEntity savedUser = userRepository.save(user);
//    	
//        this.mockMvc.perform(MockMvcRequestBuilders.post("/spontaneous/userinfo")
//                .content(savedUser.getEmail())
//                .contentType(contentType))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }
    
    @SuppressWarnings("unchecked")
	protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        String jsonString = mockHttpOutputMessage.getBodyAsString();
        return jsonString;
    }
    
    private JSONObject stringToJSON(String stringToParse) {
    	//JSONParser parser = JSONParser.parseJSON(stringToParse); //new JSONParser();
    	JSONObject json = (JSONObject)JSONParser.parseJSON(stringToParse); 
    	
    	return json;
    }
}
