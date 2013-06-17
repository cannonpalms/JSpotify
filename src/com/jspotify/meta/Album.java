package com.jspotify.meta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.jspotify.json.JSON;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Album {

	private String albumName;
	private String albumID;
	private String released;
	private Artist artist;

	private List<Track> tracks = new ArrayList<Track>();

	public Album(String id) throws IOException {
		JSONObject json = JSON.readJSONFromURL("http://ws.spotify.com/lookup/1/.json?uri=" + id + "&extras=track");
		setFields(json);

		this.albumID = id;
	}

	public Album(String id, String name) {
		this.albumName = name;
	}

	private void setFields(JSONObject json) throws IOException {
		JSONObject album = json.getJSONObject("album");
		this.albumName = album.getString("name");
		this.released = album.getString("released");
		this.artist = new Artist(album.getString("artist-id"), album.getString("artist"));

		JSONArray tracksArr = album.getJSONArray("tracks");
		for (int i = 0; i < tracksArr.size(); i++) {
			String trackID = tracksArr.getJSONObject(i).getString("href");
			this.tracks.add(new Track(trackID));
		}
	}

	public static boolean isUS(String id) throws IOException {
		JSONObject json = JSON.readJSONFromURL("http://ws.spotify.com/lookup/1/.json?uri=" + id + "&extras=track");
		JSONObject album = json.getJSONObject("album");
		JSONObject avail = album.getJSONObject("availability");
		if (avail.toString().equals("null")) {
			return false;
		}
		else {
			String territories = avail.getString("territories").toLowerCase();
			return territories.contains("us") || territories.contains("worldwide");
		}
	}

	public String getName() {
		return this.albumName;
	}

	public String getReleased() {
		return this.released;
	}

	public Artist getArtist() {
		return artist;
	}

	public String getID() {
		return this.albumID;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("album name: ");
		sb.append(getName());
		sb.append("\talbum id: ");
		sb.append(getID());
		sb.append("\n\n");

		for (Track track : this.tracks) {
			sb.append(track.toString());
			sb.append("\n");
		}

		return sb.toString();
	}
}
