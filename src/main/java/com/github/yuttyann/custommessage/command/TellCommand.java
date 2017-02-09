package com.github.yuttyann.custommessage.command;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import com.github.yuttyann.custommessage.Main;
import com.github.yuttyann.custommessage.Permission;
import com.github.yuttyann.custommessage.file.Files;
import com.github.yuttyann.custommessage.file.YamlConfig;
import com.github.yuttyann.custommessage.util.StringUtils;
import com.github.yuttyann.custommessage.util.Utils;

public class TellCommand implements TabExecutor {

	Main plugin;
	boolean apimode;

	public TellCommand(Main plugin, boolean apimode) {
		this.plugin = plugin;
		this.apimode = apimode;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!Permission.has(Permission.BUKKIT_COMMAND_TELL, sender)) {
			sender.sendMessage("§cI'm sorry, but you do not have permission to perform this command. Please contact the server administrators if you believe that this is in error.");
			return true;
		}
		if (args.length < 2) {
			sender.sendMessage("§cUsage: /tell <player> <message>");
			return true;
		}
		Player player = Utils.getOnlinePlayer(args[0]);
		if (player == null || (sender instanceof Player && !((Player) sender).canSee(player))) {
			sender.sendMessage("There's no player by that name online.");
		} else {
			String stringBuilder = StringUtils.createString(args, 1);
			String tell;
			String tell_target;
			if (apimode) {
				tell = "[%sender->%target] %message";
				tell_target = "&7%sender whispers %message";
			} else {
				YamlConfig config = Files.getConfig();
				tell = config.getString("Commands.tell");
				tell_target = config.getString("Commands.tell_target");
			}
			tell = tell.replace("%target", player.getName());
			tell = tell.replace("%sender", sender.getName());
			tell = tell.replace("%message", stringBuilder);
			tell = tell.replace("&", "§");
			tell_target = tell_target.replace("%target", player.getName());
			tell_target = tell_target.replace("%sender", sender.getName());
			tell_target = tell_target.replace("%message", stringBuilder);
			tell_target = tell_target.replace("&", "§");
			sender.sendMessage(tell);
			player.sendMessage(tell_target);
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		return null;
	}
}