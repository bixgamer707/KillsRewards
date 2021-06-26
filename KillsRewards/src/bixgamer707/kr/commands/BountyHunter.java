package bixgamer707.kr.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import bixgamer707.kr.KillsRewards;
import bixgamer707.kr.utils.Config;
import bixgamer707.kr.utils.Messages;
import bixgamer707.kr.utils.Msg;


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
			Config config = new Config();
			Messages messages = new Messages();
			List<String> hunters = config.getStringList("Bounty-Hunters");
			Msg text = new Msg();
			if(args.length > 0){
				if(args[0].equalsIgnoreCase("hunter")){			
					if(player.hasPermission("killsrewards.hunter") || player.isOp()){	
						if(plugin.containsHunter(player.getUniqueId()) || hunters.contains(player.getName())) {
							text.sendmsg(player, plugin.nombre+messages.getString("already-bounty-hunter"));
							return true;
						}else {
							plugin.addHunter(player.getUniqueId());
							for(Player players : Bukkit.getOnlinePlayers()) {
								text.sendmsg(player, plugin.nombre+messages.getString("become-bounty-hunter"));
								text.sendmsg(players, plugin.nombre+messages.getString("player-new-bounty-hunter-broadcast").replaceAll("%player%", player.getName()));
								config.set("Players."+player.getUniqueId()+".bounty-hunter", true);
								hunters.add(player.getName());							
								config.saveFile();
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