package com.github.yuttyann.custommessage.command;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.github.yuttyann.custommessage.Main;
import com.github.yuttyann.custommessage.handle.ClassHandler;

public class RulesCommand implements CommandExecutor {

	Main plugin;

	public RulesCommand(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (plugin.getConfig().getBoolean("CustomMessageAPI")) {
			sender.sendMessage("Unknown command. Type \"/help\" for help.");
			return true;
		}
		if (!plugin.getConfig().getBoolean("Rules.Enable")) {
			sender.sendMessage("Unknown command. Type \"/help\" for help.");
			return true;
		}
		if (!sender.hasPermission("custommessage.rules")) {
			sender.sendMessage(ChatColor.RED + "権限がありません");
			return true;
		}
		List<String> rules = ClassHandler.getMainClass().getConfig().getStringList("Rules.Message");
		for (String s : rules) {
			s = s.replace("&", "§");
			sender.sendMessage(s);
		}
		return true;
	}
}