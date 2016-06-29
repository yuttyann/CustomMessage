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
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.github.yuttyann.custommessage.file.Config;

public class Updater implements Listener {

	Main plugin;

	private ConsoleCommandSender sender;
	private PluginDescriptionFile yml;
	private Boolean enable;
	private Boolean error;
	private String currentVersion;
	private String content;
	private String pluginName;
	private String pluginURL;
	private String version;

	public Updater(Main plugin) {
		this.plugin = plugin;
		this.enable = false;
		this.error = false;
		this.yml = plugin.getDescription();
		this.currentVersion = yml.getVersion();
		this.pluginName = yml.getName();
		this.sender = Bukkit.getConsoleSender();
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
			version = nodelist.item(0).getTextContent().trim();
			pluginURL = nodelist.item(2).getTextContent().trim();
			content = nodelist.item(4).getTextContent().trim();
		} catch (MalformedURLException e) {
			sender.sendMessage(ChatColor.RED + "エラーが発生しました。URLが不正または不明です(MalformedURLException)");
			errorMessageTemplate();
		} catch (Exception e) {
			sender.sendMessage(ChatColor.RED + "エラーが発生しました。何らかのエラーが発生しました(Exception)");
			errorMessageTemplate();
		}
	}

	private void download() {
		InputStream input = null;
		FileOutputStream output = null;
		sender.sendMessage(ChatColor.LIGHT_PURPLE + "プラグインのダウンロードを開始しています...");
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
			File file = new File(plugin.getDataFolder(), "Downloads/" + getPluginName() + " v"+ getVersion() + ".jar");
			input = conn.getInputStream();
			output = new FileOutputStream(file, false);
			byte[] b = new byte[4096];
			int readByte = 0;
			while (-1 != (readByte = input.read(b))) {
				output.write(b, 0, readByte);
			}
			sender.sendMessage(ChatColor.GOLD + prefix + " のダウンロードが終了しました。");
			sender.sendMessage(ChatColor.GOLD + prefix + " ファイルサイズ: " + getSize(file.length()));
			sender.sendMessage(ChatColor.GOLD + prefix + " 保存場所: plugins/" + getPluginName() + "/Downloads/" + getPluginName() + " v"+ getVersion() + ".jar");
		} catch (FileNotFoundException e) {
			sender.sendMessage(ChatColor.RED + "エラーが発生しました。ファイルが存在しません(FileNotFoundException)");
			errorMessageTemplate();
		} catch (ProtocolException e) {
			sender.sendMessage(ChatColor.RED + "エラーが発生しました。使用しているプロトコルでエラーが発生または不正です(ProtocolException)");
			errorMessageTemplate();
		} catch (MalformedURLException e) {
			sender.sendMessage(ChatColor.RED + "エラーが発生しました。URLが不正または不明です(MalformedURLException)");
			errorMessageTemplate();
		} catch (IOException e) {
			sender.sendMessage(ChatColor.RED + "エラーが発生しました。入出力処理が失敗しました(IOException)");
			errorMessageTemplate();
		} catch (Exception e) {
			sender.sendMessage(ChatColor.RED + "エラーが発生しました。何らかのエラーが発生しました(Exception)");
			errorMessageTemplate();
		} finally {
			if (output != null) {
				try {
					output.flush();
					output.close();
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

	private void updateCheck() {
		if(Config.getBoolean("UpdateChecker")) {
			if((!getVersion().equals(getCurrentVersion())) && (Double.parseDouble(getVersion()) > Double.parseDouble(getCurrentVersion()))) {
				enable = true;
				if(Config.getBoolean("AutoDownload")) {
					download();
				}
			} else {
				enable = false;
			}
		}
	}

	private void sendCheckMessage() {
		if(enable && !error && getPluginURL() != null) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "最新のバージョンが存在します。v" + getVersion() + "にアップデートしてください。");
			Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "アップデート内容: " + getContent());
			Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "URL: " + getPluginURL());
		}
	}

	private void sendCheckMessage(Player player) {
		if(enable && !error && getPluginURL() != null) {
			if(!player.isOp()) {
				return;
			}
			player.sendMessage(ChatColor.GREEN + "最新のバージョンが存在します。v" + getVersion() + "にアップデートしてください。");
			player.sendMessage(ChatColor.GREEN + "アップデート内容: " + getContent());
			player.sendMessage(ChatColor.GREEN + "URL: " + getPluginURL());
		}
	}

	private void errorMessageTemplate() {
		if(!error) {
			error = true;
			sender.sendMessage(ChatColor.RED + "プラグイン名: " + getPluginName());
			sender.sendMessage(ChatColor.RED + "バージョン: v" + getVersion());
			sender.sendMessage(ChatColor.RED + "ダウンロードページ: " + "http://versionview.yuttyann44581.net/" + getPluginName() + "/");
			sender.sendMessage(ChatColor.RED + "連絡用ページ: http://file.yuttyann44581.net/contact/");
			sender.sendMessage(ChatColor.RED + "時間が経っても解決しない場合は、製作者に連絡してください。");
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerJoin(PlayerJoinEvent event) {
		sendCheckMessage(event.getPlayer());
	}
}
