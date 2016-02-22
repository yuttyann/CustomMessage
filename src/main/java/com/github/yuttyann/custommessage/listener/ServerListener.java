package com.github.yuttyann.custommessage.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import com.github.yuttyann.custommessage.Main;
import com.github.yuttyann.custommessage.TimeManager;
import com.github.yuttyann.custommessage.handle.ClassHandler;
import com.github.yuttyann.custommessage.packet.ProtocolLibPacket;

public class ServerListener implements Listener {

	Main plugin;

	public ServerListener(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onServerListPing(ServerListPingEvent event) {
		if (ClassHandler.getMainClass().getConfig().getBoolean("FakeMaxPlayer.Enable")) {
			int MaxPlayer = ClassHandler.getMainClass().getConfig().getInt("FakeMaxPlayer.MaxPlayer");
			event.setMaxPlayers(MaxPlayer);
		}
		if (ClassHandler.getMainClass().getConfig().getBoolean("Motd.Enable")) {
			String line1 = ClassHandler.getMainClass().getConfig().getString("Motd.1line");
			String line2 = ClassHandler.getMainClass().getConfig().getString("Motd.2line");
			int players = event.getNumPlayers();
			int maxplayer = Bukkit.getMaxPlayers();
			String name = Bukkit.getServerName();
			String ver = Bukkit.getServer().getVersion();
			ver = ver.split("\\(")[1];
			ver = ver.substring(4, ver.length() - 1);
			String motd = line1 + "\n" + line2;
			motd = motd.replace("%players", String.valueOf(players));
			motd = motd.replace("%maxplayers", String.valueOf(maxplayer));
			motd = motd.replace("%servername", name);
			motd = motd.replace("%version", ver);
			motd = motd.replace("%time", TimeManager.getTime());
			motd = motd.replace("&", "§");
			event.setMotd(motd);
		}
		setPlayerCountMessage();
	}

	private void setPlayerCountMessage() {
		if (getProtocolLib() == true) {
			ProtocolLibPacket PlayerCountMessage = new ProtocolLibPacket();
			PlayerCountMessage.sendPlayerCountMessage();
		} else {
			return;
		}
	}

	private Boolean getProtocolLib() {
		return plugin.protocollib;
	}
}
