package com.github.yuttyann.custommessage.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

import com.github.yuttyann.custommessage.Main;
import com.github.yuttyann.custommessage.TimeManager;
import com.github.yuttyann.custommessage.handle.ClassHandler;

public class PlayerKickListener implements Listener {

	Main plugin;

	public PlayerKickListener(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerKick(PlayerKickEvent event) {
		if (plugin.getConfig().getBoolean("PlayerKickMessage.Enable")) {
			if (!plugin.getConfig().getString("PlayerKickMessage.Broadcast").equals("none")) {
				Bukkit.broadcastMessage("");
			}
			String PlayerKickMessage = ClassHandler.getMainClass().getConfig().getString("PlayerKickMessage.Message");
			PlayerKickMessage = PlayerKickMessage.replace("%player", event.getPlayer().getName());
			PlayerKickMessage = PlayerKickMessage.replace("%line", "\n");
			PlayerKickMessage = PlayerKickMessage.replace("%time", TimeManager.getTime());
			PlayerKickMessage = PlayerKickMessage.replace("&", "§");
			event.setReason(PlayerKickMessage);
		}
	}
}