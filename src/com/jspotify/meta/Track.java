package com.jspotify.meta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.jspotify.json.JSON;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Track implements SpotifyItem {

	private Album album;

	private List<Artist> artists = new ArrayList<Artist>();;

	private String trackID;
	private String trackName;
	private String trackNumber;
	private String popularity;
	private String length;

	public Track(String id) throws IOException {
		JSONObject json = JSON.readJSONFromURL("http://ws.spotify.com/lookup/1/.json?uri=" + id);

		setFields(json);
	}

	private void setFields(JSONObject json) throws IOException {
		JSONObject track = json.getJSONObject("track");
		this.trackID = track.getString("href");
		this.trackName = track.getString("name");
		this.trackNumber = track.getString("track-number");
		this.popularity = track.getString("popularity");
		this.length = track.getString("length");

		this.album = new Album(null, track.getJSONObject("album").getString("name"));

		JSONArray artistArr = track.getJSONArray("artists");
		for (int i = 0; i < artistArr.size(); i++) {
			JSONObject objAt = artistArr.getJSONObject(i);
			if (objAt.has("href")) {
				this.artists.add(new Artist(objAt.getString("href"), objAt.getString("name")));
			}
		}
	}

	public Album getAlbum() {
		return album;
	}

	public List<Artist> getArtists() {
		return artists;
	}

	public String getTrackID() {
		return trackID;
	}

	public String getTrackName() {
		return trackName;
	}

	public String getTrackNumber() {
		return trackNumber;
	}

	public String getPopularity() {
		return popularity;
	}

	public String getLength() {
		return length;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("track name: ");
		sb.append(getTrackName());
		sb.append("\ttrack id: ");
		sb.append(getTrackID());

		return sb.toString();
	}


}
