package com.github.yuttyann.custommessage.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.github.yuttyann.custommessage.Main;
import com.github.yuttyann.custommessage.Permission;
import com.github.yuttyann.custommessage.config.CustomMessageConfig;

public class CustomMessageCommand implements CommandExecutor {

	Main plugin;

	public CustomMessageCommand(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!Permission.has(Permission.CUSTOMMESSAGE_RELOAD, sender)) {
			sender.sendMessage(ChatColor.RED + "パーミッションが無いため、実行できません。");
			return true;
		}
		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("reload")) {
				CustomMessageConfig.reloadConfig();
				sender.sendMessage(ChatColor.GREEN + "Configの再読み込みが完了しました。");
				return true;
			}
		}
		sender.sendMessage(ChatColor.LIGHT_PURPLE + "=== CustomMessage Commands ===");
		sender.sendMessage(ChatColor.AQUA + "/custommessage reload - Configの再読み込みをします。");
		sender.sendMessage(ChatColor.AQUA + "/rules - ルールを表示します。");
		return true;
	}
}
