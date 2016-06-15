package com.github.yuttyann.custommessage.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.github.yuttyann.custommessage.Main;
import com.github.yuttyann.custommessage.Sounds;
import com.github.yuttyann.custommessage.api.CustomMessage;
import com.github.yuttyann.custommessage.file.Config;
import com.github.yuttyann.custommessage.util.TimeUtils;

public class PlayerDeathListener implements Listener {

	Main plugin;
	CustomMessage api;

	public PlayerDeathListener(Main plugin) {
		this.plugin = plugin;
		this.api = CustomMessage.getAPI();
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onDeathMessage(PlayerDeathEvent event) {
		Player deader = event.getEntity();
		Player killer = deader.getKiller();
		if (killer != null) {
			if (Config.getBoolean("PlayerKillMessage.Enable")) {
				String playerkillmessage = Config.getString("PlayerKillMessage.Message");
				String nullmessage = Config.getString("PlayerKillMessage.NullMessage");
				playerkillmessage = playerkillmessage.replace("%deader", deader.getDisplayName());
				playerkillmessage = playerkillmessage.replace("%killer", killer.getDisplayName());
				playerkillmessage = playerkillmessage.replace("%weapon", api.getItemName(killer, nullmessage));
				playerkillmessage = playerkillmessage.replace("%time", TimeUtils.getTime());
				playerkillmessage = playerkillmessage.replace("&", "§");
				event.setDeathMessage(playerkillmessage);
			}
			if (!Config.getString("Sounds.KillSound").equals("none")) {
				new Sounds(plugin).playSound(killer, "Sounds.KillSound", "SoundTypes.KillSoundType");
			}
		}
		if (killer == null) {
			if (Config.getBoolean("PlayerDeathMessage.Enable")) {
				String playerdeathmessage = Config.getString("PlayerDeathMessage.Message");
				playerdeathmessage = playerdeathmessage.replace("%deader", deader.getDisplayName());
				playerdeathmessage = playerdeathmessage.replace("%time", TimeUtils.getTime());
				playerdeathmessage = playerdeathmessage.replace("&", "§");
				event.setDeathMessage(playerdeathmessage);
			}
			if (!Config.getString("Sounds.DeathSound").equals("none")) {
				new Sounds(plugin).playSound(deader, "Sounds.DeathSound", "SoundTypes.DeathSoundType");
			}
		}
	}
}
