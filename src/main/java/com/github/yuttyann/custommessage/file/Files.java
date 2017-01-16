package com.github.yuttyann.custommessage.file;


public class Files {

	private static Files instance;
	private Yaml config;

	public Files() {
		this.config = new Yaml("config");
	}

	public static Yaml getConfig() {
		return instance.config;
	}

	public static void reload() {
		instance = new Files();
	}
}
