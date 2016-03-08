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
		if (CustomMessageConfig.getConfig().getBoolean("CustomMessageAPI")) {
			sender.sendMessage("Unknown command. Type \"/help\" for help.");
			return true;
		}
		if (!Permission.hasPermission(Permission.CUSTOMMESSAGE_RELOAD, sender)) {
			sender.sendMessage(ChatColor.RED + "権限がありません");
			return true;
		}
		if (args.length == 0) {
			sender.sendMessage(ChatColor.GREEN + "コマンド: /custommessage reload");
			return true;
		}
		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("reload")) {
				CustomMessageConfig.reloadConfig();
				sender.sendMessage(ChatColor.GREEN + "Configのリロードが完了しました");
				return true;
			}
			return true;
		}
		return false;
	}
}
