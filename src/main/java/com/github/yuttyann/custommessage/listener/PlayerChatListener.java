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
			String playerchatmessage = Config.getString("ChatMessageFormat.Message");
			playerchatmessage = playerchatmessage.replace("%player", player.getDisplayName());
			playerchatmessage = playerchatmessage.replace("%chat", event.getMessage());
			playerchatmessage = playerchatmessage.replace("%time", TimeUtils.getTime());
			playerchatmessage = playerchatmessage.replace("&", "§");
			event.setFormat(playerchatmessage);
		}
		if (!Config.getString("Sounds.ChatSound").equals("none")) {
			new Sounds(plugin).playSound(player, "Sounds.ChatSound", "SoundTypes.ChatSoundType");
		}
	}
}
