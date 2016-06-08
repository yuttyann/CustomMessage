package com.github.yuttyann.custommessage.util;

public class PlatformUtils {

	private static final String os_name = System.getProperty("os.name").toLowerCase();

	public static boolean isLinux() {
		return os_name.startsWith("linux");
	}

	public static boolean isMac() {
		return os_name.startsWith("mac");
	}

	public static boolean isWindows() {
		return os_name.startsWith("windows");
	}
}