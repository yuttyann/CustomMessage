package com.github.yuttyann.custommessage.listener;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.github.yuttyann.custommessage.Permission;
import com.github.yuttyann.custommessage.Sounds;
import com.github.yuttyann.custommessage.TimeManager;
import com.github.yuttyann.custommessage.api.CustomMessage;
import com.github.yuttyann.custommessage.file.Files;
import com.github.yuttyann.custommessage.file.YamlConfig;

public class PlayerJoinQuitListener implements Listener {

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		Sounds sound = Sounds.getSounds();
		YamlConfig config = Files.getConfig();
		if (event.getPlayer().hasPlayedBefore()) {
			if (config.getBoolean("PlayerJoinQuitMessage.Enable")) {
				String playerjoinmessage = config.getString("PlayerJoinQuitMessage.JoinMessage");
				playerjoinmessage = replaceJoinQuit(playerjoinmessage, player);
				event.setJoinMessage(null);
				broadcastMessage(playerjoinmessage);
			}
			if (!config.getString("Sounds.PlayerJoinEvent_JoinSound").equals("none")) {
				if (sound.soundAuthority(player, "SoundAuthoritys.PlayerJoinEvent_JoinSoundAuthority", Permission.CUSTOMMESSAGE_SOUND_JOIN)) {
					sound.playSound(player, "Sounds.PlayerJoinEvent_JoinSound", "SoundTypes.PlayerJoinEvent_JoinSoundType");
				}
			}
		} else {
			if (config.getBoolean("FirstJoinItem.Enable")) {
				String kitname = config.getString("FirstJoinItem.KitName");
				CustomMessage.getAPI().giveKits(player, kitname);
			}
			if (config.getBoolean("PlayerJoinQuitMessage.Enable")) {
				String playerfirstjoinmessage = config.getString("PlayerJoinQuitMessage.FirstJoinMessage");
				playerfirstjoinmessage = replaceJoinQuit(playerfirstjoinmessage, player);
				event.setJoinMessage(null);
				broadcastMessage(playerfirstjoinmessage);
			}
			if (!config.getString("Sounds.PlayerJoinEvent_FirstSound").equals("none")) {
				if (sound.soundAuthority(player, "SoundAuthoritys.PlayerJoinEvent_FirstSoundAuthority", Permission.CUSTOMMESSAGE_SOUND_FIRSTJOIN)) {
					sound.playSound(player, "Sounds.PlayerJoinEvent_FirstSound", "SoundTypes.PlayerJoinEvent_FirstSoundType");
				}
			}
		}
		if (config.getBoolean("PlayerLoginMessage.Enable")) {
			for (String playerloginmessage : config.getStringList("PlayerLoginMessage.Message")) {
				playerloginmessage = replaceJoinQuit(playerloginmessage, player);
				player.sendMessage(playerloginmessage);
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		Sounds sound = Sounds.getSounds();
		YamlConfig config = Files.getConfig();
		if (config.getBoolean("PlayerJoinQuitMessage.Enable")) {
			String playerquitmessage = config.getString("PlayerJoinQuitMessage.QuitMessage");
			playerquitmessage = playerquitmessage.replace("%player", player.getDisplayName());
			playerquitmessage = playerquitmessage.replace("%time", TimeManager.getTimesofDay());
			playerquitmessage = playerquitmessage.replace("&", "§");
			event.setQuitMessage(playerquitmessage);
		}
		if (!config.getString("Sounds.PlayerQuitEvent_QuitSound").equals("none")) {
			if (sound.soundAuthority(player, "SoundAuthoritys.PlayerQuitEvent_QuitSoundAuthority", Permission.CUSTOMMESSAGE_SOUND_QUIT)) {
				sound.playSound(player, "Sounds.PlayerQuitEvent_QuitSound", "SoundTypes.PlayerQuitEvent_QuitSoundType");
			}
		}
	}

	private void broadcastMessage(String message) {
		Logger.getLogger("Minecraft").info(ChatColor.stripColor(message));
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.sendMessage(message);
		}
	}

	private String replaceJoinQuit(String message, Player player) {
		message = message.replace("%player", player.getDisplayName());
		message = message.replace("%time", TimeManager.getTimesofDay());
		message = message.replace("&", "§");
		return message;
	}
}
