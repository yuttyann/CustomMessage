package com.github.yuttyann.custommessage.listener;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.yuttyann.custommessage.Main;
import com.github.yuttyann.custommessage.api.CustomMessageAPI;
import com.github.yuttyann.custommessage.config.CustomMessageConfig;

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
			CustomMessageAPI.sendTitle(player, FadeIn, Stay, FadeOut, TitleMessage, SubTitleMessage);
		}
		if (CustomMessageConfig.getBoolean("TabTitle.Enable")) {
			String Header = CustomMessageConfig.getString("TabTitle.Header");
			String Footer = CustomMessageConfig.getString("TabTitle.Footer");
			if(Header.contains("%time") || Footer.contains("%time")) {
				tabTitleTimerStart(player, Header, Footer);
			} else {
				CustomMessageAPI.sendTabTitle(player, Header, Footer);
			}
		}
	}

	private void tabTitleTimerStart(final Player player, final String Header, final String Footer) {
		BukkitRunnable timer = new BukkitRunnable() {
			@Override
			public void run() {
				CustomMessageAPI.sendTabTitle(player, Header, Footer);
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
