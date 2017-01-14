package org.spontaneous.server.trackmanagement.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.spontaneous.server.AbstractSpontaneousIntegrationTest;
import org.spontaneous.server.common.data.TrackEntityBuilder;
import org.spontaneous.server.common.data.UserEntityBuilder;
import org.spontaneous.server.trackmanagement.entity.TrackEntity;
import org.spontaneous.server.usermanagement.dao.UserRepository;
import org.spontaneous.server.usermanagement.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Unit Test for the User-Repository
 * @author fdondorf
 *
 */
public class TrackRepositoryTest extends AbstractSpontaneousIntegrationTest {
	
	private static final Float TOTAL_DISTANCE = 9999.99f;
	
	@Autowired
	private TrackRepository trackRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
	@Before
	public void setUp() {
		trackRepository.deleteAll();
		userRepository.deleteAll();
	}
	
	@After
	public void tearDown() {
		trackRepository.deleteAll();
		userRepository.deleteAll();
	}
	  
	@Test
	public void testCreateTrack() {
		
		// Given
		TrackEntity trackEntity = TrackEntityBuilder.aDefaultTrackEntity().withDefaultSegments(3).
				withName("Track 1").withUser(createTestUser()).build();
		
		// When
		TrackEntity savedTrack = trackRepository.save(trackEntity);
		
		// Then
		Assert.assertNotNull(savedTrack.getId());
	}

	@Test
	public void testFindTracks() {
		
		// Given
		UserEntity user = createTestUser();
		List<TrackEntity> generatedTracks = createTracks(user);
		
		// When
		List<TrackEntity> foundTracks = trackRepository.findTracksByUserId(user.getId());
		
		// Then
		Assert.assertNotNull(generatedTracks);
		Assert.assertEquals(generatedTracks.size(), foundTracks.size());
	}
	
	@Test
	public void testFindTrack() {
		
		// Given
		UserEntity user = createTestUser();
		List<TrackEntity> generatedTracks = createTracks(user);
		
		// When
		TrackEntity trackEntity = trackRepository.findOne(generatedTracks.get(1).getId());
		
		// Then
		Assert.assertNotNull(trackEntity);
		Assert.assertEquals("Track 1", trackEntity.getName());
		Assert.assertEquals(TOTAL_DISTANCE, trackEntity.getTotalDistance());
		Assert.assertNotNull(trackEntity.getTrackDetails());
	}
	
	private UserEntity createTestUser() {
		return userRepository.save(UserEntityBuilder.aDefaultUserEntity().build());
	}
	
	
	private List<TrackEntity> createTracks(UserEntity user) {
		List<TrackEntity> tracks = new ArrayList<TrackEntity>();
		TrackEntity trackEntity;
		for (int i = 0; i < 10; i++) {
			trackEntity = trackRepository.save(TrackEntityBuilder.aDefaultTrackEntity().withName("Track " + i)
					.withUser(user)
					.withTotalDistance(TOTAL_DISTANCE)
					.withDefaultSegments(3).build());
			tracks.add(trackEntity);
		}
		
		return tracks;
	}
	
}
