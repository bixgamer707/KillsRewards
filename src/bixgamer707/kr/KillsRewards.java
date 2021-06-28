package bixgamer707.kr;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import bixgamer707.kr.commands.BountyHunter;
import bixgamer707.kr.commands.MainCommand;
import bixgamer707.kr.events.MessageCheckUpdater;
import bixgamer707.kr.events.MobsListener;
import bixgamer707.kr.events.PlayerJoin;
import bixgamer707.kr.events.PlayerListener;
import bixgamer707.kr.utils.YamlFile;
import me.bixgamer707.killrewards.tabcompletions.TabBounty;
import me.bixgamer707.killrewards.tabcompletions.TabMain;
import net.milkbowl.vault.economy.Economy;

public class KillsRewards extends JavaPlugin{
	public String rutaConfig;
	PluginDescriptionFile pdffile = getDescription();
	public String version = pdffile.getVersion();
	public String latestVersion;
	private Economy econ = null;
	public String getVersion() {
		return this.version;
	}
	public String latestVersion(){
		return this.latestVersion;
	}
	FileConfiguration c = getConfig();
	private List<UUID> hunters = new ArrayList<>();
	private YamlFile config;
	private YamlFile messages;
	public String name = ChatColor.translateAlternateColorCodes('&', "&8&l[&c&lKills&e&lRewards&8&l] ");	
	
	public void onEnable(){
		getLogger().info(ChatColor.WHITE+" +--------------------------------------------+");
		getLogger().info(ChatColor.LIGHT_PURPLE+" Has been activated "+ChatColor.GRAY+"(version: "+ChatColor.GREEN+version+ChatColor.LIGHT_PURPLE+")");
		getLogger().info(ChatColor.AQUA+" Thanks for using my plugin! ~bixgamer707 :)!!");
		if(Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) {
			getLogger().warning(ChatColor.translateAlternateColorCodes('&', "&cThe Vault plugin was not installed so the plugin functions are disabled"));
		}
		checkUpdate();
		getLogger().info(ChatColor.WHITE+" +--------------------------------------------+");
		registerCommands();
		registerEvents();
		setupEconomy();
		this.config = new YamlFile(this, "config.yml");
		this.messages = new YamlFile(this, "messages.yml");
		this.getCommand("killsrewards").setTabCompleter(new TabMain());
		this.getCommand("bounty").setTabCompleter(new TabBounty());
	}
	
	public void onDisable(){
		getLogger().info(ChatColor.WHITE+"+--------------------------------------------+");
		getLogger().info(ChatColor.RED+" Has been deactivated");
		getLogger().info(ChatColor.YELLOW+" Thanks for using my plugin! ~bixgamer707 :)!!");
		if(Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) {
			getLogger().warning(ChatColor.translateAlternateColorCodes('&', "&cThe Vault plugin was not installed so the plugin functions are disabled"));
		}
		checkUpdate();
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

	
	public void registerCommands(){
		this.getCommand("killsrewards").setExecutor(new MainCommand(this));	
		this.getCommand("bounty").setExecutor(new BountyHunter(this));	
	}
	
	public void registerEvents(){
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new MobsListener(this), this);
		pm.registerEvents(new PlayerListener(this), this);
		pm.registerEvents(new PlayerJoin(), this);
		pm.registerEvents(new MessageCheckUpdater(this), this);
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
	public YamlFile getConfig() {
		  return config;
		}

		public YamlFile getMessages() {
		  return messages;
		}
	public void checkUpdate() {
		try {
			  HttpURLConnection con = (HttpURLConnection) new URL("https://api.spigotmc.org/legacy/update.php?resource=93657").openConnection();
		      int timed_out = 1250;
		      con.setConnectTimeout(timed_out);	         con.setReadTimeout(timed_out);
	          latestVersion = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
	          if (latestVersion.length() <= 7) {
	        	  if(!version.equals(latestVersion)){
		        	getLogger().info(ChatColor.translateAlternateColorCodes('&', "&a&lThere is a version available of"));
		        	getLogger().info(ChatColor.translateAlternateColorCodes('&', "&e&lYou can download it at:"));
		        	getLogger().info(ChatColor.translateAlternateColorCodes('&', "&b&lhttps://www.spigotmc.org/resources/killsrewards.93657/"));
		        	  }      	  
		          }
		      } catch (Exception ex) {
		    	  getLogger().warning(ChatColor.RED +"Error while checking update.");
		      }
		}	
}
