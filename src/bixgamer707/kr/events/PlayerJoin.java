package bixgamer707.kr.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import bixgamer707.kr.utils.Msg;

public class PlayerJoin implements Listener{
	
	@EventHandler
	public void checkVault(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if(Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) {
			if(!player.hasPermission("killsrewards.warnings")) {
					return;
			}
			player.sendMessage(Msg.text("&cThe Vault plugin was not installed so the plugin functions are disabled"));					
		}	
	}
}
