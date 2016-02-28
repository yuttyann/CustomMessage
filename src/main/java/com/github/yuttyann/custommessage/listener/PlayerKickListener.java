package com.github.yuttyann.custommessage.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
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

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerKick(PlayerKickEvent event) {
		if (ClassHandler.getMainClass().getConfig().getBoolean("PlayerKickMessage.Enable")) {
			if (!ClassHandler.getMainClass().getConfig().getString("PlayerKickMessage.BroadcastMessage").equals("none")) {
				String BroadcastMessage = ClassHandler.getMainClass().getConfig().getString("PlayerKickMessage.BroadcastMessage");
				BroadcastMessage = BroadcastMessage.replace("%player", event.getPlayer().getName());
				BroadcastMessage = BroadcastMessage.replace("%time", TimeManager.getTime());
				BroadcastMessage = BroadcastMessage.replace("&", "§");
				Bukkit.broadcastMessage(BroadcastMessage);
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