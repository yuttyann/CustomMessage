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
	boolean apimode;

	public CustomMessageCommand(Main plugin, Boolean apimode) {
		this.plugin = plugin;
		this.apimode = apimode;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (apimode) {
			sender.sendMessage("Unknown command. Type \"/help\" for help.");
			return true;
		}
		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("reload")) {
				if (!Permission.has(Permission.CUSTOMMESSAGE_COMMAND_RELOAD, sender)) {
					sender.sendMessage("§cパーミッションが無いため、実行できません。");
					return true;
				}
				Config.reload();
				CommandTemplate.clear();
				if (Config.getBoolean("Rules.Enable")) {
					CommandTemplate.clear();
					CommandTemplate.addCommand("/custommessage reload - Configの再読み込みをします。");
					CommandTemplate.addCommand("/custommessage rules - ルールを表示します。");
					CommandTemplate.addCommand("/custommessage title <player> <title> <subtitle> - 指定したプレイヤーにタイトルを表示します。");
					CommandTemplate.addCommand("/custommessage tabtitle <player> <header> <footer> - 指定したプレイヤーにタブタイトルを表示します。");
				} else {
					CommandTemplate.clear();
					CommandTemplate.addCommand("/custommessage reload - Configの再読み込みをします。");
					CommandTemplate.addCommand("/custommessage title <player> <title> <subtitle> - 指定したプレイヤーにタイトルを表示します。");
					CommandTemplate.addCommand("/custommessage tabtitle <player> <header> <footer> - 指定したプレイヤーにタブタイトルを表示します。");
				}
				sender.sendMessage(ChatColor.GREEN + "Configの再読み込みが完了しました。");
				return true;
			}
			if (args[0].equalsIgnoreCase("rules") && Config.getBoolean("Rules.Enable")) {
				if (!Permission.has(Permission.CUSTOMMESSAGE_COMMAND_RULES, sender)) {
					sender.sendMessage("§cパーミッションが無いため、実行できません。");
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
					sender.sendMessage("§cパーミッションが無いため、実行できません。");
					return true;
				}
				player = Utils.getOnlinePlayer(args[1]);
				if (player == null || !((Player) sender).canSee(player)) {
					sender.sendMessage("§c指定されたプレイヤーが見つかりません。");
					return true;
				}
				String title = args[2].replace("%blank", " ");
				String subtitle = args[3].replace("%blank", " ");
				CustomMessage.getAPI().sendFullTitle(player, 10, 40, 10, title, subtitle);
				return true;
			}
			if (args[0].equalsIgnoreCase("tabtitle")) {
				if (!Permission.has(Permission.CUSTOMMESSAGE_COMMAND_TABTITLE, sender)) {
					sender.sendMessage("§cパーミッションが無いため、実行できません。");
					return true;
				}
				player = Utils.getOnlinePlayer(args[1]);
				if (player == null || !((Player) sender).canSee(player)) {
					sender.sendMessage("§c指定されたプレイヤーが見つかりません。");
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
		return null;
	}
}
