package com.github.yuttyann.custommessage.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebJson {

	private String url;

	public WebJson(String url) {
		this.url = url;
	}

	public String getJsonToString() {
		StringBuilder builder = null;
		HttpURLConnection connection = null;
		InputStream input = null;
		InputStreamReader stream = null;
		BufferedReader reader = null;
		try {
			builder = new StringBuilder();
			connection = (HttpURLConnection) new URL(url).openConnection();
			connection.setRequestMethod("GET");
			connection.setInstanceFollowRedirects(false);
			connection.connect();
			input = connection.getInputStream();
			stream = new InputStreamReader(input);
			reader = new BufferedReader(stream);
			String line;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
		} catch (Exception e) {
			System.out.println("Jsonの取得に失敗しました。");
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return builder.toString();
	}

	public String getURL() {
		return url;
	}
}
