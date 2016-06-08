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
import com.github.yuttyann.custommessage.file.Config;
import com.github.yuttyann.custommessage.util.TimeUtils;
import com.github.yuttyann.custommessage.util.Utils;
import com.github.yuttyann.custommessage.util.VersionUtils;

public class ProtocolLibPacket {

	private Main plugin;

	private final ProtocolManager manager = ProtocolLibrary.getProtocolManager();

	public ProtocolLibPacket(Main plugin) {
		this.plugin = plugin;
	}

	public void sendPlayerCountMessage() {
		if (Config.getBoolean("PlayerCountMessage.Enable")) {
			manager.addPacketListener(new PacketAdapter(plugin, ListenerPriority.NORMAL, Arrays.asList(new PacketType[] { PacketType.Status.Server.OUT_SERVER_INFO }), new ListenerOptions[] { ListenerOptions.ASYNC }) {
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
					for (String pcm : Config.getStringList("PlayerCountMessage.Message")) {
						pcm = pcm.replace("%players", playerlength.toString());
						pcm = pcm.replace("%maxplayers", maxplayer.toString());
						pcm = pcm.replace("%servername", servername);
						pcm = pcm.replace("%version", VersionUtils.getVersion());
						pcm = pcm.replace("%time", TimeUtils.getTime());
						pcm = pcm.replace("&", "§");
						list.add(new WrappedGameProfile("1", pcm));
					}
					ping.setPlayers(list);
				}
			});
		}
	}

	private boolean isNone() {
		List<String> list = Config.getStringList("PlayerCountMessage.Message");
		if(list.get(0).equals("none") && list.size() == 1) {
			return true;
		}
		return false;
	}
}
