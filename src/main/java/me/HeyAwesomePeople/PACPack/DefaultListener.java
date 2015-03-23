package me.HeyAwesomePeople.PACPack;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

public class DefaultListener implements Listener {

	@EventHandler
	public void consoleCommand(ServerCommandEvent e) {
		if (e.getCommand().equals("reload")) {
			e.setCommand("reloadmsg");
		}
	}
	
	@EventHandler
	public void playerCommand(PlayerCommandPreprocessEvent e) {
		if (e.getMessage().equals("/reload")) {
			e.setCancelled(true);
			e.getPlayer().performCommand("reloadmsg");
		}
	}
	
}
