package com.github.yuttyann.custommessage;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.yuttyann.custommessage.command.BanCommand;
import com.github.yuttyann.custommessage.command.CustomMessageCommand;
import com.github.yuttyann.custommessage.command.MeCommand;
import com.github.yuttyann.custommessage.command.SayCommand;
import com.github.yuttyann.custommessage.command.TellCommand;
import com.github.yuttyann.custommessage.command.help.CommandView;
import com.github.yuttyann.custommessage.file.Files;
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
	private boolean apimode;
	private Logger logger;
	private HashMap<String, TabExecutor> commands;
	private HashMap<String, List<CommandView>> commandhelp;

	@Override
	public void onEnable() {
		instance = this;
		apimode = false;
		logger = Logger.getLogger("Minecraft");
		setupFile();
		if (Files.getConfig().getBoolean("Disable_All_Functions")) {
			logger.info("APIモードで起動します。");
			apimode = true;
		}
		loadClass(!apimode);
		loadCommand();
		if (!Utils.isPluginEnabled("ProtocolLib") && !apimode) {
			logger.severe("ProtocolLibが導入されていないため、PlayerCountMessageを無効化しました。");
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		return commands.get(command.getName()).onCommand(sender, command, label, args);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		return commands.get(command.getName()).onTabComplete(sender, command, label, args);
	}

	public HashMap<String, List<CommandView>> getCommandHelp() {
		return commandhelp;
	}

	public File getJarFile() {
		return getFile();
	}

	private void setupFile() {
		File servericon = new File(getDataFolder(), "ServerIcon");
		if (!servericon.exists()) {
			servericon.mkdir();
			saveResource("ServerIcon/icon1.png", false);
			saveResource("ServerIcon/icon2.png", false);
		}
		Files.reload();
	}

	private void loadClass(boolean load) {
		PluginManager manager = getServer().getPluginManager();
		if (load) {
			manager.registerEvents(new PlayerChatListener(), this);
			manager.registerEvents(new PlayerDeathListener(), this);
			manager.registerEvents(new PlayerJoinQuitListener(), this);
			manager.registerEvents(new PlayerKickListener(), this);
			manager.registerEvents(new PlayerTitleListener(), this);
			manager.registerEvents(new CommandListener(), this);
			manager.registerEvents(new ServerListener(), this);
		}
		manager.registerEvents(new Updater(this), this);
	}

	private void loadCommand() {
		commandhelp = new HashMap<String, List<CommandView>>();
		commands = new HashMap<String, TabExecutor>();
		commands.put("custommessage", new CustomMessageCommand(apimode));
		commands.put("ban", new BanCommand(this));
		commands.put("me", new MeCommand(this, apimode));
		commands.put("say", new SayCommand(this, apimode));
		commands.put("tell", new TellCommand(this, apimode));
	}
}
