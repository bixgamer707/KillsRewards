package me.bixgamer707.killsrewards.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.bixgamer707.killsrewards.KillsRewards;



public class MessageCheckUpdater implements Listener{
private KillsRewards plugin;
	
	public MessageCheckUpdater(KillsRewards plugin){
		this.plugin = plugin;
	}
	@EventHandler
	public void joinChecker(PlayerJoinEvent event){
		Player player = event.getPlayer();
		if(player.isOp() && !(plugin.getVersion().equals(plugin.latestVersion))){
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l---------------"+plugin.name+"&c&l---------------"));
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aThere is a version available of"));
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Your version is &e" + plugin.latestVersion));
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7and the new one is "+plugin.version));
  		  	player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eYou can download it at:"));
  		  	player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bhttps://www.spigotmc.org/resources/killsrewards.93657/"));
  		    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l---------------"+plugin.name+"&c&l---------------"));
		}
	}
}
