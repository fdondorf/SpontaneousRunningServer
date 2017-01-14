package org.spontaneous.server.trackmanagement.api;

import java.util.Date;

public interface Track {
	
	Long getId();
	
	String getName();
	
	Double getTotalDistance();
	
	Integer getTotalDuration();
	
	Date getCreationTime();
	
	Long getUserId();
	

}
