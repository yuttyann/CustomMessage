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
import com.github.yuttyann.custommessage.util.Utils;

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
				String PlayerKillMessage = Config.getString("PlayerKillMessage.Message");
				String NullMessage = Config.getString("PlayerKillMessage.NullMessage");
				PlayerKillMessage = PlayerKillMessage.replace("%deader", deader.getDisplayName());
				PlayerKillMessage = PlayerKillMessage.replace("%killer", killer.getDisplayName());
				PlayerKillMessage = PlayerKillMessage.replace("%weapon", api.getItemName(killer, NullMessage));
				PlayerKillMessage = PlayerKillMessage.replace("%time", Utils.getTime());
				PlayerKillMessage = PlayerKillMessage.replace("&", "§");
				event.setDeathMessage(PlayerKillMessage);
			}
			if (!Config.getString("Sounds.KillSound").equals("none")) {
				new Sounds(plugin).playSound(killer, "Sounds.KillSound", "SoundTypes.KillSoundType");
			}
		}
		if (killer == null) {
			if (Config.getBoolean("PlayerDeathMessage.Enable")) {
				String PlayerDeathMessage = Config.getString("PlayerDeathMessage.Message");
				PlayerDeathMessage = PlayerDeathMessage.replace("%deader", deader.getDisplayName());
				PlayerDeathMessage = PlayerDeathMessage.replace("%time", Utils.getTime());
				PlayerDeathMessage = PlayerDeathMessage.replace("&", "§");
				event.setDeathMessage(PlayerDeathMessage);
			}
			if (!Config.getString("Sounds.DeathSound").equals("none")) {
				new Sounds(plugin).playSound(deader, "Sounds.DeathSound", "SoundTypes.DeathSoundType");
			}
		}
	}
}
