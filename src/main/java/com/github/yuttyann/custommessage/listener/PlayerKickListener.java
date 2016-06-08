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
import com.github.yuttyann.custommessage.file.Config;
import com.github.yuttyann.custommessage.util.TimeUtils;


public class PlayerKickListener implements Listener {

	Main plugin;

	public PlayerKickListener(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerKick(PlayerKickEvent event) {
		Player player = event.getPlayer();
		if (Config.getBoolean("PlayerKickMessage.Enable")) {
			if (isBanned(player)) {
				if (!Config.getString("PlayerKickMessage.BanBroadcastMessage").equals("none")) {
					String BanBroadcastMessage = Config.getString("PlayerKickMessage.BanBroadcastMessage");
					BanBroadcastMessage = BanBroadcastMessage.replace("%player", player.getName());
					BanBroadcastMessage = BanBroadcastMessage.replace("%reason", event.getReason());
					BanBroadcastMessage = BanBroadcastMessage.replace("%time", TimeUtils.getTime());
					BanBroadcastMessage = BanBroadcastMessage.replace("&", "§");
					Bukkit.broadcastMessage(BanBroadcastMessage);
					if (!Config.getString("Sounds.BanBroadcastSound").equals("none")) {
						new Sounds(plugin).playSound(player, "Sounds.BanBroadcastSound", "SoundTypes.BanBroadcastSoundType");
					}
				}
				if (!Config.getString("PlayerKickMessage.BanMessage").equals("none")) {
					String KickMessage = Config.getString("PlayerKickMessage.BanMessage");
					KickMessage = KickMessage.replace("%player", player.getName());
					KickMessage = KickMessage.replace("%reason", event.getReason());
					KickMessage = KickMessage.replace("%line", "\n");
					KickMessage = KickMessage.replace("%time", TimeUtils.getTime());
					KickMessage = KickMessage.replace("&", "§");
					event.setReason(KickMessage);
				}
			} else {
				if(event.getReason().equalsIgnoreCase("You have been idle for too long!")) {
					if (!Config.getString("PlayerKickMessage.AFKBroadcastMessage").equals("none")) {
						String AFKBroadcastMessage = Config.getString("PlayerKickMessage.AFKBroadcastMessage");
						AFKBroadcastMessage = AFKBroadcastMessage.replace("%player", player.getName());
						AFKBroadcastMessage = AFKBroadcastMessage.replace("%reason", event.getReason());
						AFKBroadcastMessage = AFKBroadcastMessage.replace("%time", TimeUtils.getTime());
						AFKBroadcastMessage = AFKBroadcastMessage.replace("&", "§");
						Bukkit.broadcastMessage(AFKBroadcastMessage);
						if (!Config.getString("Sounds.BanBroadcastSound").equals("none")) {
							new Sounds(plugin).playSound(player, "Sounds.AFKBroadcastSound", "SoundTypes.AFKBroadcastSoundType");
						}
					}
					if (!Config.getString("PlayerKickMessage.AFKMessage").equals("none")) {
						String AFKMessage = Config.getString("PlayerKickMessage.AFKMessage");
						AFKMessage = AFKMessage.replace("%player", player.getName());
						AFKMessage = AFKMessage.replace("%reason", event.getReason());
						AFKMessage = AFKMessage.replace("%line", "\n");
						AFKMessage = AFKMessage.replace("%time", TimeUtils.getTime());
						AFKMessage = AFKMessage.replace("&", "§");
						event.setReason(AFKMessage);
					}
				} else {
					if (!Config.getString("PlayerKickMessage.KickBroadcastMessage").equals("none")) {
						String KickBroadcastMessage = Config.getString("PlayerKickMessage.KickBroadcastMessage");
						KickBroadcastMessage = KickBroadcastMessage.replace("%player", player.getName());
						KickBroadcastMessage = KickBroadcastMessage.replace("%reason", event.getReason());
						KickBroadcastMessage = KickBroadcastMessage.replace("%time", TimeUtils.getTime());
						KickBroadcastMessage = KickBroadcastMessage.replace("&", "§");
						Bukkit.broadcastMessage(KickBroadcastMessage);
						if (!Config.getString("Sounds.KickBroadcastSound").equals("none")) {
							new Sounds(plugin).playSound(player, "Sounds.KickBroadcastSound", "SoundTypes.KickBroadcastSoundType");
						}
					}
					if (!Config.getString("PlayerKickMessage.KickMessage").equals("none")) {
						String KickMessage = Config.getString("PlayerKickMessage.KickMessage");
						KickMessage = KickMessage.replace("%player", player.getName());
						KickMessage = KickMessage.replace("%reason", event.getReason());
						KickMessage = KickMessage.replace("%line", "\n");
						KickMessage = KickMessage.replace("%time", TimeUtils.getTime());
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
		if (Config.getBoolean("PlayerLoginKickMessage.Enable")) {
			if(event.getResult() == Result.KICK_BANNED) {
				String BanMessage = Config.getString("PlayerLoginKickMessage.BanMessage");
				BanMessage = BanMessage.replace("%player", player.getName());
				BanMessage = BanMessage.replace("%time", TimeUtils.getTime());
				BanMessage = BanMessage.replace("%line", "\n");
				BanMessage = BanMessage.replace("&", "§");
				event.disallow(Result.KICK_BANNED, BanMessage);
			}
			if(event.getResult() == Result.KICK_WHITELIST) {
				String WhiteListMessage = Config.getString("PlayerLoginKickMessage.WhiteListMessage");
				WhiteListMessage = WhiteListMessage.replace("%player", player.getName());
				WhiteListMessage = WhiteListMessage.replace("%time", TimeUtils.getTime());
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