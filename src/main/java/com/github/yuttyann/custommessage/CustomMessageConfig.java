package com.github.yuttyann.custommessage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class CustomMessageConfig {

	private static Main plugin = null;
	private static String Encode = null;

	public static void loadConfig(Main plugin, String encode) {
		CustomMessageConfig.plugin = plugin;
		CustomMessageConfig.Encode = encode;
		File configFile = new File(plugin.getDataFolder(), "config_" + encode + ".yml");
		if (!configFile.exists()) {
			plugin.saveResource("config_" + encode + ".yml", false);
		}
	}

	public static void reloadConfig() {
		File configFile = new File(plugin.getDataFolder(), "config_" + Encode + ".yml");
		FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(configFile);
		InputStream defConfigStream = plugin.getResource("config_" + Encode + ".yml");
		if (defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			fileConfiguration.setDefaults(defConfig);
		}
	}

	public static FileConfiguration getConfig() {
		File configFile = new File(plugin.getDataFolder(), "config_" + Encode + ".yml");
		FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(configFile);
		return fileConfiguration;
	}

	public static void saveConfig() {
		File configFile = new File(plugin.getDataFolder(), "config_" + Encode + ".yml");
		if (configFile.exists()) {
			FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(configFile);
			try {
				fileConfiguration.save(configFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
