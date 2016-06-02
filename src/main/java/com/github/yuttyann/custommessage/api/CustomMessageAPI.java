package com.github.yuttyann.custommessage.api;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.github.yuttyann.custommessage.Main;
import com.github.yuttyann.custommessage.Version;
import com.github.yuttyann.custommessage.config.CustomMessageConfig;
import com.github.yuttyann.custommessage.packet.versions.v1_7_R4;
import com.github.yuttyann.custommessage.packet.versions.v1_8_R1;
import com.github.yuttyann.custommessage.packet.versions.v1_8_R2;
import com.github.yuttyann.custommessage.packet.versions.v1_8_R3;
import com.github.yuttyann.custommessage.packet.versions.v1_9_R1;
import com.github.yuttyann.custommessage.packet.versions.v1_9_R2;

public class CustomMessageAPI {

	Main plugin;

	public CustomMessageAPI(Main plugin) {
		CustomMessageAPI.this.plugin = plugin;
	}

	public static File getFile() {
		return CustomMessageConfig.getFile();
	}

	public static YamlConfiguration getConfig() {
		return CustomMessageConfig.getConfig();
	}

	public static String getItemName(Player player, String nullmessage) {
		if (player == null) {
			return "";
		}
		ItemStack hand = getItemInHand(player);
		if (hand == null || hand.getType() == Material.AIR) {
			return nullmessage;
		}
		if (!hand.hasItemMeta() || !hand.getItemMeta().hasDisplayName()) {
			return hand.getType().toString();
		}
		return hand.getItemMeta().getDisplayName();
	}

	@SuppressWarnings("deprecation")
	public static ItemStack getItemInHand(Player player) {
		if(Version.isVersion("1.9")) {
			return player.getInventory().getItemInMainHand();
		} else {
			return player.getInventory().getItemInHand();
		}
	}

	public static void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle) {
		Server server = Bukkit.getServer();
		String packageName = server.getClass().getPackage().getName();
		packageName = packageName.substring(packageName.lastIndexOf('.') + 1);
		if (packageName.equalsIgnoreCase("v1_7_R4")) {
			if (CustomMessageConfig.getBoolean("UseSpigotProtocolHack")) {
				v1_7_R4.sendTitle(player, fadeIn, stay, fadeOut, title, subtitle);
			}
		} else if (packageName.equalsIgnoreCase("v1_8_R1")) {
			v1_8_R1.sendTitle(player, fadeIn, stay, fadeOut, title, subtitle);
		} else if (packageName.equalsIgnoreCase("v1_8_R2")) {
			v1_8_R2.sendTitle(player, fadeIn, stay, fadeOut, title, subtitle);
		} else if (packageName.equalsIgnoreCase("v1_8_R3")) {
			v1_8_R3.sendTitle(player, fadeIn, stay, fadeOut, title, subtitle);
		} else if (packageName.equalsIgnoreCase("v1_9_R1")) {
			v1_9_R1.sendTitle(player, fadeIn, stay, fadeOut, title, subtitle);
		} else if (packageName.equalsIgnoreCase("v1_9_R2")) {
			v1_9_R2.sendTitle(player, fadeIn, stay, fadeOut, title, subtitle);
		}
	}

	public static void sendTabTitle(Player player, String header, String footer) {
		Server server = Bukkit.getServer();
		String packageName = server.getClass().getPackage().getName();
		packageName = packageName.substring(packageName.lastIndexOf('.') + 1);
		if (packageName.equalsIgnoreCase("v1_7_R4")) {
			if (CustomMessageConfig.getBoolean("UseSpigotProtocolHack")) {
				v1_7_R4.sendTabTitle(player, header, footer);
			}
		} else if (packageName.equalsIgnoreCase("v1_8_R1")) {
			v1_8_R1.sendTabTitle(player, header, footer);
		} else if (packageName.equalsIgnoreCase("v1_8_R2")) {
			v1_8_R2.sendTabTitle(player, header, footer);
		} else if (packageName.equalsIgnoreCase("v1_8_R3")) {
			v1_8_R3.sendTabTitle(player, header, footer);
		} else if (packageName.equalsIgnoreCase("v1_9_R1")) {
			v1_9_R1.sendTabTitle(player, header, footer);
		} else if (packageName.equalsIgnoreCase("v1_9_R2")) {
			v1_9_R2.sendTabTitle(player, header, footer);
		}
	}
}
