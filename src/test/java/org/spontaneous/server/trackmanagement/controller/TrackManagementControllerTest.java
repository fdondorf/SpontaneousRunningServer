package org.spontaneous.server.trackmanagement.controller;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.spontaneous.server.AbstractSpontaneousIntegrationTest;
import org.spontaneous.server.client.service.Header;
import org.spontaneous.server.client.service.TrackModel;
import org.spontaneous.server.common.data.TrackEntityBuilder;
import org.spontaneous.server.common.data.UserEntityBuilder;
import org.spontaneous.server.trackmanagement.dao.TrackRepository;
import org.spontaneous.server.trackmanagement.entity.TrackEntity;
import org.spontaneous.server.trackmanagement.mapper.TrackMapper;
import org.spontaneous.server.usermanagement.dao.RoleRepository;
import org.spontaneous.server.usermanagement.dao.UserRepository;
import org.spontaneous.server.usermanagement.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


/**
 * @author Flo Dondorf
 */
public class TrackManagementControllerTest extends AbstractSpontaneousIntegrationTest {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private TrackRepository trackRepository;
    
    private UserEntity user;
    private List<TrackEntity> tracks = new ArrayList<TrackEntity>();
   
    
    @Before
    public void setup() throws Exception {
    	
    	super.setup();
    	
        // Delete all user and tracks
    	this.trackRepository.deleteAll();
        this.userRepository.deleteAll();
        
        // Create user
        this.user = userRepository.save(UserEntityBuilder.aDefaultUserEntity(
        		roleRepository.findOne(1L)).build());
        
        // Create tracks
        createTracks(user);

    }

    @After
    public void tearDown() {
    	// Delete all user and tracks
    	this.trackRepository.deleteAll();
        this.userRepository.deleteAll();
    }
	
    @Test
    public void createTrackTest() throws Exception {
    	
    	// Given
		String token = getToken(this.user.getEmail(), this.user.getPassword());

		// When
		TrackEntity trackEntity = TrackEntityBuilder.aDefaultTrackEntity()
					.withDefaultSegments(2)
					.withUser(this.user).build();
		TrackModel trackModel = TrackMapper.mapTrackEntityToTrackModel(trackEntity);
		trackModel.setUserId(trackEntity.getUser().getId());
		trackModel = addHeader(trackModel, "android");
		
		
        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.post("/spontaneous/secure/track")
        		.with(bearerToken(token))
        		.content(json(trackModel))
                .contentType(contentType));
        
        // Then
        result.andExpect(MockMvcResultMatchers.status().isOk());
        assertTrue(Matchers.iterableWithSize(4).matches(trackRepository.findAll()));

    	
        revokeToken(token);
    }

    @Test
    public void updateTrackTest() throws Exception {
    	
    	// Given
		String token = getToken(this.user.getEmail(), this.user.getPassword());

		// When
		TrackEntity trackEntity = TrackEntityBuilder.aDefaultTrackEntity()
					.withDefaultSegments(2)
					.withUser(this.user).build();
		TrackModel trackModel = TrackMapper.mapTrackEntityToTrackModel(trackEntity);
		trackModel.setUserId(trackEntity.getUser().getId());
		trackModel = addHeader(trackModel, "android");
		
        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.post("/spontaneous/secure/updateTrack")
        		.with(bearerToken(token))
        		.content(json(trackModel))
                .contentType(contentType));
        
        // Then
        result.andExpect(MockMvcResultMatchers.status().isOk());
        assertTrue(Matchers.iterableWithSize(4).matches(trackRepository.findAll()));
    	
        revokeToken(token);
    }
    
    
    @Test
    public void deleteTrackTest() throws Exception {
    	
    	// Given
		String token = getToken(this.user.getEmail(), this.user.getPassword());

		// When
        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.post("/spontaneous/secure/tracks/delete/{id}", tracks.get(0).getId())
        		.with(bearerToken(token))
        		.content(json(createHeader()))
                .contentType(contentType));
        
        // Then
        result.andExpect(MockMvcResultMatchers.status().isOk());
        assertTrue(Matchers.iterableWithSize(2).matches(trackRepository.findAll()));
      
        revokeToken(token);
    }
 
	private Header createHeader() {
		Header header = new Header();
		header = addHeader(header, "android");
		return header;
	}

	@Test
    public void getTracksTest() throws Exception {
		
		// Given
		String token = getToken(this.user.getEmail(), this.user.getPassword());
		
		// When
        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.post("/spontaneous/secure/tracks").with(bearerToken(token))
        		.content(json(createHeader()))
                .contentType(contentType));
        
        // Then
        result.andExpect(MockMvcResultMatchers.status().isOk())
        	.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Track 0")))
        	.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Track 1")))
        	.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Track 2")))
        	.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("\"userId\":" + this.user.getId())));

        revokeToken(token);
    }
	
	@Test
    public void getTrackDetailsTest() throws Exception {
		
		// Given
		String token = getToken(this.user.getEmail(), this.user.getPassword());
		List<TrackEntity> tracks = trackRepository.findTracksByUserId(user.getId());
		
		// When
        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.post("/spontaneous/secure/tracks/{id}", tracks.get(0).getId())
        		.with(bearerToken(token))
        		.content(json(createHeader()))
                .contentType(contentType));
        
        // Then
        result.andExpect(MockMvcResultMatchers.status().isOk())
        	.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("trackDetails")));
        
        revokeToken(token);
    }
	
	/*************************************************
	 * Create Testdata
	 * **********************************************/
	
    private void createTracks(UserEntity user) {
        tracks.clear();
    	for (int i = 0; i < 3; i++) {
    		this.tracks.add(trackRepository.save(
    				TrackEntityBuilder.aDefaultTrackEntity()
    					.withName("Track " + i)
    					.withUser(user)
    					.withDefaultSegments(3)
    					.build()));
		}
	}
}
