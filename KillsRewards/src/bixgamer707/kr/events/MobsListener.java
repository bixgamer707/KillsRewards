package bixgamer707.kr.events;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import bixgamer707.kr.KillsRewards;
import bixgamer707.kr.utils.Config;
import bixgamer707.kr.utils.Msg;
import net.milkbowl.vault.economy.Economy;
public class MobsListener implements Listener{
	private KillsRewards plugin;

	public MobsListener(KillsRewards plugin){
		this.plugin = plugin;
	}
	@EventHandler
	public void killMob(EntityDeathEvent event) {
		Player player = event.getEntity().getKiller();
		EntityType entity = event.getEntityType();
		Economy econ = plugin.getEconomy();
		Config config = new Config();
		Msg text = new Msg();
		//Messages messages = new Messages();
		List<String> e = config.getStringList("enable-worlds");
		if(player != null) {
			if(player.getType().equals(EntityType.PLAYER)) {
				if(!event.getEntity().getType().equals(EntityType.PLAYER)) {
					if(player.hasPermission("killsrewards.receive") || player.isOp()) {
						if(config.getBoolean("enable")) {
							if(e.contains(player.getWorld().getName()) || e.contains("ALL")) {		
								if(config.getBoolean(entity.name().toLowerCase()+".enable")) {
									if(Bukkit.getServer().getPluginManager().getPlugin("Vault") != null){
										econ.depositPlayer(player, config.getDouble(entity.name().toLowerCase()+".give-money"));
										if(config.getBoolean(entity.name().toLowerCase()+".message")) {
											text.sendmsg(player, config.getString(entity.name().toLowerCase()+".message-text").replaceAll("%entity%", entity.name().toLowerCase()));
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
								}				
							}
						}
					}
				}
			}
		}
	}