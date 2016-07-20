package com.github.yuttyann.custommessage;

import org.bukkit.command.CommandSender;

public enum Permission {
	CUSTOMMESSAGE_COMMAND_RELOAD("custommessage.command.reload"),
	CUSTOMMESSAGE_COMMAND_RULES("custommessage.command.rules"),
	CUSTOMMESSAGE_COMMAND_TITLE("custommessage.command.title"),
	CUSTOMMESSAGE_COMMAND_TABTITLE("custommessage.command.tabtitle"),
	CUSTOMMESSAGE_SOUND_KILL("custommessage.sound.kill"),
	CUSTOMMESSAGE_SOUND_DEATH("custommessage.sound.death"),
	CUSTOMMESSAGE_SOUND_FIRSTJOIN("custommessage.sound.firstjoin"),
	CUSTOMMESSAGE_SOUND_JOIN("custommessage.sound.join"),
	CUSTOMMESSAGE_SOUND_QUIT("custommessage.sound.quit"),
	CUSTOMMESSAGE_SOUND_CHAT("custommessage.sound.chat"),
	CUSTOMMESSAGE_SOUND_BAN("custommessage.sound.ban"),
	CUSTOMMESSAGE_SOUND_KICK("custommessage.sound.kick"),
	CUSTOMMESSAGE_SOUND_AFK("custommessage.sound.afk"),
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

	public static Boolean has(String permission, CommandSender sender) {
		return sender.hasPermission(permission);
	}
}
