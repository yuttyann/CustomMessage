package com.github.yuttyann.custommessage.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.github.yuttyann.custommessage.Main;
import com.github.yuttyann.custommessage.TimeManager;
import com.github.yuttyann.custommessage.config.CustomMessageConfig;

public class PlayerJoinQuitListener implements Listener {

	Main plugin;

	public PlayerJoinQuitListener(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerJoin(PlayerJoinEvent event) {
		if (CustomMessageConfig.getBoolean("PlayerJoinQuitMessage.Enable")) {
			if (event.getPlayer().hasPlayedBefore()) {
				String PlayerJoinMessage = CustomMessageConfig.getString("PlayerJoinQuitMessage.JoinMessage");
				PlayerJoinMessage = PlayerJoinMessage.replace("%player", event.getPlayer().getDisplayName());
				PlayerJoinMessage = PlayerJoinMessage.replace("%time", TimeManager.getTime());
				PlayerJoinMessage = PlayerJoinMessage.replace("&", "§");
				event.setJoinMessage(PlayerJoinMessage);
			} else {
				String PlayerFirstJoinMessage = CustomMessageConfig.getString("PlayerJoinQuitMessage.FirstJoinMssage");
				PlayerFirstJoinMessage = PlayerFirstJoinMessage.replace("%player", event.getPlayer().getDisplayName());
				PlayerFirstJoinMessage = PlayerFirstJoinMessage.replace("%time", TimeManager.getTime());
				PlayerFirstJoinMessage = PlayerFirstJoinMessage.replace("&", "§");
				event.setJoinMessage(PlayerFirstJoinMessage);
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerQuit(PlayerQuitEvent event) {
		if (CustomMessageConfig.getBoolean("PlayerJoinQuitMessage.Enable")) {
			String PlayerQuitMessage = CustomMessageConfig.getString("PlayerJoinQuitMessage.QuitMessage");
			PlayerQuitMessage = PlayerQuitMessage.replace("%player", event.getPlayer().getDisplayName());
			PlayerQuitMessage = PlayerQuitMessage.replace("%time", TimeManager.getTime());
			PlayerQuitMessage = PlayerQuitMessage.replace("&", "§");
			event.setQuitMessage(PlayerQuitMessage);
		}
	}
}
