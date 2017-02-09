package com.github.yuttyann.custommessage.file;

public class Files {

	private static Files instance;
	private YamlConfig config;

	public Files() {
		this.config = YamlConfig.load("config.yml");
	}

	public static YamlConfig getConfig() {
		return instance.config;
	}

	public static void reload() {
		instance = new Files();
	}
}
