package com.github.yuttyann.custommessage.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.github.yuttyann.custommessage.Main;
import com.github.yuttyann.custommessage.Sounds;
import com.github.yuttyann.custommessage.TimeManager;
import com.github.yuttyann.custommessage.config.CustomMessageConfig;

public class PlayerChatListener implements Listener {

	Main plugin;

	public PlayerChatListener(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		if (CustomMessageConfig.getBoolean("ChatMessageFormat.Enable")) {
			String PlayerChatMessage = CustomMessageConfig.getString("ChatMessageFormat.Message");
			PlayerChatMessage = PlayerChatMessage.replace("%player", player.getDisplayName());
			PlayerChatMessage = PlayerChatMessage.replace("%chat", event.getMessage());
			PlayerChatMessage = PlayerChatMessage.replace("%time", TimeManager.getTime());
			PlayerChatMessage = PlayerChatMessage.replace("&", "§");
			event.setFormat(PlayerChatMessage);
		}
		if (!CustomMessageConfig.getString("Sounds.ChatSound").equals("none")) {
			new Sounds(plugin).playSounds(player, "Sounds.ChatSound", "SoundTypes.ChatSoundType");
		}
	}
}
