package com.github.yuttyann.custommessage.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.github.yuttyann.custommessage.Main;
import com.github.yuttyann.custommessage.TimeManager;
import com.github.yuttyann.custommessage.handle.ClassHandler;

public class PlayerChatListener implements Listener {

	Main plugin;

	public PlayerChatListener(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
		if (ClassHandler.getMainClass().getConfig().getBoolean("ChatMessageFormat.Enable")) {
			String PlayerChatMessage = ClassHandler.getMainClass().getConfig().getString("ChatMessageFormat.Message");
			PlayerChatMessage = PlayerChatMessage.replace("%player", event.getPlayer().getDisplayName());
			PlayerChatMessage = PlayerChatMessage.replace("%chat", event.getMessage());
			PlayerChatMessage = PlayerChatMessage.replace("%time", TimeManager.getTime());
			PlayerChatMessage = PlayerChatMessage.replace("&", "§");
			event.setFormat(PlayerChatMessage);
		}
	}
}
