package com.github.yuttyann.custommessage.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import com.github.yuttyann.custommessage.Main;
import com.github.yuttyann.custommessage.Sounds;
import com.github.yuttyann.custommessage.TimeManager;
import com.github.yuttyann.custommessage.config.CustomMessageConfig;


public class PlayerKickListener implements Listener {

	Main plugin;

	public PlayerKickListener(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerKick(PlayerKickEvent event) {
		if (CustomMessageConfig.getBoolean("PlayerKickMessage.Enable")) {
			Player player = event.getPlayer();
			if (!CustomMessageConfig.getString("PlayerKickMessage.BroadcastMessage").equals("none")) {
				String BroadcastMessage = CustomMessageConfig.getString("PlayerKickMessage.BroadcastMessage");
				BroadcastMessage = BroadcastMessage.replace("%player", player.getName());
				BroadcastMessage = BroadcastMessage.replace("%time", TimeManager.getTime());
				BroadcastMessage = BroadcastMessage.replace("&", "§");
				Bukkit.broadcastMessage(BroadcastMessage);
				if (!CustomMessageConfig.getString("Sounds.KickBroadcastSound").equals("none")) {
					new Sounds(plugin).playSound(player, "Sounds.KickBroadcastSound", "SoundTypes.KickBroadcastSoundType");
				}
			}
			if (!CustomMessageConfig.getString("PlayerKickMessage.AFKMessage").equals("none")) {
				if(event.getReason().equalsIgnoreCase("You have been idle for too long!")) {
					String AFKMessage = CustomMessageConfig.getString("PlayerKickMessage.AFKMessage");
					AFKMessage = AFKMessage.replace("%player", player.getName());
					AFKMessage = AFKMessage.replace("%line", "\n");
					AFKMessage = AFKMessage.replace("%time", TimeManager.getTime());
					AFKMessage = AFKMessage.replace("&", "§");
					event.setReason(AFKMessage);
					return;
				}
			}
			if (!CustomMessageConfig.getString("PlayerKickMessage.Message").equals("none")) {
				String PlayerKickMessage = CustomMessageConfig.getString("PlayerKickMessage.Message");
				PlayerKickMessage = PlayerKickMessage.replace("%player", player.getName());
				PlayerKickMessage = PlayerKickMessage.replace("%line", "\n");
				PlayerKickMessage = PlayerKickMessage.replace("%time", TimeManager.getTime());
				PlayerKickMessage = PlayerKickMessage.replace("&", "§");
				event.setReason(PlayerKickMessage);
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerLogin(PlayerLoginEvent event) {
		if (CustomMessageConfig.getBoolean("PlayerLoginKickMessage.Enable")) {
			Player player = event.getPlayer();
			if(event.getResult() == Result.KICK_BANNED) {
				String BanMessage = CustomMessageConfig.getString("PlayerLoginKickMessage.BanMessage");
				BanMessage = BanMessage.replace("%player", player.getName());
				BanMessage = BanMessage.replace("%time", TimeManager.getTime());
				BanMessage = BanMessage.replace("%line", "\n");
				BanMessage = BanMessage.replace("&", "§");
				event.disallow(Result.KICK_BANNED, BanMessage);
			}
			if(event.getResult() == Result.KICK_WHITELIST) {
				String WhiteListMessage = CustomMessageConfig.getString("PlayerLoginKickMessage.WhiteListMessage");
				WhiteListMessage = WhiteListMessage.replace("%player", player.getName());
				WhiteListMessage = WhiteListMessage.replace("%time", TimeManager.getTime());
				WhiteListMessage = WhiteListMessage.replace("%line", "\n");
				WhiteListMessage = WhiteListMessage.replace("&", "§");
				event.disallow(Result.KICK_WHITELIST, WhiteListMessage);
			}
		}
	}
}