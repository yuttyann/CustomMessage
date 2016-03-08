package com.github.yuttyann.custommessage.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.github.yuttyann.custommessage.CustomMessageConfig;
import com.github.yuttyann.custommessage.Main;
import com.github.yuttyann.custommessage.TimeManager;

public class PlayerDeathListener implements Listener {

	Main plugin;

	public PlayerDeathListener(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onDeathMessage(PlayerDeathEvent event) {
		Player killer = event.getEntity().getKiller();
		if (event.getEntity().getKiller() != null) {
			if (CustomMessageConfig.getConfig().getBoolean("PlayerKillMessage.Enable")) {
				String PlayerKillMessage = CustomMessageConfig.getConfig().getString("PlayerKillMessage.Message");
				String NullMessage = CustomMessageConfig.getConfig().getString("PlayerKillMessage.NullMessage");
				PlayerKillMessage = PlayerKillMessage.replace("%deader", event.getEntity().getDisplayName());
				PlayerKillMessage = PlayerKillMessage.replace("%killer", killer.getDisplayName());
				PlayerKillMessage = PlayerKillMessage.replace("%weapon", getItemName(killer, NullMessage));
				PlayerKillMessage = PlayerKillMessage.replace("%time", TimeManager.getTime());
				PlayerKillMessage = PlayerKillMessage.replace("&", "§");
				event.setDeathMessage(PlayerKillMessage);
			}
		}
		if (killer == null) {
			if (CustomMessageConfig.getConfig().getBoolean("PlayerDeathMessage.Enable")) {
				Player deader = event.getEntity();
				String PlayerDeathMessage = CustomMessageConfig.getConfig().getString("PlayerDeathMessage.Message");
				PlayerDeathMessage = PlayerDeathMessage.replace("%deader", deader.getDisplayName());
				PlayerDeathMessage = PlayerDeathMessage.replace("%time", TimeManager.getTime());
				PlayerDeathMessage = PlayerDeathMessage.replace("&", "§");
				event.setDeathMessage(PlayerDeathMessage);
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
