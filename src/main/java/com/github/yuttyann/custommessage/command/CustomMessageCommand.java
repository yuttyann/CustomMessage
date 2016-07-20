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
import com.github.yuttyann.custommessage.api.CustomMessage;
import com.github.yuttyann.custommessage.file.Config;
import com.github.yuttyann.custommessage.util.Utils;

public class CustomMessageCommand implements TabExecutor {

	Main plugin;

	public CustomMessageCommand(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("reload")) {
				if (!Permission.has(Permission.CUSTOMMESSAGE_COMMAND_RELOAD, sender)) {
					sender.sendMessage(ChatColor.RED + "パーミッションが無いため、実行できません。");
					return true;
				}
				Config.reloadConfig();
				sender.sendMessage(ChatColor.GREEN + "Configの再読み込みが完了しました。");
				return true;
			}
			if (args[0].equalsIgnoreCase("rules")) {
				if (!Config.getBoolean("Rules.Enable")) {
					Command.broadcastCommandMessage(sender, "Unknown command. Type \"/help\" for help.");
					return true;
				}
				if (!Permission.has(Permission.CUSTOMMESSAGE_COMMAND_RULES, sender)) {
					sender.sendMessage(ChatColor.RED + "パーミッションが無いため、実行できません。");
					return true;
				}
				for (String message : Config.getStringList("Rules.Message")) {
					message = message.replace("&", "§");
					sender.sendMessage(message);
				}
				return true;
			}
		}
		if (args.length == 4) {
			Player player;
			if (args[0].equalsIgnoreCase("title")) {
				if (!Permission.has(Permission.CUSTOMMESSAGE_COMMAND_TITLE, sender)) {
					sender.sendMessage(ChatColor.RED + "パーミッションが無いため、実行できません。");
					return true;
				}
				player = Utils.getOnlinePlayer(args[1]);
				if (player == null) {
					sender.sendMessage(ChatColor.RED + args[1] + " というプレイヤーは見つかりません。");
					return true;
				}
				String title = args[2].replace("%blank", " ");
				String subtitle = args[3].replace("%blank", " ");
				CustomMessage.getAPI().sendFullTitle(player, 10, 40, 10, title, subtitle);
				return true;
			}
			if (args[0].equalsIgnoreCase("tabtitle")) {
				if (!Permission.has(Permission.CUSTOMMESSAGE_COMMAND_TABTITLE, sender)) {
					sender.sendMessage(ChatColor.RED + "パーミッションが無いため、実行できません。");
					return true;
				}
				player = Utils.getOnlinePlayer(args[1]);
				if (player == null) {
					sender.sendMessage(ChatColor.RED + args[1] + " というプレイヤーは見つかりません。");
					return true;
				}
				String header = args[2].replace("%blank", " ");
				String footer = args[3].replace("%blank", " ");
				CustomMessage.getAPI().sendFullTabTitle(player, header, footer);
				return true;
			}
		}
		CommandTemplate.sendCommandTemplate(sender);
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 1) {
			String prefix = args[0].toLowerCase();
			ArrayList<String> commands = new ArrayList<String>();
			for (String c : new String[]{"reload", "rules", "title", "tabtitle"}) {
				if (c.startsWith(prefix)) {
					commands.add(c);
				}
			}
			return commands;
		}
		if (args.length == 2) {
			if (args[0].equalsIgnoreCase("title") || args[0].equalsIgnoreCase("tabtitle")) {
				String prefix = args[1].toLowerCase();
				ArrayList<String> commands = new ArrayList<String>();
				for (Player player : Utils.getOnlinePlayers()) {
					String name = player.getName();
					if (name.startsWith(prefix)) {
						commands.add(name);
					}
				}
				return commands;
			}
		}
		return null;
	}
}
