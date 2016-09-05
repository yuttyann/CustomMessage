package com.github.yuttyann.custommessage;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.yuttyann.custommessage.command.BanCommand;
import com.github.yuttyann.custommessage.command.CommandTemplate;
import com.github.yuttyann.custommessage.command.CustomMessageCommand;
import com.github.yuttyann.custommessage.command.MeCommand;
import com.github.yuttyann.custommessage.command.SayCommand;
import com.github.yuttyann.custommessage.command.TellCommand;
import com.github.yuttyann.custommessage.file.Config;
import com.github.yuttyann.custommessage.listener.CommandListener;
import com.github.yuttyann.custommessage.listener.PlayerChatListener;
import com.github.yuttyann.custommessage.listener.PlayerDeathListener;
import com.github.yuttyann.custommessage.listener.PlayerJoinQuitListener;
import com.github.yuttyann.custommessage.listener.PlayerKickListener;
import com.github.yuttyann.custommessage.listener.PlayerTitleListener;
import com.github.yuttyann.custommessage.listener.ServerListener;
import com.github.yuttyann.custommessage.util.Utils;

public class Main extends JavaPlugin {

	public static Main instance;
	private Boolean apimode;
	private Logger logger;
	private PluginDescriptionFile pluginyml;
	private HashMap<String, TabExecutor> commands;

	@Override
	public void onEnable() {
		instance = this;
		apimode = false;
		logger = Logger.getLogger("Minecraft");
		setUpFile();
		if (Config.getBoolean("Disable_All_Functions")) {
			logger.info("APIモードで起動します。");
			apimode = true;
			getServer().getPluginManager().registerEvents(new Updater(this), this);
		} else {
			loadClass();
		}
		loadCommand();
		if (!Utils.isPluginEnabled("ProtocolLib") && !apimode) {
			logger.severe("ProtocolLibが導入されていないため、PlayerCountMessageを無効化しました。");
		}
		pluginyml = getDescription();
		logger.info("[" + pluginyml.getName() + "] v" + pluginyml.getVersion() + " が有効になりました。");
	}

	@Override
	public void onDisable() {
		logger.info("[" + pluginyml.getName() + "] v" + pluginyml.getVersion() + " が無効になりました。");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		return commands.get(command.getName()).onCommand(sender, command, label, args);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		return commands.get(command.getName()).onTabComplete(sender, command, label, args);
	}

	private void setUpFile() {
		File servericon = new File(getDataFolder(), "ServerIcon");
		if (!servericon.exists()) {
			servericon.mkdir();
			saveResource("ServerIcon/icon1.png", false);
			saveResource("ServerIcon/icon2.png", false);
		}
		if (Utils.isWindows() && !Utils.isUpperVersion("1.9")) {
			new Config(this, "s-jis");
		} else {
			new Config(this, "utf-8");
		}
	}

	private void loadClass() {
		getServer().getPluginManager().registerEvents(new PlayerChatListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerDeathListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerJoinQuitListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerKickListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerTitleListener(this), this);
		getServer().getPluginManager().registerEvents(new CommandListener(this), this);
		getServer().getPluginManager().registerEvents(new ServerListener(this), this);
		getServer().getPluginManager().registerEvents(new Updater(this), this);
	}

	private void loadCommand() {
		setCommandTemplate();
		commands = new HashMap<String, TabExecutor>();
		commands.put("custommessage", new CustomMessageCommand(this, apimode));
		commands.put("ban", new BanCommand(this));
		commands.put("me", new MeCommand(this, apimode));
		commands.put("say", new SayCommand(this, apimode));
		commands.put("tell", new TellCommand(this, apimode));
	}

	private void setCommandTemplate() {
		new CommandTemplate(this);
		CommandTemplate.addCommand("/custommessage reload - Configの再読み込みをします。");
		if (Config.getBoolean("Rules.Enable")) {
			CommandTemplate.addCommand("/custommessage rules - ルールを表示します。");
		}
		CommandTemplate.addCommand("/custommessage title <player> <title> <subtitle> - 指定したプレイヤーにタイトルを表示します。");
		CommandTemplate.addCommand("/custommessage tabtitle <player> <header> <footer> - 指定したプレイヤーにタブタイトルを表示します。");
	}
}
