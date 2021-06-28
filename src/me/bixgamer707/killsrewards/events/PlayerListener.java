package me.bixgamer707.killsrewards.events;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import me.bixgamer707.killsrewards.KillsRewards;
import me.bixgamer707.killsrewards.utils.Msg;
import me.bixgamer707.killsrewards.utils.YamlFile;
import net.milkbowl.vault.economy.Economy;

public class PlayerListener implements Listener{
	private KillsRewards plugin;

	public PlayerListener(KillsRewards plugin){
		this.plugin = plugin;
	}	
	@EventHandler
	public void killPlayer(PlayerDeathEvent event) {
		Player player = event.getEntity().getKiller();
		Economy econ = plugin.getEconomy();
		YamlFile messages = new YamlFile(plugin, "messages");
		YamlFile config = new YamlFile(plugin, "config");
		List<String> hunters = config.getStringList("Bounty-Hunters");
		List<String> e = config.getStringList("enable-worlds");
		if(player == null) {
			return;
		}
		if(!player.hasPermission("killsrewards.receive")) {
			return;
		}
		if(!config.getBoolean("enable")) {
			return;
		}
		if(!(e.contains(player.getWorld().getName()) || e.contains("ALL"))) {				
			return;
		}
		if(!config.getBoolean(player.getType().name().toLowerCase()+".enable")) {				
			return;
		}
		if(Bukkit.getServer().getPluginManager().getPlugin("Vault") == null){
			return;
		}
		if(plugin.containsHunter(player.getUniqueId()) || hunters.contains(player.getName())) {					
			if(hunters.contains(event.getEntity().getName()) || plugin.containsHunter(event.getEntity().getUniqueId())) {
				econ.depositPlayer(player, config.getDouble(player.getType().name().toLowerCase()+".give-money")-3);
				int xp = player.getLevel();
				player.setLevel(xp+config.getInt(player.getType().name().toLowerCase()+".give-levels-xp")-1);
				if(config.getBoolean(player.getType().name().toLowerCase()+".message")) {
					player.sendMessage(Msg.text(plugin.name+config.getString(player.getType().name().toLowerCase()+".message-text").replaceAll("%entity%", player.getType().name().toLowerCase())));					
					player.sendMessage(Msg.text(plugin.name+messages.getString("bounty-hunter-kill-bounty-hunter")));											
				}
			}else {
				econ.depositPlayer(player, config.getDouble(player.getType().name().toLowerCase()+".give-money")+5);
				int xp = player.getLevel();
				player.setLevel(xp+config.getInt(player.getType().name().toLowerCase()+".give-levels-xp")+2);
				if(config.getBoolean(player.getType().name().toLowerCase()+".message")) {
					player.sendMessage(Msg.text(plugin.name+config.getString(player.getType().name().toLowerCase()+".message-text").replaceAll("%entity%", player.getType().name().toLowerCase())));					
					if(config.getBoolean("Players."+player.getUniqueId()+".bounty-hunter")) {
						player.sendMessage(Msg.text(plugin.name+messages.getString("start-bounty-hunter").replaceAll("%player%", player.getName())));
						config.set("Players."+player.getUniqueId()+".bounty-hunter", false);
						config.save();
					}	
				}			
			}
		}else {					
			if(hunters.contains(event.getEntity().getName())) {
				econ.depositPlayer(player, config.getDouble(player.getType().name().toLowerCase()+".give-money")+4);
				int xp = player.getLevel();
				player.setLevel(xp+config.getInt(player.getType().name().toLowerCase()+".give-levels-xp")+1);
				if(!config.getBoolean(player.getType().name().toLowerCase()+".message")) {
					return;
				}
				player.sendMessage(Msg.text(plugin.name+config.getString(player.getType().name().toLowerCase()+".message-text").replaceAll("%entity%", player.getType().name().toLowerCase())));
				player.sendMessage(Msg.text(plugin.name+messages.getString("player-kill-bounty-hunter")));			
				
			}else {
				econ.depositPlayer(player, config.getDouble(player.getType().name().toLowerCase()+".give-money"));
				int xp = player.getLevel();
				player.setLevel(xp+config.getInt(player.getType().name().toLowerCase()+".give-levels-xp"));
				if(!config.getBoolean(player.getType().name().toLowerCase()+".message")) {
					return;
				}
				player.sendMessage(Msg.text(plugin.name+config.getString(player.getType().name().toLowerCase()+".message-text").replaceAll("%entity%", player.getType().name().toLowerCase())));
				player.sendMessage(Msg.text(plugin.name+messages.getString("player-kill-bounty-hunter")));								
			}
		}
	}
}
