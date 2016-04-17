package com.github.yuttyann.custommessage.listener;

import org.bukkit.BanList;
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
		Player player = event.getPlayer();
		if (CustomMessageConfig.getBoolean("PlayerKickMessage.Enable")) {
			if(isBanned(player)) {
				if (!CustomMessageConfig.getString("PlayerKickMessage.BanBroadcastMessage").equals("none")) {
					String BanBroadcastMessage = CustomMessageConfig.getString("PlayerKickMessage.BanBroadcastMessage");
					BanBroadcastMessage = BanBroadcastMessage.replace("%player", player.getName());
					BanBroadcastMessage = BanBroadcastMessage.replace("%reason", event.getReason());
					BanBroadcastMessage = BanBroadcastMessage.replace("%time", TimeManager.getTime());
					BanBroadcastMessage = BanBroadcastMessage.replace("&", "§");
					Bukkit.broadcastMessage(BanBroadcastMessage);
					if (!CustomMessageConfig.getString("Sounds.BanBroadcastSound").equals("none")) {
						new Sounds(plugin).playSound(player, "Sounds.BanBroadcastSound", "SoundTypes.BanBroadcastSoundType");
					}
				}
				if (!CustomMessageConfig.getString("PlayerKickMessage.BanMessage").equals("none")) {
					String KickMessage = CustomMessageConfig.getString("PlayerKickMessage.BanMessage");
					KickMessage = KickMessage.replace("%player", player.getName());
					KickMessage = KickMessage.replace("%reason", event.getReason());
					KickMessage = KickMessage.replace("%line", "\n");
					KickMessage = KickMessage.replace("%time", TimeManager.getTime());
					KickMessage = KickMessage.replace("&", "§");
					event.setReason(KickMessage);
				}
			} else {
				if(event.getReason().equalsIgnoreCase("You have been idle for too long!")) {
					if (!CustomMessageConfig.getString("PlayerKickMessage.AFKBroadcastMessage").equals("none")) {
						String AFKBroadcastMessage = CustomMessageConfig.getString("PlayerKickMessage.AFKBroadcastMessage");
						AFKBroadcastMessage = AFKBroadcastMessage.replace("%player", player.getName());
						AFKBroadcastMessage = AFKBroadcastMessage.replace("%reason", event.getReason());
						AFKBroadcastMessage = AFKBroadcastMessage.replace("%time", TimeManager.getTime());
						AFKBroadcastMessage = AFKBroadcastMessage.replace("&", "§");
						Bukkit.broadcastMessage(AFKBroadcastMessage);
						if (!CustomMessageConfig.getString("Sounds.BanBroadcastSound").equals("none")) {
							new Sounds(plugin).playSound(player, "Sounds.AFKBroadcastSound", "SoundTypes.AFKBroadcastSoundType");
						}
					}
					if (!CustomMessageConfig.getString("PlayerKickMessage.AFKMessage").equals("none")) {
						String AFKMessage = CustomMessageConfig.getString("PlayerKickMessage.AFKMessage");
						AFKMessage = AFKMessage.replace("%player", player.getName());
						AFKMessage = AFKMessage.replace("%reason", event.getReason());
						AFKMessage = AFKMessage.replace("%line", "\n");
						AFKMessage = AFKMessage.replace("%time", TimeManager.getTime());
						AFKMessage = AFKMessage.replace("&", "§");
						event.setReason(AFKMessage);
					}
				} else {
					if (!CustomMessageConfig.getString("PlayerKickMessage.KickBroadcastMessage").equals("none")) {
						String KickBroadcastMessage = CustomMessageConfig.getString("PlayerKickMessage.KickBroadcastMessage");
						KickBroadcastMessage = KickBroadcastMessage.replace("%player", player.getName());
						KickBroadcastMessage = KickBroadcastMessage.replace("%reason", event.getReason());
						KickBroadcastMessage = KickBroadcastMessage.replace("%time", TimeManager.getTime());
						KickBroadcastMessage = KickBroadcastMessage.replace("&", "§");
						Bukkit.broadcastMessage(KickBroadcastMessage);
						if (!CustomMessageConfig.getString("Sounds.KickBroadcastSound").equals("none")) {
							new Sounds(plugin).playSound(player, "Sounds.KickBroadcastSound", "SoundTypes.KickBroadcastSoundType");
						}
					}
					if (!CustomMessageConfig.getString("PlayerKickMessage.KickMessage").equals("none")) {
						String KickMessage = CustomMessageConfig.getString("PlayerKickMessage.KickMessage");
						KickMessage = KickMessage.replace("%player", player.getName());
						KickMessage = KickMessage.replace("%reason", event.getReason());
						KickMessage = KickMessage.replace("%line", "\n");
						KickMessage = KickMessage.replace("%time", TimeManager.getTime());
						KickMessage = KickMessage.replace("&", "§");
						event.setReason(KickMessage);
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerLogin(PlayerLoginEvent event) {
		Player player = event.getPlayer();
		if (CustomMessageConfig.getBoolean("PlayerLoginKickMessage.Enable")) {
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

	private boolean isBanned(Player player) {
		return Bukkit.getBanList(BanList.Type.NAME).isBanned(player.getName());
	}
}