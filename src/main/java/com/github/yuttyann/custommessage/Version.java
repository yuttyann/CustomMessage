package com.github.yuttyann.custommessage;

import org.bukkit.Bukkit;

public class Version {

	public static boolean isVersion(String targetversion) {
		int version = verInt(getVersion().split("\\."));
		int target = verInt(targetversion.split("\\."));
		if (version >= target) {
			return true;
		}
		return false;
	}

	private static int verInt(String[] str) {
		if(str.length < 3) {
			str = new String[]{str[0], str[1], "0"};
		}
		if(str[2].length() == 1) {
			str[2] = "0" + str[2];
		}
		str[2] = "0" + str[2];
		return parseInt(str[0]) * 10000 + parseInt(str[1]) * 1000 + parseInt(str[2]);
	}

	private static int parseInt(String str) {
		return Integer.parseInt(str);
	}

	private static String getVersion() {
		String version = Bukkit.getServer().getVersion();
		version = version.split("\\(")[1];
		version = version.substring(4, version.length() - 1);
		return version;
	}
}
