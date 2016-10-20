package com.github.yuttyann.custommessage.packet.versions;

import java.lang.reflect.Field;

import net.minecraft.server.v1_9_R2.IChatBaseComponent;
import net.minecraft.server.v1_9_R2.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_9_R2.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_9_R2.PacketPlayOutTitle;
import net.minecraft.server.v1_9_R2.PacketPlayOutTitle.EnumTitleAction;
import net.minecraft.server.v1_9_R2.PlayerConnection;

import org.bukkit.craftbukkit.v1_9_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.github.yuttyann.custommessage.TimeManager;

public class v1_9_R2 {

	public static void sendTitle(Player player, Integer fadein, Integer stay, Integer fadeout, String title, String subtitle) {
		PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
		PacketPlayOutTitle packetPlayOutTimes = new PacketPlayOutTitle(EnumTitleAction.TIMES, null, fadein, stay, fadeout);
		connection.sendPacket(packetPlayOutTimes);
		if (title != null) {
			title = title.replace("%player", player.getName());
			title = title.replace("%time", TimeManager.getTimesofDay());
			title = title.replace("&", "§");
			IChatBaseComponent titleMain = ChatSerializer.a("{\"text\": \"" + title + "\"}");
			PacketPlayOutTitle packetPlayOutTitle = new PacketPlayOutTitle(EnumTitleAction.TITLE, titleMain);
			connection.sendPacket(packetPlayOutTitle);
		}
		if (subtitle != null) {
			subtitle = subtitle.replace("%player", player.getName());
			subtitle = subtitle.replace("%time", TimeManager.getTimesofDay());
			subtitle = subtitle.replace("&", "§");
			IChatBaseComponent titleSub = ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");
			PacketPlayOutTitle packetPlayOutSubTitle = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, titleSub);
			connection.sendPacket(packetPlayOutSubTitle);
		}
	}

	public static void sendTabTitle(Player player, String header, String footer) {
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
		PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
		IChatBaseComponent tabTitle = ChatSerializer.a("{\"text\": \"" + header + "\"}");
		IChatBaseComponent tabFoot = ChatSerializer.a("{\"text\": \"" + footer + "\"}");
		PacketPlayOutPlayerListHeaderFooter headerPacket = new PacketPlayOutPlayerListHeaderFooter(tabTitle);
		try {
			Field field = headerPacket.getClass().getDeclaredField("b");
			field.setAccessible(true);
			field.set(headerPacket, tabFoot);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.sendPacket(headerPacket);
		}
	}
}
