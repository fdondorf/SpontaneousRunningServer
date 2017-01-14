package org.spontaneous.server.common.data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.spontaneous.server.trackmanagement.entity.TrackEntity;
import org.spontaneous.server.trackmanagement.entity.TrackSegment;
import org.spontaneous.server.trackmanagement.entity.Waypoint;
import org.spontaneous.server.usermanagement.entity.UserEntity;

public class TrackEntityBuilder {

	private TrackEntity entity = new TrackEntity();

	private TrackEntityBuilder() {
		//entity.setId(UUID.randomUUID().getMostSignificantBits());
	}

	public static TrackEntityBuilder aDefaultTrackEntity() {
		TrackEntityBuilder trackEntityBuilder = new TrackEntityBuilder();
		trackEntityBuilder.entity.setName("Track");
		trackEntityBuilder.entity.setCreationTime(LocalDateTime.now());
		trackEntityBuilder.entity.setTotalDistance(12.57f);
		trackEntityBuilder.entity.setTotalDuration(13123123L);
		trackEntityBuilder.entity.setTrackDetails(new ArrayList<TrackSegment>());
		return trackEntityBuilder;
	  }

	public TrackEntityBuilder withDefaultSegments(int numberOfSegments) {
		
		TrackSegment trackSegment;
		for (int i = 0; i < numberOfSegments; i++) {
			trackSegment = new TrackSegment();
			trackSegment.setId(Long.valueOf(i));
			trackSegment.setStartTime("2016-12-11 18:18:18");
			trackSegment.setEndTime("2016-12-11 18:48:18");
			trackSegment.setWaypoints(createWaypoints(trackSegment));
			entity.getTrackDetails().add(trackSegment);
		}
		return this;
	}
	
	  public TrackEntityBuilder withTotalDistance(Float totalDistance) {
		  entity.setTotalDistance(totalDistance);
		  return this;
	  }

	  public TrackEntityBuilder withUser(UserEntity user) {
		  entity.setUser(user);
		  return this;
	  }

	  public TrackEntityBuilder withName(String name) {
		  entity.setName(name);
		  return this;
	  }
	  
	  public TrackEntityBuilder withTrackDetails(List<TrackSegment> segments) {
		  entity.setTrackDetails(segments);
		  return this;
	  }
	  
	public TrackEntity build() {
		return this.entity;
	}
	
	private List<Waypoint> createWaypoints(TrackSegment segment) {
		List<Waypoint> waypoints = new ArrayList<>();
		Waypoint waypoint = null;
		for (int i = 0; i < 100; i++) {
			waypoint = new Waypoint();
			waypoint.setId(Long.valueOf(i));
			waypoint.setLatitude(123123123.123);
			waypoint.setLongitude(3234234.23);
			waypoint.setAltitude(123d);
			waypoint.setAccurracy(4.123534d);
			waypoint.setSegmentId(segment.getId());
			waypoints.add(waypoint);
		}
		return waypoints;
	}
}
