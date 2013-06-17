package com.cannon.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import net.sf.json.JSONObject;



public class JSON {
	
	public static JSONObject readJSONFromURL(String url) throws IOException {
		InputStream in = new URL(url).openStream();
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(in, Charset.forName("UTF-8")));
			
			String jsonText = readAll(br);
			
			return JSONObject.fromObject(jsonText);
		}
		finally {
			in.close();
		}
	}
	
	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		
		int c;
		
		while ((c = rd.read()) != -1) {
			sb.append((char) c);
		}
		
		return sb.toString();
	}
}
