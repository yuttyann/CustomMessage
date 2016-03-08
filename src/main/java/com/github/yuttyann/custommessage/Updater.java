package com.github.yuttyann.custommessage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilderFactory;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Updater implements Listener {

	Main plugin;

	private Boolean enable;
	private PluginDescriptionFile yml;
	private String currentVersion;
	private String pluginName;
	private String pluginURL;
	private String version;

	public Updater(Main plugin) {
		this.plugin = plugin;
		this.enable = false;
		this.yml = plugin.getDescription();
		this.currentVersion = yml.getVersion();
		this.pluginName = yml.getName();
		setUp();
		updateCheck();
		sendCheckMessage();
	}

	public String getName() {
		return pluginName;
	}

	public String getVersion() {
		return version;
	}

	public String getCurrentVersion() {
		return currentVersion;
	}

	public String getURL() {
		return pluginURL;
	}

	public void setUp() {
		try {
			URL url = new URL("http://versionview.yuttyann44581.net/" + getName() + "/");
			InputStream input = url.openConnection().getInputStream();
			Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(input);
			Node node = document.getElementsByTagName("p").item(0);
			NodeList nodelist = node.getChildNodes();
			version = nodelist.item(0).getTextContent();
			version = version.trim();
			pluginURL = nodelist.item(2).getTextContent();
			pluginURL = pluginURL.trim();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void updateCheck() {
		if(CustomMessageConfig.getConfig().getBoolean("UpdateChecker")) {
			if((!getVersion().equals(getCurrentVersion())) && (Double.valueOf(getVersion()) > Double.valueOf(getCurrentVersion()))) {
				enable = true;
				if(CustomMessageConfig.getConfig().getBoolean("AutoDownload")) {
					download();
				}
			} else {
				enable = false;
			}
		}
	}

	public void download() {
		try {
			URL url = new URL(getURL());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setAllowUserInteraction(false);
			conn.setInstanceFollowRedirects(true);
			conn.setRequestMethod("GET");
			conn.connect();
			int httpStatusCode = conn.getResponseCode();
			if (httpStatusCode != HttpURLConnection.HTTP_OK) {
				throw new Exception();
			}
			InputStream in = conn.getInputStream();
			File file = new File("plugins/" + getName() + "/" + getName() + " v"+ getVersion() + ".jar");
			FileOutputStream out = new FileOutputStream(file, false);
			byte[] b = new byte[4096];
			int readByte = 0;
			while (-1 != (readByte = in.read(b))) {
				out.write(b, 0, readByte);
			}
			in.close();
			out.close();
			Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "[" + getName() + " v"+ getVersion() + ".jar] のダウンロードが終了しました。");
			Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "[" + getName() + " v"+ getVersion() + ".jar]" + " 保存先: plugins/" + getName() + "/");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendCheckMessage() {
		if(enable) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "最新のバージョンが存在します。v" + getVersion() + "にアップデートしてください。");
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "URL: " + getURL());
		}
	}

	public void sendCheckMessage(Player player) {
		if(enable) {
			if(!player.isOp()) {
				return;
			}
			player.sendMessage(ChatColor.RED + "最新のバージョンが存在します。v" + getVersion() + "にアップデートしてください。");
			player.sendMessage(ChatColor.RED + "URL: " + getURL());
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		sendCheckMessage(event.getPlayer());
	}
}
