package com.github.yuttyann.custommessage.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.github.yuttyann.custommessage.Main;
import com.github.yuttyann.custommessage.Permission;
import com.github.yuttyann.custommessage.file.Config;
import com.github.yuttyann.custommessage.util.Utils;

public class SayCommand implements CommandExecutor {

	Main plugin;

	public SayCommand(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!Permission.has(Permission.BUKKIT_COMMAND_SAY, sender)) {
			sender.sendMessage(ChatColor.RED + "I'm sorry, but you do not have permission to perform this command. Please contact the server administrators if you believe that this is in error.");
			return true;
		}
		if (args.length == 0) {
			sender.sendMessage(ChatColor.RED + "Usage: /say <message ...>");
			return true;
		}
		String message = Config.getString("Commands.Say");
		message = message.replace("%name", getName(sender));
		message = message.replace("%message", Utils.stringBuilder(args, 0).replace("&", "§"));
		message = message.replace("&", "§");
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
}
