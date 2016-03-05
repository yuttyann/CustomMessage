package com.github.yuttyann.custommessage;

import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.yuttyann.custommessage.api.CustomMessageAPI;
import com.github.yuttyann.custommessage.command.CustomMessageCommand;
import com.github.yuttyann.custommessage.command.MeCommand;
import com.github.yuttyann.custommessage.command.RulesCommand;
import com.github.yuttyann.custommessage.command.SayCommand;
import com.github.yuttyann.custommessage.handle.ClassHandler;
import com.github.yuttyann.custommessage.listener.PlayerChatListener;
import com.github.yuttyann.custommessage.listener.PlayerDeathListener;
import com.github.yuttyann.custommessage.listener.PlayerJoinQuitListener;
import com.github.yuttyann.custommessage.listener.PlayerKickListener;
import com.github.yuttyann.custommessage.listener.PlayerTitleListener;
import com.github.yuttyann.custommessage.listener.ServerListener;
import com.github.yuttyann.custommessage.packet.versions.v1_7_R4;
import com.github.yuttyann.custommessage.packet.versions.v1_8_R1;
import com.github.yuttyann.custommessage.packet.versions.v1_8_R2;
import com.github.yuttyann.custommessage.packet.versions.v1_8_R3;
import com.github.yuttyann.custommessage.packet.versions.v1_9_R1;

public class Main extends JavaPlugin {

	public Boolean protocollib = null;
	public Logger logger = Logger.getLogger("Minecraft");
	private HashMap<String, CommandExecutor> commands = new HashMap<String, CommandExecutor>();;

	@Override
	public void onEnable() {
		saveDefaultConfig();
		loadHandler();
		loadProtocolLib();
		loadClass();
		loadCommands();
		loadTitle();
		loadAPI();
		PluginDescriptionFile yml = getDescription();
		logger.info("[" + yml.getName() + "] v" + yml.getVersion() + " が有効になりました");
	}

	@Override
	public void onDisable() {
		PluginDescriptionFile yml = getDescription();
		logger.info("[" + yml.getName() + "] v" + yml.getVersion() + " が無効になりました");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		return commands.get(command.getName()).onCommand(sender, command, label, args);
	}

	private void loadHandler() {
		if (getConfig().getBoolean("CustomMessageAPI")) {
			return;
		}
		new ClassHandler(this);
	}

	private void loadClass() {
		if (getConfig().getBoolean("CustomMessageAPI")) {
			return;
		}
		getServer().getPluginManager().registerEvents(new PlayerChatListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerDeathListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerJoinQuitListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerKickListener(this), this);
		getServer().getPluginManager().registerEvents(new ServerListener(this), this);
		getServer().getPluginManager().registerEvents(new Updater(this), this);
	}

	private void loadProtocolLib() {
		if (getConfig().getBoolean("CustomMessageAPI")) {
			return;
		}
		if (!getServer().getPluginManager().isPluginEnabled("ProtocolLib")) {
			logger.severe("ProtocolLibが導入されていないのでPlayerCountMessageは機能しません！");
			protocollib = false;
		} else {
			protocollib = true;
		}
	}

	private void loadCommands() {
		commands.put("rules", new RulesCommand(this));
		commands.put("custommessage", new CustomMessageCommand(this));
		commands.put("say", new SayCommand(this));
		commands.put("me", new MeCommand(this));
	}

	private void loadAPI() {
		if (!getConfig().getBoolean("CustomMessageAPI")) {
			return;
		}
		Server server = Bukkit.getServer();
		String packageName = server.getClass().getPackage().getName();
		packageName = packageName.substring(packageName.lastIndexOf('.') + 1);
		if (packageName.equalsIgnoreCase("v1_7_R4")) {
			if(getConfig().getBoolean("ProtocolHack")) {
				new v1_7_R4(this);
			}
			new CustomMessageAPI(this);
		} else if (packageName.equalsIgnoreCase("v1_8_R1")) {
			new v1_8_R1(this);
			new CustomMessageAPI(this);
		} else if (packageName.equalsIgnoreCase("v1_8_R2")) {
			new v1_8_R2(this);
			new CustomMessageAPI(this);
		} else if (packageName.equalsIgnoreCase("v1_8_R3")) {
			new v1_8_R3(this);
			new CustomMessageAPI(this);
		} else if (packageName.equalsIgnoreCase("v1_9_R1")) {
			new v1_9_R1(this);
			new CustomMessageAPI(this);
		} else {
			new CustomMessageAPI(this);
		}
	}

	private void loadTitle() {
		if (getConfig().getBoolean("CustomMessageAPI")) {
			return;
		}
		Server server = Bukkit.getServer();
		String packageName = server.getClass().getPackage().getName();
		packageName = packageName.substring(packageName.lastIndexOf('.') + 1);
		if (packageName.equalsIgnoreCase("v1_7_R4")) {
			if(getConfig().getBoolean("ProtocolHack")) {
				new v1_7_R4(this);
				getServer().getPluginManager().registerEvents(new PlayerTitleListener(this), this);
			}
		} else if (packageName.equalsIgnoreCase("v1_8_R1")) {
			new v1_8_R1(this);
			getServer().getPluginManager().registerEvents(new PlayerTitleListener(this), this);
		} else if (packageName.equalsIgnoreCase("v1_8_R2")) {
			new v1_8_R2(this);
			getServer().getPluginManager().registerEvents(new PlayerTitleListener(this), this);
		} else if (packageName.equalsIgnoreCase("v1_8_R3")) {
			new v1_8_R3(this);
			getServer().getPluginManager().registerEvents(new PlayerTitleListener(this), this);
		} else if (packageName.equalsIgnoreCase("v1_9_R1")) {
			new v1_9_R1(this);
			getServer().getPluginManager().registerEvents(new PlayerTitleListener(this), this);
		} else {
			return;
		}
	}
}
