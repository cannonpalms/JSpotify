package com.jspotify.meta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.jspotify.json.JSON;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Artist {

	private String artistID;
	private String artistName;
	
	private List<Album> albums = new ArrayList<Album>();
	
	public Artist(String id) throws IOException {
		JSONObject json = JSON.readJSONFromURL("http://ws.spotify.com/lookup/1/.json?uri=" + id + "&extras=album");
		
		setFields(json);
	}
	
	public Artist(String id, String name) {
		this.artistID = id;
		this.artistName = name;
	}
	
	private void setFields(JSONObject json) throws IOException {
		JSONObject obj = json.getJSONObject("artist");
		
		this.artistID = obj.getString("href");
		this.artistName = obj.getString("name");
		
		JSONArray albumArr = obj.getJSONArray("albums");
		for (int i = 0; i < albumArr.size(); i++) {
			JSONObject objAt = albumArr.getJSONObject(i);
			JSONObject album = objAt.getJSONObject("album");
			String albumID = album.getString("href");
			if (Album.isUS(albumID)) {
				this.albums.add(new Album(albumID));
			}
		}
	}
	
	public String getID() {
		return this.artistID;
	}
	
	public String getName() {
		return this.artistName;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("artist name: ");
		sb.append(getName());
		sb.append("\tartist id: ");
		sb.append(getID());
		sb.append("\n\n\n\n");
		
		for (Album album : this.albums) {
			sb.append(album.toString());
			sb.append("\n\n\n");
		}
		
		return sb.toString();
	}
	
	public static void main(String[] args) {
		String artistID = "spotify:artist:79hrYiudVcFyyxyJW0ipTy";
		try {
			Artist artist = new Artist(artistID);
			System.out.println(artist);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
