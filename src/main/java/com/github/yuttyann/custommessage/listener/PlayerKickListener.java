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
import com.github.yuttyann.custommessage.TimeManager;
import com.github.yuttyann.custommessage.file.Config;
import com.github.yuttyann.custommessage.util.Utils;


public class PlayerKickListener implements Listener {

	Main plugin;

	public PlayerKickListener(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerKick(PlayerKickEvent event) {
		Player player = event.getPlayer();
		Sounds sound = Sounds.getSounds();
		boolean enable = Config.getBoolean("PlayerKickMessage.Enable");
		if (isBanned(player)) {
			if (enable && !Config.getString("PlayerKickMessage.BanBroadcastMessage").equals("none")) {
				String banbroadcastmessage = Config.getString("PlayerKickMessage.BanBroadcastMessage");
				banbroadcastmessage = replaceKick(banbroadcastmessage, player, event);
				Bukkit.broadcastMessage(banbroadcastmessage);
			}
			if (enable && !Config.getString("PlayerKickMessage.BanMessage").equals("none")) {
				String banmessage = Config.getString("PlayerKickMessage.BanMessage");
				banmessage = banmessage.replace("%line", Utils.getLineFeedCode());
				banmessage = replaceKick(banmessage, player, event);
				event.setReason(banmessage);
			}
			if (!Config.getString("Sounds.PlayerKickEvent_BanSound").equals("none")) {
				if (sound.soundAuthority(player, "SoundAuthoritys.PlayerKickEvent_BanSoundAuthority", Permission.CUSTOMMESSAGE_SOUND_BAN)) {
					sound.playSound(player, "Sounds.PlayerKickEvent_BanSound", "SoundTypes.PlayerKickEvent_BanSoundType");
				}
			}
		} else {
			if(event.getReason().equals("You have been idle for too long!")) {
				if (enable && !Config.getString("PlayerKickMessage.AFKBroadcastMessage").equals("none")) {
					String afkbroadcastmessage = Config.getString("PlayerKickMessage.AFKBroadcastMessage");
					afkbroadcastmessage = replaceKick(afkbroadcastmessage, player, event);
					Bukkit.broadcastMessage(afkbroadcastmessage);
				}
				if (enable && !Config.getString("PlayerKickMessage.AFKMessage").equals("none")) {
					String afkmessage = Config.getString("PlayerKickMessage.AFKMessage");
					afkmessage = afkmessage.replace("%line", Utils.getLineFeedCode());
					afkmessage = replaceKick(afkmessage, player, event);
					event.setReason(afkmessage);
				}
				if (!Config.getString("Sounds.PlayerKickEvent_AFKSound").equals("none")) {
					if (sound.soundAuthority(player, "SoundAuthoritys.PlayerKickEvent_AFKSoundAuthority", Permission.CUSTOMMESSAGE_SOUND_BAN)) {
						sound.playSound(player, "Sounds.PlayerKickEvent_AFKSound", "SoundTypes.PlayerKickEvent_AFKSoundType");
					}
				}
			} else {
				if (enable && !Config.getString("PlayerKickMessage.KickBroadcastMessage").equals("none")) {
					String kickbroadcastmessage = Config.getString("PlayerKickMessage.KickBroadcastMessage");
					kickbroadcastmessage = replaceKick(kickbroadcastmessage, player, event);
					Bukkit.broadcastMessage(kickbroadcastmessage);
				}
				if (enable && !Config.getString("PlayerKickMessage.KickMessage").equals("none")) {
					String kickmessage = Config.getString("PlayerKickMessage.KickMessage");
					kickmessage = kickmessage.replace("%line", Utils.getLineFeedCode());
					kickmessage = replaceKick(kickmessage, player, event);
					event.setReason(kickmessage);
				}
				if (!Config.getString("Sounds.PlayerKickEvent_KickSound").equals("none")) {
					if (sound.soundAuthority(player, "SoundAuthoritys.PlayerKickEvent_KickSoundAuthority", Permission.CUSTOMMESSAGE_SOUND_BAN)) {
						sound.playSound(player, "Sounds.PlayerKickEvent_KickSound", "SoundTypes.PlayerKickEvent_KickSoundType");
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerLogin(PlayerLoginEvent event) {
		if (!Config.getBoolean("PlayerLoginKickMessage.Enable")) {
			return;
		}
		Player player = event.getPlayer();
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

	@SuppressWarnings("deprecation")
	private boolean isBanned(Player player) {
		if (Utils.isUpperVersion("1.7.5")) {
			return Bukkit.getBanList(BanList.Type.NAME).isBanned(player.getName());
		} else {
			return Bukkit.getOfflinePlayer(player.getName()).isBanned();
		}
	}

	private String replaceLoginKick(String message, Player player, PlayerLoginEvent event) {
		message = message.replace("%player", player.getName());
		message = message.replace("%time", TimeManager.getTimesofDay());
		message = message.replace("%line", Utils.getLineFeedCode());
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