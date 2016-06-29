package com.github.yuttyann.custommessage.listener;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.github.yuttyann.custommessage.Main;
import com.github.yuttyann.custommessage.Sounds;
import com.github.yuttyann.custommessage.api.CustomMessage;
import com.github.yuttyann.custommessage.file.Config;
import com.github.yuttyann.custommessage.util.TimeUtils;
import com.github.yuttyann.custommessage.util.VersionUtils;

public class PlayerDeathListener implements Listener {

	Main plugin;

	public PlayerDeathListener(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onDeathMessage(PlayerDeathEvent event) {
		if (Config.getBoolean("DeathMessage.Enable")) {
			DamageCause cause = event.getEntity().getLastDamageCause().getCause();
			Player deader = event.getEntity();
			String deathmessage = "";
			if (deader.getLastDamageCause() instanceof EntityDamageByEntityEvent) {
				EntityDamageByEntityEvent entitydamage =  (EntityDamageByEntityEvent) deader.getLastDamageCause();
				Entity entity = entitydamage.getDamager();
				if (deader instanceof Player && entity instanceof Arrow && ((Arrow) entitydamage.getDamager()).getShooter() instanceof Player) {
					Arrow arrow = (Arrow) entitydamage.getDamager();
					Player killer = (Player) arrow.getShooter();
					String nullstr = Config.getString("DeathMessage.Weapon.NullMessage");
					deathmessage = Config.getString("DeathMessage.Messages.Player");
					deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
					deathmessage = deathmessage.replace("%killer", killer.getDisplayName());
					deathmessage = deathmessage.replace("%weapon", CustomMessage.getAPI().getWeaoonName(killer, nullstr));
					deathmessage = deathmessage.replace("%time", TimeUtils.getTime());
					deathmessage = deathmessage.replace("&", "§");
					if (!Config.getString("Sounds.KillSound").equals("none")) {
						new Sounds(plugin).playSound(killer, "Sounds.KillSound", "SoundTypes.KillSoundType");
					}
				} else if (deader instanceof Player && entity instanceof Player) {
					Player killer = event.getEntity().getKiller();
					String nullstr = Config.getString("DeathMessage.Weapon.NullMessage");
					deathmessage = Config.getString("DeathMessage.Messages.Player");
					deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
					deathmessage = deathmessage.replace("%killer", killer.getDisplayName());
					deathmessage = deathmessage.replace("%weapon", CustomMessage.getAPI().getWeaoonName(killer, nullstr));
					deathmessage = deathmessage.replace("%time", TimeUtils.getTime());
					deathmessage = deathmessage.replace("&", "§");
				} else if (isEntity(entity, "PIG_ZOMBIE")) {
					deathmessage = Config.getString("DeathMessage.Messages.ZombiePigman");
					deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
					if (VersionUtils.isVersion("1.8")) {
						deathmessage = deathmessage.replace("%killer", entity.getName());
					}
					deathmessage = deathmessage.replace("%time", TimeUtils.getTime());
					deathmessage = deathmessage.replace("&", "§");
					if (!Config.getString("Sounds.DeathSound").equals("none")) {
						new Sounds(plugin).playSound(deader, "Sounds.DeathSound", "SoundTypes.DeathSoundType");
					}
				} else if (isEntity(entity, "ZOMBIE") && zombieType(entity, "HUSK")) {
					deathmessage = Config.getString("DeathMessage.Messages.Husk");
					deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
					if (VersionUtils.isVersion("1.8")) {
						deathmessage = deathmessage.replace("%killer", entity.getName());
					}
					deathmessage = deathmessage.replace("%time", TimeUtils.getTime());
					deathmessage = deathmessage.replace("&", "§");
					if (!Config.getString("Sounds.DeathSound").equals("none")) {
						new Sounds(plugin).playSound(deader, "Sounds.DeathSound", "SoundTypes.DeathSoundType");
					}
				} else if (isEntity(entity, "ZOMBIE") && !zombieType(entity, "HUSK")) {
					deathmessage = Config.getString("DeathMessage.Messages.Zombie");
					deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
					if (VersionUtils.isVersion("1.8")) {
						deathmessage = deathmessage.replace("%killer", entity.getName());
					}
					deathmessage = deathmessage.replace("%time", TimeUtils.getTime());
					deathmessage = deathmessage.replace("&", "§");
					if (!Config.getString("Sounds.DeathSound").equals("none")) {
						new Sounds(plugin).playSound(deader, "Sounds.DeathSound", "SoundTypes.DeathSoundType");
					}
				} else if (isEntity(entity, "SPIDER")) {
					deathmessage = Config.getString("DeathMessage.Messages.Spider");
					deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
					if (VersionUtils.isVersion("1.8")) {
						deathmessage = deathmessage.replace("%killer", entity.getName());
					}
					deathmessage = deathmessage.replace("%time", TimeUtils.getTime());
					deathmessage = deathmessage.replace("&", "§");
					if (!Config.getString("Sounds.DeathSound").equals("none")) {
						new Sounds(plugin).playSound(deader, "Sounds.DeathSound", "SoundTypes.DeathSoundType");
					}
				} else if (isEntity(entity, "SILVERFISH")) {
					deathmessage = Config.getString("DeathMessage.Messages.SilverFish");
					deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
					if (VersionUtils.isVersion("1.8")) {
						deathmessage = deathmessage.replace("%killer", entity.getName());
					}
					deathmessage = deathmessage.replace("%time", TimeUtils.getTime());
					deathmessage = deathmessage.replace("&", "§");
					if (!Config.getString("Sounds.DeathSound").equals("none")) {
						new Sounds(plugin).playSound(deader, "Sounds.DeathSound", "SoundTypes.DeathSoundType");
					}
				} else if (isEntity(entity, "SLIME")) {
					deathmessage = Config.getString("DeathMessage.Messages.Slime");
					deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
					if (VersionUtils.isVersion("1.8")) {
						deathmessage = deathmessage.replace("%killer", entity.getName());
					}
					deathmessage = deathmessage.replace("%time", TimeUtils.getTime());
					deathmessage = deathmessage.replace("&", "§");
					if (!Config.getString("Sounds.DeathSound").equals("none")) {
						new Sounds(plugin).playSound(deader, "Sounds.DeathSound", "SoundTypes.DeathSoundType");
					}
				} else if (isEntity(entity, "MAGMA_CUBE")) {
					deathmessage = Config.getString("DeathMessage.Messages.MagmaCube");
					deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
					if (VersionUtils.isVersion("1.8")) {
						deathmessage = deathmessage.replace("%killer", entity.getName());
					}
					deathmessage = deathmessage.replace("%time", TimeUtils.getTime());
					deathmessage = deathmessage.replace("&", "§");
					if (!Config.getString("Sounds.DeathSound").equals("none")) {
						new Sounds(plugin).playSound(deader, "Sounds.DeathSound", "SoundTypes.DeathSoundType");
					}
				} else if (isEntity(entity, "ENDERMITE")) {
					deathmessage = Config.getString("DeathMessage.Messages.EnderMite");
					deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
					if (VersionUtils.isVersion("1.8")) {
						deathmessage = deathmessage.replace("%killer", entity.getName());
					}
					deathmessage = deathmessage.replace("%time", TimeUtils.getTime());
					deathmessage = deathmessage.replace("&", "§");
					if (!Config.getString("Sounds.DeathSound").equals("none")) {
						new Sounds(plugin).playSound(deader, "Sounds.DeathSound", "SoundTypes.DeathSoundType");
					}
				} else if (isEntity(entity, "ENDERMAN")) {
					deathmessage = Config.getString("DeathMessage.Messages.Enderman");
					deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
					if (VersionUtils.isVersion("1.8")) {
						deathmessage = deathmessage.replace("%killer", entity.getName());
					}
					deathmessage = deathmessage.replace("%time", TimeUtils.getTime());
					deathmessage = deathmessage.replace("&", "§");
					if (!Config.getString("Sounds.DeathSound").equals("none")) {
						new Sounds(plugin).playSound(deader, "Sounds.DeathSound", "SoundTypes.DeathSoundType");
					}
				} else if (isEntity(entity, "ENDER_DRAGON")) {
					deathmessage = Config.getString("DeathMessage.Messages.EnderDragon");
					deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
					if (VersionUtils.isVersion("1.8")) {
						deathmessage = deathmessage.replace("%killer", entity.getName());
					}
					deathmessage = deathmessage.replace("%time", TimeUtils.getTime());
					deathmessage = deathmessage.replace("&", "§");
					if (!Config.getString("Sounds.DeathSound").equals("none")) {
						new Sounds(plugin).playSound(deader, "Sounds.DeathSound", "SoundTypes.DeathSoundType");
					}
				} else if (isEntity(entity, "CAVE_SPIDER")) {
					deathmessage = Config.getString("DeathMessage.Messages.CaveSpider");
					deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
					if (VersionUtils.isVersion("1.8")) {
						deathmessage = deathmessage.replace("%killer", entity.getName());
					}
					deathmessage = deathmessage.replace("%time", TimeUtils.getTime());
					deathmessage = deathmessage.replace("&", "§");
					if (!Config.getString("Sounds.DeathSound").equals("none")) {
						new Sounds(plugin).playSound(deader, "Sounds.DeathSound", "SoundTypes.DeathSoundType");
					}
				} else if (isEntity(entity, "IRON_GOLEM")) {
					deathmessage = Config.getString("DeathMessage.Messages.IronGolem");
					deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
					if (VersionUtils.isVersion("1.8")) {
						deathmessage = deathmessage.replace("%killer", entity.getName());
					}
					deathmessage = deathmessage.replace("%time", TimeUtils.getTime());
					deathmessage = deathmessage.replace("&", "§");
					if (!Config.getString("Sounds.DeathSound").equals("none")) {
						new Sounds(plugin).playSound(deader, "Sounds.DeathSound", "SoundTypes.DeathSoundType");
					}
				} else if (isEntity(entity, "WOLF")) {
					deathmessage = Config.getString("DeathMessage.Messages.Wolf");
					deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
					if (VersionUtils.isVersion("1.8")) {
						deathmessage = deathmessage.replace("%killer", entity.getName());
					}
					deathmessage = deathmessage.replace("%time", TimeUtils.getTime());
					deathmessage = deathmessage.replace("&", "§");
					if (!Config.getString("Sounds.DeathSound").equals("none")) {
						new Sounds(plugin).playSound(deader, "Sounds.DeathSound", "SoundTypes.DeathSoundType");
					}
				} else if (isEntity(entity, "POLAR_BEAR")) {
					deathmessage = Config.getString("DeathMessage.Messages.PolarBear");
					deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
					if (VersionUtils.isVersion("1.8")) {
						deathmessage = deathmessage.replace("%killer", entity.getName());
					}
					deathmessage = deathmessage.replace("%time", TimeUtils.getTime());
					deathmessage = deathmessage.replace("&", "§");
					if (!Config.getString("Sounds.DeathSound").equals("none")) {
						new Sounds(plugin).playSound(deader, "Sounds.DeathSound", "SoundTypes.DeathSoundType");
					}
				} else if (isEntity(entity, "GIANT")) {
					deathmessage = Config.getString("DeathMessage.Messages.Giant");
					deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
					if (VersionUtils.isVersion("1.8")) {
						deathmessage = deathmessage.replace("%killer", entity.getName());
					}
					deathmessage = deathmessage.replace("%time", TimeUtils.getTime());
					deathmessage = deathmessage.replace("&", "§");
					if (!Config.getString("Sounds.DeathSound").equals("none")) {
						new Sounds(plugin).playSound(deader, "Sounds.DeathSound", "SoundTypes.DeathSoundType");
					}
				} else if (isEntity(entity, "GUARDIAN")) {
					deathmessage = Config.getString("DeathMessage.Messages.Guardian");
					deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
					if (VersionUtils.isVersion("1.8")) {
						deathmessage = deathmessage.replace("%killer", entity.getName());
					}
					deathmessage = deathmessage.replace("%time", TimeUtils.getTime());
					deathmessage = deathmessage.replace("&", "§");
					if (!Config.getString("Sounds.DeathSound").equals("none")) {
						new Sounds(plugin).playSound(deader, "Sounds.DeathSound", "SoundTypes.DeathSoundType");
					}
				} else if (isEntity(entity, "WITHER")) {
					deathmessage = Config.getString("DeathMessage.Messages.Wither");
					deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
					if (VersionUtils.isVersion("1.8")) {
						deathmessage = deathmessage.replace("%killer", entity.getName());
					}
					deathmessage = deathmessage.replace("%time", TimeUtils.getTime());
					deathmessage = deathmessage.replace("&", "§");
					if (!Config.getString("Sounds.DeathSound").equals("none")) {
						new Sounds(plugin).playSound(deader, "Sounds.DeathSound", "SoundTypes.DeathSoundType");
					}
				} else if (isEntity(entity, "BLAZE")) {
					deathmessage = Config.getString("DeathMessage.Messages.Blaze");
					deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
					if (VersionUtils.isVersion("1.8")) {
						deathmessage = deathmessage.replace("%killer", entity.getName());
					}
					deathmessage = deathmessage.replace("%time", TimeUtils.getTime());
					deathmessage = deathmessage.replace("&", "§");
					if (!Config.getString("Sounds.DeathSound").equals("none")) {
						new Sounds(plugin).playSound(deader, "Sounds.DeathSound", "SoundTypes.DeathSoundType");
					}
				} else if (isEntity(entity, "GHAST")) {
					deathmessage = Config.getString("DeathMessage.Messages.Ghast");
					deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
					if (VersionUtils.isVersion("1.8")) {
						deathmessage = deathmessage.replace("%killer", entity.getName());
					}
					deathmessage = deathmessage.replace("%time", TimeUtils.getTime());
					deathmessage = deathmessage.replace("&", "§");
					if (!Config.getString("Sounds.DeathSound").equals("none")) {
						new Sounds(plugin).playSound(deader, "Sounds.DeathSound", "SoundTypes.DeathSoundType");
					}
				} else if (isEntity(entity, "WITCH")) {
					deathmessage = Config.getString("DeathMessage.Messages.Witch");
					deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
					if (VersionUtils.isVersion("1.8")) {
						deathmessage = deathmessage.replace("%killer", entity.getName());
					}
					deathmessage = deathmessage.replace("%time", TimeUtils.getTime());
					deathmessage = deathmessage.replace("&", "§");
					if (!Config.getString("Sounds.DeathSound").equals("none")) {
						new Sounds(plugin).playSound(deader, "Sounds.DeathSound", "SoundTypes.DeathSoundType");
					}
				} else {
					Entity killer = null;
					if (entitydamage.getDamager() instanceof Arrow && ((Arrow) entitydamage.getDamager()).getShooter() instanceof LivingEntity) {
						Arrow arrow = (Arrow) entitydamage.getDamager();
						LivingEntity livingentity = (LivingEntity) arrow.getShooter();
						killer = livingentity;
						entity = livingentity;
					} else {
						killer = entity;
					}
					if (isEntity(entity, "SKELETON") && skeletonType(entity, "NORMAL")) {
						deathmessage = Config.getString("DeathMessage.Messages.Skeleton");
						deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
						deathmessage = deathmessage.replace("%killer", killer.getName());
						deathmessage = deathmessage.replace("%time", TimeUtils.getTime());
						deathmessage = deathmessage.replace("&", "§");
						if (!Config.getString("Sounds.DeathSound").equals("none")) {
							new Sounds(plugin).playSound(deader, "Sounds.DeathSound", "SoundTypes.DeathSoundType");
						}
					}
					if (isEntity(entity, "SKELETON") && skeletonType(entity, "WITHER")) {
						deathmessage = Config.getString("DeathMessage.Messages.WitherSkeleton");
						deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
						if (VersionUtils.isVersion("1.8")) {
							deathmessage = deathmessage.replace("%killer", killer.getName());
						}
						deathmessage = deathmessage.replace("%time", TimeUtils.getTime());
						deathmessage = deathmessage.replace("&", "§");
					}
					if (isEntity(entity, "SKELETON") && skeletonType(entity, "STRAY")) {
						deathmessage = Config.getString("DeathMessage.Messages.Stray");
						deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
						if (VersionUtils.isVersion("1.8")) {
							deathmessage = deathmessage.replace("%killer", killer.getName());
						}
						deathmessage = deathmessage.replace("%time", TimeUtils.getTime());
						deathmessage = deathmessage.replace("&", "§");
						if (!Config.getString("Sounds.DeathSound").equals("none")) {
							new Sounds(plugin).playSound(deader, "Sounds.DeathSound", "SoundTypes.DeathSoundType");
						}
					}
				}
			} else {
				if (cause.equals(DamageCause.DROWNING)) {
					deathmessage = Config.getString("DeathMessage.Messages.Drowning");
					deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
					deathmessage = deathmessage.replace("%time", TimeUtils.getTime());
					deathmessage = deathmessage.replace("&", "§");
					if (!Config.getString("Sounds.DeathSound").equals("none")) {
						new Sounds(plugin).playSound(deader, "Sounds.DeathSound", "SoundTypes.DeathSoundType");
					}
				} else if (cause.equals(DamageCause.FALL)) {
					deathmessage = Config.getString("DeathMessage.Messages.Fall");
					deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
					deathmessage = deathmessage.replace("%time", TimeUtils.getTime());
					deathmessage = deathmessage.replace("&", "§");
					if (!Config.getString("Sounds.DeathSound").equals("none")) {
						new Sounds(plugin).playSound(deader, "Sounds.DeathSound", "SoundTypes.DeathSoundType");
					}
				} else if (cause.equals(DamageCause.VOID)) {
					deathmessage = Config.getString("DeathMessage.Messages.Void");
					deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
					deathmessage = deathmessage.replace("%time", TimeUtils.getTime());
					deathmessage = deathmessage.replace("&", "§");
					if (!Config.getString("Sounds.DeathSound").equals("none")) {
						new Sounds(plugin).playSound(deader, "Sounds.DeathSound", "SoundTypes.DeathSoundType");
					}
				} else if (cause.equals(DamageCause.LAVA)) {
					deathmessage = Config.getString("DeathMessage.Messages.Lava");
					deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
					deathmessage = deathmessage.replace("%time", TimeUtils.getTime());
					deathmessage = deathmessage.replace("&", "§");
					if (!Config.getString("Sounds.DeathSound").equals("none")) {
						new Sounds(plugin).playSound(deader, "Sounds.DeathSound", "SoundTypes.DeathSoundType");
					}
				} else if (cause.equals(DamageCause.MAGIC)) {
					deathmessage = Config.getString("DeathMessage.Messages.Magic");
					deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
					deathmessage = deathmessage.replace("%time", TimeUtils.getTime());
					deathmessage = deathmessage.replace("&", "§");
					if (!Config.getString("Sounds.DeathSound").equals("none")) {
						new Sounds(plugin).playSound(deader, "Sounds.DeathSound", "SoundTypes.DeathSoundType");
					}
				} else if (cause.equals(DamageCause.SUFFOCATION)) {
					deathmessage = Config.getString("DeathMessage.Messages.Suffocation");
					deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
					deathmessage = deathmessage.replace("%time", TimeUtils.getTime());
					deathmessage = deathmessage.replace("&", "§");
					if (!Config.getString("Sounds.DeathSound").equals("none")) {
						new Sounds(plugin).playSound(deader, "Sounds.DeathSound", "SoundTypes.DeathSoundType");
					}
				} else if (cause.equals(DamageCause.PROJECTILE)) {
					deathmessage = Config.getString("DeathMessage.Messages.Projectile");
					deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
					deathmessage = deathmessage.replace("%time", TimeUtils.getTime());
					deathmessage = deathmessage.replace("&", "§");
					if (!Config.getString("Sounds.DeathSound").equals("none")) {
						new Sounds(plugin).playSound(deader, "Sounds.DeathSound", "SoundTypes.DeathSoundType");
					}
				} else if (cause.equals(DamageCause.STARVATION)) {
					deathmessage = Config.getString("DeathMessage.Messages.Starvation");
					deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
					deathmessage = deathmessage.replace("%time", TimeUtils.getTime());
					deathmessage = deathmessage.replace("&", "§");
					if (!Config.getString("Sounds.DeathSound").equals("none")) {
						new Sounds(plugin).playSound(deader, "Sounds.DeathSound", "SoundTypes.DeathSoundType");
					}
				} else if (cause.equals(DamageCause.WITHER)) {
					deathmessage = Config.getString("DeathMessage.Messages.Withered");
					deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
					deathmessage = deathmessage.replace("%time", TimeUtils.getTime());
					deathmessage = deathmessage.replace("&", "§");
					if (!Config.getString("Sounds.DeathSound").equals("none")) {
						new Sounds(plugin).playSound(deader, "Sounds.DeathSound", "SoundTypes.DeathSoundType");
					}
				} else if (cause.equals(DamageCause.FIRE) || cause.equals(DamageCause.FIRE_TICK)) {
					deathmessage = Config.getString("DeathMessage.Messages.Fire");
					deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
					deathmessage = deathmessage.replace("%time", TimeUtils.getTime());
					deathmessage = deathmessage.replace("&", "§");
					if (!Config.getString("Sounds.DeathSound").equals("none")) {
						new Sounds(plugin).playSound(deader, "Sounds.DeathSound", "SoundTypes.DeathSoundType");
					}
				} else if (cause.equals(DamageCause.ENTITY_EXPLOSION) || cause.equals(DamageCause.BLOCK_EXPLOSION)) {
					deathmessage = Config.getString("DeathMessage.Messages.Explosion");
					deathmessage = deathmessage.replace("%deader", deader.getDisplayName());
					deathmessage = deathmessage.replace("%time", TimeUtils.getTime());
					deathmessage = deathmessage.replace("&", "§");
					if (!Config.getString("Sounds.DeathSound").equals("none")) {
						new Sounds(plugin).playSound(deader, "Sounds.DeathSound", "SoundTypes.DeathSoundType");
					}
				}
			}
			event.setDeathMessage(deathmessage);
		}
	}

	private boolean isEntity(Entity entity, String typename) {
		EntityType type = null;
		try {
			type = EntityType.valueOf(typename);
			if (entity.getType().equals(type)) {
				return true;
			}
		} catch(Exception e) {
			return false;
		}
		return false;
	}

	private boolean skeletonType(Entity entity, String typename) {
		SkeletonType type = null;
		try {
			type = SkeletonType.valueOf(typename);
			if (((Skeleton) entity).getSkeletonType().equals(type)) {
				return true;
			}
		} catch(Exception e) {
			return false;
		}
		return false;
	}

	private boolean zombieType(Entity entity, String typename) {
		Profession type = null;
		try {
			type = Profession.valueOf(typename);
			if (((Skeleton) entity).getSkeletonType().equals(type)) {
				return true;
			}
		} catch(Exception e) {
			return false;
		}
		return false;
	}
}
