package me.HeyAwesomePeople.PACPack.ScreenWords;

import me.HeyAwesomePeople.PACPack.PACPack;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ScreenWords implements Listener {
	private PACPack plugin = PACPack.instance;

	private String parseMessage(String s, PlayerJoinEvent e) {
		return ChatColor.translateAlternateColorCodes('&', s.replace("%player%", e.getPlayer().getDisplayName()));
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		if (e.getPlayer().hasPlayedBefore() && plugin.config.returning) {
			ScreenManager.sendTitle(e.getPlayer(), parseMessage(plugin.config.rMsg, e), parseMessage(plugin.config.rSubMsg, e), plugin.config.rFadeIn * 20, plugin.config.rStayOn * 20, plugin.config.rFadeOut * 20);
			return;
		}
		if (!e.getPlayer().hasPlayedBefore() && plugin.config.newplayer) {
			ScreenManager.sendTitle(e.getPlayer(), parseMessage(plugin.config.npMsg, e), parseMessage(plugin.config.npSubMsg, e), plugin.config.npFadeIn * 20, plugin.config.npStayOn * 20, plugin.config.npFadeOut * 20);
			return;
		}

	}

}
