package com.github.yuttyann.custommessage.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.github.yuttyann.custommessage.Main;
import com.github.yuttyann.custommessage.Permission;
import com.github.yuttyann.custommessage.Sounds;
import com.github.yuttyann.custommessage.file.Config;

public class CommandListener implements Listener {

	Main plugin;

	public CommandListener(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		if (!Config.getString("Sounds.CommandSound").equals("none")) {
			Sounds sound = Sounds.getSounds();
			if (sound.soundAuthority(player, "SoundAuthoritys.CommandSoundAuthority", Permission.CUSTOMMESSAGE_SOUND_COMMAND)) {
				sound.playSound(player, "Sounds.ChatSound", "SoundTypes.CommandSoundType");
			}
		}
	}
}
