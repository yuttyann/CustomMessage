package com.github.yuttyann.custommessage.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.github.yuttyann.custommessage.Main;
import com.github.yuttyann.custommessage.Permission;
import com.github.yuttyann.custommessage.Sounds;
import com.github.yuttyann.custommessage.TimeManager;
import com.github.yuttyann.custommessage.file.Files;
import com.github.yuttyann.custommessage.file.Yaml;

public class PlayerChatListener implements Listener {

	Main plugin;

	public PlayerChatListener(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		Yaml config = Files.getConfig();
		if (config.getBoolean("ChatMessageFormat.Enable")) {
			String playerchatmessage = config.getString("ChatMessageFormat.Message");
			playerchatmessage = playerchatmessage.replace("%player", player.getDisplayName());
			playerchatmessage = playerchatmessage.replace("%chat", event.getMessage());
			playerchatmessage = playerchatmessage.replace("%time", TimeManager.getTimesofDay());
			playerchatmessage = playerchatmessage.replace("&", "§");
			event.setFormat(playerchatmessage);
		}
		if (!config.getString("Sounds.PlayerChatEvent_ChatSound").equals("none")) {
			Sounds sound = Sounds.getSounds();
			if (sound.soundAuthority(player, "SoundAuthoritys.PlayerChatEvent_ChatSoundAuthority", Permission.CUSTOMMESSAGE_SOUND_CHAT)) {
				sound.playSound(player, "Sounds.PlayerChatEvent_ChatSound", "SoundTypes.PlayerChatEvent_ChatSoundType");
			}
		}
	}
}
