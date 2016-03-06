package com.github.yuttyann.custommessage.packet;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerOptions;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedServerPing;
import com.github.yuttyann.custommessage.TimeManager;
import com.github.yuttyann.custommessage.handle.ClassHandler;

public class ProtocolLibPacket {

	private static final ProtocolManager manager = ProtocolLibrary.getProtocolManager();

	public void sendPlayerCountMessage() {
		if (ClassHandler.getMainClass().getConfig().getBoolean("PlayerCountMessage.Enable")) {
			manager.addPacketListener(new PacketAdapter(ClassHandler.getMainClass(), ListenerPriority.NORMAL, Arrays.asList(new PacketType[] { PacketType.Status.Server.OUT_SERVER_INFO }), new ListenerOptions[] { ListenerOptions.ASYNC }) {
				@Override
				public void onPacketSending(PacketEvent event) {
					WrappedServerPing ping = event.getPacket().getServerPings().read(0);
					if(ClassHandler.getMainClass().getConfig().getStringList("PlayerCountMessage.Message").contains("null")) {
						ping.setPlayers(null);
						return;
					}
					List<WrappedGameProfile> list = new ArrayList<WrappedGameProfile>();
					int playerlength = getOnlinePlayers().size();
					int maxplayer = Bukkit.getMaxPlayers();
					String name = Bukkit.getServerName();
					String ver = Bukkit.getServer().getVersion();
					ver = ver.split("\\(")[1];
					ver = ver.substring(4, ver.length() - 1);
					for (String pcm : ClassHandler.getMainClass().getConfig().getStringList("PlayerCountMessage.Message")) {
						pcm = pcm.replace("%players", String.valueOf(playerlength));
						pcm = pcm.replace("%maxplayers", String.valueOf(maxplayer));
						pcm = pcm.replace("%servername", name);
						pcm = pcm.replace("%version", ver);
						pcm = pcm.replace("%time", TimeManager.getTime());
						pcm = pcm.replace("&", "§");
						list.add(new WrappedGameProfile("1", pcm));
					}
					ping.setPlayers(list);
				}
			});
		}
	}

	@SuppressWarnings("unchecked")
	private ArrayList<Player> getOnlinePlayers() {
		try {
			if (Bukkit.class.getMethod("getOnlinePlayers", new Class<?>[0]).getReturnType() == Collection.class) {
				Collection<?> temp = ((Collection<?>) Bukkit.class.getMethod("getOnlinePlayers", new Class<?>[0]).invoke(null, new Object[0]));
				return new ArrayList<Player>((Collection<? extends Player>) temp);
			} else {
				Player[] temp = ((Player[]) Bukkit.class.getMethod("getOnlinePlayers", new Class<?>[0]).invoke(null, new Object[0]));
				ArrayList<Player> players = new ArrayList<Player>();
				for (Player t : temp) {
					players.add(t);
				}
				return players;
			}
		} catch (NoSuchMethodException ex) {
		} catch (InvocationTargetException ex) {
		} catch (IllegalAccessException ex) {
		}
		return new ArrayList<Player>();
	}
}
