package com.github.yuttyann.custommessage.packet;

import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;

public class TitleReflection {

	private static final Class<?> TITLE_VERSIONS;
	private static final Object TITLE_INSTANCE;
	private static boolean failure = false;

	static {
		Class<?> nbtTagVersions = null;
		Object nbtTagInstance = null;
		try {
			nbtTagVersions = Class.forName("com.github.yuttyann.custommessage.packet.versions." + getPackage());
			nbtTagInstance = nbtTagVersions.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			failure = true;
		} finally {
			TITLE_VERSIONS = nbtTagVersions;
			TITLE_INSTANCE = nbtTagInstance;
		}
	}

	public static void sendTitle(Player player, Integer fadein, Integer stay, Integer fadeout, String title, String subtitle) {
		if (failure) {
			return;
		}
		try {
			Method method = TITLE_VERSIONS.getMethod("sendTitle", Player.class, Integer.class, Integer.class, Integer.class, String.class, String.class);
			method.invoke(TITLE_INSTANCE, player, fadein, stay, fadeout, title, subtitle);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void sendTabTitle(Player player, String header, String footer) {
		if (failure) {
			return;
		}
		try {
			Method method = TITLE_VERSIONS.getMethod("sendTabTitle", Player.class, String.class, String.class);
			method.invoke(TITLE_INSTANCE, player, header, footer);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String getPackage() {
		Server server = Bukkit.getServer();
		String packageName = server.getClass().getPackage().getName();
		packageName = packageName.substring(packageName.lastIndexOf('.') + 1);
		return packageName;
	}

	public static boolean isFail() {
		return failure;
	}
}
