package bixgamer707.kr;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import bixgamer707.kr.commands.BountyHunter;
import bixgamer707.kr.commands.MainCommand;
import bixgamer707.kr.events.MobsListener;
import bixgamer707.kr.events.PlayerListener;
import bixgamer707.kr.utils.Config;
import bixgamer707.kr.utils.Messages;
import bixgamer707.kr.utils.TabBounty;
import bixgamer707.kr.utils.TabMain;
import net.milkbowl.vault.economy.Economy;

public class KillsRewards extends JavaPlugin{
	public String rutaConfig;
	PluginDescriptionFile pdffile = getDescription();
	public String version = pdffile.getVersion();
	private Economy econ = null;
	FileConfiguration c = getConfig();
	private List<UUID> hunters = new ArrayList<>();
	public String nombre = ChatColor.translateAlternateColorCodes('&', "&8&l[&c&lKills&e&lRewards&8&l] ");	
	
	public void onEnable(){
		getLogger().info(ChatColor.WHITE+" +--------------------------------------------+");
		getLogger().info(ChatColor.LIGHT_PURPLE+" Has been activated "+ChatColor.GRAY+"(version: "+ChatColor.GREEN+version+ChatColor.LIGHT_PURPLE+")");
		getLogger().info(ChatColor.AQUA+" Thanks for using my plugin! ~bixgamer707 :)!!");
		getLogger().info(ChatColor.WHITE+" +--------------------------------------------+");
		registrarCommands();
		registerEvents();
		setupEconomy();
		new Config();
		new Messages();
		this.getCommand("killsrewards").setTabCompleter(new TabMain());
		this.getCommand("bounty").setTabCompleter(new TabBounty());
	}
	
	public void onDisable(){
		getLogger().info(ChatColor.WHITE+"+--------------------------------------------+");
		getLogger().info(ChatColor.RED+" Has been deactivated");
		getLogger().info(ChatColor.YELLOW+" Thanks for using my plugin! ~bixgamer707 :)!!");
		getLogger().info(ChatColor.WHITE+"+--------------------------------------------+");
	}
	private boolean setupEconomy(){
		if(getServer().getPluginManager().getPlugin("Vault") == null){
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if(rsp == null){
			return false;
		}
		econ = rsp.getProvider();
		return econ != null;
	}
	
	public Economy getEconomy(){
		return this.econ;
	}

	
	public void registrarCommands(){
		this.getCommand("killsrewards").setExecutor(new MainCommand(this));	
		this.getCommand("bounty").setExecutor(new BountyHunter(this));	
	}
	
	public void registerEvents(){
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new MobsListener(this), this);
		pm.registerEvents(new PlayerListener(this), this);
	} 
	public boolean containsHunter(UUID uuid) {
		return hunters.contains(uuid);
	}
	public void addHunter(UUID uuid) {
		if(!containsHunter(uuid)) {
			hunters.add(uuid);
		}
	}
	public void removeHunter(UUID uuid) {
		if(containsHunter(uuid)) {
			hunters.remove(uuid);
		}
	}
	public List<UUID> getHunters(){
		return hunters;
	}
}
