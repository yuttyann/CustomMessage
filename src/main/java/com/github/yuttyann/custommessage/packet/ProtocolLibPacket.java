package com.github.yuttyann.custommessage.packet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerOptions;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedServerPing;
import com.github.yuttyann.custommessage.Main;
import com.github.yuttyann.custommessage.TimeManager;
import com.github.yuttyann.custommessage.file.Files;
import com.github.yuttyann.custommessage.file.YamlConfig;
import com.github.yuttyann.custommessage.util.Utils;

public class ProtocolLibPacket {

	private static final ProtocolManager manager = ProtocolLibrary.getProtocolManager();

	public static void sendPlayerCountMessage() {
		final YamlConfig config = Files.getConfig();
		if (!config.getBoolean("PlayerCountMessage.Enable")) {
			return;
		}
		manager.addPacketListener(new PacketAdapter(Main.instance, ListenerPriority.HIGH, Arrays.asList(new PacketType[]{ PacketType.Status.Server.OUT_SERVER_INFO }), ListenerOptions.ASYNC) {
			@Override
			public void onPacketSending(PacketEvent event) {
				WrappedServerPing ping = event.getPacket().getServerPings().read(0);
				if (isNone()) {
					ping.setPlayers(null);
					return;
				}
				List<WrappedGameProfile> list = new ArrayList<WrappedGameProfile>();
				Integer playerlength = Utils.getOnlinePlayers().size();
				Integer maxplayer = Bukkit.getMaxPlayers();
				String servername = Bukkit.getServerName();
				for (String pcm : config.getStringList("PlayerCountMessage.Message")) {
					pcm = pcm.replace("%players", playerlength.toString());
					pcm = pcm.replace("%maxplayers", maxplayer.toString());
					pcm = pcm.replace("%servername", servername);
					pcm = pcm.replace("%version", Utils.getVersion());
					pcm = pcm.replace("%time", TimeManager.getTimesofDay());
					pcm = pcm.replace("&", "§");
					list.add(new WrappedGameProfile("1", pcm));
				}
				ping.setPlayers(list);
			}
		});
	}

	private static boolean isNone() {
		List<String> list = Files.getConfig().getStringList("PlayerCountMessage.Message");
		if(list.get(0).equals("none") && list.size() == 1) {
			return true;
		}
		return false;
	}
}
