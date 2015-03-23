package me.HeyAwesomePeople.PACPack;

import java.io.File;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

public class Config {

	private PACPack plugin = PACPack.instance;
	private FileConfiguration config = plugin.getConfig();
	
	// Placing limiter
	public Boolean placinglimiter;
	public List<String> plBlockBlacklist;
	
	// Messages
	public Boolean returning;
	public String rMsg;
	public String rSubMsg;
	public Integer rStayOn;
	public Integer rFadeIn;
	public Integer rFadeOut;
	public Boolean newplayer;
	public String npMsg;
	public String npSubMsg;
	public Integer npStayOn;
	public Integer npFadeIn;
	public Integer npFadeOut;
	
	// Found Diamonds
	public Boolean fdEnabled;
	public String fdBypassPerm;
	
	// Spawner Changing
	public Boolean scEnabled;
	public String scChangingPerm;
	public String scBypassPerm;
	
	// Entity Limiter
	public Boolean limitEntity;
	public Integer entitiesPerChunk;
	public List<String> checkAnimals;
	
	public String mapChangeMessage;
	
	public Config() {
		if (!new File(plugin.getDataFolder() + File.separator + "config.yml").exists()) {
			plugin.saveDefaultConfig();
		}
		
		placinglimiter = config.getBoolean("placinglimiter.enabled");
		plBlockBlacklist = config.getStringList("placinglimiter.blockBlacklist");
		
		returning = config.getBoolean("message.returning.enabled");
		rMsg = config.getString("message.returning.message");
		rSubMsg = config.getString("message.returning.submessage");
		rStayOn = config.getInt("message.returning.stayOn");
		rFadeIn = config.getInt("message.returning.fadeIn");
		rFadeOut = config.getInt("message.returning.fadeOut");
		
		newplayer = config.getBoolean("message.newplayer.enabled");
		npMsg = config.getString("message.newplayer.message");
		npSubMsg = config.getString("message.newplayer.submessage");
		npStayOn = config.getInt("message.newplayer.stayOn");
		npFadeIn = config.getInt("message.newplayer.fadeIn");
		npFadeOut = config.getInt("message.newplayer.fadeOut");
		
		fdEnabled = config.getBoolean("founddiamonds.enabled");
		fdBypassPerm = config.getString("founddiamonds.bypassPermission");
		
		scEnabled = config.getBoolean("spawnerchanging.enabled");
		scChangingPerm = config.getString("spawnerchanging.chaningPermission");
		scBypassPerm = config.getString("spawnerchanging.bypassPermission");
		
		limitEntity = config.getBoolean("entitylimiter.enabled");
		entitiesPerChunk = config.getInt("entitylimiter.maxEntitiesPerChunk");
		checkAnimals = config.getStringList("entitylimiter.checkAnimals");
	}
	
}
