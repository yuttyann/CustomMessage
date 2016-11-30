package com.github.yuttyann.custommessage.api;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.github.yuttyann.custommessage.file.Config;
import com.github.yuttyann.custommessage.packet.versions.v1_10_R1;
import com.github.yuttyann.custommessage.packet.versions.v1_7_R4;
import com.github.yuttyann.custommessage.packet.versions.v1_8_R1;
import com.github.yuttyann.custommessage.packet.versions.v1_8_R2;
import com.github.yuttyann.custommessage.packet.versions.v1_8_R3;
import com.github.yuttyann.custommessage.packet.versions.v1_9_R1;
import com.github.yuttyann.custommessage.packet.versions.v1_9_R2;
import com.github.yuttyann.custommessage.util.Utils;
import com.github.yuttyann.kits.listener.ItemListener;
import com.shampaggon.crackshot.CSUtility;

public class CustomMessage {

	public static CustomMessage getAPI() {
		return new CustomMessage();
	}

	public File getFile() {
		return Config.getFile();
	}

	public YamlConfiguration getConfig() {
		return Config.getConfig();
	}

	@Deprecated
	public String getItemName_Old(Player player, String nullstr) {
		ItemStack item = Utils.getItemInHand(player);
		if (item == null || item.getType() == Material.AIR) {
			return nullstr;
		}
		if (!item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) {
			return item.getType().toString();
		}
		return item.getItemMeta().getDisplayName();
	}

	public String getItemName(Player player, String nullstr) {
		ItemStack item = Utils.getItemInHand(player);
		if (item == null || item.getType() == Material.AIR) {
			return nullstr;
		}
		if (!item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) {
			return item.getType().toString();
		}
		String displayname = item.getItemMeta().getDisplayName();
		if (getWeaponTitle(item) != null) {
			String index = "";
			if (displayname.contains("▪ «")) {
				index = "▪ «";
			} else if (displayname.contains("▫ «")) {
				index = "▫ «";
			} else if (displayname.contains("- «")) {
				index = "- «";
			} else if (displayname.contains("«")){
				index = "«";
			}
			return displayname.substring(0, displayname.lastIndexOf(index)).trim();
		}
		return displayname;
	}

	public String getWeaponTitle(ItemStack item) {
		if (Utils.isPluginEnabled("CrackShot")) {
			return new CSUtility().getWeaponTitle(item);
		}
		return null;
	}

	public String getPackage() {
		Server server = Bukkit.getServer();
		String packageName = server.getClass().getPackage().getName();
		packageName = packageName.substring(packageName.lastIndexOf('.') + 1);
		return packageName;
	}

	public void giveKits(Player player, String kitname) {
		if (Utils.isPluginEnabled("Kits")) {
			ItemListener.give(player, kitname);
		}
	}

	@Deprecated
	public void sendTitle(Player player, Integer fadein, Integer stay, Integer fadeout, String title) {
		sendFullTitle(player, fadein, stay, fadeout, title, "");
	}

	@Deprecated
	public void sendSubTitle(Player player, Integer fadein, Integer stay, Integer fadeout, String subtitle) {
		sendFullTitle(player, fadein, stay, fadeout, "", subtitle);
	}

	@Deprecated
	public void sendHeader(Player player, String header) {
		sendFullTabTitle(player, header, "");
	}

	@Deprecated
	public void sendFooter(Player player, String footer) {
		sendFullTabTitle(player, "", footer);
	}

	public void sendFullTitle(Player player, Integer fadein, Integer stay, Integer fadeout, String title, String subtitle) {
		String packageName = getPackage();
		if (packageName.equalsIgnoreCase("v1_7_R4")) {
			if (Config.getBoolean("UseSpigotProtocolHack")) {
				v1_7_R4.sendTitle(player, fadein, stay, fadeout, title, subtitle);
			}
		} else if (packageName.equalsIgnoreCase("v1_8_R1")) {
			v1_8_R1.sendTitle(player, fadein, stay, fadeout, title, subtitle);
		} else if (packageName.equalsIgnoreCase("v1_8_R2")) {
			v1_8_R2.sendTitle(player, fadein, stay, fadeout, title, subtitle);
		} else if (packageName.equalsIgnoreCase("v1_8_R3")) {
			v1_8_R3.sendTitle(player, fadein, stay, fadeout, title, subtitle);
		} else if (packageName.equalsIgnoreCase("v1_9_R1")) {
			v1_9_R1.sendTitle(player, fadein, stay, fadeout, title, subtitle);
		} else if (packageName.equalsIgnoreCase("v1_9_R2")) {
			v1_9_R2.sendTitle(player, fadein, stay, fadeout, title, subtitle);
		} else if (packageName.equalsIgnoreCase("v1_10_R1")) {
			v1_10_R1.sendTitle(player, fadein, stay, fadeout, title, subtitle);
		}
	}

	public void sendFullTabTitle(Player player, String header, String footer) {
		String packageName = getPackage();
		if (packageName.equalsIgnoreCase("v1_7_R4")) {
			if (Config.getBoolean("UseSpigotProtocolHack")) {
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
		} else if (packageName.equalsIgnoreCase("v1_10_R1")) {
			v1_10_R1.sendTabTitle(player, header, footer);
		}
	}
}
