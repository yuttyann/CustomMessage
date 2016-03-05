package com.github.yuttyann.custommessage.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.github.yuttyann.custommessage.Main;
import com.github.yuttyann.custommessage.handle.ClassHandler;

public class SayCommand implements CommandExecutor {

	Main plugin;

	public SayCommand(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("bukkit.command.say")) {
			sender.sendMessage(ChatColor.RED + "I'm sorry, but you do not have permission to perform this command. Please contact the server administrators if you believe that this is in error.");
			return true;
		}
		if (args.length == 0) {
			sender.sendMessage(ChatColor.RED + "Usage: /say <message ...>");
			return false;
		}
		String message = ClassHandler.getMainClass().getConfig().getString("Commands.Say");
		message = message.replace("&", "§");
		message = message.replace("%name", getName(sender));
		message = message.replace("%message", stringBuilder(args, 0).replace("&", "§"));
		Bukkit.broadcastMessage(message);
		return true;
	}

	public String getName(CommandSender sender) {
		if (sender instanceof ConsoleCommandSender) {
			return "Server";
		} else if (sender instanceof Player) {
			return ((Player) sender).getDisplayName();
		} else {
			return sender.getName();
		}
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
}
