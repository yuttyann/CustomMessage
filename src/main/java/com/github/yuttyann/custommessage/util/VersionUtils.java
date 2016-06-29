package com.github.yuttyann.custommessage.util;

import org.bukkit.Bukkit;

public class VersionUtils {

	public static boolean isVersion(String targetversion) {
		int version = versionInt(getVersion().split("\\."));
		int target = versionInt(targetversion.split("\\."));
		if (version >= target) {
			return true;
		}
		return false;
	}

	private static int versionInt(String[] version) {
		if (version.length < 3) {
			version = new String[]{version[0], version[1], "0"};
		}
		if (version[1].length() == 1) {
			version[1] = "0" + version[1];
		}
		if (version[2].length() == 1) {
			version[2] = "0" + version[2];
		}
		version[2] = "0" + version[2];
		return parseInt(version[0]) * 100000 + parseInt(version[1]) * 1000 + parseInt(version[2]);
	}

	public static String getVersion() {
		String version = Bukkit.getServer().getVersion();
		version = version.split("\\(")[1];
		version = version.substring(4, version.length() - 1);
		return version;
	}

	private static int parseInt(String str) {
		return Integer.parseInt(str);
	}
}
