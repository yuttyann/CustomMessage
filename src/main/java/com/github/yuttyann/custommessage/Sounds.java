package com.github.yuttyann.custommessage;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.yuttyann.custommessage.file.Config;

public class Sounds {

	public static Sounds getSounds() {
		return new Sounds();
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
						ConsoleCommandSender sender = Bukkit.getConsoleSender();
						sender.sendMessage(ChatColor.RED + "サウンドエラー: " + args[0].toUpperCase() + "-" + args[1] + "-" + args[2]);
					}
				}
			}.runTaskLater(Main.instance, parseLong(args[3]));
		}
	}

	public void soundType(Player player, String soundtype, String[] args) {
		String type = Config.getString(soundtype);
		if (type.toLowerCase().equals("player")) {
			player.playSound(player.getLocation(), Sound.valueOf(args[0].toUpperCase()), parseFloat(args[1]), parseFloat(args[2]));
		} else if (type.toLowerCase().equals("allplayers")) {
			for (Player players : Bukkit.getOnlinePlayers()) {
				players.playSound(players.getLocation(), Sound.valueOf(args[0].toUpperCase()), parseFloat(args[1]), parseFloat(args[2]));
			}
		}
	}

	public boolean soundAuthority(Player player, String soundauthority, Permission permission) {
		String type = Config.getString(soundauthority);
		if (type.toLowerCase().equals("none")) {
			return true;
		} else if (type.toLowerCase().equals("operator") && player.isOp()) {
			return true;
		} else if (type.toLowerCase().equals("permission") && Permission.has(permission, player)) {
			return true;
		} else {
			return false;
		}
	}

	private long parseLong(String str) {
		return Long.parseLong(str);
	}

	private Float parseFloat(String str) {
		return Float.parseFloat(str);
	}
}
