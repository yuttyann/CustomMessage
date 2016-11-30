package com.github.yuttyann.custommessage.command;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import com.github.yuttyann.custommessage.Main;
import com.github.yuttyann.custommessage.Permission;
import com.github.yuttyann.custommessage.file.Config;
import com.github.yuttyann.custommessage.util.Utils;

public class MeCommand implements TabExecutor {

	Main plugin;
	boolean apimode;

	public MeCommand(Main plugin, Boolean apimode) {
		this.plugin = plugin;
		this.apimode = apimode;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!Permission.has(Permission.BUKKIT_COMMAND_ME, sender)) {
			sender.sendMessage("§cI'm sorry, but you do not have permission to perform this command. Please contact the server administrators if you believe that this is in error.");
			return true;
		}
		if (args.length == 0)  {
			sender.sendMessage("§cUsage: /me <action>");
			return true;
		}
		String message;
		if (apimode) {
			message = "* %sender %message";
		} else {
			message = Config.getString("Commands.Me");;
		}
		message = message.replace("%sender", sender.getName());
		message = message.replace("%message", Utils.stringBuilder(args, 0).replace("&", "§"));
		message = message.replace("&", "§");
		Bukkit.broadcastMessage(message);
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		return null;
	}
}
