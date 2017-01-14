package org.spontaneous.server.client.service;

import java.util.ArrayList;
import java.util.List;

public class TracksResult {

	private List<TrackModel> tracks;

	public List<TrackModel> getTracks() {
		return tracks;
	}

	public void setTracks(List<TrackModel> tracks) {
		this.tracks = tracks;
	}
	
	public boolean addTrack(TrackModel trackModel) {
		if (tracks == null) {
			tracks = new ArrayList<TrackModel>();
		}
		return tracks.add(trackModel);
	}
	
}
