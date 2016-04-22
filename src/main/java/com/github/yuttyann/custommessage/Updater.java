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
	private String content;
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

	private String getCurrentVersion() {
		return currentVersion;
	}

	private String getContent() {
		return content;
	}

	private String getPluginName() {
		return pluginName;
	}

	private String getPluginURL() {
		return pluginURL;
	}

	private String getVersion() {
		return version;
	}

	private void setUp() {
		try {
			URL url = new URL("http://versionview.yuttyann44581.net/" + getPluginName() + "/");
			InputStream input = url.openConnection().getInputStream();
			Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(input);
			Node node = document.getElementsByTagName("p").item(0);
			NodeList nodelist = node.getChildNodes();
			version = nodelist.item(0).getTextContent();
			version = version.trim();
			pluginURL = nodelist.item(2).getTextContent();
			pluginURL = pluginURL.trim();
			content = nodelist.item(4).getTextContent();
			content = content.trim();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void updateCheck() {
		if (CustomMessageConfig.getBoolean("UpdateChecker")) {
			if ((!getVersion().equals(getCurrentVersion())) && (Double.parseDouble(getVersion()) > Double.parseDouble(getCurrentVersion()))) {
				enable = true;
				if (CustomMessageConfig.getBoolean("AutoDownload")) {
					download();
				}
			} else {
				enable = false;
			}
		}
	}

	private void download() {
		try {
			String prefix = "[" + getPluginName() + " v"+ getVersion() + ".jar]";
			URL url = new URL(getPluginURL());
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
			File file = new File(plugin.getDataFolder(), getPluginName() + " v"+ getVersion() + ".jar");
			FileOutputStream out = new FileOutputStream(file, false);
			byte[] b = new byte[4096];
			int readByte = 0;
			while (-1 != (readByte = in.read(b))) {
				out.write(b, 0, readByte);
			}
			in.close();
			out.close();
			Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + prefix + " のダウンロードが終了しました。");
			Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + prefix + " ファイルサイズ: " + getSize(file.length()));
			Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + prefix + " 保存先: plugins/" + getPluginName() + "/" + getPluginName() + " v"+ getVersion() + ".jar");
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
		if (enable) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "最新のバージョンが存在します。v" + getVersion() + "にアップデートしてください。");
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "アップデート内容: " + getContent());
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "URL: " + getPluginURL());
		}
	}

	private void sendCheckMessage(Player player) {
		if (enable) {
			if(!player.isOp()) {
				return;
			}
			player.sendMessage(ChatColor.RED + "最新のバージョンが存在します。v" + getVersion() + "にアップデートしてください。");
			player.sendMessage(ChatColor.RED + "アップデート内容: " + getContent());
			player.sendMessage(ChatColor.RED + "URL: " + getPluginURL());
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerJoin(PlayerJoinEvent event) {
		sendCheckMessage(event.getPlayer());
	}
}
