package com.github.yuttyann.custommessage;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.yuttyann.custommessage.command.BanCommand;
import com.github.yuttyann.custommessage.command.CustomMessageCommand;
import com.github.yuttyann.custommessage.command.MeCommand;
import com.github.yuttyann.custommessage.command.RulesCommand;
import com.github.yuttyann.custommessage.command.SayCommand;
import com.github.yuttyann.custommessage.command.TellCommand;
import com.github.yuttyann.custommessage.command.TitleCommand;
import com.github.yuttyann.custommessage.file.Config;
import com.github.yuttyann.custommessage.listener.PlayerChatListener;
import com.github.yuttyann.custommessage.listener.PlayerDeathListener;
import com.github.yuttyann.custommessage.listener.PlayerJoinQuitListener;
import com.github.yuttyann.custommessage.listener.PlayerKickListener;
import com.github.yuttyann.custommessage.listener.PlayerTitleListener;
import com.github.yuttyann.custommessage.listener.ServerListener;
import com.github.yuttyann.custommessage.util.PlatformUtils;
import com.github.yuttyann.custommessage.util.Utils;
import com.github.yuttyann.custommessage.util.VersionUtils;

public class Main extends JavaPlugin {

	private PluginDescriptionFile yml = getDescription();
	private Logger logger = Logger.getLogger("Minecraft");
	private HashMap<String, CommandExecutor> commands = new HashMap<String, CommandExecutor>();

	@Override
	public void onEnable() {
		setUpFile();
		loadClass();
		loadCommand();
		if (!Utils.isPluginEnabled("ProtocolLib")) {
			logger.severe("ProtocolLibが導入されていないため、PlayerCountMessageを無効化しました。");
		}
		logger.info("[" + yml.getName() + "] v" + yml.getVersion() + " が有効になりました。");
	}

	@Override
	public void onDisable() {
		logger.info("[" + yml.getName() + "] v" + yml.getVersion() + " が無効になりました。");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		return commands.get(command.getName()).onCommand(sender, command, label, args);
	}

	private void setUpFile() {
		File servericon = new File(getDataFolder(), "ServerIcon");
		File downloads = new File(getDataFolder(), "Downloads");
		if (!servericon.exists()) {
			servericon.mkdir();
			saveResource("ServerIcon/icon1.png", false);
			saveResource("ServerIcon/icon2.png", false);
		}
		if (!downloads.exists()) {
			downloads.mkdir();
		}
		if ((PlatformUtils.isLinux()) || (PlatformUtils.isMac())) {
			new Config(this, "utf-8");
		} else if (PlatformUtils.isWindows()) {
			if(VersionUtils.isVersion("1.9")) {
				new Config(this, "utf-8");
			} else {
				new Config(this, "s-jis");
			}
		}
	}

	private void loadClass() {
		getServer().getPluginManager().registerEvents(new PlayerChatListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerDeathListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerJoinQuitListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerKickListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerTitleListener(this), this);
		getServer().getPluginManager().registerEvents(new ServerListener(this), this);
		getServer().getPluginManager().registerEvents(new Updater(this), this);
	}

	private void loadCommand() {
		commands.put("custommessage", new CustomMessageCommand(this));
		commands.put("rules", new RulesCommand(this));
		commands.put("ban", new BanCommand(this));
		commands.put("me", new MeCommand(this));
		commands.put("say", new SayCommand(this));
		commands.put("tell", new TellCommand(this));
		commands.put("title", new TitleCommand(this));
	}
}
