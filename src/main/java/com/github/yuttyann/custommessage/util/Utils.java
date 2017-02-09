package com.github.yuttyann.custommessage.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.github.yuttyann.custommessage.file.Files;
import com.github.yuttyann.custommessage.file.PluginYaml;

public class Utils {

	private static final String REGEX_H = "[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}";
	private static final String REGEX_NH = "[0-9a-f]{8}[0-9a-f]{4}[1-5][0-9a-f]{3}[89ab][0-9a-f]{3}[0-9a-f]{12}";

	private static String serverVersion;
	private static Boolean isWindows;
	private static Boolean isCB175orLaterCache;
	private static Boolean isCB178orLaterCache;
	private static Boolean isCB18orLaterCache;
	private static Boolean isCB19orLaterCache;

	public static boolean isWindows() {
		if (isWindows == null) {
			isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
		}
		return isWindows;
	}

	public static boolean isPluginEnabled(String plugin) {
		return Bukkit.getServer().getPluginManager().isPluginEnabled(plugin);
	}

	public static boolean isCB175orLater() {
		if (isCB175orLaterCache == null) {
			isCB175orLaterCache = isUpperVersion(getVersion(), "1.7.5");
		}
		return isCB175orLaterCache;
	}

	public static boolean isCB178orLater() {
		if (isCB178orLaterCache == null) {
			isCB178orLaterCache = isUpperVersion(getVersion(), "1.7.8");
		}
		return isCB178orLaterCache;
	}

	public static boolean isCB18orLater() {
		if (isCB18orLaterCache == null) {
			isCB18orLaterCache = isUpperVersion(getVersion(), "1.8");
		}
		return isCB18orLaterCache;
	}

	public static boolean isCB19orLater() {
		if (isCB19orLaterCache == null) {
			isCB19orLaterCache = isUpperVersion(getVersion(), "1.9");
		}
		return isCB19orLaterCache;
	}

	public static boolean isUpperVersion(String version, String target) {
		return getVersionInt(version) >= getVersionInt(target);
	}

	public static int getVersionInt(String version) {
		String[] array = StringUtils.split(version, ".");
		int result = (Integer.parseInt(array[0]) * 100000) + (Integer.parseInt(array[1]) * 1000);
		if (array.length == 3) {
			result += Integer.parseInt(array[2]);
		}
		return result;
	}

	public static String getVersion() {
		if (serverVersion == null) {
			String version = Bukkit.getBukkitVersion();
			serverVersion = version.substring(0, version.indexOf("-"));
		}
		return serverVersion;
	}

	public static void sendPluginMessage(Object msg) {
		sendPluginMessage(Bukkit.getConsoleSender(), msg);
	}

	public static void sendPluginMessage(CommandSender sender, Object msg) {
		if (msg == null) {
			return;
		}
		String message = msg.toString();
		String color = "";
		if (sender instanceof Player) {
			for (ChatColor ccolor : ChatColor.values()) {
				if (message.startsWith(ccolor.toString())) {
					color = ccolor.toString();
					break;
				}
			}
		}
		String prefix = "";
		if (Files.getConfig().getBoolean("MessagePrefix")) {
			prefix = "[" + PluginYaml.getName() + "] ";
		}
		if (message.contains("\\n")) {
			String[] newLine = StringUtils.split(message, "\n");
			for(int i = 0, l = newLine.length ; i < l ; i++) {
				sender.sendMessage(color + prefix + newLine[i]);
			}
		} else {
			sender.sendMessage(color + prefix + message);
		}
	}

	@SuppressWarnings("deprecation")
	public static ItemStack getItemInHand(Player player) {
		if(isCB19orLater()) {
			return player.getInventory().getItemInMainHand();
		} else {
			return player.getInventory().getItemInHand();
		}
	}

	public static World getWorld(String name) {
		World world = null;
		if (Bukkit.getWorld(name) != null) {
			world = Bukkit.getWorld(name);
		} else if (isWorld(name)) {
			world = Bukkit.createWorld(WorldCreator.name(name));
		}
		return world;
	}

	public static boolean isWorld(String world) {
		return new File(world + "/level.dat").exists();
	}

	public static Player getOnlinePlayer(Object uuid_or_name) {
		boolean isUUID = false;
		if (uuid_or_name instanceof UUID) {
			isUUID = true;
		} else if (uuid_or_name instanceof String) {
			String str = uuid_or_name.toString();
			if (isUUID(str)) {
				uuid_or_name = fromString(str);
				isUUID = true;
			}
		}
		Object value;
		for (Player player : getOnlinePlayers()) {
			if (isUUID) {
				value = player.getUniqueId();
			} else {
				value = player.getName();
			}
			if (value.equals(uuid_or_name)) {
				return player;
			}
		}
		return null;
	}

	public static boolean isUUID(String uuid) {
		return uuid.matches(REGEX_H) || uuid.matches(REGEX_NH);
	}

	public static UUID fromString(String uuid) {
		try {
			if (uuid.matches(REGEX_NH)) {
				return UUID.fromString(uuid.substring(0, 8) + "-" + uuid.substring(8, 12) + "-" + uuid.substring(12, 16) + "-" + uuid.substring(16, 20) + "-" + uuid.substring(20, 32));
			}
			return UUID.fromString(uuid);
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<Player> getOnlinePlayers() {
		Object players = Bukkit.getOnlinePlayers();
		if (players instanceof Collection) {
			return new ArrayList<Player>((Collection<? extends Player>) players);
		}
		return new ArrayList<Player>(Arrays.asList((Player[]) players));
	}

	public static ArrayList<OfflinePlayer> getOfflinePlayers() {
		return new ArrayList<OfflinePlayer>(Arrays.asList(Bukkit.getOfflinePlayers()));
	}
}