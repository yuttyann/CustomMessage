package com.github.yuttyann.custommessage.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import com.github.yuttyann.custommessage.Permission;
import com.github.yuttyann.custommessage.api.CustomMessage;
import com.github.yuttyann.custommessage.command.help.CommandHelp;
import com.github.yuttyann.custommessage.file.Files;
import com.github.yuttyann.custommessage.file.Yaml;
import com.github.yuttyann.custommessage.util.Utils;

public class CustomMessageCommand implements TabExecutor {

	boolean apimode;

	public CustomMessageCommand(boolean apimode) {
		this.apimode = apimode;
		CommandHelp help = new CommandHelp();
			help.put("custommessage",
			help.getView().setAll("reload - コンフィグの再読み込みを行います。", Permission.CUSTOMMESSAGE_COMMAND_RELOAD, true),
			help.getView().setAll("rules - ルールを表示します。", Permission.CUSTOMMESSAGE_COMMAND_RULES, true),
			help.getView().setAll("title <player> <title> <subtitle> - 指定したプレイヤーにタイトルを表示します。", Permission.CUSTOMMESSAGE_COMMAND_TITLE, true),
			help.getView().setAll("tabtitle <player> <header> <footer> - 指定したプレイヤーにタブタイトルを表示します。", Permission.CUSTOMMESSAGE_COMMAND_TABTITLE, true)
		);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (apimode) {
			sender.sendMessage("Unknown command. Type \"/help\" for help.");
			return true;
		}
		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("reload")) {
				if (!Permission.has(Permission.CUSTOMMESSAGE_COMMAND_RELOAD, sender)) {
					Utils.sendPluginMessage(sender, "§cパーミッションが無いため、実行できません。");
					return true;
				}
				Files.reload();
				Utils.sendPluginMessage(sender, "§a全てのファイルの再読み込みが完了しました。");
				return true;
			}
			Yaml config = Files.getConfig();
			if (args[0].equalsIgnoreCase("rules") && config.getBoolean("Rules.Enable")) {
				if (!Permission.has(Permission.CUSTOMMESSAGE_COMMAND_RULES, sender)) {
					Utils.sendPluginMessage(sender, "§cパーミッションが無いため、実行できません。");
					return true;
				}
				for (String message : config.getStringList("Rules.Message")) {
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
					Utils.sendPluginMessage(sender, "§cパーミッションが無いため、実行できません。");
					return true;
				}
				player = Utils.getOnlinePlayer(args[1]);
				if (player == null || !((Player) sender).canSee(player)) {
					Utils.sendPluginMessage(sender, "§c指定されたプレイヤーが見つかりません。");
					return true;
				}
				String title = args[2].replace("%blank", " ");
				String subtitle = args[3].replace("%blank", " ");
				CustomMessage.getAPI().sendFullTitle(player, 10, 40, 10, title, subtitle);
				return true;
			}
			if (args[0].equalsIgnoreCase("tabtitle")) {
				if (!Permission.has(Permission.CUSTOMMESSAGE_COMMAND_TABTITLE, sender)) {
					Utils.sendPluginMessage(sender, "§cパーミッションが無いため、実行できません。");
					return true;
				}
				player = Utils.getOnlinePlayer(args[1]);
				if (player == null || !((Player) sender).canSee(player)) {
					Utils.sendPluginMessage(sender, "§c指定されたプレイヤーが見つかりません。");
					return true;
				}
				String header = args[2].replace("%blank", " ");
				String footer = args[3].replace("%blank", " ");
				CustomMessage.getAPI().sendFullTabTitle(player, header, footer);
				return true;
			}
		}
		label = label.equalsIgnoreCase("custommessage") ? label : "custommessage";
		CommandHelp.sendHelpMessage(sender, command);;
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
