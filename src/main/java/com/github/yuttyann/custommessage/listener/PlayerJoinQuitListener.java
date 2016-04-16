package com.github.yuttyann.custommessage.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.github.yuttyann.custommessage.Main;
import com.github.yuttyann.custommessage.Sounds;
import com.github.yuttyann.custommessage.TimeManager;
import com.github.yuttyann.custommessage.config.CustomMessageConfig;

public class PlayerJoinQuitListener implements Listener {

	Main plugin;

	public PlayerJoinQuitListener(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (event.getPlayer().hasPlayedBefore()) {
			if (CustomMessageConfig.getBoolean("PlayerJoinQuitMessage.Enable")) {
				String PlayerJoinMessage = CustomMessageConfig.getString("PlayerJoinQuitMessage.JoinMessage");
				PlayerJoinMessage = PlayerJoinMessage.replace("%player", player.getDisplayName());
				PlayerJoinMessage = PlayerJoinMessage.replace("%time", TimeManager.getTime());
				PlayerJoinMessage = PlayerJoinMessage.replace("&", "§");
				event.setJoinMessage(PlayerJoinMessage);
			}
			if (!CustomMessageConfig.getString("Sounds.JoinSound").equals("none")) {
				new Sounds(plugin).playSound(player, "Sounds.JoinSound", "SoundTypes.JoinSoundType");
			}
		} else {
			if (CustomMessageConfig.getBoolean("PlayerJoinQuitMessage.Enable")) {
				String PlayerFirstJoinMessage = CustomMessageConfig.getString("PlayerJoinQuitMessage.FirstJoinMssage");
				PlayerFirstJoinMessage = PlayerFirstJoinMessage.replace("%player", player.getDisplayName());
				PlayerFirstJoinMessage = PlayerFirstJoinMessage.replace("%time", TimeManager.getTime());
				PlayerFirstJoinMessage = PlayerFirstJoinMessage.replace("&", "§");
				event.setJoinMessage(PlayerFirstJoinMessage);
			}
			if (!CustomMessageConfig.getString("Sounds.FirstJoinSound").equals("none")) {
				new Sounds(plugin).playSound(player, "Sounds.FirstJoinSound", "SoundTypes.FirstJoinSoundType");
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if (CustomMessageConfig.getBoolean("PlayerJoinQuitMessage.Enable")) {
			String PlayerQuitMessage = CustomMessageConfig.getString("PlayerJoinQuitMessage.QuitMessage");
			PlayerQuitMessage = PlayerQuitMessage.replace("%player", player.getDisplayName());
			PlayerQuitMessage = PlayerQuitMessage.replace("%time", TimeManager.getTime());
			PlayerQuitMessage = PlayerQuitMessage.replace("&", "§");
			event.setQuitMessage(PlayerQuitMessage);
		}
		if (!CustomMessageConfig.getString("Sounds.QuitSound").equals("none")) {
			new Sounds(plugin).playSound(player, "Sounds.QuitSound", "SoundTypes.QuitSoundType");
		}
	}
}
