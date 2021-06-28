package bixgamer707.kr.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import bixgamer707.kr.KillsRewards;
import bixgamer707.kr.utils.Msg;
import bixgamer707.kr.utils.YamlFile;


public class BountyHunter implements CommandExecutor {
private KillsRewards plugin;
	
	public BountyHunter(KillsRewards plugin){
		this.plugin = plugin;
	}
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)){
			plugin.getLogger().warning(ChatColor.RED+"You cannot run commands from the console");
			return false;
		}else{
			Player player = (Player) sender;
			YamlFile messages = new YamlFile(plugin, "messages");
			YamlFile config = new YamlFile(plugin, "config");
			List<String> hunters = config.getStringList("Bounty-Hunters");
			if(args.length > 0){
				if(args[0].equalsIgnoreCase("hunter")){			
					if(player.hasPermission("killsrewards.hunter")){	
						if(plugin.containsHunter(player.getUniqueId()) || hunters.contains(player.getName())) {
							player.sendMessage(Msg.text(plugin.name+messages.getString("already-bounty-hunter")));
							return true;
						}else {
							plugin.addHunter(player.getUniqueId());
							for(Player players : Bukkit.getOnlinePlayers()) {
								player.sendMessage(Msg.text(plugin.name+messages.getString("become-bounty-hunter")));
								players.sendMessage(Msg.text(plugin.name+messages.getString("player-new-bounty-hunter-broadcast").replaceAll("%player%", player.getName())));
								config.set("Players."+player.getUniqueId()+".bounty-hunter", true);
								hunters.add(player.getName());							
								config.save();
								return true;
							}	
						}	
					}
				}			
			}
		}
		return true;
	}
}