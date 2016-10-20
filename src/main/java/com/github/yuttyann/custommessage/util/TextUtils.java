package com.github.yuttyann.custommessage.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.bukkit.Bukkit;

public class TextUtils {

	public static ArrayList<String> getTextList(File file) {
		ArrayList<String> list = null;
		BufferedReader buReader = null;
		try {
			buReader = new BufferedReader(new FileReader(file));
			list = new ArrayList<String>();
			String line;
			while ((line = buReader.readLine()) != null) {
				list.add(line);
			}
		} catch (FileNotFoundException e) {
			Bukkit.getConsoleSender().sendMessage("§cエラー[" + e.toString() + "]");
		} catch (IOException e) {
			Bukkit.getConsoleSender().sendMessage("§cエラー[" + e.toString() + "]");
		} finally {
			if (buReader != null) {
				try {
					buReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	public static ArrayList<String> getTextList(String url) {
		ArrayList<String> list = null;
		InputStream input = null;
		InputStreamReader inReader = null;
		BufferedReader buReader = null;
		try {
			input = new URL(url).openStream();
			inReader = new InputStreamReader(input);
			buReader = new BufferedReader(inReader);
			list = new ArrayList<String>();
			String line;
			while ((line = buReader.readLine()) != null) {
				list.add(line);
			}
		} catch (MalformedURLException e) {
			Bukkit.getConsoleSender().sendMessage("§cエラー[" + e.toString() + "]");
		} catch (IOException e) {
			Bukkit.getConsoleSender().sendMessage("§cエラー[" + e.toString() + "]");
		} finally {
			if (buReader != null) {
				try {
					buReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (inReader != null) {
				try {
					inReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}
}
