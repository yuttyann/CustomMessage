package com.github.yuttyann.custommessage.handle;

import com.github.yuttyann.custommessage.Main;

public class ClassHandler {

	private static Main plugin;

	public ClassHandler(Main instance) {
		plugin = instance;
	}

	public static Main getMainClass() {
		return plugin;
	}
}
