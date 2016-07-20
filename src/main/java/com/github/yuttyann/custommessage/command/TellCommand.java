package com.github.yuttyann.custommessage.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import com.github.yuttyann.custommessage.Main;
import com.github.yuttyann.custommessage.Permission;
import com.github.yuttyann.custommessage.file.Config;
import com.github.yuttyann.custommessage.util.Utils;

public class TellCommand implements TabExecutor {

	Main plugin;

	public TellCommand(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!Permission.has(Permission.BUKKIT_COMMAND_TELL, sender)) {
			sender.sendMessage(ChatColor.RED + "I'm sorry, but you do not have permission to perform this command. Please contact the server administrators if you believe that this is in error.");
			return true;
		}
		if (args.length < 2) {
			sender.sendMessage(ChatColor.RED + "Usage: /tell <player> <message>");
			return true;
		}
		Player player = Utils.getOnlinePlayer(args[0]);
		if (player == null || (sender instanceof Player && !((Player) sender).canSee(player))) {
			sender.sendMessage("There's no player by that name online.");
		} else {
			String stringBuilder = Utils.stringBuilder(args, 1);
			String tell = Config.getString("Commands.tell");
			String tell_target = Config.getString("Commands.tell_target");
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
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 1) {
			String prefix = args[0].toLowerCase();
			ArrayList<String> commands = new ArrayList<String>();
			for (Player player : Utils.getOnlinePlayers()) {
				String name = player.getName();
				if (name.startsWith(prefix)) {
					commands.add(name);
				}
			}
			return commands;
		}
		return null;
	}
}