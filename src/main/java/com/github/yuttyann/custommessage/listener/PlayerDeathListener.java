package com.github.yuttyann.custommessage.listener;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.projectiles.ProjectileSource;

import com.github.yuttyann.custommessage.Permission;
import com.github.yuttyann.custommessage.Sounds;
import com.github.yuttyann.custommessage.TimeManager;
import com.github.yuttyann.custommessage.api.CustomMessage;
import com.github.yuttyann.custommessage.file.Files;
import com.github.yuttyann.custommessage.file.Yaml;
import com.github.yuttyann.custommessage.util.BlockLocation;
import com.github.yuttyann.custommessage.util.Utils;

@SuppressWarnings("deprecation")
public class PlayerDeathListener implements Listener {

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player deader = event.getEntity();
		Sounds sound = Sounds.getSounds();
		String deathmessage = "";
		Yaml config = Files.getConfig();
		BlockLocation deaderLocation = BlockLocation.fromLocation(deader.getLocation());
		if (deader.getLastDamageCause() instanceof EntityDamageByEntityEvent) {
			boolean isUpperVersion_v1_11 = Utils.isUpperVersion(Utils.getVersion(), "1.11");
			EntityDamageByEntityEvent lastcause =  (EntityDamageByEntityEvent) deader.getLastDamageCause();
			Entity entity = lastcause.getDamager();
			BlockLocation killerLocation = BlockLocation.fromLocation(entity.getLocation());
			if (isShot(entity) && deader instanceof Player && getShooter(entity) instanceof Player) {
				Player killer = (Player) getShooter(entity);
				String nullstr = config.getString("DeathMessage.Weapon.NullMessage");
				killerLocation = BlockLocation.fromLocation(killer.getLocation());
				deathmessage = config.getString("DeathMessage.Messages.Player");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%killer", killer.getDisplayName());
				deathmessage = deathmessage.replace("%deadercoords", deaderLocation.getCoords());
				deathmessage = deathmessage.replace("%killercoords", killerLocation.getCoords());
				deathmessage = deathmessage.replace("%world", deaderLocation.getWorld().getName());
				deathmessage = deathmessage.replace("%weapon", CustomMessage.getAPI().getItemName(killer, nullstr));
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
				if (!config.getString("Sounds.PlayerDeathEvent_KillSound").equals("none")) {
					if (sound.soundAuthority(killer, "SoundAuthoritys.PlayerDeathEvent_KillSoundAuthority", Permission.CUSTOMMESSAGE_SOUND_KILL)) {
						sound.playSound(killer, "Sounds.PlayerDeathEvent_KillSound", "SoundTypes.PlayerDeathEvent_KillSoundType");
					}
				}
			} else if (deader instanceof Player && entity instanceof Player) {
				Player killer = event.getEntity().getKiller();
				String nullstr = config.getString("DeathMessage.Weapon.NullMessage");
				killerLocation = BlockLocation.fromLocation(killer.getLocation());
				deathmessage = config.getString("DeathMessage.Messages.Player");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%killer", killer.getDisplayName());
				deathmessage = deathmessage.replace("%deadercoords", deaderLocation.getCoords());
				deathmessage = deathmessage.replace("%killercoords", killerLocation.getCoords());
				deathmessage = deathmessage.replace("%world", deaderLocation.getWorld().getName());
				deathmessage = deathmessage.replace("%weapon", CustomMessage.getAPI().getItemName(killer, nullstr));
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
				if (!config.getString("Sounds.PlayerDeathEvent_KillSound").equals("none")) {
					if (sound.soundAuthority(killer, "SoundAuthoritys.PlayerDeathEvent_KillSoundAuthority", Permission.CUSTOMMESSAGE_SOUND_KILL)) {
						sound.playSound(killer, "Sounds.PlayerDeathEvent_KillSound", "SoundTypes.PlayerDeathEvent_KillSoundType");
					}
				}
			} else if (isEntity(entity, "PIG_ZOMBIE")) {
				deathmessage = config.getString("DeathMessage.Messages.ZombiePigman");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%killer", getEntityName(entity));
				deathmessage = deathmessage.replace("%deadercoords", deaderLocation.getCoords());
				deathmessage = deathmessage.replace("%killercoords", killerLocation.getCoords());
				deathmessage = deathmessage.replace("%world", deaderLocation.getWorld().getName());
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
			} else if ((isUpperVersion_v1_11 && isEntity(entity, "HUSK"))
				|| (!isUpperVersion_v1_11 && isEntity(entity, "ZOMBIE") && zombieType(entity, "HUSK"))) {
				deathmessage = config.getString("DeathMessage.Messages.Husk");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%killer", getEntityName(entity));
				deathmessage = deathmessage.replace("%deadercoords", deaderLocation.getCoords());
				deathmessage = deathmessage.replace("%killercoords", killerLocation.getCoords());
				deathmessage = deathmessage.replace("%world", deaderLocation.getWorld().getName());
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
			} else if ((isUpperVersion_v1_11 && isEntity(entity, "ZOMBIE"))
				|| (!isUpperVersion_v1_11 && isEntity(entity, "ZOMBIE") && !zombieType(entity, "HUSK"))) {
				deathmessage = config.getString("DeathMessage.Messages.Zombie");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%killer", getEntityName(entity));
				deathmessage = deathmessage.replace("%deadercoords", deaderLocation.getCoords());
				deathmessage = deathmessage.replace("%killercoords", killerLocation.getCoords());
				deathmessage = deathmessage.replace("%world", deaderLocation.getWorld().getName());
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
			} else if (isEntity(entity, "SPIDER")) {
				deathmessage = config.getString("DeathMessage.Messages.Spider");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%killer", getEntityName(entity));
				deathmessage = deathmessage.replace("%deadercoords", deaderLocation.getCoords());
				deathmessage = deathmessage.replace("%killercoords", killerLocation.getCoords());
				deathmessage = deathmessage.replace("%world", deaderLocation.getWorld().getName());
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
			} else if (isEntity(entity, "SILVERFISH")) {
				deathmessage = config.getString("DeathMessage.Messages.SilverFish");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%killer", getEntityName(entity));
				deathmessage = deathmessage.replace("%deadercoords", deaderLocation.getCoords());
				deathmessage = deathmessage.replace("%killercoords", killerLocation.getCoords());
				deathmessage = deathmessage.replace("%world", deaderLocation.getWorld().getName());
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
			} else if (isEntity(entity, "SLIME")) {
				deathmessage = config.getString("DeathMessage.Messages.Slime");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%killer", getEntityName(entity));
				deathmessage = deathmessage.replace("%deadercoords", deaderLocation.getCoords());
				deathmessage = deathmessage.replace("%killercoords", killerLocation.getCoords());
				deathmessage = deathmessage.replace("%world", deaderLocation.getWorld().getName());
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
			} else if (isEntity(entity, "MAGMA_CUBE")) {
				deathmessage = config.getString("DeathMessage.Messages.MagmaCube");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%killer", getEntityName(entity));
				deathmessage = deathmessage.replace("%deadercoords", deaderLocation.getCoords());
				deathmessage = deathmessage.replace("%killercoords", killerLocation.getCoords());
				deathmessage = deathmessage.replace("%world", deaderLocation.getWorld().getName());
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
			} else if (isEntity(entity, "ENDERMITE")) {
				deathmessage = config.getString("DeathMessage.Messages.EnderMite");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%killer", getEntityName(entity));
				deathmessage = deathmessage.replace("%deadercoords", deaderLocation.getCoords());
				deathmessage = deathmessage.replace("%killercoords", killerLocation.getCoords());
				deathmessage = deathmessage.replace("%world", deaderLocation.getWorld().getName());
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
			} else if (isEntity(entity, "ENDERMAN")) {
				deathmessage = config.getString("DeathMessage.Messages.Enderman");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%killer", getEntityName(entity));
				deathmessage = deathmessage.replace("%deadercoords", deaderLocation.getCoords());
				deathmessage = deathmessage.replace("%killercoords", killerLocation.getCoords());
				deathmessage = deathmessage.replace("%world", deaderLocation.getWorld().getName());
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
			} else if (isEntity(entity, "ENDER_DRAGON")) {
				deathmessage = config.getString("DeathMessage.Messages.EnderDragon");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%killer", getEntityName(entity));
				deathmessage = deathmessage.replace("%deadercoords", deaderLocation.getCoords());
				deathmessage = deathmessage.replace("%killercoords", killerLocation.getCoords());
				deathmessage = deathmessage.replace("%world", deaderLocation.getWorld().getName());
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
			} else if (isEntity(entity, "CAVE_SPIDER")) {
				deathmessage = config.getString("DeathMessage.Messages.CaveSpider");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%killer", getEntityName(entity));
				deathmessage = deathmessage.replace("%deadercoords", deaderLocation.getCoords());
				deathmessage = deathmessage.replace("%killercoords", killerLocation.getCoords());
				deathmessage = deathmessage.replace("%world", deaderLocation.getWorld().getName());
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
			} else if (isEntity(entity, "IRON_GOLEM")) {
				deathmessage = config.getString("DeathMessage.Messages.IronGolem");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%killer", getEntityName(entity));
				deathmessage = deathmessage.replace("%deadercoords", deaderLocation.getCoords());
				deathmessage = deathmessage.replace("%killercoords", killerLocation.getCoords());
				deathmessage = deathmessage.replace("%world", deaderLocation.getWorld().getName());
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
			} else if (isEntity(entity, "WOLF")) {
				deathmessage = config.getString("DeathMessage.Messages.Wolf");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%killer", getEntityName(entity));
				deathmessage = deathmessage.replace("%deadercoords", deaderLocation.getCoords());
				deathmessage = deathmessage.replace("%killercoords", killerLocation.getCoords());
				deathmessage = deathmessage.replace("%world", deaderLocation.getWorld().getName());
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
			} else if (isEntity(entity, "POLAR_BEAR")) {
				deathmessage = config.getString("DeathMessage.Messages.PolarBear");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%killer", getEntityName(entity));
				deathmessage = deathmessage.replace("%deadercoords", deaderLocation.getCoords());
				deathmessage = deathmessage.replace("%killercoords", killerLocation.getCoords());
				deathmessage = deathmessage.replace("%world", deaderLocation.getWorld().getName());
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
			} else if (isEntity(entity, "GIANT")) {
				deathmessage = config.getString("DeathMessage.Messages.Giant");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%killer", getEntityName(entity));
				deathmessage = deathmessage.replace("%deadercoords", deaderLocation.getCoords());
				deathmessage = deathmessage.replace("%killercoords", killerLocation.getCoords());
				deathmessage = deathmessage.replace("%world", deaderLocation.getWorld().getName());
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
			} else if (isEntity(entity, "GUARDIAN")) {
				deathmessage = config.getString("DeathMessage.Messages.Guardian");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%killer", getEntityName(entity));
				deathmessage = deathmessage.replace("%deadercoords", deaderLocation.getCoords());
				deathmessage = deathmessage.replace("%killercoords", killerLocation.getCoords());
				deathmessage = deathmessage.replace("%world", deaderLocation.getWorld().getName());
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
			} else if (isEntity(entity, "WITHER")) {
				deathmessage = config.getString("DeathMessage.Messages.Wither");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%killer", getEntityName(entity));
				deathmessage = deathmessage.replace("%deadercoords", deaderLocation.getCoords());
				deathmessage = deathmessage.replace("%killercoords", killerLocation.getCoords());
				deathmessage = deathmessage.replace("%world", deaderLocation.getWorld().getName());
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
			} else if (isEntity(entity, "BLAZE")) {
				deathmessage = config.getString("DeathMessage.Messages.Blaze");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%killer", getEntityName(entity));
				deathmessage = deathmessage.replace("%deadercoords", deaderLocation.getCoords());
				deathmessage = deathmessage.replace("%killercoords", killerLocation.getCoords());
				deathmessage = deathmessage.replace("%world", deaderLocation.getWorld().getName());
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
			} else if (isEntity(entity, "GHAST")) {
				deathmessage = config.getString("DeathMessage.Messages.Ghast");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%killer", getEntityName(entity));
				deathmessage = deathmessage.replace("%deadercoords", deaderLocation.getCoords());
				deathmessage = deathmessage.replace("%killercoords", killerLocation.getCoords());
				deathmessage = deathmessage.replace("%world", deaderLocation.getWorld().getName());
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
			} else if (isEntity(entity, "WITCH")) {
				deathmessage = config.getString("DeathMessage.Messages.Witch");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%killer", getEntityName(entity));
				deathmessage = deathmessage.replace("%deadercoords", deaderLocation.getCoords());
				deathmessage = deathmessage.replace("%killercoords", killerLocation.getCoords());
				deathmessage = deathmessage.replace("%world", deaderLocation.getWorld().getName());
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
			} else if (lastcause.getCause().equals(DamageCause.ENTITY_EXPLOSION) || lastcause.getCause().equals(DamageCause.BLOCK_EXPLOSION)) {
				deathmessage = config.getString("DeathMessage.Messages.Explosion");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%deadercoords", deaderLocation.getCoords());
				deathmessage = deathmessage.replace("%world", deaderLocation.getWorld().getName());
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
			} else {
				Entity killer = null;
				if (lastcause.getDamager() instanceof Arrow && ((Arrow) lastcause.getDamager()).getShooter() instanceof LivingEntity) {
					Arrow arrow = (Arrow) lastcause.getDamager();
					LivingEntity livingentity = (LivingEntity) arrow.getShooter();
					killer = livingentity;
					entity = livingentity;
				} else {
					killer = entity;
				}
				if ((isUpperVersion_v1_11 && isEntity(entity, "SKELETON"))
					|| (!isUpperVersion_v1_11 && isEntity(entity, "SKELETON") && skeletonType(entity, "NORMAL"))) {
					killerLocation = BlockLocation.fromLocation(killer.getLocation());
					deathmessage = config.getString("DeathMessage.Messages.Skeleton");
					deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
					deathmessage = deathmessage.replace("%killer", getEntityName(killer));
					deathmessage = deathmessage.replace("%deadercoords", deaderLocation.getCoords());
					deathmessage = deathmessage.replace("%killercoords", killerLocation.getCoords());
					deathmessage = deathmessage.replace("%world", deaderLocation.getWorld().getName());
					deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
					deathmessage = deathmessage.replace("&", "§");
				}
				if ((isUpperVersion_v1_11 && isEntity(entity, "WITHER_SKELETON"))
					||(!isUpperVersion_v1_11 && isEntity(entity, "SKELETON") && skeletonType(entity, "WITHER"))) {
					killerLocation = BlockLocation.fromLocation(killer.getLocation());
					deathmessage = config.getString("DeathMessage.Messages.WitherSkeleton");
					deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
					deathmessage = deathmessage.replace("%killer", getEntityName(killer));
					deathmessage = deathmessage.replace("%deadercoords", deaderLocation.getCoords());
					deathmessage = deathmessage.replace("%killercoords", killerLocation.getCoords());
					deathmessage = deathmessage.replace("%world", deaderLocation.getWorld().getName());
					deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
					deathmessage = deathmessage.replace("&", "§");
				}
				if ((isUpperVersion_v1_11 && isEntity(entity, "STRAY"))
					|| (!isUpperVersion_v1_11 && isEntity(entity, "SKELETON") && skeletonType(entity, "STRAY"))) {
					killerLocation = BlockLocation.fromLocation(killer.getLocation());
					deathmessage = config.getString("DeathMessage.Messages.Stray");
					deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
					deathmessage = deathmessage.replace("%killer", getEntityName(killer));
					deathmessage = deathmessage.replace("%deadercoords", deaderLocation.getCoords());
					deathmessage = deathmessage.replace("%killercoords", killerLocation.getCoords());
					deathmessage = deathmessage.replace("%world", deaderLocation.getWorld().getName());
					deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
					deathmessage = deathmessage.replace("&", "§");
				}
			}
		} else {
			DamageCause cause = deader.getLastDamageCause().getCause();
			if (cause.equals(DamageCause.DROWNING)) {
				deathmessage = config.getString("DeathMessage.Messages.Drowning");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%deadercoords", deaderLocation.getCoords());
				deathmessage = deathmessage.replace("%world", deaderLocation.getWorld().getName());
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
			} else if (cause.equals(DamageCause.FALL)) {
				deathmessage = config.getString("DeathMessage.Messages.Fall");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%deadercoords", deaderLocation.getCoords());
				deathmessage = deathmessage.replace("%world", deaderLocation.getWorld().getName());
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
			} else if (cause.equals(DamageCause.VOID)) {
				deathmessage = config.getString("DeathMessage.Messages.Void");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%deadercoords", deaderLocation.getCoords());
				deathmessage = deathmessage.replace("%world", deaderLocation.getWorld().getName());
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
			} else if (cause.equals(DamageCause.LAVA)) {
				deathmessage = config.getString("DeathMessage.Messages.Lava");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%deadercoords", deaderLocation.getCoords());
				deathmessage = deathmessage.replace("%world", deaderLocation.getWorld().getName());
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
			} else if (cause.equals(DamageCause.MAGIC)) {
				deathmessage = config.getString("DeathMessage.Messages.Magic");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%deadercoords", deaderLocation.getCoords());
				deathmessage = deathmessage.replace("%world", deaderLocation.getWorld().getName());
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
			} else if (cause.equals(DamageCause.SUFFOCATION)) {
				deathmessage = config.getString("DeathMessage.Messages.Suffocation");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%deadercoords", deaderLocation.getCoords());
				deathmessage = deathmessage.replace("%world", deaderLocation.getWorld().getName());
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
			} else if (cause.equals(DamageCause.PROJECTILE)) {
				deathmessage = config.getString("DeathMessage.Messages.Projectile");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%deadercoords", deaderLocation.getCoords());
				deathmessage = deathmessage.replace("%world", deaderLocation.getWorld().getName());
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
			} else if (cause.equals(DamageCause.STARVATION)) {
				deathmessage = config.getString("DeathMessage.Messages.Starvation");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%deadercoords", deaderLocation.getCoords());
				deathmessage = deathmessage.replace("%world", deaderLocation.getWorld().getName());
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
			} else if (cause.equals(DamageCause.WITHER)) {
				deathmessage = config.getString("DeathMessage.Messages.Withered");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%deadercoords", deaderLocation.getCoords());
				deathmessage = deathmessage.replace("%world", deaderLocation.getWorld().getName());
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
			} else if (cause.equals(DamageCause.FIRE) || cause.equals(DamageCause.FIRE_TICK)) {
				deathmessage = config.getString("DeathMessage.Messages.Fire");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%deadercoords", deaderLocation.getCoords());
				deathmessage = deathmessage.replace("%world", deaderLocation.getWorld().getName());
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
			} else if (cause.equals(DamageCause.ENTITY_EXPLOSION) || cause.equals(DamageCause.BLOCK_EXPLOSION)) {
				deathmessage = config.getString("DeathMessage.Messages.Explosion");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%deadercoords", deaderLocation.getCoords());
				deathmessage = deathmessage.replace("%world", deaderLocation.getWorld().getName());
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
			}
		}
		if (!config.getString("Sounds.PlayerDeathEvent_DeathSound").equals("none")) {
			if (sound.soundAuthority(deader, "SoundAuthoritys.PlayerDeathEvent_DeathSoundAuthority", Permission.CUSTOMMESSAGE_SOUND_DEATH)) {
				sound.playSound(deader, "Sounds.PlayerDeathEvent_DeathSound", "SoundTypes.PlayerDeathEvent_DeathSoundType");
			}
		}
		if (config.getBoolean("DeathMessage.Enable")) {
			event.setDeathMessage(deathmessage);
		}
	}

	private boolean isShot(Entity entity) {
		return entity instanceof Arrow || entity instanceof Snowball || entity instanceof Egg;
	}

	private ProjectileSource getShooter(Entity entity) {
		ProjectileSource projectile = null;
		if (entity instanceof Arrow) {
			projectile = ((Arrow) entity).getShooter();
		} else if (entity instanceof Snowball) {
			projectile = ((Snowball) entity).getShooter();
		} else if (entity instanceof Egg) {
			projectile = ((Egg) entity).getShooter();
		}
		return projectile;
	}

	private String getEntityName(Entity entity) {
		if (Utils.isUpperVersion_v18()) {
			return entity.getName();
		}
		LivingEntity livingentity = (LivingEntity) entity;
		if (livingentity.getCustomName() != null) {
			return livingentity.getCustomName();
		}
		return entity.getType().toString();
	}

	private boolean isEntity(Entity entity, String typename) {
		EntityType type;
		try {
			type = EntityType.valueOf(typename);
			if (entity.getType().equals(type)) {
				return true;
			}
		} catch(Exception e) {}
		return false;
	}

	private boolean skeletonType(Entity entity, String typename) {
		SkeletonType type = null;
		try {
			type = SkeletonType.valueOf(typename);
			Skeleton skeleton = (Skeleton) entity;
			if (skeleton.getSkeletonType().equals(type)) {
				return true;
			}
		} catch(Exception e) {}
		return false;
	}

	private boolean zombieType(Entity entity, String typename) {
		Profession type = null;
		try {
			type = Profession.valueOf(typename);
			Zombie zombie = (Zombie) entity;
			if (zombie.getVillagerProfession().equals(type)) {
				return true;
			}
		} catch(Exception e) {}
		return false;
	}
}
