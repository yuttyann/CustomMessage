package com.github.yuttyann.custommessage;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public enum Permission {
	CUSTOMMESSAGE_COMMAND_RELOAD("custommessage.command.reload"),
	CUSTOMMESSAGE_COMMAND_RULES("custommessage.command.rules"),
	CUSTOMMESSAGE_COMMAND_TITLE("custommessage.command.title"),
	BUKKIT_COMMAND_BAN_PLAYER("bukkit.command.ban.player"),
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
