package com.github.yuttyann.custommessage.packet.versions;

import net.minecraft.server.v1_7_R4.ChatSerializer;
import net.minecraft.server.v1_7_R4.IChatBaseComponent;
import net.minecraft.server.v1_7_R4.PlayerConnection;

import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.spigotmc.ProtocolInjector.PacketTabHeader;
import org.spigotmc.ProtocolInjector.PacketTitle;
import org.spigotmc.ProtocolInjector.PacketTitle.Action;

import com.github.yuttyann.custommessage.TimeManager;

public class v1_7_R4 {

	public static void sendTitle(Player player, Integer fadein, Integer stay, Integer fadeout, String title, String subtitle) {
		CraftPlayer craftPlayer = (CraftPlayer) player;
		if (craftPlayer.getHandle().playerConnection.networkManager.getVersion() != 47) {
			return;
		}
		if (title == null) {
			title = "";
		}
		if (subtitle == null) {
			subtitle = "";
		}
		title = title.replace("%player", player.getName());
		title = title.replace("%time", TimeManager.getTimesofDay());
		title = title.replace("&", "§");
		subtitle = subtitle.replace("%player", player.getName());
		subtitle = subtitle.replace("%time", TimeManager.getTimesofDay());
		subtitle = subtitle.replace("&", "§");
		IChatBaseComponent serializedTitle = ChatSerializer.a(convert(title));
		IChatBaseComponent serializedSubTitle = ChatSerializer.a(convert(subtitle));
		IChatBaseComponent title2 = serializedTitle;
		IChatBaseComponent subtitle2 = serializedSubTitle;
		craftPlayer.getHandle().playerConnection.sendPacket(new PacketTitle(Action.TIMES, fadein, stay, fadeout));
		if (title != null) {
			craftPlayer.getHandle().playerConnection.sendPacket(new PacketTitle(Action.TITLE, title2));
		}
		if (subtitle != null) {
			craftPlayer.getHandle().playerConnection.sendPacket(new PacketTitle(Action.SUBTITLE, subtitle2));
		}
	}

	public static void sendTabTitle(Player player, String header, String footer) {
		CraftPlayer craftPlayer = (CraftPlayer) player;
		if (craftPlayer.getHandle().playerConnection.networkManager.getVersion() != 47) {
			return;
		}
		PlayerConnection connection = craftPlayer.getHandle().playerConnection;
		if (header == null) {
			header = "";
		}
		if (footer == null) {
			footer = "";
		}
		header = header.replace("%player", player.getName());
		header = header.replace("%time", TimeManager.getTimesofDay());
		header = header.replace("&", "§");
		footer = footer.replace("%player", player.getName());
		footer = footer.replace("%time", TimeManager.getTimesofDay());
		footer = footer.replace("&", "§");
		IChatBaseComponent header2 = ChatSerializer.a("{'color': 'white', 'text': '" + header + "'}");
		IChatBaseComponent footer2 = ChatSerializer.a("{'color': 'white', 'text': '" + footer + "'}");
		connection.sendPacket(new PacketTabHeader(header2, footer2));
	}

	private static String convert(String text) {
		if ((text == null) || (text.length() == 0)) {
			return "\"\"";
		}
		int len = text.length();
		StringBuilder sb = new StringBuilder(len + 4);
		sb.append('"');
		for (int i = 0; i < len; i++) {
			char c = text.charAt(i);
			switch (c) {
			case '"':
			case '\\':
				sb.append('\\');
				sb.append(c);
				break;
			case '/':
				sb.append('\\');
				sb.append(c);
				break;
			case '\b':
				sb.append("\\b");
				break;
			case '\t':
				sb.append("\\t");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\r':
				sb.append("\\r");
				break;
			default:
				if (c < ' ') {
					String t = "000" + Integer.toHexString(c);
					sb.append("\\u" + t.substring(t.length() - 4));
				} else {
					sb.append(c);
				}
				break;
			}
		}
		sb.append('"');
		return sb.toString();
	}
}
