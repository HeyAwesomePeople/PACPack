package me.HeyAwesomePeople.PACPack;

import me.HeyAwesomePeople.PACPack.BlockControl.PlacingLimiter;
import me.HeyAwesomePeople.PACPack.FoundDiamonds.FoundDiamonds;
import me.HeyAwesomePeople.PACPack.LagHelper.LagHelper;
import me.HeyAwesomePeople.PACPack.ScreenWords.ScreenWords;
import me.HeyAwesomePeople.PACPack.SpawnerChange.SpawnerChangerBlocker;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.material.SpawnEgg;
import org.bukkit.plugin.java.JavaPlugin;

public class PACPack extends JavaPlugin implements Listener, CommandExecutor {

	public static PACPack instance;

	public Config config;
	public LagHelper laghelp;

	@Override
	public void onEnable() {
		instance = this;
		getServer().getPluginManager().registerEvents(this, this);

		getServer().getPluginManager().registerEvents(new ScreenWords(), this);
		getServer().getPluginManager().registerEvents(new FoundDiamonds(), this);
		getServer().getPluginManager().registerEvents(new SpawnerChangerBlocker(), this);
		getServer().getPluginManager().registerEvents(new LagHelper(), this);
		getServer().getPluginManager().registerEvents(new PlacingLimiter(), this);
		getServer().getPluginManager().registerEvents(new DefaultListener(), this);

		config = new Config();
		laghelp = new LagHelper();
	}

	@Override
	public void onDisable() {
		reloadConfig();
	}

	public boolean onCommand(final CommandSender d, Command cmd,
			String commandLabel, final String[] args) {
		if (commandLabel.equalsIgnoreCase("pac")) {
			if (!d.hasPermission("pacpack.admin")) {
				return false;
			}
			if (args.length == 0) {
				d.sendMessage(ChatColor.RED + "==== PACPack Cmds ====");
				d.sendMessage(ChatColor.DARK_AQUA + "/pac reload - Reload config");
				d.sendMessage(ChatColor.DARK_AQUA + "/pac item - Get the item in hand");
				d.sendMessage(ChatColor.DARK_AQUA + "/pac enforce - Enforce the entity limiter");
			} else {
				if (args[0].equalsIgnoreCase("reload")) {
					reloadConfig();
					config = new Config();
					d.sendMessage(ChatColor.BLUE + "[PAC] PACPack reloaded!");
					return false;
				}
				if (args[0].equalsIgnoreCase("item")) {
					if ((d instanceof Player)) {
						d.sendMessage(ChatColor.BLUE + "Item in hand: " + ((Player) d).getItemInHand().getType().toString());
						if (((Player) d).getItemInHand().getType().equals(Material.MONSTER_EGG)) {
							SpawnEgg s = (SpawnEgg) ((Player) d).getItemInHand().getData();
							d.sendMessage(ChatColor.BLUE + "Egg Type: " + s.getSpawnedType().toString());
						}
					} else {
						d.sendMessage(ChatColor.RED + "Cannot execute command in console!");
					}
				}
				if (args[0].equalsIgnoreCase("enforce")) {
					d.sendMessage(ChatColor.BLUE + "[LagHelper] Enforced " + config.entitiesPerChunk + " animals per chunk. Total animals removed: " + laghelp.enforceRule());
					return false;
				}
			}
		}
		if (commandLabel.equalsIgnoreCase("reloadmsg")) {
			d.sendMessage(ChatColor.RED + "HAP: No, you may not reload! Restart the server instead.");
			return false;
		}
		return false;
	}

}
