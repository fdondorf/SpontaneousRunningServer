package org.spontaneous.server.usermanagement.controller;

import static org.junit.Assert.assertTrue;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.spontaneous.server.AbstractSpontaneousIntegrationTest;
import org.spontaneous.server.Application;
import org.spontaneous.server.client.service.Header;
import org.spontaneous.server.client.service.TrackModel;
import org.spontaneous.server.client.service.UserModel;
import org.spontaneous.server.common.data.TrackEntityBuilder;
import org.spontaneous.server.common.data.UserEntityBuilder;
import org.spontaneous.server.trackmanagement.dao.TrackRepository;
import org.spontaneous.server.trackmanagement.entity.TrackEntity;
import org.spontaneous.server.trackmanagement.mapper.TrackMapper;
import org.spontaneous.server.usermanagement.api.Gender;
import org.spontaneous.server.usermanagement.dao.UserRepository;
import org.spontaneous.server.usermanagement.entity.RoleEntity;
import org.spontaneous.server.usermanagement.entity.UserEntity;
import org.spontaneous.server.usermanagement.mapper.UserMapper;
import org.spontaneous.server.usermanagement.to.UserTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


/**
 * @author Flo Dondorf
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class, initializers = ConfigFileApplicationContextInitializer.class)
@TestPropertySource(locations="classpath:application-test.properties")
@WebAppConfiguration
public class UserManagementControllerTest extends AbstractSpontaneousIntegrationTest {

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private TrackRepository trackRepository;

    private UserEntity user;
    
    @Before
    public void setup() throws Exception {
    	
    	super.setup();

        // Delete tracks
        trackRepository.deleteAll();
        
        // Delete all user
        this.userRepository.deleteAll();
        
        // Create user
        this.user = userRepository.save(UserEntityBuilder.aDefaultUserEntity(
        		roleRepository.findOne(1L)).build());

    }

    @After
    public void tearDown() {
    	// Delete all user and tracks
    	this.trackRepository.deleteAll();
        this.userRepository.deleteAll();
    }
    
    @Test
    public void registerTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/spontaneous/register")
                .content(this.json(new UserTO("Flo", "Dondorf", "test@test.de", "password", Gender.MALE.getName())))
                .contentType(contentType))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test
    public void updateUserTest() throws Exception {
    	
    	// Given
    	String token = getToken(this.user.getEmail(), this.user.getPassword());

    	// When
    	UserEntity userEntity = UserEntityBuilder.aDefaultUserEntity(roleRepository.findOne(1L))
    			.build();
    	userEntity = userRepository.save(userEntity);
    	
    	UserModel userModel = UserMapper.mapUserEntityToUserModel(userEntity);
    	userModel = addHeader(userModel, "android");
    	userModel.setFirstname("UpdatedFirstname");
    	userModel.setLastname("UpdatedLastname");
    	userModel.setGender(Gender.FEMALE.getName());
    			
    	ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.post("/spontaneous/secure/user/update")
    			.with(bearerToken(token))
    	        .content(json(userModel))
    	        .contentType(contentType));
    	        
    	// Then
    	result.andExpect(MockMvcResultMatchers.status().isOk());
    	    	
    	revokeToken(token);
    }
    
    @Test
    public void getUserInfoTest() throws Exception {
    	
    	// Given
    	String token = getToken(this.user.getEmail(), this.user.getPassword());

    	Header header = new Header();
    	header = addHeader(header, "android");
    	
    	// Get user info
        this.mockMvc.perform(MockMvcRequestBuilders.post("/spontaneous/secure/userinfo")
        		.with(bearerToken(token))
                .content(json(header))
                .contentType(contentType))
                .andExpect(MockMvcResultMatchers.status().isOk());
        
    	revokeToken(token);
    }
    
}
