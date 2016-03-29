package com.github.yuttyann.custommessage;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public enum Permission {
	CUSTOMMESSAGE_RELOAD("custommessage.reload"),
	CUSTOMMESSAGE_RULES("custommessage.rules"),
	BUKKIT_COMMAND_ME("bukkit.command.me"),
	BUKKIT_COMMAND_SAY("bukkit.command.say"),
	BUKKIT_COMMAND_TELL("bukkit.command.tell");

	private String node;

	private Permission(String node) {
		this.node = node;
	}

	public String getNode() {
		return node;
	}

	public static Boolean has(Permission permission, CommandSender sender) {
		return sender.hasPermission(permission.getNode());
	}

	public static Boolean has(Permission permission, Player player) {
		return player.hasPermission(permission.getNode());
	}
}
