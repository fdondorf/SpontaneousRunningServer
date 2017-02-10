package org.spontaneous.server.client.service.rest;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.security.Principal;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spontaneous.server.auth.logic.api.AuthenticatedUser;
import org.spontaneous.server.client.service.Header;
import org.spontaneous.server.client.service.TrackModel;
import org.spontaneous.server.client.service.TrackResult;
import org.spontaneous.server.client.service.TracksResult;
import org.spontaneous.server.client.service.exception.ApplicationException;
import org.spontaneous.server.client.service.exception.UserNotFoundException;
import org.spontaneous.server.trackmanagement.dao.TrackRepository;
import org.spontaneous.server.trackmanagement.entity.TrackEntity;
import org.spontaneous.server.trackmanagement.mapper.TrackMapper;
import org.spontaneous.server.usermanagement.dao.UserRepository;
import org.spontaneous.server.usermanagement.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TrackManagementController extends AbstractClientAuthController {

	private static final Logger LOG = LoggerFactory.getLogger(TrackManagementController.class);
	
	@Autowired
	private TrackRepository trackRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	/**
	 * Returns Tracks, wrapped in a ResponseEntity.
	   *
	   * @param headerData the HeaderData
	   * @param principal the requested Principal
	   * @return UserInfo wrapped in a ResponseEntitiy
	 * @throws UserPrincipalNotFoundException 
	   */
	  @RequestMapping(value = "/tracks", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	  public ResponseEntity<TracksResult> getTracks(@RequestBody Header headerData, Principal principal) throws UserPrincipalNotFoundException {
		
		  LOG.info("Calling Controller 'tracks'");
			
		  // TODO 
		  // validateInputRequestData(headerData);

		  TracksResult result = new TracksResult();
		  
		  // User validation
		  AuthenticatedUser authUser = getAuthUser(principal);
		  if (authUser == null) throw new UserPrincipalNotFoundException("For the given principal no user was found...");
		  
		  UserEntity user = userRepository.findByEmail(authUser.getUsername());

		  Iterable<TrackEntity> tracks = trackRepository.findTracksByUserId(user.getId());
		  Iterator<TrackEntity> iter = tracks.iterator();
		  TrackEntity trackEntity;
		  while(iter.hasNext()) {
			  trackEntity = iter.next();
			  result.addTrack(TrackMapper.mapTrackEntityToTrackModel(trackEntity));
		  }
		  
		  return new ResponseEntity<TracksResult>(result, HttpStatus.OK);
	  }
	  
	  @RequestMapping(value = "/tracks/{id}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	  public ResponseEntity<TrackResult> getTrackDetails(@PathVariable Long id, @RequestBody Header headerData, Principal principal) throws UserPrincipalNotFoundException {
		  
		  LOG.info("Calling Controller 'trackdetails'");
			
		  // TODO: Validate Params and Security, AppVersion
		  // validateInputRequestData(headerData);
		  
		  // User validation
		  AuthenticatedUser authUser = getAuthUser(principal);
		  if (authUser == null) throw new UserPrincipalNotFoundException("For the given principal no user was found...");

		  // Get data
		  TrackResult result = new TrackResult();
		  TrackEntity trackEntity = trackRepository.findOne(id);
		  if (trackEntity != null) {
			  result.setTrackDetails(TrackMapper.mapTrackEntityToTrackModel(trackEntity));
		  }
		  
		  return new ResponseEntity<TrackResult>(result, HttpStatus.OK);
	  }

	  @RequestMapping(value = "/updateTrack", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	  public ResponseEntity<Void> updateTrack(@RequestBody TrackModel track, Principal principal) throws UserPrincipalNotFoundException {
		  
		  LOG.info("Calling Controller 'updateTrack'");
			
		  // TODO: Validate AppVersion
		  // validateInputRequestData(track);
		  
		  // User validation
		  AuthenticatedUser authUser = getAuthUser(principal);
		  if (authUser == null) throw new UserPrincipalNotFoundException("For the given principal no user was found...");

		  // Save data
		  TrackEntity trackEntity = TrackMapper.mapTrackModelToEntity(track);
		  
		  // TODO: Delete this workaround
		  if (track.getUserId() == null)
			  track.setUserId(1L);
		  
		  trackEntity.setUser(userRepository.findOne(Long.valueOf(track.getUserId())));
		  
		  // Set name if it is empty
		  if (trackEntity.getName() == null) {
			  trackEntity.setName("Aktivität " + trackEntity.getId());
		  }
		  
		  trackEntity = trackRepository.save(trackEntity);
		  if (trackEntity == null)
			  throw new ApplicationContextException("Error during creation of new track...");
 
		  return new ResponseEntity<Void>(HttpStatus.OK);
	  }
	  
	  @RequestMapping(value = "/track", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	  public ResponseEntity<Void> createTrack(@RequestBody TrackModel track, Principal principal) throws UserPrincipalNotFoundException {
		  
		  LOG.info("Calling Controller 'createTrack' with params " + track.toString());
			
		  // TODO: Validate AppVersion
		  // validateInputRequestData(track);
		  
		  // User validation
		  AuthenticatedUser authUser = getAuthUser(principal);
		  if (authUser == null) throw new UserPrincipalNotFoundException("For the given principal no user was found...");

		  // Save data
		  TrackEntity trackEntity = TrackMapper.mapTrackModelToEntity(track);
		  
		  // TODO: Delete this workaround
		  if (track.getUserId() == -1) {
			  track.setUserId(1L);
			  LOG.info("Set userId of track to 1");
		  }
		  
		  UserEntity user = userRepository.findOne(Long.valueOf(track.getUserId()));
		  if (user == null) throw new UserNotFoundException("For the given id no user was found...");
		  trackEntity.setUser(userRepository.findOne(Long.valueOf(track.getUserId())));
		  
		  // Set name if it is empty
		  if (trackEntity.getName() == null) {
			  trackEntity.setName("Aktivität " + trackEntity.getId());
		  }
		  
		  trackEntity = trackRepository.save(trackEntity);
		  if (trackEntity == null)
			  throw new ApplicationContextException("Error during creation of new track...");
 
		  return new ResponseEntity<Void>(HttpStatus.OK);
	  }

	  @RequestMapping(value = "/tracks/delete/{id}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	  public ResponseEntity<Void> deleteTrack(@PathVariable Long id, @RequestBody Header headerData, Principal principal) throws UserPrincipalNotFoundException {
		  
		  LOG.info("Calling Controller 'deleteTrack' with id [" + id + "]");
			
		  // TODO: Validate AppVersion
		  // validateInputRequestData(headerData);
		  
		  // User validation
		  AuthenticatedUser authUser = getAuthUser(principal);
		  if (authUser == null) throw new UserPrincipalNotFoundException("For the given principal no user was found...");

		  // Delete track
		  try {
			  trackRepository.delete(id);
		  } catch (Exception exc) {
			  LOG.error("Eror during deleteting track with id [" + id+ "]");
			  throw new ApplicationException(exc.getMessage(), exc);
		  }
		  return new ResponseEntity<Void>(HttpStatus.OK);
	  }
	  
}
