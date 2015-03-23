package me.HeyAwesomePeople.PACPack.LagHelper;

import me.HeyAwesomePeople.PACPack.PACPack;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R2.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R2.PacketPlayOutChat;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class LagHelper implements Listener {
	private PACPack plugin = PACPack.instance;

	@EventHandler
	public void mobSpawnEvent(CreatureSpawnEvent e) {
		Entity[] tE = e.getEntity().getWorld().getChunkAt(e.getLocation()).getEntities();
		int animals = 0;
		for (Entity en : tE) {
			if (en instanceof LivingEntity) {
				if (plugin.config.checkAnimals.contains(en.getType().toString())) {
					animals++;
				}
			}
		}

		if (animals > plugin.config.entitiesPerChunk) {
			e.setCancelled(true);
		}
	}

	public void packetChat(Player player, String words, String hover) {
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutChat(ChatSerializer.a("{\"text\":\"\",\"extra\":[{\"text\":\"" + words + colors(" &5&kI") + "\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"" + hover + "\"}}]}")));
	}

	public String colors(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}

	public boolean onCommand(final CommandSender d, Command cmd,
			String commandLabel, final String[] args) {
		if (commandLabel.equalsIgnoreCase("laghelp") || commandLabel.equalsIgnoreCase("lh")) {
			if (!d.hasPermission("laghelper.cmd")) return false;
			if (args.length == 0) {
				if (d instanceof Player) {
					d.sendMessage(ChatColor.BLUE + "[LagHelper] Commands - Hover for info");
					packetChat((Player) d, ChatColor.DARK_AQUA + "/lh check", ChatColor.DARK_PURPLE + "Makes sure chunks have no more than 16 mobs");
					return false;
				}
				d.sendMessage(ChatColor.BLUE + "[LagHelper] Commands");
				d.sendMessage(ChatColor.DARK_AQUA + "/lh check - Makes sure chunks have no more than 16 mobs.");
			} else if (args.length > 0) {
				if (args[0].equalsIgnoreCase("check")) {
					d.sendMessage(ChatColor.BLUE + "[LagHelper] Enforced 16 farm animals per chunk. Total animals removed: " + enforceRule());
				}
			}
		}
		return false;
	}

	public int enforceRule() {
		int animals = 0;
		int totalDeleted = 0;
		for (World s : Bukkit.getWorlds()) {
			for (Chunk c : s.getLoadedChunks()) {
				for (Entity en : c.getEntities()) {
					if (en instanceof LivingEntity) {
						if (plugin.config.checkAnimals.contains(en.getType().toString())) {
							animals++;
						}
					}
				}
				if (animals > plugin.config.entitiesPerChunk) {
					for (Entity en : c.getEntities()) {
						if (en instanceof LivingEntity) {
							if (plugin.config.checkAnimals.contains(en.getType().toString())) {
								en.remove();
								animals--;
								totalDeleted++;
								if (animals > plugin.config.entitiesPerChunk) {
									continue;
								} else {
									break;
								}
							}
						}
					}
				} else {
					animals = 0;
				}
			}
		}
		return totalDeleted;
	}
}
