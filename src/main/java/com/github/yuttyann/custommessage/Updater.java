package com.github.yuttyann.custommessage;

import java.awt.Desktop;
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
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.bukkit.Bukkit;
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
import com.github.yuttyann.custommessage.util.Utils;

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
	private String historyurl;
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

	public String getPluginFileName() {
		return getPluginName() + " v" + getVersion() + ".jar";
	}

	public String getPluginName() {
		return pluginname;
	}

	public String getPluginURL() {
		return pluginurl;
	}

	public String getHistoryURL() {
		return historyurl;
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
			historyurl = nodelist.item(6).getTextContent().trim();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			errorMessageTemplate();
		} catch (IOException e) {
			e.printStackTrace();
			errorMessageTemplate();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			errorMessageTemplate();
		} catch (SAXException e) {
			e.printStackTrace();
			errorMessageTemplate();
		}
	}

	private void updateCheck() {
		if(Config.getBoolean("UpdateChecker")) {
			if(Double.parseDouble(getVersion()) > Double.parseDouble(getCurrentVersion())) {
				enable = true;
				boolean first = false;
				File file = new File(plugin.getDataFolder(), "更新履歴.txt");
				ArrayList<String> list = new ArrayList<String>();
				if (file.exists()) {
					list = Utils.getTextList(file);
				}
				if(Config.getBoolean("AutoDownload")) {
					sender.sendMessage("§dプラグインのダウンロードを開始しています...");
					if (!file.exists()) {
						first = true;
					}
					fileDownload(getHistoryURL(), file, false);
					fileDownload(getPluginURL(), new File(plugin.getDataFolder(), "Downloads/" + getPluginFileName()), true);
				}
				if (Config.getBoolean("OpenTextFile") && !getError()) {
					openTextFile(list, first);
				}
			} else {
				enable = false;
			}
		}
	}

	private void openTextFile(ArrayList<String> list, boolean first) {
		File file = new File(plugin.getDataFolder(), "更新履歴.txt");
		if (!first) {
			if (list.equals(Utils.getTextList(getHistoryURL()))) {
				return;
			}
		}
		Desktop desktop = Desktop.getDesktop();
		try {
			desktop.open(new File(file.getPath()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void fileDownload(String url, File file, boolean message) {
		InputStream input = null;
		FileOutputStream output = null;
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
			conn.setAllowUserInteraction(false);
			conn.setInstanceFollowRedirects(true);
			conn.setRequestMethod("GET");
			conn.connect();
			int httpStatusCode = conn.getResponseCode();
			if (httpStatusCode != HttpURLConnection.HTTP_OK) {
				conn.disconnect();
				return;
			}
			File downloads = new File(plugin.getDataFolder(), "Downloads");
			if (!downloads.exists()) {
				downloads.mkdir();
			}
			input = conn.getInputStream();
			output = new FileOutputStream(file, false);
			byte[] b = new byte[4096];
			int readByte = 0;
			while (-1 != (readByte = input.read(b))) {
				output.write(b, 0, readByte);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			errorMessageTemplate();
		} catch (ProtocolException e) {
			e.printStackTrace();
			errorMessageTemplate();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			errorMessageTemplate();
		} catch (IOException e) {
			e.printStackTrace();
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
			if (message && !getError()) {
				String prefix = "[" + file.getName() + "]";
				sender.sendMessage("§6" + prefix + " ダウンロードが終了しました。");
				sender.sendMessage("§6" + prefix + " ファイルサイズ: " + getSize(file.length()));
				sender.sendMessage("§6" + prefix + " 保存場所: " + file.getPath().replace("\\", "/"));
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

	private void sendCheckMessage(Player player, boolean console) {
		if(getEnable() && !getError() && getPluginURL() != null) {
			if (console) {
				sender.sendMessage("§a最新のバージョンが存在します。v" + getVersion() + "にアップデートしてください。");
				sender.sendMessage("§aアップデート内容: " + getContent());
			} else {
				if(!player.isOp()) {
					return;
				}
				player.sendMessage("§a最新のバージョンが存在します。v" + getVersion() + "にアップデートしてください。");
				player.sendMessage("§aアップデート内容: " + getContent());
			}
		}
	}

	private void errorMessageTemplate() {
		if(!getError()) {
			error = true;
			sender.sendMessage("§cプラグイン名: " + getPluginName());
			sender.sendMessage("§cバージョン: v" + getVersion());
			sender.sendMessage("§c取得ページ: " + getSiteURL());
			sender.sendMessage("§c連絡用ページ: http://file.yuttyann44581.net/contact/");
			sender.sendMessage("§c解決しない場合は、製作者に連絡してください。");
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	private void onPlayerJoin(PlayerJoinEvent event) {
		sendCheckMessage(event.getPlayer(), false);
	}
}