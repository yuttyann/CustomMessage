package com.github.yuttyann.custommessage.command;

import org.apache.commons.lang.StringUtils;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.yuttyann.custommessage.Main;
import com.github.yuttyann.custommessage.Permission;

public class BanCommand implements CommandExecutor {

	Main plugin;

	public BanCommand(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!Permission.has(Permission.BUKKIT_COMMAND_BAN_PLAYER, sender)) {
			sender.sendMessage(ChatColor.RED + "I'm sorry, but you do not have permission to perform this command. Please contact the server administrators if you believe that this is in error.");
			return true;
		}
		if (args.length == 0) {
			sender.sendMessage(ChatColor.RED + "Usage: /ban <player> [reason ...]");
			return false;
		}
		String reason = args.length > 0 ? StringUtils.join(args, ' ', 1, args.length) : null;
		Bukkit.getBanList(BanList.Type.NAME).addBan(args[0], reason, null, sender.getName());
		Player player = Bukkit.getPlayerExact(args[0]);
		if (player != null) {
			player.kickPlayer("Banned by admin.");
		}
		Command.broadcastCommandMessage(sender, "Banned player " + args[0]);
		return true;
	}
}
