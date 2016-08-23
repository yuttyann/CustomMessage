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
import javax.xml.parsers.ParserConfigurationException;

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
import org.xml.sax.SAXException;

import com.github.yuttyann.custommessage.file.Config;

public class Updater implements Listener {

	Main plugin;

	private static Updater updater;
	private ConsoleCommandSender sender;
	private PluginDescriptionFile pluginyml;
	private Boolean enable;
	private Boolean error;
	private String currentversion;
	private String content;
	private String pluginname;
	private String pluginurl;
	private String siteurl;
	private String version;

	public Updater(Main plugin) {
		this.plugin = plugin;
		updater = this;
		enable = false;
		error = false;
		pluginyml = plugin.getDescription();
		currentversion = pluginyml.getVersion();
		pluginname = pluginyml.getName();
		siteurl = "http://versionview.yuttyann44581.net/" + getPluginName() + "/";
		sender = Bukkit.getConsoleSender();
		setUp();
		updateCheck();
		sendCheckMessage(null, true);
	}

	public static Updater getUpdater() {
		return updater;
	}

	public Boolean getEnable() {
		return enable;
	}

	public Boolean getError() {
		return error;
	}

	public String getCurrentVersion() {
		return currentversion;
	}

	public String getContent() {
		return content;
	}

	public String getFileName() {
		return getPluginName() + " v" + getVersion() + ".jar";
	}

	public String getPluginName() {
		return pluginname;
	}

	public String getPluginURL() {
		return pluginurl;
	}

	public String getSiteURL() {
		return siteurl;
	}

	public String getVersion() {
		return version;
	}

	private void setUp() {
		try {
			URL url = new URL(getSiteURL());
			InputStream input = url.openConnection().getInputStream();
			Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(input);
			Node node = document.getElementsByTagName("p").item(0);
			NodeList nodelist = node.getChildNodes();
			version = nodelist.item(0).getTextContent().trim();
			pluginurl = nodelist.item(2).getTextContent().trim();
			content = nodelist.item(4).getTextContent().trim();
		} catch (MalformedURLException e) {
			sender.sendMessage(ChatColor.RED + "URLが不正または不明です。(MalformedURLException)");
			errorMessageTemplate();
		} catch (IOException e) {
			sender.sendMessage(ChatColor.RED + "入出力処理が失敗しました。(IOException)");
			errorMessageTemplate();
		} catch (ParserConfigurationException e) {
			sender.sendMessage(ChatColor.RED + "重大な構成エラーが発生しました。(ParserConfigurationException)");
			errorMessageTemplate();
		} catch (SAXException e) {
			sender.sendMessage(ChatColor.RED + "エラーが発生しました。(SAXException)");
			errorMessageTemplate();
		}
	}

	private void fileDownload() {
		String filename = null;
		File file = null;
		InputStream input = null;
		FileOutputStream output = null;
		sender.sendMessage(ChatColor.LIGHT_PURPLE + "プラグインのダウンロードを開始しています...");
		try {
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
			File downloads = new File(plugin.getDataFolder(), "Downloads");
			if (!downloads.exists()) {
				downloads.mkdir();
			}
			filename = getFileName();
			file = new File(plugin.getDataFolder(), "Downloads/" + filename);
			input = conn.getInputStream();
			output = new FileOutputStream(file, false);
			byte[] b = new byte[4096];
			int readByte = 0;
			while (-1 != (readByte = input.read(b))) {
				output.write(b, 0, readByte);
			}
		} catch (FileNotFoundException e) {
			sender.sendMessage(ChatColor.RED + "ファイルが存在しません。(FileNotFoundException)");
			errorMessageTemplate();
		} catch (ProtocolException e) {
			sender.sendMessage(ChatColor.RED + "使用しているプロトコルでエラーが発生または不正です。(ProtocolException)");
			errorMessageTemplate();
		} catch (MalformedURLException e) {
			sender.sendMessage(ChatColor.RED + "URLが不正または不明です。(MalformedURLException)");
			errorMessageTemplate();
		} catch (IOException e) {
			sender.sendMessage(ChatColor.RED + "入出力処理が失敗しました。(IOException)");
			errorMessageTemplate();
		} catch (Exception e) {
			sender.sendMessage(ChatColor.RED + "エラーが発生しました。(Exception)");
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
			String prefix = "[" + filename + "]";
			sender.sendMessage(ChatColor.GOLD + prefix + " ダウンロードが終了しました。");
			sender.sendMessage(ChatColor.GOLD + prefix + " ファイルサイズ: " + getSize(file.length()));
			sender.sendMessage(ChatColor.GOLD + prefix + " 保存場所: plugins/" + getPluginName() + "/Downloads/" + filename);
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
			if(Double.parseDouble(getVersion()) > Double.parseDouble(getCurrentVersion())) {
				enable = true;
				if(Config.getBoolean("AutoDownload")) {
					fileDownload();
				}
			} else {
				enable = false;
			}
		}
	}

	private void sendCheckMessage(Player player, boolean console) {
		if(getEnable() && !getError() && getPluginURL() != null) {
			if (console) {
				sender.sendMessage(ChatColor.GREEN + "最新のバージョンが存在します。v" + getVersion() + "にアップデートしてください。");
				sender.sendMessage(ChatColor.GREEN + "アップデート内容: " + getContent());
			} else {
				if(!player.isOp()) {
					return;
				}
				player.sendMessage(ChatColor.GREEN + "最新のバージョンが存在します。v" + getVersion() + "にアップデートしてください。");
				player.sendMessage(ChatColor.GREEN + "アップデート内容: " + getContent());
			}
		}
	}

	private void errorMessageTemplate() {
		if(!getError()) {
			error = true;
			sender.sendMessage(ChatColor.RED + "プラグイン名: " + getPluginName());
			sender.sendMessage(ChatColor.RED + "バージョン: v" + getVersion());
			sender.sendMessage(ChatColor.RED + "取得ページ: " + getSiteURL());
			sender.sendMessage(ChatColor.RED + "連絡用ページ: http://file.yuttyann44581.net/contact/");
			sender.sendMessage(ChatColor.RED + "時間が経っても解決しない場合は、製作者に連絡してください。");
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerJoin(PlayerJoinEvent event) {
		sendCheckMessage(event.getPlayer(), false);
	}
}
