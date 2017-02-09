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

import com.github.yuttyann.custommessage.Permission;
import com.github.yuttyann.custommessage.Sounds;
import com.github.yuttyann.custommessage.TimeManager;
import com.github.yuttyann.custommessage.file.Files;
import com.github.yuttyann.custommessage.file.YamlConfig;
import com.github.yuttyann.custommessage.util.Utils;


public class PlayerKickListener implements Listener {

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerKick(PlayerKickEvent event) {
		Player player = event.getPlayer();
		Sounds sound = Sounds.getSounds();
		YamlConfig config = Files.getConfig();
		boolean enable = config.getBoolean("PlayerKickMessage.Enable");
		if (isBanned(player)) {
			if (enable && !config.getString("PlayerKickMessage.BanBroadcastMessage").equals("none")) {
				String banbroadcastmessage = config.getString("PlayerKickMessage.BanBroadcastMessage");
				banbroadcastmessage = replaceKick(banbroadcastmessage, player, event);
				Bukkit.broadcastMessage(banbroadcastmessage);
			}
			if (enable && !config.getString("PlayerKickMessage.BanMessage").equals("none")) {
				String banmessage = config.getString("PlayerKickMessage.BanMessage");
				banmessage = banmessage.replace("%line", "\n");
				banmessage = replaceKick(banmessage, player, event);
				event.setReason(banmessage);
			}
			if (!config.getString("Sounds.PlayerKickEvent_BanSound").equals("none")) {
				if (sound.soundAuthority(player, "SoundAuthoritys.PlayerKickEvent_BanSoundAuthority", Permission.CUSTOMMESSAGE_SOUND_BAN)) {
					sound.playSound(player, "Sounds.PlayerKickEvent_BanSound", "SoundTypes.PlayerKickEvent_BanSoundType");
				}
			}
		} else {
			if(event.getReason().equals("You have been idle for too long!")) {
				if (enable && !config.getString("PlayerKickMessage.AFKBroadcastMessage").equals("none")) {
					String afkbroadcastmessage = config.getString("PlayerKickMessage.AFKBroadcastMessage");
					afkbroadcastmessage = replaceKick(afkbroadcastmessage, player, event);
					Bukkit.broadcastMessage(afkbroadcastmessage);
				}
				if (enable && !config.getString("PlayerKickMessage.AFKMessage").equals("none")) {
					String afkmessage = config.getString("PlayerKickMessage.AFKMessage");
					afkmessage = afkmessage.replace("%line", "\n");
					afkmessage = replaceKick(afkmessage, player, event);
					event.setReason(afkmessage);
				}
				if (!config.getString("Sounds.PlayerKickEvent_AFKSound").equals("none")) {
					if (sound.soundAuthority(player, "SoundAuthoritys.PlayerKickEvent_AFKSoundAuthority", Permission.CUSTOMMESSAGE_SOUND_BAN)) {
						sound.playSound(player, "Sounds.PlayerKickEvent_AFKSound", "SoundTypes.PlayerKickEvent_AFKSoundType");
					}
				}
			} else {
				if (enable && !config.getString("PlayerKickMessage.KickBroadcastMessage").equals("none")) {
					String kickbroadcastmessage = config.getString("PlayerKickMessage.KickBroadcastMessage");
					kickbroadcastmessage = replaceKick(kickbroadcastmessage, player, event);
					Bukkit.broadcastMessage(kickbroadcastmessage);
				}
				if (enable && !config.getString("PlayerKickMessage.KickMessage").equals("none")) {
					String kickmessage = config.getString("PlayerKickMessage.KickMessage");
					kickmessage = kickmessage.replace("%line", "\n");
					kickmessage = replaceKick(kickmessage, player, event);
					event.setReason(kickmessage);
				}
				if (!config.getString("Sounds.PlayerKickEvent_KickSound").equals("none")) {
					if (sound.soundAuthority(player, "SoundAuthoritys.PlayerKickEvent_KickSoundAuthority", Permission.CUSTOMMESSAGE_SOUND_BAN)) {
						sound.playSound(player, "Sounds.PlayerKickEvent_KickSound", "SoundTypes.PlayerKickEvent_KickSoundType");
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerLogin(PlayerLoginEvent event) {
		YamlConfig config = Files.getConfig();
		if (!config.getBoolean("PlayerLoginKickMessage.Enable")) {
			return;
		}
		Player player = event.getPlayer();
		if(event.getResult() == Result.KICK_BANNED) {
			String banmessage = config.getString("PlayerLoginKickMessage.BanMessage");
			banmessage = replaceLoginKick(banmessage, player, event);
			event.disallow(Result.KICK_BANNED, banmessage);
		}
		if(event.getResult() == Result.KICK_WHITELIST) {
			String whitelistmessage = config.getString("PlayerLoginKickMessage.WhiteListMessage");
			whitelistmessage = replaceLoginKick(whitelistmessage, player, event);
			event.disallow(Result.KICK_WHITELIST, whitelistmessage);
		}
	}

	@SuppressWarnings("deprecation")
	private boolean isBanned(Player player) {
		if (Utils.isCB175orLater()) {
			return Bukkit.getBanList(BanList.Type.NAME).isBanned(player.getName());
		} else {
			return Bukkit.getOfflinePlayer(player.getName()).isBanned();
		}
	}

	private String replaceLoginKick(String message, Player player, PlayerLoginEvent event) {
		message = message.replace("%player", player.getName());
		message = message.replace("%time", TimeManager.getTimesofDay());
		message = message.replace("%line", "\n");
		message = message.replace("&", "§");
		return message;
	}

	private String replaceKick(String message, Player player, PlayerKickEvent event) {
		message = message.replace("%player", player.getName());
		message = message.replace("%reason", event.getReason());
		message = message.replace("%time", TimeManager.getTimesofDay());
		message = message.replace("&", "§");
		return message;
	}
}