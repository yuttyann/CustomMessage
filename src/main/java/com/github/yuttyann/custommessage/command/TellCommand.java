package com.github.yuttyann.custommessage.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.yuttyann.custommessage.Main;
import com.github.yuttyann.custommessage.Permission;
import com.github.yuttyann.custommessage.config.CustomMessageConfig;

public class TellCommand implements CommandExecutor {

	Main plugin;

	public TellCommand(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!Permission.hasPermission(Permission.BUKKIT_COMMAND_TELL, sender)) {
			sender.sendMessage(ChatColor.RED + "I'm sorry, but you do not have permission to perform this command. Please contact the server administrators if you believe that this is in error.");
			return true;
		}
		if (args.length < 2) {
			sender.sendMessage(ChatColor.RED + "Usage: /tell <player> <message>");
			return false;
		}
		Player player = Bukkit.getPlayerExact(args[0]);
		if (player == null || (sender instanceof Player && !((Player) sender).canSee(player))) {
			sender.sendMessage("There's no player by that name online.");
		} else {
			String stringBuilder = stringBuilder(args, 1);
			String tell = CustomMessageConfig.getString("Commands.tell");
			String tell_target = CustomMessageConfig.getString("Commands.tell_target");
			tell = tell.replace("%target", player.getName());
			tell = tell.replace("%name", sender.getName());
			tell = tell.replace("%message", stringBuilder);
			tell = tell.replace("&", "§");
			tell_target = tell_target.replace("%target", player.getName());
			tell_target = tell_target.replace("%name", sender.getName());
			tell_target = tell_target.replace("%message", stringBuilder);
			tell_target = tell_target.replace("&", "§");
			sender.sendMessage(tell);
			player.sendMessage(tell_target);
		}
		return false;
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
