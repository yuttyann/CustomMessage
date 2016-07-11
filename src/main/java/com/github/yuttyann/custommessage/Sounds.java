package com.github.yuttyann.custommessage;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.yuttyann.custommessage.file.Config;

public class Sounds {

	private static Main plugin;

	public Sounds(Main plugin) {
		Sounds.plugin = plugin;
	}

	public void playSound(final Player player, String sound, final String soundtype) {
		String soundlist = Config.getString(sound);
		String[] soundfxList = soundlist.replace(" ", "").split(",");
		for (String soundfx : soundfxList) {
			final String[] args = soundfx.split("-");
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

	public void soundType(Player player, String soundtype, String[] args) {
		String type = Config.getString(soundtype);
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

	public boolean soundAuthority(Player player, String soundauthority, Permission permission) {
		String type = Config.getString(soundauthority);
		switch (type) {
		case "none":
			return true;
		case "operator":
			if (player.isOp()) {
				return true;
			}
		case "permission":
			if (Permission.has(permission, player)) {
				return true;
			}
		default:
			return false;
		}
	}

	private long parseLong(String str) {
		return Long.parseLong(str);
	}

	private Float parseFloat(String str) {
		return Float.parseFloat(str);
	}

	public static Sounds getSounds() {
		return new Sounds(plugin);
	}
}
