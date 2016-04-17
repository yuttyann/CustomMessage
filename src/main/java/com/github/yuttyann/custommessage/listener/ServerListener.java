package com.github.yuttyann.custommessage.listener;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import com.github.yuttyann.custommessage.Main;
import com.github.yuttyann.custommessage.TimeManager;
import com.github.yuttyann.custommessage.Version;
import com.github.yuttyann.custommessage.config.CustomMessageConfig;
import com.github.yuttyann.custommessage.packet.ProtocolLibPacket;

public class ServerListener implements Listener {

	Main plugin;

	public ServerListener(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onServerListPing(ServerListPingEvent event) {
		if (CustomMessageConfig.getBoolean("FakeMaxPlayer.Enable")) {
			int MaxPlayer = CustomMessageConfig.getConfig().getInt("FakeMaxPlayer.MaxPlayer");
			event.setMaxPlayers(MaxPlayer);
		}
		if (CustomMessageConfig.getBoolean("Motd.Enable")) {
			Integer players = event.getNumPlayers();
			Integer maxplayer = Bukkit.getMaxPlayers();
			String servername = Bukkit.getServerName();
			String motd = getMotd();
			motd = motd.replace("%players", players.toString());
			motd = motd.replace("%maxplayers", maxplayer.toString());
			motd = motd.replace("%servername", servername);
			motd = motd.replace("%version", Version.getVersion());
			motd = motd.replace("%time", TimeManager.getTime());
			motd = motd.replace("&", "§");
			event.setMotd(motd);
		}
		setPlayerCountMessage();
	}

	private String getMotd() {
		List<String> list = CustomMessageConfig.getStringList("Motd.Message");
		String motd = "";
		if(list != null && list.size() >= 1 && list.size() <= 2) {
			motd = list.get(0);
			if(list.size() == 2) {
				motd = motd + "\n" + list.get(1);
			}
		}
		return motd;
	}

	private void setPlayerCountMessage() {
		if (isProtocolLib() == null) {
			return;
		} else if (isProtocolLib()) {
			new ProtocolLibPacket(plugin).sendPlayerCountMessage();
		} else {
			return;
		}
	}

	private Boolean isProtocolLib() {
		return plugin.protocollib;
	}
}
