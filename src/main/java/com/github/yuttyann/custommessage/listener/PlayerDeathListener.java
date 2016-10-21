package com.github.yuttyann.custommessage.listener;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.github.yuttyann.custommessage.Main;
import com.github.yuttyann.custommessage.Permission;
import com.github.yuttyann.custommessage.Sounds;
import com.github.yuttyann.custommessage.TimeManager;
import com.github.yuttyann.custommessage.api.CustomMessage;
import com.github.yuttyann.custommessage.file.Config;
import com.github.yuttyann.custommessage.util.Utils;

public class PlayerDeathListener implements Listener {

	Main plugin;

	public PlayerDeathListener(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player deader = event.getEntity();
		Sounds sound = Sounds.getSounds();
		String deathmessage = "";
		if (deader.getLastDamageCause() instanceof EntityDamageByEntityEvent) {
			EntityDamageByEntityEvent lastcause =  (EntityDamageByEntityEvent) deader.getLastDamageCause();
			Entity entity = lastcause.getDamager();
			if (deader instanceof Player && entity instanceof Arrow && ((Arrow) lastcause.getDamager()).getShooter() instanceof Player) {
				Arrow arrow = (Arrow) lastcause.getDamager();
				Player killer = (Player) arrow.getShooter();
				String nullstr = Config.getString("DeathMessage.Weapon.NullMessage");
				deathmessage = Config.getString("DeathMessage.Messages.Player");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%killer", killer.getDisplayName());
				deathmessage = deathmessage.replace("%weapon", CustomMessage.getAPI().getItemName(killer, nullstr));
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
				if (!Config.getString("Sounds.PlayerDeathEvent_KillSound").equals("none")) {
					if (sound.soundAuthority(killer, "SoundAuthoritys.PlayerDeathEvent_KillSoundAuthority", Permission.CUSTOMMESSAGE_SOUND_KILL)) {
						sound.playSound(killer, "Sounds.PlayerDeathEvent_KillSound", "SoundTypes.PlayerDeathEvent_KillSoundType");
					}
				}
			} else if (deader instanceof Player && entity instanceof Player) {
				Player killer = event.getEntity().getKiller();
				String nullstr = Config.getString("DeathMessage.Weapon.NullMessage");
				deathmessage = Config.getString("DeathMessage.Messages.Player");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%killer", killer.getDisplayName());
				deathmessage = deathmessage.replace("%weapon", CustomMessage.getAPI().getItemName(killer, nullstr));
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
				if (!Config.getString("Sounds.PlayerDeathEvent_KillSound").equals("none")) {
					if (sound.soundAuthority(killer, "SoundAuthoritys.PlayerDeathEvent_KillSoundAuthority", Permission.CUSTOMMESSAGE_SOUND_KILL)) {
						sound.playSound(killer, "Sounds.PlayerDeathEvent_KillSound", "SoundTypes.PlayerDeathEvent_KillSoundType");
					}
				}
			} else if (isEntity(entity, "PIG_ZOMBIE")) {
				deathmessage = Config.getString("DeathMessage.Messages.ZombiePigman");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%killer", getEntityName(entity));
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
			} else if (isEntity(entity, "ZOMBIE") && zombieType(entity, "HUSK")) {
				deathmessage = Config.getString("DeathMessage.Messages.Husk");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%killer", getEntityName(entity));
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
			} else if (isEntity(entity, "ZOMBIE") && !zombieType(entity, "HUSK")) {
				deathmessage = Config.getString("DeathMessage.Messages.Zombie");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%killer", getEntityName(entity));
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
			} else if (isEntity(entity, "SPIDER")) {
				deathmessage = Config.getString("DeathMessage.Messages.Spider");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%killer", getEntityName(entity));
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
			} else if (isEntity(entity, "SILVERFISH")) {
				deathmessage = Config.getString("DeathMessage.Messages.SilverFish");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%killer", getEntityName(entity));
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
			} else if (isEntity(entity, "SLIME")) {
				deathmessage = Config.getString("DeathMessage.Messages.Slime");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%killer", getEntityName(entity));
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
			} else if (isEntity(entity, "MAGMA_CUBE")) {
				deathmessage = Config.getString("DeathMessage.Messages.MagmaCube");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%killer", getEntityName(entity));
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
			} else if (isEntity(entity, "ENDERMITE")) {
				deathmessage = Config.getString("DeathMessage.Messages.EnderMite");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%killer", getEntityName(entity));
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
			} else if (isEntity(entity, "ENDERMAN")) {
				deathmessage = Config.getString("DeathMessage.Messages.Enderman");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%killer", getEntityName(entity));
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
			} else if (isEntity(entity, "ENDER_DRAGON")) {
				deathmessage = Config.getString("DeathMessage.Messages.EnderDragon");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%killer", getEntityName(entity));
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
			} else if (isEntity(entity, "CAVE_SPIDER")) {
				deathmessage = Config.getString("DeathMessage.Messages.CaveSpider");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%killer", getEntityName(entity));
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
			} else if (isEntity(entity, "IRON_GOLEM")) {
				deathmessage = Config.getString("DeathMessage.Messages.IronGolem");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%killer", getEntityName(entity));
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
			} else if (isEntity(entity, "WOLF")) {
				deathmessage = Config.getString("DeathMessage.Messages.Wolf");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%killer", getEntityName(entity));
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
			} else if (isEntity(entity, "POLAR_BEAR")) {
				deathmessage = Config.getString("DeathMessage.Messages.PolarBear");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%killer", getEntityName(entity));
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
			} else if (isEntity(entity, "GIANT")) {
				deathmessage = Config.getString("DeathMessage.Messages.Giant");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%killer", getEntityName(entity));
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
			} else if (isEntity(entity, "GUARDIAN")) {
				deathmessage = Config.getString("DeathMessage.Messages.Guardian");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%killer", getEntityName(entity));
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
			} else if (isEntity(entity, "WITHER")) {
				deathmessage = Config.getString("DeathMessage.Messages.Wither");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%killer", getEntityName(entity));
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
			} else if (isEntity(entity, "BLAZE")) {
				deathmessage = Config.getString("DeathMessage.Messages.Blaze");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%killer", getEntityName(entity));
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
			} else if (isEntity(entity, "GHAST")) {
				deathmessage = Config.getString("DeathMessage.Messages.Ghast");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%killer", getEntityName(entity));
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
			} else if (isEntity(entity, "WITCH")) {
				deathmessage = Config.getString("DeathMessage.Messages.Witch");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%killer", getEntityName(entity));
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
			} else if ((lastcause.getCause().equals(DamageCause.ENTITY_EXPLOSION) || lastcause.getCause().equals(DamageCause.BLOCK_EXPLOSION))) {
				deathmessage = Config.getString("DeathMessage.Messages.Explosion");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
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
				if (isEntity(entity, "SKELETON") && skeletonType(entity, "NORMAL")) {
					deathmessage = Config.getString("DeathMessage.Messages.Skeleton");
					deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
					deathmessage = deathmessage.replace("%killer", getEntityName(killer));
					deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
					deathmessage = deathmessage.replace("&", "§");
				}
				if (isEntity(entity, "SKELETON") && skeletonType(entity, "WITHER")) {
					deathmessage = Config.getString("DeathMessage.Messages.WitherSkeleton");
					deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
					deathmessage = deathmessage.replace("%killer", getEntityName(killer));
					deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
					deathmessage = deathmessage.replace("&", "§");
				}
				if (isEntity(entity, "SKELETON") && skeletonType(entity, "STRAY")) {
					deathmessage = Config.getString("DeathMessage.Messages.Stray");
					deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
					deathmessage = deathmessage.replace("%killer", getEntityName(killer));
					deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
					deathmessage = deathmessage.replace("&", "§");
				}
			}
		} else {
			DamageCause cause = deader.getLastDamageCause().getCause();
			if (cause.equals(DamageCause.DROWNING)) {
				deathmessage = Config.getString("DeathMessage.Messages.Drowning");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
			} else if (cause.equals(DamageCause.FALL)) {
				deathmessage = Config.getString("DeathMessage.Messages.Fall");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
			} else if (cause.equals(DamageCause.VOID)) {
				deathmessage = Config.getString("DeathMessage.Messages.Void");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
			} else if (cause.equals(DamageCause.LAVA)) {
				deathmessage = Config.getString("DeathMessage.Messages.Lava");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
			} else if (cause.equals(DamageCause.MAGIC)) {
				deathmessage = Config.getString("DeathMessage.Messages.Magic");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
			} else if (cause.equals(DamageCause.SUFFOCATION)) {
				deathmessage = Config.getString("DeathMessage.Messages.Suffocation");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
			} else if (cause.equals(DamageCause.PROJECTILE)) {
				deathmessage = Config.getString("DeathMessage.Messages.Projectile");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
			} else if (cause.equals(DamageCause.STARVATION)) {
				deathmessage = Config.getString("DeathMessage.Messages.Starvation");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
			} else if (cause.equals(DamageCause.WITHER)) {
				deathmessage = Config.getString("DeathMessage.Messages.Withered");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
			} else if (cause.equals(DamageCause.FIRE) || cause.equals(DamageCause.FIRE_TICK)) {
				deathmessage = Config.getString("DeathMessage.Messages.Fire");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
			} else if (cause.equals(DamageCause.ENTITY_EXPLOSION) || cause.equals(DamageCause.BLOCK_EXPLOSION)) {
				deathmessage = Config.getString("DeathMessage.Messages.Explosion");
				deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
				deathmessage = deathmessage.replace("%time", TimeManager.getTimesofDay());
				deathmessage = deathmessage.replace("&", "§");
			}
		}
		if (!Config.getString("Sounds.PlayerDeathEvent_DeathSound").equals("none")) {
			if (sound.soundAuthority(deader, "SoundAuthoritys.PlayerDeathEvent_DeathSoundAuthority", Permission.CUSTOMMESSAGE_SOUND_DEATH)) {
				sound.playSound(deader, "Sounds.PlayerDeathEvent_DeathSound", "SoundTypes.PlayerDeathEvent_DeathSoundType");
			}
		}
		if (Config.getBoolean("DeathMessage.Enable")) {
			event.setDeathMessage(deathmessage);
		}
	}

	private String getEntityName(Entity entity) {
		if (Utils.isUpperVersion("1.8")) {
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
		} catch(Exception e) {
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	private boolean skeletonType(Entity entity, String typename) {
		SkeletonType type = null;
		try {
			type = SkeletonType.valueOf(typename);
			Skeleton skeleton = (Skeleton) entity;
			if (skeleton.getSkeletonType().equals(type)) {
				return true;
			}
		} catch(Exception e) {
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	private boolean zombieType(Entity entity, String typename) {
		Profession type = null;
		try {
			type = Profession.valueOf(typename);
			Zombie zombie = (Zombie) entity;
			if (zombie.getVillagerProfession().equals(type)) {
				return true;
			}
		} catch(Exception e) {
		}
		return false;
	}
}
