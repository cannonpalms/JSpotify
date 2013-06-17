package com.jspotify.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.jspotify.json.JSON;
import com.jspotify.meta.Album;
import com.jspotify.meta.Artist;
import com.jspotify.meta.SpotifyItem;
import com.jspotify.meta.Track;


public class Search {

	public static final int ARTIST = 0;
	public static final int ALBUM = 1;
	public static final int TRACK = 2;

	private List<SpotifyItem> results;

	public Search(int type, String query) {
		try {
			this.results = search(type, buildURL(type, query));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<SpotifyItem> getResults() {
		return this.results;
	}

	private List<SpotifyItem> search(int type, String url) throws IOException {
		List<SpotifyItem> results = new ArrayList<SpotifyItem>();
		switch (type) {
		
		case ARTIST:
			results.addAll(searchArtist(url));
			break;
		case ALBUM:
			results.addAll(searchAlbum(url));
			break;
		case TRACK:
			results.addAll(searchTrack(url));
			break;
		default:
			throw new IllegalArgumentException("invalid type");
			
		}
		
		return results;
	}
	
	private List<Artist> searchArtist(String url) throws IOException {
		List<Artist> results = new ArrayList<Artist>();
		
		JSONObject base = JSON.readJSONFromURL(url);
		JSONObject info = base.getJSONObject("info");
		String type = info.getString("type") + "s";
		JSONArray json = base.getJSONArray(type);
		
		for (int i = 0; i < json.size(); i++) {
			JSONObject current = json.getJSONObject(i);
			results.add(new Artist(current.getString("href")));
		}
		
		return results;
	}
	
	private List<Album> searchAlbum(String url) throws IOException {
		List<Album> results = new ArrayList<Album>();
		
		JSONObject base = JSON.readJSONFromURL(url);
		JSONObject info = base.getJSONObject("info");
		String type = info.getString("type") + "s";
		JSONArray json = base.getJSONArray(type);
		
		for (int i = 0; i < json.size(); i++) {
			JSONObject current = json.getJSONObject(i);
			results.add(new Album(current.getString("href")));
		}
		
		return results;
	}
	
	private List<Track> searchTrack(String url) throws IOException {
		List<Track> results = new ArrayList<Track>();
		
		JSONObject base = JSON.readJSONFromURL(url);
		JSONObject info = base.getJSONObject("info");
		String type = info.getString("type") + "s";
		JSONArray json = base.getJSONArray(type);
		
		for (int i = 0; i < json.size(); i++) {
			JSONObject current = json.getJSONObject(i);
			results.add(new Track(current.getString("href")));
		}
		
		return results;
	}

	private static String buildURL(int type, String query) {
		StringBuilder sb = new StringBuilder();
		sb.append("http://ws.spotify.com/search/1/");

		switch (type) {

		case ARTIST:
			sb.append("artist.json?q=");
			break;
		case ALBUM:
			sb.append("album.json?q=");
			break;
		case TRACK:
			sb.append("track.json?q=");
			break;
		default:
			throw new IllegalArgumentException("invalid type");
		}

		sb.append(convert(query));

		return sb.toString();
	}

	private static String convert(String query) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < query.length(); i++) {
			char current = query.charAt(i);
			if (current == ' ') {
				sb.append("%20"); 
			}
			else {
				sb.append(current);
			}
		}

		return sb.toString();
	}

	public static void main(String[] args) {
		String query = "The Blue Book";
		int type = Search.ALBUM;
		Search search = new Search(type, query);
		for (SpotifyItem item : search.getResults()) {
			System.out.println(item);
		}
	}
}
