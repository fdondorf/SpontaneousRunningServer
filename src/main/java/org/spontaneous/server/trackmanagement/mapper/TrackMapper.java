package org.spontaneous.server.trackmanagement.mapper;

import org.spontaneous.server.client.service.GeoPointModel;
import org.spontaneous.server.client.service.SegmentModel;
import org.spontaneous.server.client.service.TrackModel;
import org.spontaneous.server.common.util.DateUtil;
import org.spontaneous.server.trackmanagement.entity.TrackEntity;
import org.spontaneous.server.trackmanagement.entity.TrackSegment;
import org.spontaneous.server.trackmanagement.entity.Waypoint;

public class TrackMapper {

	public static TrackEntity mapTrackModelToEntity(TrackModel trackModel) {
		
		TrackEntity trackEntity = new TrackEntity();
		trackEntity.setId(trackModel.getId());
		trackEntity.setName(trackModel.getName());
		trackEntity.setCreationTime(DateUtil.getDateTimeFromTimestamp(trackModel.getCreationTime()));
		trackEntity.setTotalDistance(trackModel.getTotalDistance());
		trackEntity.setTotalDuration(trackModel.getTotalDuration());
		trackEntity.setAverageSpeed(null);

		for (SegmentModel segment : trackModel.getSegments()) {
			trackEntity.addTrackDetail(mapTrackSegmentToSegmentModel(segment));
		}

		return trackEntity;
	}
	
	public static TrackSegment mapTrackSegmentToSegmentModel(SegmentModel segmentModel) {
		
		TrackSegment trackSegment = new TrackSegment();
		trackSegment.setId(segmentModel.getId());
		trackSegment.setStartTime(DateUtil.parse(segmentModel.getStartTimeInMillis()));
		trackSegment.setEndTime(DateUtil.parse(segmentModel.getEndTimeInMillis()));

		for (GeoPointModel geoPoint : segmentModel.getWayPoints()) {
			trackSegment.addWaypoint(mapTrackSegmentToSegmentModel(geoPoint));
		}
		
		return trackSegment;
	}
	
	public static Waypoint mapTrackSegmentToSegmentModel(GeoPointModel geoPoint) {
		
		Waypoint waypoint = new Waypoint();
		waypoint.setId(geoPoint.getId());
		waypoint.setAccurracy(geoPoint.getAccurracy());
		waypoint.setAltitude(geoPoint.getAltitude());
		waypoint.setBearing(geoPoint.getBearing());
		waypoint.setDistance(geoPoint.getDistance());
		waypoint.setLatitude(geoPoint.getLatitude());
		waypoint.setLongitude(geoPoint.getLongitude());
		waypoint.setSegmentId(geoPoint.getSegmentId());
		waypoint.setSpeed(geoPoint.getSpeed());
		waypoint.setTime(geoPoint.getTime());
		
		return waypoint;
	}
	
	public static TrackModel mapTrackEntityToTrackModel(TrackEntity trackEntity) {
		
		TrackModel trackModel = new TrackModel();
		trackModel.setId(trackEntity.getId());
		trackModel.setName(trackEntity.getName());
		trackModel.setCreationTime(DateUtil.getDateTimeFromTimestamp(trackEntity.getCreationTime()));
		trackModel.setTotalDistance(trackEntity.getTotalDistance());
		trackModel.setTotalDuration(trackEntity.getTotalDuration());
		trackModel.setUserId(trackEntity.getUser().getId());
		
		for (TrackSegment segment : trackEntity.getTrackDetails()) {
			trackModel.getSegments().add(mapTrackSegmentToSegmentModel(segment));
		}
		return trackModel;
	}
	
	public static SegmentModel mapTrackSegmentToSegmentModel(TrackSegment trackSegment) {
		
		SegmentModel segmentModel = new SegmentModel();
		segmentModel.setId(trackSegment.getId());
		segmentModel.setStartTimeInMillis(DateUtil.parseToLong(trackSegment.getStartTime()));
		segmentModel.setEndTimeInMillis(DateUtil.parseToLong(trackSegment.getEndTime()));
		
		for (Waypoint waypoint : trackSegment.getWaypoints()) {
			segmentModel.getWayPoints().add(mapWaypointToGeoPointModel(waypoint));
		}
		
		return segmentModel;
	}
	
	
	public static GeoPointModel mapWaypointToGeoPointModel(Waypoint waypoint) {
		
		GeoPointModel geoPointModel = new GeoPointModel();
		geoPointModel.setId(waypoint.getId());
		geoPointModel.setAccurracy(waypoint.getAccurracy());
		geoPointModel.setAltitude(waypoint.getAltitude());
		geoPointModel.setBearing(waypoint.getBearing());
		geoPointModel.setDistance(waypoint.getDistance());
		geoPointModel.setLatitude(waypoint.getLatitude());
		geoPointModel.setLongitude(waypoint.getLongitude());
		geoPointModel.setSegmentId(waypoint.getSegmentId());
		geoPointModel.setSpeed(waypoint.getSpeed());
		geoPointModel.setTime(waypoint.getTime());
		
		return geoPointModel;
	}
	
}
