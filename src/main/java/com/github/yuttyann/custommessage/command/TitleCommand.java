package com.github.yuttyann.custommessage.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.yuttyann.custommessage.Main;
import com.github.yuttyann.custommessage.Permission;
import com.github.yuttyann.custommessage.api.CustomMessage;
import com.github.yuttyann.custommessage.util.Utils;

public class TitleCommand implements CommandExecutor {

	Main plugin;

	public TitleCommand(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!Permission.has(Permission.CUSTOMMESSAGE_COMMAND_TITLE, sender)) {
			sender.sendMessage(ChatColor.RED + "パーミッションが無いため、実行できません。");
			return true;
		}
		if (args.length == 3) {
			if (!args[0].equalsIgnoreCase("tab")) {
				Player player = Utils.getPlayerExact(args[0]);
				if (player == null) {
					sender.sendMessage(ChatColor.RED + args[0] + " というプレイヤーは見つかりません。");
					return true;
				}
				String title = args[1];
				String subtitle = args[2];
				CustomMessage.getAPI().sendFullTitle(player, 10, 40, 10, title, subtitle);
				return true;
			}
		}
		if (args.length == 4) {
			if (args[0].equalsIgnoreCase("tab")) {
				Player player = Utils.getPlayerExact(args[1]);
				if (player == null) {
					sender.sendMessage(ChatColor.RED + args[1] + " というプレイヤーは見つかりません。");
					return true;
				}
				String header = args[2];
				String footer = args[3];
				CustomMessage.getAPI().sendFullTabTitle(player, header, footer);
				return true;
			}
		}
		Utils.getCommandTemplate(sender);
		return true;
	}
}
