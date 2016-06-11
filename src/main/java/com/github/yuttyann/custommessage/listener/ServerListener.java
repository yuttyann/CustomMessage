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
import com.github.yuttyann.custommessage.file.Config;
import com.github.yuttyann.custommessage.packet.ProtocolLibPacket;
import com.github.yuttyann.custommessage.util.TimeUtils;
import com.github.yuttyann.custommessage.util.Utils;
import com.github.yuttyann.custommessage.util.VersionUtils;

public class ServerListener implements Listener {

	Main plugin;

	public ServerListener(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onServerListPing(ServerListPingEvent event) {
		if (Config.getBoolean("FakeMaxPlayer.Enable")) {
			int MaxPlayer = Config.getConfig().getInt("FakeMaxPlayer.MaxPlayer");
			event.setMaxPlayers(MaxPlayer);
		}
		Integer players = event.getNumPlayers();
		Integer maxplayer = Bukkit.getMaxPlayers();
		String servername = Bukkit.getServerName();
		String motd = null;
		if (Config.getBoolean("RandomMotd.Enable")) {
			motd = getRandomMotd();
			motd = replaceMotd(motd, players, maxplayer, servername);
			motd = motd.replace("&", "§");
			event.setMotd(motd);
		} else if (Config.getBoolean("Motd.Enable")) {
			motd = getMotd();
			motd = replaceMotd(motd, players, maxplayer, servername);
			event.setMotd(motd);
		} else {
			motd = Utils.getServer().getMotd();
			motd = replaceMotd(motd, players, maxplayer, servername);
			event.setMotd(motd);
		}
		if (Config.getBoolean("ServerIcon.Enable")) {
			try {
				event.setServerIcon(Bukkit.loadServerIcon(getServerIcon()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		setPlayerCountMessage();
	}

	private String getRandomMotd() {
		ArrayList<String> arraylist = new ArrayList<String>();
		for (String set : Utils.getConfigSection("RandomMotd.Message", false)) {
			arraylist.add(set);
		}
		int size = Utils.getRandom().nextInt(arraylist.size());
		String random = arraylist.get(size);
		List<String> list = Config.getStringList("RandomMotd.Message." + random);
		String motd = "";
		if (list != null && list.size() >= 1 && list.size() <= 2) {
			motd = list.get(0);
			if (list.size() == 2) {
				motd = motd + "\n" + list.get(1);
			}
		}
		return motd;
	}

	private String getMotd() {
		List<String> list = Config.getStringList("Motd.Message");
		String motd = "";
		if (list != null && list.size() >= 1 && list.size() <= 2) {
			motd = list.get(0);
			if (list.size() == 2) {
				motd = motd + "\n" + list.get(1);
			}
		}
		return motd;
	}

	private String replaceMotd(String motd, Integer players, Integer maxplayer, String servername) {
		motd = motd.replace("%players", players.toString());
		motd = motd.replace("%maxplayers", maxplayer.toString());
		motd = motd.replace("%servername", servername);
		motd = motd.replace("%version", VersionUtils.getVersion());
		motd = motd.replace("%time", TimeUtils.getTime());
		motd = motd.replace("&", "§");
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

	private void setPlayerCountMessage() {
		if (Utils.isPluginEnabled("ProtocolLib")) {
			new ProtocolLibPacket(plugin).sendPlayerCountMessage();
		}
	}
}
