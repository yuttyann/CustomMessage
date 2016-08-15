package com.github.yuttyann.custommessage.listener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import com.github.yuttyann.custommessage.Main;
import com.github.yuttyann.custommessage.TimeManager;
import com.github.yuttyann.custommessage.file.Config;
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
		if (Config.getBoolean("RandomMotd.Enable")) {
			servermotd = getRandomMotd();
			onlineplayers = event.getNumPlayers();
			maxplayer = Bukkit.getMaxPlayers();
			servername = Bukkit.getServerName();
			servermotd = replaceMotd(servermotd, onlineplayers, maxplayer, servername);
			event.setMotd(servermotd);
		} else if (Config.getBoolean("Motd.Enable")) {
			servermotd = getMotd();
			onlineplayers = event.getNumPlayers();
			maxplayer = Bukkit.getMaxPlayers();
			servername = Bukkit.getServerName();
			servermotd = replaceMotd(servermotd, onlineplayers, maxplayer, servername);
			event.setMotd(servermotd);
		}
		if (Config.getBoolean("FakeMaxPlayer.Enable")) {
			fakemaxplayer = Config.getConfig().getInt("FakeMaxPlayer.MaxPlayer");
			event.setMaxPlayers(fakemaxplayer);
		}
		if (Config.getBoolean("ServerIcon.Enable")) {
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
		List<String> messagelist = new ArrayList<String>(Utils.getConfigSection(Config.getConfig(), "RandomMotd.Message", false));
		List<String> randomlist = Config.getStringList("RandomMotd.Message." + messagelist.get(Utils.getRandom().nextInt(messagelist.size())));
		String motd = "";
		if (randomlist != null && randomlist.size() > 0 && randomlist.size() < 3) {
			motd = randomlist.get(0);
			if (randomlist.size() == 2) {
				motd = motd + Utils.getLineFeedCode() + randomlist.get(1);
			}
		}
		return motd;
	}

	private String getMotd() {
		List<String> list = Config.getStringList("Motd.Message");
		String motd = "";
		if (list != null && list.size() > 0 && list.size() < 3) {
			motd = list.get(0);
			if (list.size() == 2) {
				motd = motd + Utils.getLineFeedCode() + list.get(1);
			}
		}
		return motd;
	}

	private File getServerIcon() {
		File[] icons = new File(plugin.getDataFolder(), "ServerIcon").listFiles();
		int size = Utils.getRandom().nextInt(icons.length);
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
