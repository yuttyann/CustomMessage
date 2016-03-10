package com.github.yuttyann.custommessage.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.github.yuttyann.custommessage.Main;
import com.github.yuttyann.custommessage.Permission;
import com.github.yuttyann.custommessage.config.CustomMessageConfig;

public class MeCommand implements CommandExecutor {

	Main plugin;

	public MeCommand(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!Permission.hasPermission(Permission.BUKKIT_COMMAND_ME, sender)) {
			sender.sendMessage(ChatColor.RED + "I'm sorry, but you do not have permission to perform this command. Please contact the server administrators if you believe that this is in error.");
			return true;
		}
		if (args.length == 0)  {
			sender.sendMessage(ChatColor.RED + "Usage: /me <action>");
			return false;
		}
		String message = CustomMessageConfig.getString("Commands.Me");
		message = message.replace("%name", sender.getName());
		message = message.replace("%message", stringBuilder(args, 0).replace("&", "§"));
		message = message.replace("&", "§");
		Bukkit.broadcastMessage(message);
		return true;
	}

	private String stringBuilder(String[] args, Integer integer) {
		StringBuilder builder = new StringBuilder();
		for (int i = integer; i < args.length; i++) {
			if (i > integer)
				builder.append(" ");
			builder.append(args[i]);
		}
		return builder.toString();
	}
}
