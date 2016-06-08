package com.github.yuttyann.custommessage.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.github.yuttyann.custommessage.Main;
import com.github.yuttyann.custommessage.Sounds;
import com.github.yuttyann.custommessage.file.Config;
import com.github.yuttyann.custommessage.util.TimeUtils;

public class PlayerChatListener implements Listener {

	Main plugin;

	public PlayerChatListener(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		if (Config.getBoolean("ChatMessageFormat.Enable")) {
			String PlayerChatMessage = Config.getString("ChatMessageFormat.Message");
			PlayerChatMessage = PlayerChatMessage.replace("%player", player.getDisplayName());
			PlayerChatMessage = PlayerChatMessage.replace("%chat", event.getMessage());
			PlayerChatMessage = PlayerChatMessage.replace("%time", TimeUtils.getTime());
			PlayerChatMessage = PlayerChatMessage.replace("&", "§");
			event.setFormat(PlayerChatMessage);
		}
		if (!Config.getString("Sounds.ChatSound").equals("none")) {
			new Sounds(plugin).playSound(player, "Sounds.ChatSound", "SoundTypes.ChatSoundType");
		}
	}
}
