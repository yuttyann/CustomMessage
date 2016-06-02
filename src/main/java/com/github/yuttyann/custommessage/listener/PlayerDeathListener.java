package com.github.yuttyann.custommessage.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.github.yuttyann.custommessage.Main;
import com.github.yuttyann.custommessage.Sounds;
import com.github.yuttyann.custommessage.TimeManager;
import com.github.yuttyann.custommessage.api.CustomMessageAPI;
import com.github.yuttyann.custommessage.config.CustomMessageConfig;

public class PlayerDeathListener implements Listener {

	Main plugin;

	public PlayerDeathListener(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onDeathMessage(PlayerDeathEvent event) {
		Player deader = event.getEntity();
		Player killer = deader.getKiller();
		if (killer != null) {
			if (CustomMessageConfig.getBoolean("PlayerKillMessage.Enable")) {
				String PlayerKillMessage = CustomMessageConfig.getString("PlayerKillMessage.Message");
				String NullMessage = CustomMessageConfig.getString("PlayerKillMessage.NullMessage");
				PlayerKillMessage = PlayerKillMessage.replace("%deader", deader.getDisplayName());
				PlayerKillMessage = PlayerKillMessage.replace("%killer", killer.getDisplayName());
				PlayerKillMessage = PlayerKillMessage.replace("%weapon", CustomMessageAPI.getItemName(killer, NullMessage));
				PlayerKillMessage = PlayerKillMessage.replace("%time", TimeManager.getTime());
				PlayerKillMessage = PlayerKillMessage.replace("&", "§");
				event.setDeathMessage(PlayerKillMessage);
			}
			if (!CustomMessageConfig.getString("Sounds.KillSound").equals("none")) {
				new Sounds(plugin).playSound(killer, "Sounds.KillSound", "SoundTypes.KillSoundType");
			}
		}
		if (killer == null) {
			if (CustomMessageConfig.getBoolean("PlayerDeathMessage.Enable")) {
				String PlayerDeathMessage = CustomMessageConfig.getString("PlayerDeathMessage.Message");
				PlayerDeathMessage = PlayerDeathMessage.replace("%deader", deader.getDisplayName());
				PlayerDeathMessage = PlayerDeathMessage.replace("%time", TimeManager.getTime());
				PlayerDeathMessage = PlayerDeathMessage.replace("&", "§");
				event.setDeathMessage(PlayerDeathMessage);
			}
			if (!CustomMessageConfig.getString("Sounds.DeathSound").equals("none")) {
				new Sounds(plugin).playSound(deader, "Sounds.DeathSound", "SoundTypes.DeathSoundType");
			}
		}
	}
}
