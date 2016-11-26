package org.spontaneous.server.usermanagement.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The {@link ApplicationPersistenceEntity persistent entity} for a track.
 *
 * @author fdondorf
 */
@Entity
@Table(name = "TRACK")
public class TrackEntity {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)  
	private Long id;
	
	private String name;
	
	private Long totalDistance;
	
	private Long totalDuration;
	
	private Long creationTime;
	
	  //private StaffMemberEntity user;
	
	private Long averageSpeed;
	
	private String trackData;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(unique = true)
	public String getName() {
	  return this.name;
	}

    public void setName(String name) {
      this.name = name;
    }

    public String getTrackData() {
      return this.trackData;
    }

    public void setTrackData(String trackData) {
      this.trackData = trackData;
    }

    public Long getTotalDistance() {
      return this.totalDistance;
    }

    
    public void setTotalDistance(Long totalDistance) {
      this.totalDistance = totalDistance;
    }

    public Long getTotalDuration() {
      return this.totalDuration;
    }

    public void setTotalDuration(Long totalDuration) {
      this.totalDuration = totalDuration;
    }


    public Long getCreationTime() {
      return this.creationTime;
    }

    public void setCreationTime(Long creationTime) {
      this.creationTime = creationTime;
    }

//  /**
//   * @return user
//   */
//  @ManyToOne
//  public StaffMemberEntity getUser() {
//
//    return this.user;
//  }
//
//  /**
//   * @param user new value of {@link #getUser}.
//   */
//  public void setUser(StaffMemberEntity user) {
//
//    this.user = user;
//  }


    public Long getAverageSpeed() {
      return this.averageSpeed;
    }

    public void setAverageSpeed(Long averageSpeed) {
      this.averageSpeed = averageSpeed;
    }

}
