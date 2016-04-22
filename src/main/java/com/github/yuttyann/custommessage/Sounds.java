package com.github.yuttyann.custommessage;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.yuttyann.custommessage.config.CustomMessageConfig;

public class Sounds {

	Main plugin;

	public Sounds(Main plugin) {
		this.plugin = plugin;
	}

	public void playSound(final Player player, String sound, final String soundtype) {
		String soundList = CustomMessageConfig.getString(sound);
		String[] soundFxList = soundList.replace(" ", "").split(",");
		for (final String soundFx : soundFxList) {
			final String[] args = soundFx.split("-");
			new BukkitRunnable() {
				public void run() {
					try {
						soundType(player, soundtype, args);
					} catch (IllegalArgumentException e) {
						Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "サウンドが存在しないか設定を間違えている可能性があります。");
					}
				}
			}.runTaskLater(plugin, parseLong(args[3]));
		}
	}

	private void soundType(Player player, String soundtype, String[] args) {
		String type = CustomMessageConfig.getString(soundtype);
		switch (type) {
		case "player":
			player.playSound(player.getLocation(), Sound.valueOf(args[0].toUpperCase()), parseFloat(args[1]), parseFloat(args[2]));
			break;
		case "allplayers":
			for (Player players : Bukkit.getOnlinePlayers()) {
				players.playSound(players.getLocation(), Sound.valueOf(args[0].toUpperCase()), parseFloat(args[1]), parseFloat(args[2]));
			}
			break;
		default:
			break;
		}
	}

	private long parseLong(String str) {
		return Long.parseLong(str);
	}

	private float parseFloat(String str) {
		return Float.parseFloat(str);
	}
}
