package bixgamer707.kr.events;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import bixgamer707.kr.KillsRewards;
import bixgamer707.kr.utils.Msg;
import bixgamer707.kr.utils.YamlFile;
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
		YamlFile config = new YamlFile(plugin, "config");
		List<String> e = config.getStringList("enable-worlds");
		if(player == null) {
			return;
		}
		if(event.getEntity().getType().equals(EntityType.PLAYER)) {
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
		if(!config.getBoolean(entity.name().toLowerCase()+".enable")) {						
			return;
		}
		if(Bukkit.getServer().getPluginManager().getPlugin("Vault") == null){										
			return;
		}
		econ.depositPlayer(player, config.getDouble(entity.name().toLowerCase()+".give-money"));
		if(config.getBoolean(entity.name().toLowerCase()+".message")) {
			player.sendMessage(Msg.text(config.getString(entity.name().toLowerCase()+".message-text").replaceAll("%entity%", entity.name().toLowerCase())));
		}
	}
}			