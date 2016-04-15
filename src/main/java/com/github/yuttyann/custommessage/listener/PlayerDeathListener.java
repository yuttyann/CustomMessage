package com.github.yuttyann.custommessage.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.github.yuttyann.custommessage.Main;
import com.github.yuttyann.custommessage.Sounds;
import com.github.yuttyann.custommessage.TimeManager;
import com.github.yuttyann.custommessage.config.CustomMessageConfig;

public class PlayerDeathListener implements Listener {

	Main plugin;

	public PlayerDeathListener(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onDeathMessage(PlayerDeathEvent event) {
		Player killer = event.getEntity().getKiller();
		if (killer != null) {
			if (CustomMessageConfig.getBoolean("PlayerKillMessage.Enable")) {
				String PlayerKillMessage = CustomMessageConfig.getString("PlayerKillMessage.Message");
				String NullMessage = CustomMessageConfig.getString("PlayerKillMessage.NullMessage");
				PlayerKillMessage = PlayerKillMessage.replace("%deader", event.getEntity().getDisplayName());
				PlayerKillMessage = PlayerKillMessage.replace("%killer", killer.getDisplayName());
				PlayerKillMessage = PlayerKillMessage.replace("%weapon", getItemName(killer, NullMessage));
				PlayerKillMessage = PlayerKillMessage.replace("%time", TimeManager.getTime());
				PlayerKillMessage = PlayerKillMessage.replace("&", "§");
				event.setDeathMessage(PlayerKillMessage);
			}
			if (!CustomMessageConfig.getString("Sounds.KillSound").equals("none")) {
				new Sounds(plugin).playSounds(killer, "Sounds.KillSound", "SoundTypes.KillSoundType");
			}
		}
		if (killer == null) {
			Player deader = event.getEntity();
			if (CustomMessageConfig.getBoolean("PlayerDeathMessage.Enable")) {
				String PlayerDeathMessage = CustomMessageConfig.getString("PlayerDeathMessage.Message");
				PlayerDeathMessage = PlayerDeathMessage.replace("%deader", deader.getDisplayName());
				PlayerDeathMessage = PlayerDeathMessage.replace("%time", TimeManager.getTime());
				PlayerDeathMessage = PlayerDeathMessage.replace("&", "§");
				event.setDeathMessage(PlayerDeathMessage);
			}
			if (!CustomMessageConfig.getString("Sounds.DeathSound").equals("none")) {
				new Sounds(plugin).playSounds(deader, "Sounds.DeathSound", "SoundTypes.DeathSoundType");
			}
		}
	}

	private static String getItemName(Player player, String nullmessage) {
		if (player == null) {
			return "";
		}
		if (player.getItemInHand() == null || player.getItemInHand().getType() == Material.AIR) {
			return nullmessage;
		}
		if (!player.getItemInHand().hasItemMeta() || !player.getItemInHand().getItemMeta().hasDisplayName()) {
			return player.getItemInHand().getType().toString();
		}
		return player.getItemInHand().getItemMeta().getDisplayName();
	}
}
