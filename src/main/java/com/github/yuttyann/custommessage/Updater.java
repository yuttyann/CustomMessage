package com.github.yuttyann.custommessage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilderFactory;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.github.yuttyann.custommessage.config.CustomMessageConfig;

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

	private String getName() {
		return pluginName;
	}

	private String getVersion() {
		return version;
	}

	private String getCurrentVersion() {
		return currentVersion;
	}

	private String getURL() {
		return pluginURL;
	}

	private void setUp() {
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

	private void updateCheck() {
		if(CustomMessageConfig.getBoolean("UpdateChecker")) {
			if((!getVersion().equals(getCurrentVersion())) && (Double.parseDouble(getVersion()) > Double.parseDouble(getCurrentVersion()))) {
				enable = true;
				if(CustomMessageConfig.getBoolean("AutoDownload")) {
					download();
				}
			} else {
				enable = false;
			}
		}
	}

	private void download() {
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
			File file = new File(plugin.getDataFolder(), getName() + " v"+ getVersion() + ".jar");
			FileOutputStream out = new FileOutputStream(file, false);
			byte[] b = new byte[4096];
			int readByte = 0;
			while (-1 != (readByte = in.read(b))) {
				out.write(b, 0, readByte);
			}
			in.close();
			out.close();
			Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "[" + getName() + " v"+ getVersion() + ".jar] のダウンロードが終了しました。");
			Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "[" + getName() + " v"+ getVersion() + ".jar] ファイルサイズ: " + getSize(file.length()));
			Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "[" + getName() + " v"+ getVersion() + ".jar] 保存先: plugins/" + getName() + "/" + getName() + " v"+ getVersion() + ".jar");
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

	private String getSize(long size) {
		if (1024 > size) {
			return size + " Byte";
		} else if (1024 * 1024 > size) {
			double dsize = size;
			dsize = dsize / 1024;
			BigDecimal bi = new BigDecimal(String.valueOf(dsize));
			double value = bi.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
			return value + " KB";
		} else {
			double dsize = size;
			dsize = dsize / 1024 / 1024;
			BigDecimal bi = new BigDecimal(String.valueOf(dsize));
			double value = bi.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
			return value + " MB";
		}
	}

	private void sendCheckMessage() {
		if(enable) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "最新のバージョンが存在します。v" + getVersion() + "にアップデートしてください。");
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "URL: " + getURL());
		}
	}

	private void sendCheckMessage(Player player) {
		if(enable) {
			if(!player.isOp()) {
				return;
			}
			player.sendMessage(ChatColor.RED + "最新のバージョンが存在します。v" + getVersion() + "にアップデートしてください。");
			player.sendMessage(ChatColor.RED + "URL: " + getURL());
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerJoin(PlayerJoinEvent event) {
		sendCheckMessage(event.getPlayer());
	}
}
