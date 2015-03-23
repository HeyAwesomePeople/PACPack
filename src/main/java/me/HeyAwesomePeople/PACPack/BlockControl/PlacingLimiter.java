package me.HeyAwesomePeople.PACPack.BlockControl;

import me.HeyAwesomePeople.PACPack.PACPack;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlacingLimiter implements Listener {
	private PACPack plugin = PACPack.instance;
	
	@EventHandler
	public void playerPlaceBlock(BlockPlaceEvent e) {
		if (!plugin.config.placinglimiter) {
			return;
		}
		if (doesListContainBlock(e.getBlock().getType().toString())) {
			e.getPlayer().sendMessage(getMessage(e.getBlock().getType().toString()));
			e.setCancelled(true);
			return;
		}
	}
	
	public String getMessage(String b) {
		for (String s : plugin.config.plBlockBlacklist) {
			if (s.split(":")[0].equals(b)) {
				return ChatColor.translateAlternateColorCodes('&', s.split(":")[1]);
			}
			continue;
		}
		return ChatColor.RED + "You cannot place this block!";
	}
	
	public boolean doesListContainBlock(String b) {
		for (String s : plugin.config.plBlockBlacklist) {
			if (s.split(":")[0].equals(b)) {
				return true;
			}
			continue;
		}
		return false;
	}
	
}
