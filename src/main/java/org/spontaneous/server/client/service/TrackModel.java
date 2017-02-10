package org.spontaneous.server.client.service;

import java.util.ArrayList;
import java.util.List;

/**
 * Model containing the data of a track
 * @author fdondorf
 *
 */
public class TrackModel extends Header {

	private Long id;
	private String name;
	private Float totalDistance;
	private Long totalDuration;
	private Long creationTime;
	private Long userId;
	private List<SegmentModel> segments = new ArrayList<SegmentModel>();
	
	public TrackModel(){;}
			
	public TrackModel(Long id, String name, Float totalDistance, Long totalDuration, Long creationTime, Long userId) {
		super();
		this.id = id;
		this.name = name;
		this.totalDistance = totalDistance;
		this.totalDuration = totalDuration;
		this.creationTime = creationTime;
		this.userId = userId;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Float getTotalDistance() {
		return totalDistance;
	}
	
	public void setTotalDistance(Float totalDistance) {
		this.totalDistance = totalDistance;
	}
	
	public Long getTotalDuration() {
		return totalDuration;
	}

	public void setTotalDuration(Long totalDuration) {
		this.totalDuration = totalDuration;
	}

	public Long getCreationTime() {
		return creationTime;
	}
	
	public void setCreationTime(Long creationTime) {
		this.creationTime = creationTime;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<SegmentModel> getSegments() {
		return segments;
	}

	public void setSegments(List<SegmentModel> segments) {
		this.segments = segments;
	}
	
	public boolean addSegment(SegmentModel segment) {
		if (this.segments == null) {
			this.segments = new ArrayList<SegmentModel>();
		}
		return this.segments.add(segment);
	}

	@Override
	public String toString() {
		return "TrackModel [id=" + id + ", name=" + name + ", totalDistance=" + totalDistance + ", totalDuration="
				+ totalDuration + ", creationTime=" + creationTime + ", userId=" + userId + ", segments=" + segments
				+ "]";
	}

}
