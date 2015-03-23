package me.HeyAwesomePeople.PACPack.SpawnerChange;

import me.HeyAwesomePeople.PACPack.PACPack;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Event.Result;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.SpawnEgg;

public class SpawnerChangerBlocker implements Listener{

	private PACPack plugin = PACPack.instance;
	
	@EventHandler
	public void playerAttemptChangeSpawner(PlayerInteractEvent e) {
		Bukkit.broadcastMessage("1");
		if (!plugin.config.scEnabled) {
			Bukkit.broadcastMessage("2");
			return;
		}
		Bukkit.broadcastMessage("3");
		if (e.getPlayer().hasPermission(plugin.config.scBypassPerm)) {
			Bukkit.broadcastMessage("4");
			return;
		}
		Bukkit.broadcastMessage("5");
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Bukkit.broadcastMessage("6");
			if (e.getItem() == null) return;
			Bukkit.broadcastMessage("7");
			if (e.getItem().getType() == null) return;
			Bukkit.broadcastMessage("8");
			Material m = e.getItem().getType();
			if ((m.equals(Material.MONSTER_EGG)) && (e.getClickedBlock().getType().equals(Material.MOB_SPAWNER))) {
				Bukkit.broadcastMessage("9");
				SpawnEgg se = (SpawnEgg) e.getPlayer().getItemInHand().getData();
				if (e.getPlayer().hasPermission(plugin.config.scChangingPerm.replace("%eggname%", se.getSpawnedType().toString().toLowerCase()))) {
					Bukkit.broadcastMessage("10");
					e.setCancelled(false);
					e.setUseInteractedBlock(Result.ALLOW);
					e.setUseItemInHand(Result.ALLOW);
					return;
				}
				Bukkit.broadcastMessage("11");
				e.setCancelled(true);
				e.setUseInteractedBlock(Result.DENY);
				e.setUseItemInHand(Result.DENY);
				return;
			}
		}
	}
	
}
