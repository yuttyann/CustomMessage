package com.github.yuttyann.custommessage.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.github.yuttyann.custommessage.file.Config;

public class Utils {

	@SuppressWarnings("deprecation")
	public static Player getPlayerExact(String name) {
		return Bukkit.getPlayerExact(name);
	}

	@SuppressWarnings("deprecation")
	public static Player getPlayer(String name) {
		return Bukkit.getPlayer(name);
	}

	public static Server getServer() {
		return Bukkit.getServer();
	}

	public static Plugin getPlugin(String plugin) {
		return Bukkit.getServer().getPluginManager().getPlugin(plugin);
	}

	public static Plugin[] getPlugins() {
		return Bukkit.getServer().getPluginManager().getPlugins();
	}

	public static boolean isPluginEnabled(String plugin) {
		return Bukkit.getServer().getPluginManager().isPluginEnabled(plugin);
	}

	public static Random getRandom() {
		return new Random();
	}

	public static Set<String> getConfigSection(String str, boolean key) {
		return Config.getConfigurationSection(str).getKeys(key);
	}

	public static String stringBuilder(String[] args, Integer integer) {
		StringBuilder builder = new StringBuilder();
		for (int i = integer; i < args.length; i++) {
			if (i > integer)
				builder.append(" ");
			builder.append(args[i]);
		}
		return builder.toString();
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<Player> getOnlinePlayers() {
		try {
			if (Bukkit.class.getMethod("getOnlinePlayers", new Class<?>[0]).getReturnType() == Collection.class) {
				Collection<?> temp = ((Collection<?>)Bukkit.class.getMethod("getOnlinePlayers", new Class<?>[0]).invoke(null, new Object[0]));
				return new ArrayList<Player>((Collection<? extends Player>)temp);
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

	public static void getCommandTemplate(CommandSender sender) {
		sender.sendMessage(ChatColor.LIGHT_PURPLE + "=== CustomMessage Commands ===");
		sender.sendMessage(ChatColor.AQUA + "/custommessage reload - Configの再読み込みをします。");
		sender.sendMessage(ChatColor.AQUA + "/rules - ルールを表示します。");
		sender.sendMessage(ChatColor.AQUA + "/title <player> <title> <subtitle> - 指定したプレイヤーにタイトルを表示します。");
		sender.sendMessage(ChatColor.AQUA + "/title tab <player> <header> <footer> - 指定したプレイヤーにタブタイトルを表示します。");
	}
}
