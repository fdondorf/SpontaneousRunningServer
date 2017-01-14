package org.spontaneous.server.trackmanagement.dao;

import java.util.List;

import org.spontaneous.server.trackmanagement.entity.TrackEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface TrackRepository extends CrudRepository<TrackEntity, Long>{

//	@Query("Select new org.spontaneous.server.trackmanagement.to.TrackInfo(t.id, t.name, t.creationTime, "
//			+ "t.totalDuration, t.totalDistance, t.user.id, t.averageSpeed) from TrackEntity t "
//			+ "where t.user.id = :userId")
	@Query("Select new org.spontaneous.server.trackmanagement.entity.TrackEntity(t.id, t.name, t.creationTime, "
	+ "t.totalDistance, t.totalDuration, t.user) from TrackEntity t where t.user.id = :userId")
	List<TrackEntity> findTracksByUserId(@Param("userId") Long userId);
	 
}
