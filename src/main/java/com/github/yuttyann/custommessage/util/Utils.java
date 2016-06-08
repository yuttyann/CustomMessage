package com.github.yuttyann.custommessage.util;

import java.util.Calendar;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.yuttyann.custommessage.file.Config;

public class Utils {

	private static String os_name;
	private static Integer year;
	private static Integer month;
	private static Integer day;
	private static Integer hour;
	private static Integer minute;
	private static Integer second;
	private static Integer week;
	private static Integer day_of_year;

	private static String[] week_name =
	{
		Config.getString("Sunday"), Config.getString("Monday"), Config.getString("Tuesday"), Config.getString("Wednesday"),
		Config.getString("Thursday"), Config.getString("Friday"), Config.getString("Saturday")
	};

	public static boolean isLinux() {
		os_name = System.getProperty("os.name").toLowerCase();
		return os_name.startsWith("linux");
	}

	public static boolean isMac() {
		os_name = System.getProperty("os.name").toLowerCase();
		return os_name.startsWith("mac");
	}

	public static boolean isWindows() {
		os_name = System.getProperty("os.name").toLowerCase();
		return os_name.startsWith("windows");
	}

	public static boolean isVersion(String targetversion) {
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
		if (version[2].length() == 1) {
			version[2] = "0" + version[2];
		}
		version[2] = "0" + version[2];
		return parseInt(version[0]) * 10000 + parseInt(version[1]) * 1000 + parseInt(version[2]);
	}

	public static String getVersion() {
		String version = Bukkit.getServer().getVersion();
		version = version.split("\\(")[1];
		version = version.substring(4, version.length() - 1);
		return version;
	}

	private static int parseInt(String str) {
		return Integer.parseInt(str);
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

	@SuppressWarnings("deprecation")
	public static Player getPlayerExact(String name) {
		return Bukkit.getPlayerExact(name);
	}

	@SuppressWarnings("deprecation")
	public static Player getPlayer(String name) {
		return Bukkit.getPlayer(name);
	}

	public static Integer getYear() {
		year = Calendar.getInstance().get(Calendar.YEAR);
		return year;
	}

	public static Integer getMonth() {
		month = Calendar.getInstance().get(Calendar.MONTH) + 1;
		return month;
	}

	public static Integer getDay() {
		day = Calendar.getInstance().get(Calendar.DATE);
		return day;
	}

	public static Integer getHour() {
		hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		return hour;
	}

	public static Integer getMinute() {
		minute = Calendar.getInstance().get(Calendar.MINUTE);
		return minute;
	}

	public static Integer getSecond() {
		second = Calendar.getInstance().get(Calendar.SECOND);
		return second;
	}

	public static Integer getWeek() {
		week = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;
		return week;
	}

	public static Integer getDay_Of_Year() {
		day_of_year = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
		return day_of_year;
	}

	public static String getTime() {
		String time = Config.getString("Time");
		time = time.replace("%year", getYear().toString());
		time = time.replace("%month", getMonth().toString());
		time = time.replace("%day", getDay().toString());
		time = time.replace("%hour", getHour().toString());
		time = time.replace("%minute", getMinute().toString());
		time = time.replace("%second", getSecond().toString());
		time = time.replace("%week", week_name[getWeek()]);
		time = time.replace("%dayofyear", getDay_Of_Year().toString());
		time = time.replace("&", "§");
		return time;
	}

	public static void getCommandTemplate(CommandSender sender) {
		sender.sendMessage(ChatColor.LIGHT_PURPLE + "=== CustomMessage Commands ===");
		sender.sendMessage(ChatColor.AQUA + "/custommessage reload - Configの再読み込みをします。");
		sender.sendMessage(ChatColor.AQUA + "/rules - ルールを表示します。");
		sender.sendMessage(ChatColor.AQUA + "/title <player> <title> <subtitle> - 指定したプレイヤーにタイトルを表示します。");
		sender.sendMessage(ChatColor.AQUA + "/title tab <player> <header> <footer> - 指定したプレイヤーにタブタイトルを表示します。");
	}
}
