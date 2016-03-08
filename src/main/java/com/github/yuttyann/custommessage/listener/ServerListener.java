package com.github.yuttyann.custommessage.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import com.github.yuttyann.custommessage.CustomMessageConfig;
import com.github.yuttyann.custommessage.Main;
import com.github.yuttyann.custommessage.TimeManager;
import com.github.yuttyann.custommessage.packet.ProtocolLibPacket;

public class ServerListener implements Listener {

	Main plugin;

	public ServerListener(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onServerListPing(ServerListPingEvent event) {
		if (CustomMessageConfig.getConfig().getBoolean("FakeMaxPlayer.Enable")) {
			int MaxPlayer = CustomMessageConfig.getConfig().getInt("FakeMaxPlayer.MaxPlayer");
			event.setMaxPlayers(MaxPlayer);
		}
		if (CustomMessageConfig.getConfig().getBoolean("Motd.Enable")) {
			String line1 = CustomMessageConfig.getConfig().getString("Motd.1line");
			String line2 = CustomMessageConfig.getConfig().getString("Motd.2line");
			int players = event.getNumPlayers();
			int maxplayer = Bukkit.getMaxPlayers();
			String name = Bukkit.getServerName();
			String version = Bukkit.getServer().getVersion();
			version = version.split("\\(")[1];
			version = version.substring(4, version.length() - 1);
			String motd = line1 + "\n" + line2;
			motd = motd.replace("%players", String.valueOf(players));
			motd = motd.replace("%maxplayers", String.valueOf(maxplayer));
			motd = motd.replace("%servername", name);
			motd = motd.replace("%version", version);
			motd = motd.replace("%time", TimeManager.getTime());
			motd = motd.replace("&", "§");
			event.setMotd(motd);
		}
		setPlayerCountMessage();
	}

	private void setPlayerCountMessage() {
		if (getProtocolLib() == null) {
			return;
		} else if (getProtocolLib()) {
			new ProtocolLibPacket(plugin).sendPlayerCountMessage();
		} else {
			return;
		}
	}

	private Boolean getProtocolLib() {
		return plugin.protocollib;
	}
}
