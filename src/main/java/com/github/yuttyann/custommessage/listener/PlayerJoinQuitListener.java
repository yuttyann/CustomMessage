package com.github.yuttyann.custommessage.listener;

import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.github.yuttyann.custommessage.Main;
import com.github.yuttyann.custommessage.Sounds;
import com.github.yuttyann.custommessage.file.Config;
import com.github.yuttyann.custommessage.util.TimeUtils;

public class PlayerJoinQuitListener implements Listener {

	Main plugin;

	public PlayerJoinQuitListener(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (event.getPlayer().hasPlayedBefore()) {
			if (Config.getBoolean("PlayerJoinQuitMessage.Enable")) {
				String PlayerJoinMessage = Config.getString("PlayerJoinQuitMessage.JoinMessage");
				PlayerJoinMessage = PlayerJoinMessage.replace("%player", player.getDisplayName());
				PlayerJoinMessage = PlayerJoinMessage.replace("%time", TimeUtils.getTime());
				PlayerJoinMessage = PlayerJoinMessage.replace("&", "§");
				event.setJoinMessage(null);
				broadcastMessage(PlayerJoinMessage);
			}
			if (!Config.getString("Sounds.JoinSound").equals("none")) {
				new Sounds(plugin).playSound(player, "Sounds.JoinSound", "SoundTypes.JoinSoundType");
			}
		} else {
			if (Config.getBoolean("PlayerJoinQuitMessage.Enable")) {
				String PlayerFirstJoinMessage = Config.getString("PlayerJoinQuitMessage.FirstJoinMssage");
				PlayerFirstJoinMessage = PlayerFirstJoinMessage.replace("%player", player.getDisplayName());
				PlayerFirstJoinMessage = PlayerFirstJoinMessage.replace("%time", TimeUtils.getTime());
				PlayerFirstJoinMessage = PlayerFirstJoinMessage.replace("&", "§");
				event.setJoinMessage(null);
				broadcastMessage(PlayerFirstJoinMessage);
			}
			if (!Config.getString("Sounds.FirstJoinSound").equals("none")) {
				new Sounds(plugin).playSound(player, "Sounds.FirstJoinSound", "SoundTypes.FirstJoinSoundType");
			}
		}
		if (Config.getBoolean("PlayerLoginMessage.Enable")) {
			List<String> loginmessages = Config.getStringList("PlayerLoginMessage.Message");
			for (String message : loginmessages) {
				message = message.replace("%player", player.getDisplayName());
				message = message.replace("%time", TimeUtils.getTime());
				message = message.replace("&", "§");
				player.sendMessage(message);
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if (Config.getBoolean("PlayerJoinQuitMessage.Enable")) {
			String PlayerQuitMessage = Config.getString("PlayerJoinQuitMessage.QuitMessage");
			PlayerQuitMessage = PlayerQuitMessage.replace("%player", player.getDisplayName());
			PlayerQuitMessage = PlayerQuitMessage.replace("%time", TimeUtils.getTime());
			PlayerQuitMessage = PlayerQuitMessage.replace("&", "§");
			event.setQuitMessage(PlayerQuitMessage);
		}
		if (!Config.getString("Sounds.QuitSound").equals("none")) {
			new Sounds(plugin).playSound(player, "Sounds.QuitSound", "SoundTypes.QuitSoundType");
		}
	}

	private void broadcastMessage(String message) {
		Logger.getLogger("Minecraft").info(ChatColor.stripColor(message));
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.sendMessage(message);
		}
	}
}
