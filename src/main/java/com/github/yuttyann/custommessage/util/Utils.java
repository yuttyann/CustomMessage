package com.github.yuttyann.custommessage.util;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.github.yuttyann.custommessage.file.PluginYaml;

public class Utils {

	public static boolean isWindows() {
		return System.getProperty("os.name").toLowerCase().startsWith("windows");
	}

	public static boolean isPluginEnabled(String plugin) {
		return Bukkit.getServer().getPluginManager().isPluginEnabled(plugin);
	}

	private static Boolean isUpperVersion_v175;
	private static Boolean isUpperVersion_v18;
	private static Boolean isUpperVersion_v19;
	private static Boolean isUpperVersion_v111;

	public static boolean isUpperVersion_v175() {
		if (isUpperVersion_v175 == null) {
			isUpperVersion_v175 = isUpperVersion(getVersion(), "1.7.5");
		}
		return isUpperVersion_v175;
	}

	public static boolean isUpperVersion_v18() {
		if (isUpperVersion_v18 == null) {
			isUpperVersion_v18 = isUpperVersion(getVersion(), "1.8");
		}
		return isUpperVersion_v18;
	}

	public static boolean isUpperVersion_v19() {
		if (isUpperVersion_v19 == null) {
			isUpperVersion_v19 = isUpperVersion(getVersion(), "1.9");
		}
		return isUpperVersion_v19;
	}

	public static boolean isUpperVersion_v111() {
		if (isUpperVersion_v111 == null) {
			isUpperVersion_v111 = isUpperVersion(getVersion(), "1.11");
		}
		return isUpperVersion_v111;
	}

	public static boolean isUpperVersion(String version, String target) {
		return versionInt(version.split("\\.")) >= versionInt(target.split("\\."));
	}

	public static int versionInt(String[] versions) {
		if (versions.length < 3) {
			versions = new String[]{versions[0], versions[1], "0"};
		}
		if (versions[1].length() == 1) {
			versions[1] = "0" + versions[1];
		}
		if (versions[2].length() == 1) {
			versions[2] = "0" + versions[2];
		}
		versions[2] = "0" + versions[2];
		return Integer.parseInt(versions[0]) * 100000 + Integer.parseInt(versions[1]) * 1000 + Integer.parseInt(versions[2]);
	}

	public static String getVersion() {
		String version = Bukkit.getServer().getVersion();
		version = version.split("\\(")[1];
		version = version.substring(4, version.length() - 1);
		return version;
	}

	public static void sendPluginMessage(Object msg) {
		if (msg == null) {
			return;
		}
		String message = msg.toString();
		String prefix = "[" + PluginYaml.getName() + "] ";
		if (message.contains("\\n")) {
			String[] newLine = message.split("\\\\n");
			ConsoleCommandSender sender = Bukkit.getConsoleSender();
			for(int i = 0, l = newLine.length ; i < l ; i++) {
				sender.sendMessage(prefix + newLine[i]);
			}
		} else {
			Bukkit.getConsoleSender().sendMessage(prefix + message);
		}
	}

	public static void sendPluginMessage(CommandSender sender, Object msg) {
		if (msg == null) {
			return;
		}
		String message = msg.toString();
		String colorCode = "";
		for (ChatColor color : ChatColor.values()) {
			if (message.startsWith(color.toString())) {
				colorCode = color.toString();
				break;
			}
		}
		String prefix = "[" + PluginYaml.getName() + "] ";
		if (message.contains("\\n")) {
			String[] newLine = message.split("\\\\n");
			for(int i = 0, l = newLine.length ; i < l ; i++) {
				sender.sendMessage(colorCode + prefix + newLine[i]);
			}
		} else {
			sender.sendMessage(colorCode + prefix + message);
		}
	}

	public static String stringBuilder(String[] args, Integer integer) {
		StringBuilder builder = new StringBuilder();
		for (int i = integer; i < args.length; i++) {
			if (i > integer) {
				builder.append(" ");
			}
			builder.append(args[i]);
		}
		return builder.toString();
	}

	@SuppressWarnings("deprecation")
	public static ItemStack getItemInHand(Player player) {
		if(isUpperVersion_v19()) {
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

	private static final String REGEX_H = "[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}";
	private static final String REGEX_NH = "[0-9a-f]{8}[0-9a-f]{4}[1-5][0-9a-f]{3}[89ab][0-9a-f]{3}[0-9a-f]{12}";

	public static boolean isUUID(String uuid) {
		return uuid.matches(REGEX_H) || uuid.matches(REGEX_NH);
	}

	public static UUID fromString(String uuid) {
		if (uuid.matches(REGEX_NH)) {
			return UUID.fromString(uuid.substring(0, 8) + "-" + uuid.substring(8, 12) + "-" + uuid.substring(12, 16) + "-" + uuid.substring(16, 20) + "-" + uuid.substring(20, 32));
		}
		return UUID.fromString(uuid);
	}

	public static ArrayList<Player> getOnlinePlayers() {
		try {
			return getOnlinePlayers_Method();
		} catch (Exception e) {
			ArrayList<Player> players = new ArrayList<Player>();
			for (Player player : Bukkit.getOnlinePlayers()) {
				players.add(player);
			}
			return players;
		}
	}

	@SuppressWarnings("unchecked")
	private static ArrayList<Player> getOnlinePlayers_Method() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		try {
			if (Bukkit.class.getMethod("getOnlinePlayers", new Class<?>[0]).getReturnType() == Collection.class) {
				Collection<?> temp = ((Collection<?>) Bukkit.class.getMethod("getOnlinePlayers", new Class<?>[0]).invoke(null, new Object[0]));
				return new ArrayList<Player>((Collection<? extends Player>) temp);
			} else {
				Player[] temp = ((Player[]) Bukkit.class.getMethod("getOnlinePlayers", new Class<?>[0]).invoke(null, new Object[0]));
				ArrayList<Player> players = new ArrayList<Player>();
				for (Player t : temp) {
					players.add(t);
				}
				return players;
			}
		} catch (NoSuchMethodException e) {
			throw e;
		} catch (InvocationTargetException e) {
			throw e;
		} catch (IllegalAccessException e) {
			throw e;
		}
	}
}