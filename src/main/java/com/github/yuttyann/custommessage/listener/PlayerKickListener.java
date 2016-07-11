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
import com.github.yuttyann.custommessage.Permission;
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
		Sounds sound = Sounds.getSounds();
		if (Config.getBoolean("PlayerKickMessage.Enable")) {
			if (isBanned(player)) {
				if (!Config.getString("PlayerKickMessage.BanBroadcastMessage").equals("none")) {
					String banbroadcastmessage = Config.getString("PlayerKickMessage.BanBroadcastMessage");
					banbroadcastmessage = replaceKick(banbroadcastmessage, player, event);
					Bukkit.broadcastMessage(banbroadcastmessage);
					if (!Config.getString("Sounds.BanBroadcastSound").equals("none")) {
						if (sound.soundAuthority(player, "SoundAuthoritys.BanBroadcastSoundAuthority", Permission.CUSTOMMESSAGE_SOUND_BAN)) {
							sound.playSound(player, "Sounds.BanBroadcastSound", "SoundTypes.BanBroadcastSoundType");
						}
					}
				}
				if (!Config.getString("PlayerKickMessage.BanMessage").equals("none")) {
					String banmessage = Config.getString("PlayerKickMessage.BanMessage");
					banmessage = banmessage.replace("%line", "\n");
					banmessage = replaceKick(banmessage, player, event);
					event.setReason(banmessage);
				}
			} else {
				if(event.getReason().equalsIgnoreCase("You have been idle for too long!")) {
					if (!Config.getString("PlayerKickMessage.AFKBroadcastMessage").equals("none")) {
						String afkbroadcastmessage = Config.getString("PlayerKickMessage.AFKBroadcastMessage");
						afkbroadcastmessage = replaceKick(afkbroadcastmessage, player, event);
						Bukkit.broadcastMessage(afkbroadcastmessage);
						if (!Config.getString("Sounds.BanBroadcastSound").equals("none")) {
							if (sound.soundAuthority(player, "SoundAuthoritys.AFKBroadcastSoundAuthority", Permission.CUSTOMMESSAGE_SOUND_AFK)) {
								sound.playSound(player, "Sounds.AFKBroadcastSound", "SoundTypes.AFKBroadcastSoundType");
							}
						}
					}
					if (!Config.getString("PlayerKickMessage.AFKMessage").equals("none")) {
						String afkmessage = Config.getString("PlayerKickMessage.AFKMessage");
						afkmessage = afkmessage.replace("%line", "\n");
						afkmessage = replaceKick(afkmessage, player, event);
						event.setReason(afkmessage);
					}
				} else {
					if (!Config.getString("PlayerKickMessage.KickBroadcastMessage").equals("none")) {
						String kickbroadcastmessage = Config.getString("PlayerKickMessage.KickBroadcastMessage");
						kickbroadcastmessage = replaceKick(kickbroadcastmessage, player, event);
						Bukkit.broadcastMessage(kickbroadcastmessage);
						if (!Config.getString("Sounds.KickBroadcastSound").equals("none")) {
							if (sound.soundAuthority(player, "SoundAuthoritys.KickBroadcastSoundAuthority", Permission.CUSTOMMESSAGE_SOUND_KICK)) {
								sound.playSound(player, "Sounds.KickBroadcastSound", "SoundTypes.KickBroadcastSoundType");
							}
						}
					}
					if (!Config.getString("PlayerKickMessage.KickMessage").equals("none")) {
						String kickmessage = Config.getString("PlayerKickMessage.KickMessage");
						kickmessage = kickmessage.replace("%line", "\n");
						kickmessage = replaceKick(kickmessage, player, event);
						event.setReason(kickmessage);
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
				String banmessage = Config.getString("PlayerLoginKickMessage.BanMessage");
				banmessage = replaceLoginKick(banmessage, player, event);
				event.disallow(Result.KICK_BANNED, banmessage);
			}
			if(event.getResult() == Result.KICK_WHITELIST) {
				String whitelistmessage = Config.getString("PlayerLoginKickMessage.WhiteListMessage");
				whitelistmessage = replaceLoginKick(whitelistmessage, player, event);
				event.disallow(Result.KICK_WHITELIST, whitelistmessage);
			}
		}
	}

	private boolean isBanned(Player player) {
		return Bukkit.getBanList(BanList.Type.NAME).isBanned(player.getName());
	}

	private String replaceLoginKick(String message, Player player, PlayerLoginEvent event) {
		message = message.replace("%player", player.getName());
		message = message.replace("%time", TimeUtils.getTime());
		message = message.replace("%line", "\n");
		message = message.replace("&", "§");
		return message;
	}

	private String replaceKick(String message, Player player, PlayerKickEvent event) {
		message = message.replace("%player", player.getName());
		message = message.replace("%reason", event.getReason());
		message = message.replace("%time", TimeUtils.getTime());
		message = message.replace("&", "§");
		return message;
	}
}