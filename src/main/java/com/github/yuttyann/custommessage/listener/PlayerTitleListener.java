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
import com.github.yuttyann.custommessage.api.CustomMessage;
import com.github.yuttyann.custommessage.file.Config;

public class PlayerTitleListener implements Listener {

	Main plugin;

	HashMap<String, BukkitRunnable> timers;

	public PlayerTitleListener(Main plugin) {
		this.plugin = plugin;
		this.timers = new HashMap<String, BukkitRunnable>();
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
		if (Config.getBoolean("Title.Enable")) {
			int fadein = Config.getInt("TitleTime.FadeIn");
			int stay = Config.getInt("TitleTime.Stay");
			int fadeout = Config.getInt("TitleTime.FadeOut");
			String titlemessage = Config.getString("Title.TitleMessage");
			String subtitlemessage = Config.getString("Title.SubTitleMessage");
			CustomMessage.getAPI().sendFullTitle(player, fadein, stay, fadeout, titlemessage, subtitlemessage);
		}
		if (Config.getBoolean("TabTitle.Enable")) {
			String header = Config.getString("TabTitle.Header");
			String footer = Config.getString("TabTitle.Footer");
			if(header.contains("%time") || footer.contains("%time")) {
				tabTitleTimerStart(player, header, footer);
			} else {
				CustomMessage.getAPI().sendFullTabTitle(player, header, footer);
			}
		}
	}

	private void tabTitleTimerStart(final Player player, final String Header, final String Footer) {
		BukkitRunnable timer = new BukkitRunnable() {
			@Override
			public void run() {
				if (player != null) {
					CustomMessage.getAPI().sendFullTabTitle(player, Header, Footer);
				}
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
