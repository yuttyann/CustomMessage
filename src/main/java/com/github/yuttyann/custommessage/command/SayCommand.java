package com.github.yuttyann.custommessage.command;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import com.github.yuttyann.custommessage.Main;
import com.github.yuttyann.custommessage.Permission;
import com.github.yuttyann.custommessage.file.Files;
import com.github.yuttyann.custommessage.util.StringUtils;

public class SayCommand implements TabExecutor {

	Main plugin;
	boolean apimode;

	public SayCommand(Main plugin, boolean apimode) {
		this.plugin = plugin;
		this.apimode = apimode;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!Permission.has(Permission.BUKKIT_COMMAND_SAY, sender)) {
			sender.sendMessage("§cI'm sorry, but you do not have permission to perform this command. Please contact the server administrators if you believe that this is in error.");
			return true;
		}
		if (args.length == 0) {
			sender.sendMessage("§cUsage: /say <message ...>");
			return true;
		}
		String message;
		if (apimode) {
			message = "&d[%sender] %message";
		} else {
			message = Files.getConfig().getString("Commands.Say");
		}
		message = message.replace("%sender", getName(sender));
		message = message.replace("%message", StringUtils.createString(args, 0).replace("&", "§"));
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

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		return null;
	}
}
