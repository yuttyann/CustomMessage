package com.github.yuttyann.custommessage.listener;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.github.yuttyann.custommessage.CustomMessageConfig;
import com.github.yuttyann.custommessage.Main;
import com.github.yuttyann.custommessage.packet.versions.v1_7_R4;
import com.github.yuttyann.custommessage.packet.versions.v1_8_R1;
import com.github.yuttyann.custommessage.packet.versions.v1_8_R2;
import com.github.yuttyann.custommessage.packet.versions.v1_8_R3;
import com.github.yuttyann.custommessage.packet.versions.v1_9_R1;

public class PlayerTitleListener implements Listener {

	Main plugin;

	public PlayerTitleListener(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		playerTitle(player);
	}

	private void playerTitle(Player player) {
		if (CustomMessageConfig.getConfig().getBoolean("Title.Enable")) {
			int FadeIn = CustomMessageConfig.getConfig().getInt("TitleTime.FadeIn");
			int Stay = CustomMessageConfig.getConfig().getInt("TitleTime.Stay");
			int FadeOut = CustomMessageConfig.getConfig().getInt("TitleTime.FadeOut");
			String TitleMessage = CustomMessageConfig.getConfig().getString("Title.TitleMessage");
			String SubTitleMessage = CustomMessageConfig.getConfig().getString("Title.SubTitleMessage");
			sendTitle(player, FadeIn, Stay, FadeOut, TitleMessage, SubTitleMessage);
		}
		if (CustomMessageConfig.getConfig().getBoolean("TabTitle.Enable")) {
			String Header = CustomMessageConfig.getConfig().getString("TabTitle.Header");
			String Footer = CustomMessageConfig.getConfig().getString("TabTitle.Footer");
			sendTabTitle(player, Header, Footer);
		}
	}

	private static void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle) {
		Server server = Bukkit.getServer();
		String packageName = server.getClass().getPackage().getName();
		packageName = packageName.substring(packageName.lastIndexOf('.') + 1);
		if (packageName.equalsIgnoreCase("v1_7_R4")) {
			v1_7_R4.sendTitle(player, fadeIn, stay, fadeOut, title, subtitle);
		} else if (packageName.equalsIgnoreCase("v1_8_R1")) {
			v1_8_R1.sendTitle(player, fadeIn, stay, fadeOut, title, subtitle);
		} else if (packageName.equalsIgnoreCase("v1_8_R2")) {
			v1_8_R2.sendTitle(player, fadeIn, stay, fadeOut, title, subtitle);
		} else if (packageName.equalsIgnoreCase("v1_8_R3")) {
			v1_8_R3.sendTitle(player, fadeIn, stay, fadeOut, title, subtitle);
		} else if (packageName.equalsIgnoreCase("v1_9_R1")) {
			v1_9_R1.sendTitle(player, fadeIn, stay, fadeOut, title, subtitle);
		} else {
			return;
		}
	}

	private static void sendTabTitle(Player player, String header, String footer) {
		Server server = Bukkit.getServer();
		String packageName = server.getClass().getPackage().getName();
		packageName = packageName.substring(packageName.lastIndexOf('.') + 1);
		if (packageName.equalsIgnoreCase("v1_7_R4")) {
			v1_7_R4.sendTabTitle(player, header, footer);
		} else if (packageName.equalsIgnoreCase("v1_8_R1")) {
			v1_8_R1.sendTabTitle(player, header, footer);
		} else if (packageName.equalsIgnoreCase("v1_8_R2")) {
			v1_8_R2.sendTabTitle(player, header, footer);
		} else if (packageName.equalsIgnoreCase("v1_8_R3")) {
			v1_8_R3.sendTabTitle(player, header, footer);
		} else if (packageName.equalsIgnoreCase("v1_9_R1")) {
			v1_9_R1.sendTabTitle(player, header, footer);
		} else {
			return;
		}
	}
}
