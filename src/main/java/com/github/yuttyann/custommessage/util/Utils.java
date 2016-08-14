package com.github.yuttyann.custommessage.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Utils {

	public static boolean isLinux() {
		return System.getProperty("os.name").toLowerCase().startsWith("linux");
	}

	public static boolean isMac() {
		return System.getProperty("os.name").toLowerCase().startsWith("mac");
	}

	public static boolean isWindows() {
		return System.getProperty("os.name").toLowerCase().startsWith("windows");
	}

	public static boolean isPluginEnabled(String plugin) {
		return Bukkit.getServer().getPluginManager().isPluginEnabled(plugin);
	}

	public static boolean isUpperVersion(String targetversion) {
		int version = versionInt(getVersion().split("\\."));
		int target = versionInt(targetversion.split("\\."));
		if (version >= target) {
			return true;
		}
		return false;
	}

	private static int versionInt(String[] version) {
		if (version.length < 3) {
			version = new String[]{version[0], version[1], "0"};
		}
		if (version[1].length() == 1) {
			version[1] = "0" + version[1];
		}
		if (version[2].length() == 1) {
			version[2] = "0" + version[2];
		}
		version[2] = "0" + version[2];
		return Integer.parseInt(version[0]) * 100000 + Integer.parseInt(version[1]) * 1000 + Integer.parseInt(version[2]);
	}

	public static Random getRandom() {
		return new Random();
	}

	public static String getLineFeedCode() {
		return "\n";
	}

	public static String getVersion() {
		String version = Bukkit.getServer().getVersion();
		version = version.split("\\(")[1];
		version = version.substring(4, version.length() - 1);
		return version;
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

	public static String getName(UUID uuid) {
		String name = null;
		Player online = getOnlinePlayer(uuid);
		if (online != null) {
			name = online.getName();
		}
		OfflinePlayer offline = getOfflinePlayer(uuid);
		if (offline != null) {
			name = offline.getName();
		}
		return name;
	}

	public static UUID getUniqueId(String name) {
		UUID id = null;
		Player online = getOnlinePlayer(name);
		if (online != null) {
			id = online.getUniqueId();
		}
		OfflinePlayer offline = getOfflinePlayer(name);
		if (offline != null) {
			id = offline.getPlayer().getUniqueId();
		}
		return id;
	}

	public static UUID getUniqueId(Player player) {
		UUID id = null;
		String name = player.getName();
		Player online = getOnlinePlayer(name);
		if (online != null) {
			id = online.getUniqueId();
		}
		OfflinePlayer offline = getOfflinePlayer(name);
		if (offline != null) {
			id = offline.getPlayer().getUniqueId();
		}
		return id;
	}

	public static Set<String> getConfigSection(YamlConfiguration yaml, String path, boolean key) {
		return yaml.getConfigurationSection(path).getKeys(key);
	}

	public static Plugin getPlugin(String plugin) {
		return Bukkit.getServer().getPluginManager().getPlugin(plugin);
	}

	public static Plugin[] getPlugins() {
		return Bukkit.getServer().getPluginManager().getPlugins();
	}

	@SuppressWarnings("deprecation")
	public static Player getPlayer(String name) {
		return Bukkit.getPlayer(name);
	}

	public static Player getOnlinePlayer(String name) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (player.getName().equals(name)) {
				return player;
			}
		}
		return null;
	}

	public static Player getOnlinePlayer(UUID uuid) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (player.getUniqueId().equals(uuid)) {
				return player;
			}
		}
		return null;
	}

	public static OfflinePlayer getOfflinePlayer(String name) {
		for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
			if (player.getName().equals(name)) {
				return player;
			}
		}
		return null;
	}

	public static OfflinePlayer getOfflinePlayer(UUID uuid) {
		for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
			if (player.getPlayer().getUniqueId().equals(uuid)) {
				return player;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<Player> getOnlinePlayers() {
		try {
			if (Bukkit.class.getMethod("getOnlinePlayers", new Class<?>[0]).getReturnType() == Collection.class) {
				Collection<?> temp = ((Collection<?>)Bukkit.class.getMethod("getOnlinePlayers", new Class<?>[0]).invoke(null, new Object[0]));
				return new ArrayList<Player>((Collection<? extends Player>) temp);
			} else {
				Player[] temp = ((Player[])Bukkit.class.getMethod("getOnlinePlayers", new Class<?>[0]).invoke(null, new Object[0]));
				ArrayList<Player> players = new ArrayList<Player>();
				for ( Player t : temp ) {
					players.add(t);
				}
				return players;
			}
		} catch (NoSuchMethodException ex) {
			ex.printStackTrace();
		} catch (InvocationTargetException ex) {
			ex.printStackTrace();
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
		}
		return new ArrayList<Player>();
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<OfflinePlayer> getOfflinePlayers() {
		try {
			if (Bukkit.class.getMethod("getOfflinePlayers", new Class<?>[0]).getReturnType() == Collection.class) {
				Collection<?> temp = ((Collection<?>) Bukkit.class.getMethod("getOfflinePlayers", new Class<?>[0]).invoke(null, new Object[0]));
				return new ArrayList<OfflinePlayer>((Collection<? extends OfflinePlayer>) temp);
			} else {
				OfflinePlayer[] temp = ((OfflinePlayer[]) Bukkit.class.getMethod("getOfflinePlayers", new Class<?>[0]).invoke(null, new Object[0]));
				ArrayList<OfflinePlayer> players = new ArrayList<OfflinePlayer>();
				for (OfflinePlayer t : temp) {
					players.add(t);
				}
				return players;
			}
		} catch (NoSuchMethodException ex) {
			ex.printStackTrace();
		} catch (InvocationTargetException ex) {
			ex.printStackTrace();
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
		}
		return new ArrayList<OfflinePlayer>();
	}
}
