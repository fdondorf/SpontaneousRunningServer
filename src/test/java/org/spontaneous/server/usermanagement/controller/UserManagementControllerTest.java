package org.spontaneous.server.usermanagement.controller;

import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.spontaneous.server.AbstractSpontaneousIntegrationTest;
import org.spontaneous.server.Application;
import org.spontaneous.server.trackmanagement.dao.TrackRepository;
import org.spontaneous.server.usermanagement.api.Gender;
import org.spontaneous.server.usermanagement.dao.UserRepository;
import org.spontaneous.server.usermanagement.to.UserTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.http.MediaType;
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
@TestPropertySource(locations="classpath:application-test.properties")
@WebAppConfiguration
public class UserManagementControllerTest extends AbstractSpontaneousIntegrationTest {

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private TrackRepository trackRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;
    
    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        // Delete tracks
        trackRepository.deleteAll();
        
        // Delete all user
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
        this.mockMvc.perform(MockMvcRequestBuilders.post("/spontaneous/updateUser")
                .content(this.json(new UserTO("Flo", "Dondorf", "test@test.de", "password", Gender.MALE.getName())))
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
    
}
