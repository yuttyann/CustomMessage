package com.github.yuttyann.custommessage.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.github.yuttyann.custommessage.Main;
import com.github.yuttyann.custommessage.Permission;
import com.github.yuttyann.custommessage.file.Config;

public class RulesCommand implements CommandExecutor {

	Main plugin;

	public RulesCommand(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!Config.getBoolean("Rules.Enable")) {
			sender.sendMessage("Unknown command. Type \"/help\" for help.");
			return true;
		}
		if (!Permission.has(Permission.CUSTOMMESSAGE_COMMAND_RULES, sender)) {
			sender.sendMessage(ChatColor.RED + "パーミッションが無いため、実行できません。");
			return true;
		}
		for (String message : Config.getStringList("Rules.Message")) {
			message = message.replace("&", "§");
			sender.sendMessage(message);
		}
		return true;
	}
}
