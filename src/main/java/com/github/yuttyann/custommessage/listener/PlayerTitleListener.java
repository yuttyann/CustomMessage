package com.github.yuttyann.custommessage.listener;

import java.util.HashMap;
import java.util.UUID;

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

	HashMap<UUID, BukkitRunnable> timers;

	public PlayerTitleListener(Main plugin) {
		this.plugin = plugin;
		timers = new HashMap<UUID, BukkitRunnable>();
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		sendTitle(player);
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if(timers.containsKey(player.getUniqueId())) {
			tabTitleTimerReset(player);
		}
	}

	private void sendTitle(Player player) {
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

	private void tabTitleTimerStart(final Player player, final String header, final String footer) {
		BukkitRunnable timer = new BukkitRunnable() {
			@Override
			public void run() {
				if (player.isOnline()) {
					CustomMessage.getAPI().sendFullTabTitle(player, header, footer);
				}
			}
		};
		timer.runTaskTimer(plugin, 0, 20);
		timers.put(player.getUniqueId(), timer);
	}

	private void tabTitleTimerReset(Player player) {
		timers.get(player.getUniqueId()).cancel();
		timers.remove(player.getUniqueId());
	}
}
