package com.github.yuttyann.custommessage.listener;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.yuttyann.custommessage.Main;
import com.github.yuttyann.custommessage.config.CustomMessageConfig;
import com.github.yuttyann.custommessage.packet.versions.v1_7_R4;
import com.github.yuttyann.custommessage.packet.versions.v1_8_R1;
import com.github.yuttyann.custommessage.packet.versions.v1_8_R2;
import com.github.yuttyann.custommessage.packet.versions.v1_8_R3;
import com.github.yuttyann.custommessage.packet.versions.v1_9_R1;
import com.github.yuttyann.custommessage.packet.versions.v1_9_R2;

public class PlayerTitleListener implements Listener {

	Main plugin;

	private HashMap<String, BukkitRunnable> timers;

	public PlayerTitleListener(Main plugin) {
		this.plugin = plugin;
		timers = new HashMap<String, BukkitRunnable>();
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		playerTitle(player);
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if(timers.containsKey(player.getName())) {
			tabTitleTimerStop(player);
		}
	}

	private void playerTitle(Player player) {
		if (CustomMessageConfig.getBoolean("Title.Enable")) {
			int FadeIn = CustomMessageConfig.getInt("TitleTime.FadeIn");
			int Stay = CustomMessageConfig.getInt("TitleTime.Stay");
			int FadeOut = CustomMessageConfig.getInt("TitleTime.FadeOut");
			String TitleMessage = CustomMessageConfig.getString("Title.TitleMessage");
			String SubTitleMessage = CustomMessageConfig.getString("Title.SubTitleMessage");
			sendTitle(player, FadeIn, Stay, FadeOut, TitleMessage, SubTitleMessage);
		}
		if (CustomMessageConfig.getBoolean("TabTitle.Enable")) {
			String Header = CustomMessageConfig.getString("TabTitle.Header");
			String Footer = CustomMessageConfig.getString("TabTitle.Footer");
			if(Header.contains("%time") || Footer.contains("%time")) {
				tabTitleTimerStart(player, Header, Footer);
			} else {
				sendTabTitle(player, Header, Footer);
			}
		}
	}

	private void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle) {
		Server server = Bukkit.getServer();
		String packageName = server.getClass().getPackage().getName();
		packageName = packageName.substring(packageName.lastIndexOf('.') + 1);
		if (packageName.equalsIgnoreCase("v1_7_R4")) {
			v1_7_R4.sendTitle(player, fadeIn, stay, fadeOut, title, subtitle);
		} else if (packageName.equalsIgnoreCase("v1_8_R1")) {
			v1_8_R1.sendTitle(player, fadeIn, stay, fadeOut, title, subtitle);
		} else if (packageName.equalsIgnoreCase("v1_8_R2")) {
			v1_8_R2.sendTitle(player, fadeIn, stay, fadeOut, title, subtitle);
		} else if (packageName.equalsIgnoreCase("v1_8_R3")) {
			v1_8_R3.sendTitle(player, fadeIn, stay, fadeOut, title, subtitle);
		} else if (packageName.equalsIgnoreCase("v1_9_R1")) {
			v1_9_R1.sendTitle(player, fadeIn, stay, fadeOut, title, subtitle);
		} else if (packageName.equalsIgnoreCase("v1_9_R2")) {
			v1_9_R2.sendTitle(player, fadeIn, stay, fadeOut, title, subtitle);
		} else {
			return;
		}
	}

	private void sendTabTitle(Player player, String header, String footer) {
		Server server = Bukkit.getServer();
		String packageName = server.getClass().getPackage().getName();
		packageName = packageName.substring(packageName.lastIndexOf('.') + 1);
		if (packageName.equalsIgnoreCase("v1_7_R4")) {
			v1_7_R4.sendTabTitle(player, header, footer);
		} else if (packageName.equalsIgnoreCase("v1_8_R1")) {
			v1_8_R1.sendTabTitle(player, header, footer);
		} else if (packageName.equalsIgnoreCase("v1_8_R2")) {
			v1_8_R2.sendTabTitle(player, header, footer);
		} else if (packageName.equalsIgnoreCase("v1_8_R3")) {
			v1_8_R3.sendTabTitle(player, header, footer);
		} else if (packageName.equalsIgnoreCase("v1_9_R1")) {
			v1_9_R1.sendTabTitle(player, header, footer);
		} else if (packageName.equalsIgnoreCase("v1_9_R2")) {
			v1_9_R2.sendTabTitle(player, header, footer);
		} else {
			return;
		}
	}

	private void tabTitleTimerStart(final Player player, final String Header, final String Footer) {
		BukkitRunnable timer = new BukkitRunnable() {
			@Override
			public void run() {
				sendTabTitle(player, Header, Footer);
			}
		};
		timer.runTaskTimer(plugin, 0, 20);
		timers.put(player.getName(), timer);
	}

	private void tabTitleTimerStop(Player player) {
		timers.get(player.getName()).cancel();
		timers.remove(player.getName());
	}
}
