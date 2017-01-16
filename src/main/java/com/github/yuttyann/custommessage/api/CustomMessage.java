package com.github.yuttyann.custommessage.api;

import java.io.File;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.github.yuttyann.custommessage.file.Files;
import com.github.yuttyann.custommessage.packet.TitleReflection;
import com.github.yuttyann.custommessage.util.Utils;
import com.github.yuttyann.kits.listener.ItemListener;
import com.shampaggon.crackshot.CSUtility;

public class CustomMessage {

	public static CustomMessage getAPI() {
		return new CustomMessage();
	}

	public File getFile() {
		return Files.getConfig().getFile();
	}

	public YamlConfiguration getConfig() {
		return Files.getConfig().getYamlConfiguration();
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
			int lastIndex = displayname.length();
			if (displayname.contains("▪ «")) {
				lastIndex = displayname.lastIndexOf("▪ «");
			} else if (displayname.contains("▫ «")) {
				lastIndex = displayname.lastIndexOf("▫ «");
			} else if (displayname.contains("- «")) {
				lastIndex = displayname.lastIndexOf("- «");
			} else if (displayname.contains("«")){
				lastIndex = displayname.lastIndexOf("«");
			}
			return displayname.substring(0, lastIndex).trim();
		}
		return displayname;
	}

	public String getWeaponTitle(ItemStack item) {
		if (Utils.isPluginEnabled("CrackShot")) {
			return new CSUtility().getWeaponTitle(item);
		}
		return null;
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
		TitleReflection.sendTitle(player, fadein, stay, fadeout, title, subtitle);
	}

	public void sendFullTabTitle(Player player, String header, String footer) {
		TitleReflection.sendTabTitle(player, header, footer);
	}
}
