package com.github.yuttyann.custommessage.listener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import com.github.yuttyann.custommessage.Main;
import com.github.yuttyann.custommessage.TimeManager;
import com.github.yuttyann.custommessage.file.Files;
import com.github.yuttyann.custommessage.file.Yaml;
import com.github.yuttyann.custommessage.packet.ProtocolLibPacket;
import com.github.yuttyann.custommessage.util.Utils;

public class ServerListener implements Listener {

	Main plugin;

	public ServerListener(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onServerListPing(ServerListPingEvent event) {
		Integer onlineplayers = null;
		Integer fakemaxplayer = null;
		Integer maxplayer = null;
		String servername = null;
		String servermotd = null;
		Yaml config = Files.getConfig();
		if (config.getBoolean("RandomMotd.Enable")) {
			servermotd = getRandomMotd();
			onlineplayers = event.getNumPlayers();
			maxplayer = Bukkit.getMaxPlayers();
			servername = Bukkit.getServerName();
			servermotd = replaceMotd(servermotd, onlineplayers, maxplayer, servername);
			event.setMotd(servermotd);
		} else if (config.getBoolean("Motd.Enable")) {
			servermotd = getMotd();
			onlineplayers = event.getNumPlayers();
			maxplayer = Bukkit.getMaxPlayers();
			servername = Bukkit.getServerName();
			servermotd = replaceMotd(servermotd, onlineplayers, maxplayer, servername);
			event.setMotd(servermotd);
		}
		if (config.getBoolean("FakeMaxPlayer.Enable")) {
			fakemaxplayer = config.getInt("FakeMaxPlayer.MaxPlayer");
			event.setMaxPlayers(fakemaxplayer);
		}
		if (config.getBoolean("ServerIcon.Enable")) {
			try {
				event.setServerIcon(Bukkit.loadServerIcon(getServerIcon()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (Utils.isPluginEnabled("ProtocolLib")) {
			ProtocolLibPacket.sendPlayerCountMessage();
		}
	}

	private String getRandomMotd() {
		Yaml config = Files.getConfig();
		List<String> messagelist = new ArrayList<String>(config.getConfigurationSection("RandomMotd.Message").getKeys(false));
		List<String> randomlist = config.getStringList("RandomMotd.Message." + messagelist.get(new Random().nextInt(messagelist.size())));
		String motd = "";
		if (randomlist != null && randomlist.size() > 0 && randomlist.size() < 3) {
			motd = randomlist.get(0);
			if (randomlist.size() == 2) {
				motd = motd + "\n" + randomlist.get(1);
			}
		}
		return motd;
	}

	private String getMotd() {
		List<String> list = Files.getConfig().getStringList("Motd.Message");
		String motd = "";
		if (list != null && list.size() > 0 && list.size() < 3) {
			motd = list.get(0);
			if (list.size() == 2) {
				motd = motd + "\n" + list.get(1);
			}
		}
		return motd;
	}

	private File getServerIcon() {
		File[] icons = new File(plugin.getDataFolder(), "ServerIcon").listFiles();
		int size = new Random().nextInt(icons.length);
		if (icons.length > 0) {
			return icons[size];
		}
		return null;
	}

	private String replaceMotd(String motd, Integer players, Integer maxplayer, String servername) {
		motd = motd.replace("%players", players.toString());
		motd = motd.replace("%maxplayers", maxplayer.toString());
		motd = motd.replace("%servername", servername);
		motd = motd.replace("%version", Utils.getVersion());
		motd = motd.replace("%time", TimeManager.getTimesofDay());
		motd = motd.replace("&", "§");
		return motd;
	}
}
