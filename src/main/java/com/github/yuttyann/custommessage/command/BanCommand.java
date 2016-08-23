package com.github.yuttyann.custommessage.command;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import com.github.yuttyann.custommessage.Main;
import com.github.yuttyann.custommessage.Permission;
import com.github.yuttyann.custommessage.util.Utils;

public class BanCommand implements TabExecutor {

	Main plugin;

	public BanCommand(Main plugin) {
		this.plugin = plugin;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!Permission.has(Permission.BUKKIT_COMMAND_BAN_PLAYER, sender)) {
			sender.sendMessage(ChatColor.RED + "I'm sorry, but you do not have permission to perform this command. Please contact the server administrators if you believe that this is in error.");
			return true;
		}
		if (args.length == 0) {
			sender.sendMessage(ChatColor.RED + "Usage: /ban <player> [reason ...]");
			return true;
		}
		String reason = args.length > 0 ? StringUtils.join(args, ' ', 1, args.length) : null;
		if (Utils.isUpperVersion("1.7.5")) {
			Bukkit.getBanList(BanList.Type.NAME).addBan(args[0], reason, null, sender.getName());
		} else {
			Bukkit.getOfflinePlayer(args[0]).setBanned(true);
		}
		Player player = Utils.getOnlinePlayer(args[0]);
		if (player != null) {
			if (args.length >= 2) {
				player.kickPlayer(reason);
			} else {
				player.kickPlayer("Banned by admin.");
			}
		}
		Command.broadcastCommandMessage(sender, "Banned player " + args[0]);
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		return null;
	}
}
