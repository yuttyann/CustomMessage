package com.github.yuttyann.custommessage.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Color;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import com.github.yuttyann.custommessage.Main;
import com.github.yuttyann.custommessage.Version;
import com.google.common.base.Charsets;

public class CustomMessageConfig {

	static Main plugin;

	private static String filename;
	private static File configFile;
	private static YamlConfiguration config;

	public CustomMessageConfig(Main plugin, String encode) {
		CustomMessageConfig.plugin = plugin;
		CustomMessageConfig.filename = "config_" + encode + ".yml";
		CustomMessageConfig.configFile = new File(plugin.getDataFolder(), filename);
		if (!CustomMessageConfig.configFile.exists()) {
			plugin.saveResource(filename, false);
		}
		CustomMessageConfig.config = YamlConfiguration.loadConfiguration(configFile);
	}

	public static YamlConfiguration getConfig() {
		return config;
	}

	@SuppressWarnings("deprecation")
	public static void reloadConfig() {
		if (!configFile.exists()) {
			plugin.saveResource(filename, false);
		}
		config = YamlConfiguration.loadConfiguration(configFile);
		InputStream defConfigStream = plugin.getResource(filename);
		if (defConfigStream != null) {
			YamlConfiguration defConfig;
			if(Version.isVersion("1.9")) {
				defConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream, Charsets.UTF_8));
			} else {
				defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			}
			config.setDefaults(defConfig);
		}
	}

	public static void saveConfig() {
		if (configFile.exists()) {
			try {
				config.save(configFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void save(File file) {
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void save(String file) {
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		};
	}

	public static void addDefault(String path, Object value) {
		config.addDefault(path, value);
	}

	public static void addDefault(Configuration defaults) {
		config.addDefaults(defaults);
	}

	public static void addDefault(Map<String, Object> defaults) {
		config.addDefaults(defaults);
	}

	public static void set(String path, Object value) {
		config.set(path, value);
	}

	public static void setDefaults(Configuration defaults) {
		config.setDefaults(defaults);
	}

	public static ConfigurationSection createSection(String path) {
		return config.createSection(path);
	}

	public static ConfigurationSection createSection(String path, Map<?, ?> map) {
		return config.createSection(path, map);
	}

	public static ConfigurationSection getConfigurationSection(String path) {
		return config.getConfigurationSection(path);
	}

	public static Configuration getDefaults() {
		return config.getDefaults();
	}

	public static ConfigurationSection getDefaultSection() {
		return config.getDefaultSection();
	}

	public static ConfigurationSection getParent() {
		return config.getParent();
	}

	public static Configuration getRoot() {
		return config.getRoot();
	}

	public static String getCurrentPath() {
		return config.getCurrentPath();
	}

	public static String getName() {
		return config.getName();
	}

	public static String getString(String path) {
		return config.getString(path);
	}

	public static String getString(String path, String def) {
		return config.getString(path, def);
	}

	public static String saveToString() {
		return config.saveToString();
	}

	public static Object get(String path) {
		return config.get(path);
	}

	public static Object get(String path, Object def) {
		return config.get(path, def);
	}

	public static Color getColor(String path) {
		return config.getColor(path);
	}

	public static Color getColor(String path, Color def) {
		return config.getColor(path, def);
	}

	public static ItemStack getItemStack(String path) {
		return config.getItemStack(path);
	}

	public static ItemStack getItemStack(String path, ItemStack def) {
		return config.getItemStack(path, def);
	}

	public static Vector getVector(String path) {
		return config.getVector(path);
	}

	public static Vector getVector(String path, Vector def) {
		return config.getVector(path, def);
	}

	public static FileConfigurationOptions options(String path) {
		return config.options();
	}

	public static boolean contains(String path) {
		return config.contains(path);
	}

	public static boolean getBoolean(String path) {
		return config.getBoolean(path);
	}

	public static boolean getBoolean(String path, boolean def) {
		return config.getBoolean(path, def);
	}

	public static boolean isString(String path) {
		return config.isString(path);
	}

	public static boolean isColor(String path) {
		return config.isColor(path);
	}

	public static boolean isItemStack(String path) {
		return config.isItemStack(path);
	}

	public static boolean isVector(String path) {
		return config.isVector(path);
	}

	public static boolean isBoolean(String path) {
		return config.isBoolean(path);
	}

	public static boolean isOfflinePlayer(String path) {
		return config.isOfflinePlayer(path);
	}

	public static boolean isConfigurationSection(String path) {
		return config.isConfigurationSection(path);
	}

	public static boolean isInt(String path) {
		return config.isInt(path);
	}

	public static boolean isDouble(String path) {
		return config.isDouble(path);
	}

	public static boolean isLong(String path) {
		return config.isLong(path);
	}

	public static boolean isSet(String path) {
		return config.isSet(path);
	}

	public static boolean isList(String path) {
		return config.isList(path);
	}

	public static int getInt(String path) {
		return config.getInt(path);
	}

	public static int getInt(String path, int def) {
		return config.getInt(path, def);
	}

	public static double getDouble(String path) {
		return config.getDouble(path);
	}

	public static double getDouble(String path, double def) {
		return config.getDouble(path, def);
	}

	public static long getLong(String path) {
		return config.getLong(path);
	}

	public static long getLong(String path, long def) {
		return config.getLong(path, def);
	}

	public static Map<String, Object> getValues(boolean deep) {
		return config.getValues(deep);
	}

	public static Set<String> getKeys(boolean deep) {
		return config.getKeys(deep);
	}

	public static List<?> getList(String path) {
		return config.getList(path);
	}

	public static List<?> getList(String path, List<?> def) {
		return config.getList(path, def);
	}

	public static List<Map<?, ?>> getMapList(String path) {
		return config.getMapList(path);
	}

	public static List<String> getStringList(String path) {
		return config.getStringList(path);
	}

	public static List<Boolean> getBooleanList(String path) {
		return config.getBooleanList(path);
	}

	public static List<Character> getCharacterList(String path) {
		return config.getCharacterList(path);
	}

	public static List<Integer> getIntegerList(String path) {
		return config.getIntegerList(path);
	}

	public static List<Double> getDoubleList(String path) {
		return config.getDoubleList(path);
	}

	public static List<Float> getFloatList(String path) {
		return config.getFloatList(path);
	}

	public static List<Long> getLongList(String path) {
		return config.getLongList(path);
	}

	public static List<Short> getShortList(String path) {
		return config.getShortList(path);
	}

	public static List<Byte> getByteList(String path) {
		return config.getByteList(path);
	}
}