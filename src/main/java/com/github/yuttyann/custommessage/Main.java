package com.github.yuttyann.custommessage;

import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.yuttyann.custommessage.api.CustomMessageAPI;
import com.github.yuttyann.custommessage.command.BanCommand;
import com.github.yuttyann.custommessage.command.CustomMessageCommand;
import com.github.yuttyann.custommessage.command.MeCommand;
import com.github.yuttyann.custommessage.command.RulesCommand;
import com.github.yuttyann.custommessage.command.SayCommand;
import com.github.yuttyann.custommessage.command.TellCommand;
import com.github.yuttyann.custommessage.config.CustomMessageConfig;
import com.github.yuttyann.custommessage.listener.PlayerChatListener;
import com.github.yuttyann.custommessage.listener.PlayerDeathListener;
import com.github.yuttyann.custommessage.listener.PlayerJoinQuitListener;
import com.github.yuttyann.custommessage.listener.PlayerKickListener;
import com.github.yuttyann.custommessage.listener.PlayerTitleListener;
import com.github.yuttyann.custommessage.listener.ServerListener;

public class Main extends JavaPlugin {

	public Boolean protocollib = null;
	private Logger logger = Logger.getLogger("Minecraft");
	private HashMap<String, CommandExecutor> commands = new HashMap<String, CommandExecutor>();

	@Override
	public void onEnable() {
		setUpConfig();
		loadProtocolLib();
		loadClass();
		loadCommand();
		PluginDescriptionFile yml = getDescription();
		logger.info("[" + yml.getName() + "] v" + yml.getVersion() + " が有効になりました。");
	}

	@Override
	public void onDisable() {
		PluginDescriptionFile yml = getDescription();
		logger.info("[" + yml.getName() + "] v" + yml.getVersion() + " が無効になりました。");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		return commands.get(command.getName()).onCommand(sender, command, label, args);
	}

	private void setUpConfig() {
		if ((PlatformUtils.isLinux()) || (PlatformUtils.isMac())) {
			new CustomMessageConfig(this, "utf-8");
		} else if (PlatformUtils.isWindows()) {
			if (Version.isVersion("1.9")) {
				new CustomMessageConfig(this, "utf-8");
			} else {
				new CustomMessageConfig(this, "s-jis");
			}
		}
	}

	private void loadProtocolLib() {
		if (getServer().getPluginManager().isPluginEnabled("ProtocolLib")) {
			protocollib = true;
		} else {
			logger.severe("ProtocolLibが導入されていないため、PlayerCountMessageを無効化しました。");
			protocollib = false;
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
		new CustomMessageAPI(this);
	}

	private void loadCommand() {
		commands.put("custommessage", new CustomMessageCommand(this));
		commands.put("rules", new RulesCommand(this));
		commands.put("ban", new BanCommand(this));
		commands.put("me", new MeCommand(this));
		commands.put("say", new SayCommand(this));
		commands.put("tell", new TellCommand(this));
	}
}
