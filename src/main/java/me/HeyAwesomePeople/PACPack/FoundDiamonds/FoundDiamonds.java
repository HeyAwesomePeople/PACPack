package me.HeyAwesomePeople.PACPack.FoundDiamonds;

import java.util.HashMap;
import java.util.UUID;

import me.HeyAwesomePeople.PACPack.PACPack;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class FoundDiamonds implements Listener {

	private PACPack plugin = PACPack.instance;

	public HashMap<UUID, Integer> streak = new HashMap<UUID, Integer>();
	public HashMap<UUID, Integer> streakTime = new HashMap<UUID, Integer>();
	public HashMap<UUID, Integer> streakTask = new HashMap<UUID, Integer>();

	public HashMap<UUID, Integer> estreak = new HashMap<UUID, Integer>();
	public HashMap<UUID, Integer> estreakTime = new HashMap<UUID, Integer>();
	public HashMap<UUID, Integer> estreakTask = new HashMap<UUID, Integer>();

	@EventHandler
	public void onMineDiamonds(BlockBreakEvent e) {
		if (plugin.config.fdEnabled == false) {
			return;
		}
		if (e.getPlayer().hasPermission(plugin.config.fdBypassPerm)) {
			return;
		}
		if (e.getBlock().getType().equals(Material.DIAMOND_ORE)) {
			final Player p = e.getPlayer();
			final UUID id = e.getPlayer().getUniqueId();
			if ((this.streak.containsKey(id)) && (this.streakTime.containsKey(id)) && (this.streakTask.containsKey(id))) {
				this.streak.put(id, Integer.valueOf(((Integer) this.streak.get(id)).intValue() + 1));
				this.streakTime.put(id, Integer.valueOf(15));
				Bukkit.getServer().getScheduler().cancelTask(((Integer) this.streakTask.get(id)).intValue());
			} else {
				this.streak.put(id, Integer.valueOf(1));
				this.streakTime.put(id, Integer.valueOf(15));
			}
			int task = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
				public void run() {
					if ((FoundDiamonds.this.streak.containsKey(id)) && (FoundDiamonds.this.streakTime.containsKey(id)) && (FoundDiamonds.this.streakTask.containsKey(id))) {
						if (((Integer) FoundDiamonds.this.streakTime.get(id)).intValue() == 0) {
							Bukkit.getServer().getScheduler().cancelTask(((Integer) FoundDiamonds.this.streakTask.get(id)).intValue());
							Bukkit.broadcastMessage(ChatColor.WHITE + p.getDisplayName() + ChatColor.AQUA + " has found " + FoundDiamonds.this.streak.get(id) + " diamonds!");
							FoundDiamonds.this.cleanUp(id);
						} else {
							FoundDiamonds.this.streakTime.put(id, Integer.valueOf(((Integer) FoundDiamonds.this.streakTime.get(id)).intValue() - 1));
						}
					}
				}
			}, 5L, 20L);

			this.streakTask.put(id, Integer.valueOf(task));
		}
		if (e.getBlock().getType().equals(Material.EMERALD_ORE)) {
			final Player p = e.getPlayer();
			final UUID id = e.getPlayer().getUniqueId();
			if ((this.estreak.containsKey(id)) && (this.estreakTime.containsKey(id)) && (this.estreakTask.containsKey(id))) {
				this.estreak.put(id, Integer.valueOf(((Integer) this.estreak.get(id)).intValue() + 1));
				this.estreakTime.put(id, Integer.valueOf(15));
				Bukkit.getServer().getScheduler().cancelTask(((Integer) this.estreakTask.get(id)).intValue());
			} else {
				this.estreak.put(id, Integer.valueOf(1));
				this.estreakTime.put(id, Integer.valueOf(15));
			}
			int task = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
				public void run() {
					if ((FoundDiamonds.this.estreak.containsKey(id)) && (FoundDiamonds.this.estreakTime.containsKey(id)) && (FoundDiamonds.this.estreakTask.containsKey(id))) {
						if (((Integer) FoundDiamonds.this.estreakTime.get(id)).intValue() == 0) {
							Bukkit.getServer().getScheduler().cancelTask(((Integer) FoundDiamonds.this.estreakTask.get(id)).intValue());
							Bukkit.broadcastMessage(ChatColor.WHITE + p.getDisplayName() + ChatColor.GREEN + " has found " + FoundDiamonds.this.estreak.get(id) + " emeralds!");
							FoundDiamonds.this.cleanUp2(id);
						} else {
							FoundDiamonds.this.estreakTime.put(id, Integer.valueOf(((Integer) FoundDiamonds.this.estreakTime.get(id)).intValue() - 1));
						}
					}
				}
			}, 5L, 20L);

			this.estreakTask.put(id, Integer.valueOf(task));
		}
	}

	public void cleanUp(UUID i) {
		this.streak.remove(i);
		this.streakTime.remove(i);
		this.streakTask.remove(i);
	}

	public void cleanUp2(UUID i) {
		this.estreak.remove(i);
		this.estreakTime.remove(i);
		this.estreakTask.remove(i);
	}

	@EventHandler
	public void playerLeave(PlayerQuitEvent e) {
		cleanUp(e.getPlayer().getUniqueId());
		cleanUp2(e.getPlayer().getUniqueId());
	}

	@EventHandler
	public void playerLeave(PlayerKickEvent e) {
		cleanUp(e.getPlayer().getUniqueId());
		cleanUp2(e.getPlayer().getUniqueId());
	}

}
