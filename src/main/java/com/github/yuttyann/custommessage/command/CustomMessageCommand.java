package com.github.yuttyann.custommessage.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import com.github.yuttyann.custommessage.Main;
import com.github.yuttyann.custommessage.handle.ClassHandler;

public class CustomMessageCommand implements CommandExecutor {

	Main plugin;

	public CustomMessageCommand(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (plugin.getConfig().getBoolean("CustomMessageAPI")) {
			sender.sendMessage("Unknown command. Type \"/help\" for help.");
			return true;
		}
		if (args.length == 0) {
			sender.sendMessage(ChatColor.GREEN + "コマンド: /custommessage reload");
			sender.sendMessage(ChatColor.GREEN + "コマンド: /custommessage config");
			return true;
		}
		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("reload")) {
				if (!sender.hasPermission("custommessage.reload") && !sender.isOp()) {
					sender.sendMessage(ChatColor.RED + "権限がありません");
					return true;
				}
				plugin.reloadConfig();
				sender.sendMessage(ChatColor.GREEN + "Configのリロードが完了しました");
				return true;
			}
			if (args[0].equalsIgnoreCase("config")) {
				if (!sender.hasPermission("custommessage.config") && !sender.isOp()) {
					sender.sendMessage(ChatColor.RED + "権限がありません");
					return true;
				}
				ConfigSettings(sender);
				return true;
			}
			return true;
		}
		return false;
	}

	private FileConfiguration getConfig() {
		return ClassHandler.getMainClass().getConfig();
	}

	private void ConfigSettings(CommandSender sender) {
		boolean protocolhackenable = getConfig().getBoolean("ProtocolHack");
		boolean apienable = getConfig().getBoolean("CustomMessageAPI");
		boolean playercountenable = getConfig().getBoolean("PlayerCountMessage.Enable");
		boolean fakeenable = getConfig().getBoolean("FakeMaxPlayer.Enable");
		boolean playerkillenable = getConfig().getBoolean("PlayerKillMessage.Enable");
		boolean playerdeathenable = getConfig().getBoolean("PlayerDeathMessage.Enable");
		boolean joinenable = getConfig().getBoolean("PlayerJoinQuitMessage.Enable");
		boolean chatenable = getConfig().getBoolean("ChatMessageFormat.Enable");
		boolean kickenable = getConfig().getBoolean("PlayerKickMessage.Enable");
		boolean rulesenable = getConfig().getBoolean("Rules.Enable");
		boolean titleenable = getConfig().getBoolean("Title.Enable");
		boolean tabtitleenable = getConfig().getBoolean("TabTitle.Enable");
		boolean motdenable = getConfig().getBoolean("Rules.Enable");

		int fakeplayernumber = getConfig().getInt("FakeMaxPlayer.MaxPlayer");

		String time = getConfig().getString("Time");
		String playerkill = getConfig().getString("PlayerKillMessage.Message");
		String playerdeath = getConfig().getString("PlayerDeathMessage.Message");
		String firstjoin = getConfig().getString("PlayerJoinQuitMessage.FirstJoinMssage");
		String join = getConfig().getString("PlayerJoinQuitMessage.JoinMessage");
		String quit = getConfig().getString("PlayerJoinQuitMessage.QuitMessage");
		String chat = getConfig().getString("ChatMessageFormat.Message");
		String kick = getConfig().getString("PlayerKickMessage.Message");
		String title = getConfig().getString("Title.TitleMessage");
		String subtitle = getConfig().getString("Title.SubTitleMessage");
		String tabtitle1 = getConfig().getString("TabTitle.Header");
		String tabtitle2 = getConfig().getString("TabTitle.Footer");
		String motd1 = getConfig().getString("Motd.1line");
		String motd2 = getConfig().getString("Motd.2line");

		sender.sendMessage(ChatColor.AQUA + "---------- ConfigSettings ----------");
		sender.sendMessage(ChatColor.GREEN + "ProtocolHack_Enable: " + ChatColor.GOLD + protocolhackenable);
		sender.sendMessage(ChatColor.GREEN + "CustomMessageAPI_Enable: " + ChatColor.GOLD + apienable);
		sender.sendMessage(ChatColor.GREEN + "PlayerCountMessage_Enable: " + ChatColor.GOLD + playercountenable);
		sender.sendMessage(ChatColor.GREEN + "FakeMaxPlayer_Enable: " + ChatColor.GOLD + fakeenable);
		sender.sendMessage(ChatColor.GREEN + "PlayerKillMessage_Enable: " + ChatColor.GOLD + playerkillenable);
		sender.sendMessage(ChatColor.GREEN + "PlayerDeathMessage_Enable: " + ChatColor.GOLD + playerdeathenable);
		sender.sendMessage(ChatColor.GREEN + "PlayerJoinQuitMessage_Enable: " + ChatColor.GOLD + joinenable);
		sender.sendMessage(ChatColor.GREEN + "ChatMessageFormat_Enable: " + ChatColor.GOLD + chatenable);
		sender.sendMessage(ChatColor.GREEN + "Rules_Enable: " + ChatColor.GOLD + rulesenable);
		sender.sendMessage(ChatColor.GREEN + "Title_Enable: " + ChatColor.GOLD + titleenable);
		sender.sendMessage(ChatColor.GREEN + "TabTitle_Enable: " + ChatColor.GOLD + tabtitleenable);
		sender.sendMessage(ChatColor.GREEN + "PlayerKickMessage_Enable: " + ChatColor.GOLD + kickenable);
		sender.sendMessage(ChatColor.GREEN + "Motd_Enable: " + ChatColor.GOLD + motdenable);
		sender.sendMessage(ChatColor.GREEN + "FakeMaxPlayer_Enable: " + ChatColor.BLUE + fakeplayernumber);

		sender.sendMessage(ChatColor.GREEN + "Time: " + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', time));
		sender.sendMessage(ChatColor.GREEN + "KillMessage: " + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', playerkill));
		sender.sendMessage(ChatColor.GREEN + "DeathMessage: " + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', playerdeath));
		sender.sendMessage(ChatColor.GREEN + "FirstJoinMssage: " + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', firstjoin));
		sender.sendMessage(ChatColor.GREEN + "JoinMssage: " + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', join));
		sender.sendMessage(ChatColor.GREEN + "QuitMssage: " + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', quit));
		sender.sendMessage(ChatColor.GREEN + "ChatMessage: " + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', chat));
		sender.sendMessage(ChatColor.GREEN + "KickMessage: " + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', kick));
		sender.sendMessage(ChatColor.GREEN + "TitleMessage: " + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', title));
		sender.sendMessage(ChatColor.GREEN + "SubTitleMessage: " + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', subtitle));
		sender.sendMessage(ChatColor.GREEN + "TabTitleHeader: " + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', tabtitle1));
		sender.sendMessage(ChatColor.GREEN + "TabTitleFooter: " + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', tabtitle2));
		sender.sendMessage(ChatColor.GREEN + "Motd1Line: " + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', motd1));
		sender.sendMessage(ChatColor.GREEN + "Motd2Line: " + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', motd2));
	}
}
