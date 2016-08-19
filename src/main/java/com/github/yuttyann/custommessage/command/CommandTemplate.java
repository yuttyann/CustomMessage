package com.github.yuttyann.custommessage.command;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.github.yuttyann.custommessage.Main;
import com.github.yuttyann.custommessage.Updater;

public class CommandTemplate {

	private static String pluginname;
	private static ArrayList<String> commandtemplate;

	public CommandTemplate(Main plugin) {
		pluginname = Updater.getUpdater().getPluginName();
		commandtemplate = new ArrayList<String>();
	}

	public static ArrayList<String> getCommandTemplate() {
		return commandtemplate;
	}

	public static void clear() {
		commandtemplate.clear();
	}

	public static void addCommand(String command) {
		commandtemplate.add(command);
	}

	public static void removeCommand(String command) {
		commandtemplate.remove(command);
	}

	public static String getCommand(int index) {
		return commandtemplate.get(index);
	}

	public static void sendCommandTemplate(CommandSender sender) {
		sender.sendMessage(ChatColor.LIGHT_PURPLE + "=== " + pluginname + " Commands ===");
		for (String commands : commandtemplate) {
			sender.sendMessage(ChatColor.AQUA + commands);
		}
	}
}
