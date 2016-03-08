package com.github.yuttyann.custommessage.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import com.github.yuttyann.custommessage.CustomMessageConfig;
import com.github.yuttyann.custommessage.Main;
import com.github.yuttyann.custommessage.TimeManager;


public class PlayerKickListener implements Listener {

	Main plugin;

	public PlayerKickListener(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerKick(PlayerKickEvent event) {
		if (CustomMessageConfig.getConfig().getBoolean("PlayerKickMessage.Enable")) {
			if (!CustomMessageConfig.getConfig().getString("PlayerKickMessage.BroadcastMessage").equals("none")) {
				String BroadcastMessage = CustomMessageConfig.getConfig().getString("PlayerKickMessage.BroadcastMessage");
				BroadcastMessage = BroadcastMessage.replace("%player", event.getPlayer().getName());
				BroadcastMessage = BroadcastMessage.replace("%time", TimeManager.getTime());
				BroadcastMessage = BroadcastMessage.replace("&", "§");
				Bukkit.broadcastMessage(BroadcastMessage);
			}
			if (!CustomMessageConfig.getConfig().getString("PlayerKickMessage.AFKMessage").equals("none")) {
				if(event.getReason().equalsIgnoreCase("You have been idle for too long!")) {
					String AFKMessage = CustomMessageConfig.getConfig().getString("PlayerKickMessage.AFKMessage");
					AFKMessage = AFKMessage.replace("%player", event.getPlayer().getName());
					AFKMessage = AFKMessage.replace("%line", "\n");
					AFKMessage = AFKMessage.replace("%time", TimeManager.getTime());
					AFKMessage = AFKMessage.replace("&", "§");
					event.setReason(AFKMessage);
					return;
				}
			}
			if (!CustomMessageConfig.getConfig().getString("PlayerKickMessage.Message").equals("none")) {
				String PlayerKickMessage = CustomMessageConfig.getConfig().getString("PlayerKickMessage.Message");
				PlayerKickMessage = PlayerKickMessage.replace("%player", event.getPlayer().getName());
				PlayerKickMessage = PlayerKickMessage.replace("%line", "\n");
				PlayerKickMessage = PlayerKickMessage.replace("%time", TimeManager.getTime());
				PlayerKickMessage = PlayerKickMessage.replace("&", "§");
				event.setReason(PlayerKickMessage);
			}
		}
	}

	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event) {
		if (CustomMessageConfig.getConfig().getBoolean("PlayerLoginKickMessage.Enable")) {
			if(event.getResult() == Result.KICK_BANNED) {
				String BanMessage = CustomMessageConfig.getConfig().getString("PlayerLoginKickMessage.BanMessage");
				BanMessage = BanMessage.replace("%player", event.getPlayer().getName());
				BanMessage = BanMessage.replace("%time", TimeManager.getTime());
				BanMessage = BanMessage.replace("%line", "\n");
				BanMessage = BanMessage.replace("&", "§");
				event.disallow(Result.KICK_BANNED, BanMessage);
			}
			if(event.getResult() == Result.KICK_WHITELIST) {
				String WhiteListMessage = CustomMessageConfig.getConfig().getString("PlayerLoginKickMessage.WhiteListMessage");
				WhiteListMessage = WhiteListMessage.replace("%player", event.getPlayer().getName());
				WhiteListMessage = WhiteListMessage.replace("%time", TimeManager.getTime());
				WhiteListMessage = WhiteListMessage.replace("%line", "\n");
				WhiteListMessage = WhiteListMessage.replace("&", "§");
				event.disallow(Result.KICK_WHITELIST, WhiteListMessage);
			}
		}
	}
}