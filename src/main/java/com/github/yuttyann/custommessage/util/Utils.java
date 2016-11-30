package com.github.yuttyann.custommessage.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class Utils {

	public static void fileDownload(String url, File file) {
		InputStream input = null;
		FileOutputStream output = null;
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
			conn.setAllowUserInteraction(false);
			conn.setInstanceFollowRedirects(true);
			conn.setRequestMethod("GET");
			conn.connect();
			int httpStatusCode = conn.getResponseCode();
			if (httpStatusCode != HttpURLConnection.HTTP_OK) {
				conn.disconnect();
				return;
			}
			input = conn.getInputStream();
			output = new FileOutputStream(file, false);
			byte[] b = new byte[4096];
			int readByte = 0;
			while (-1 != (readByte = input.read(b))) {
				output.write(b, 0, readByte);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.flush();
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

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
			if (isUpperVersion("1.7.5")) {
				id = offline.getUniqueId();
			} else {
				try {
					id = UUIDFetcher.getUniqueId(name);
				} catch (Exception e) {
					return null;
				}
			}
		}
		return id;
	}

	@SuppressWarnings("deprecation")
	public static ItemStack getItemInHand(Player player) {
		if(isUpperVersion("1.9")) {
			return player.getInventory().getItemInMainHand();
		} else {
			return player.getInventory().getItemInHand();
		}
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
		boolean isUpperVersion_v1_7_5 = isUpperVersion("1.7.5");
		for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
			if (isUpperVersion_v1_7_5) {
				if (player.getUniqueId().equals(uuid)) {
					return player;
				}
			} else {
				if (getUniqueId(player.getName()).equals(uuid)) {
					return player;
				}
			}
		}
		return null;
	}

	public static ArrayList<String> getTextList(File file) {
		ArrayList<String> list = null;
		BufferedReader buReader = null;
		try {
			buReader = new BufferedReader(new FileReader(file));
			list = new ArrayList<String>();
			String line;
			while ((line = buReader.readLine()) != null) {
				list.add(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (buReader != null) {
				try {
					buReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	public static ArrayList<String> getTextList(String url) {
		ArrayList<String> list = null;
		InputStream input = null;
		InputStreamReader inReader = null;
		BufferedReader buReader = null;
		try {
			input = new URL(url).openStream();
			inReader = new InputStreamReader(input);
			buReader = new BufferedReader(inReader);
			list = new ArrayList<String>();
			String line;
			while ((line = buReader.readLine()) != null) {
				list.add(line);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (buReader != null) {
				try {
					buReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (inReader != null) {
				try {
					inReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
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
				for (Player t : temp) {
					players.add(t);
				}
				return players;
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
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
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return new ArrayList<OfflinePlayer>();
	}
}
