package com.github.yuttyann.custommessage.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.github.yuttyann.custommessage.Main;
import com.github.yuttyann.custommessage.Permission;
import com.github.yuttyann.custommessage.Sounds;
import com.github.yuttyann.custommessage.file.Files;

public class CommandListener implements Listener {

	Main plugin;

	public CommandListener(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		if (!Files.getConfig().getString("Sounds.PlayerCommandEvent_CommandSound").equals("none")) {
			Sounds sound = Sounds.getSounds();
			if (sound.soundAuthority(player, "SoundAuthoritys.PlayerCommandEvent_CommandSoundAuthority", Permission.CUSTOMMESSAGE_SOUND_COMMAND)) {
				sound.playSound(player, "Sounds.PlayerCommandEvent_CommandSound", "SoundTypes.PlayerCommandEvent_CommandSoundType");
			}
		}
	}
}
