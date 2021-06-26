package bixgamer707.kr.events;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import bixgamer707.kr.KillsRewards;
import bixgamer707.kr.utils.Config;
import bixgamer707.kr.utils.Messages;
import bixgamer707.kr.utils.Msg;
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
		Config config = new Config();
		Msg text = new Msg();
		List<String> hunters = config.getStringList("Bounty-Hunters");
		Messages messages = new Messages();
		List<String> e = config.getStringList("enable-worlds");
		if(player != null && player.getType().equals(EntityType.PLAYER)) {
			if(player.hasPermission("killsrewards.receive") || player.isOp()) {
				if(config.getBoolean("enable")) {
					if(e.contains(player.getWorld().getName()) || e.contains("ALL")) {		
						if(config.getBoolean(player.getType().name().toLowerCase()+".enable")) {
							if(plugin.containsHunter(player.getUniqueId()) || hunters.contains(player.getName())) {
								if(hunters.contains(event.getEntity().getName()) || plugin.containsHunter(event.getEntity().getUniqueId())) {
									if(Bukkit.getServer().getPluginManager().getPlugin("Vault") != null){
										econ.depositPlayer(player, config.getDouble(player.getType().name().toLowerCase()+".give-money")-3);
										int xp = player.getLevel();
										player.setLevel(xp+config.getInt(player.getType().name().toLowerCase()+".give-levels-xp")-1);
										if(config.getBoolean(player.getType().name().toLowerCase()+".message")) {
											text.sendmsg(player, plugin.nombre+config.getString(player.getType().name().toLowerCase()+".message-text").replaceAll("%entity%", player.getType().name().toLowerCase()));					
											text.sendmsg(player, plugin.nombre+messages.getString("bounty-hunter-kill-bounty-hunter"));											
										}
								}else {
									for(Player players : Bukkit.getOnlinePlayers()) {
										plugin.getLogger().warning(ChatColor.translateAlternateColorCodes('&', "&cThe Vault plugin was not installed so the plugin functions are disabled"));
										if(players.hasPermission("killsrewards.warnings")) {
											text.sendmsg(players, plugin.nombre+"&cThe Vault plugin was not installed so the plugin functions are disabled");
										}							
									}
								}
								}else {
									if(Bukkit.getServer().getPluginManager().getPlugin("Vault") != null){
										econ.depositPlayer(player, config.getDouble(player.getType().name().toLowerCase()+".give-money")+5);
										int xp = player.getLevel();
										player.setLevel(xp+config.getInt(player.getType().name().toLowerCase()+".give-levels-xp")+2);
										if(config.getBoolean(player.getType().name().toLowerCase()+".message")) {
											text.sendmsg(player, plugin.nombre+config.getString(player.getType().name().toLowerCase()+".message-text").replaceAll("%entity%", player.getType().name().toLowerCase()));					
											for(Player players : Bukkit.getOnlinePlayers()) {
												if(config.getBoolean("Players."+player.getUniqueId()+".bounty-hunter")) {
													text.sendmsg(players, plugin.nombre+messages.getString("start-bounty-hunter").replaceAll("%player%", player.getName()));
													config.set("Players."+player.getUniqueId()+".bounty-hunter", false);
													config.saveFile();
												}	
											}
										}
								}else {
									for(Player players : Bukkit.getOnlinePlayers()) {
										plugin.getLogger().warning(ChatColor.translateAlternateColorCodes('&', "&cThe Vault plugin was not installed so the plugin functions are disabled"));
										if(players.hasPermission("killsrewards.warnings")) {
											text.sendmsg(players, plugin.nombre+"&cThe Vault plugin was not installed so the plugin functions are disabled");
										}							
									}
								}
								}
								}else {
									if(hunters.contains(event.getEntity().getName())) {
										econ.depositPlayer(player, config.getDouble(player.getType().name().toLowerCase()+".give-money")+4);
										int xp = player.getLevel();
										player.setLevel(xp+config.getInt(player.getType().name().toLowerCase()+".give-levels-xp")+1);
										if(config.getBoolean(player.getType().name().toLowerCase()+".message")) {
											text.sendmsg(player, plugin.nombre+config.getString(player.getType().name().toLowerCase()+".message-text").replaceAll("%entity%", player.getType().name().toLowerCase()));
											text.sendmsg(player, plugin.nombre+messages.getString("player-kill-bounty-hunter"));					
									}	
								}
							}		
						}			
					}
				}
			}
		}
	}
}
