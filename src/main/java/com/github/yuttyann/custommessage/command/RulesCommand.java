package com.github.yuttyann.custommessage.command;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.github.yuttyann.custommessage.Main;
import com.github.yuttyann.custommessage.Permission;
import com.github.yuttyann.custommessage.config.CustomMessageConfig;

public class RulesCommand implements CommandExecutor {

	Main plugin;

	public RulesCommand(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!CustomMessageConfig.getBoolean("Rules.Enable")) {
			sender.sendMessage("Unknown command. Type \"/help\" for help.");
			return true;
		}
		if (!Permission.has(Permission.CUSTOMMESSAGE_RULES, sender)) {
			sender.sendMessage(ChatColor.RED + "パーミッションが無いため、実行できません。");
			return true;
		}
		List<String> rules = CustomMessageConfig.getStringList("Rules.Message");
		for (String message : rules) {
			message = message.replace("&", "§");
			sender.sendMessage(message);
		}
		return true;
	}
}
