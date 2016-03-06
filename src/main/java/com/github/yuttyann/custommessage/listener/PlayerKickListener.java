package com.github.yuttyann.custommessage.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

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
			if (!ClassHandler.getMainClass().getConfig().getString("PlayerKickMessage.AFKMessage").equals("none")) {
				if(event.getReason().equalsIgnoreCase("You have been idle for too long!")) {
					String AFKMessage = ClassHandler.getMainClass().getConfig().getString("PlayerKickMessage.AFKMessage");
					AFKMessage = AFKMessage.replace("%player", event.getPlayer().getName());
					AFKMessage = AFKMessage.replace("%line", "\n");
					AFKMessage = AFKMessage.replace("%time", TimeManager.getTime());
					AFKMessage = AFKMessage.replace("&", "§");
					event.setReason(AFKMessage);
					return;
				}
			}
			if (!ClassHandler.getMainClass().getConfig().getString("PlayerKickMessage.Message").equals("none")) {
				String PlayerKickMessage = ClassHandler.getMainClass().getConfig().getString("PlayerKickMessage.Message");
				PlayerKickMessage = PlayerKickMessage.replace("%player", event.getPlayer().getName());
				PlayerKickMessage = PlayerKickMessage.replace("%line", "\n");
				PlayerKickMessage = PlayerKickMessage.replace("%time", TimeManager.getTime());
				PlayerKickMessage = PlayerKickMessage.replace("&", "§");
				event.setReason(PlayerKickMessage);
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerLogin(PlayerLoginEvent event) {
		if(event.getResult() == Result.KICK_BANNED) {
			String BanMessage = ClassHandler.getMainClass().getConfig().getString("PlayerLoginKickMessage.BanMessage");
			BanMessage = BanMessage.replace("%player", event.getPlayer().getName());
			BanMessage = BanMessage.replace("%time", TimeManager.getTime());
			BanMessage = BanMessage.replace("%line", "\n");
			BanMessage = BanMessage.replace("&", "§");
			event.disallow(Result.KICK_BANNED, BanMessage);
		}
		if(event.getResult() == Result.KICK_WHITELIST) {
			String WhiteListMessage = ClassHandler.getMainClass().getConfig().getString("PlayerLoginKickMessage.WhiteListMessage");
			WhiteListMessage = WhiteListMessage.replace("%player", event.getPlayer().getName());
			WhiteListMessage = WhiteListMessage.replace("%time", TimeManager.getTime());
			WhiteListMessage = WhiteListMessage.replace("%line", "\n");
			WhiteListMessage = WhiteListMessage.replace("&", "§");
			event.disallow(Result.KICK_WHITELIST, WhiteListMessage);
		}
	}
}