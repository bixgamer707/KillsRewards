package bixgamer707.kr.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import bixgamer707.kr.KillsRewards;
import bixgamer707.kr.utils.Config;
import bixgamer707.kr.utils.Messages;
import bixgamer707.kr.utils.Msg;

public class MainCommand implements CommandExecutor{	
private KillsRewards plugin;
	
	public MainCommand(KillsRewards plugin){
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command comando, String label, String[] args) {
		if(!(sender instanceof Player)){
			plugin.getLogger().warning(ChatColor.RED+"You cannot run commands from the console");
			return false;
		}else{
			Player player = (Player) sender;
			Messages messages = new Messages();
			Config config = new Config();
			Msg text = new Msg();
			if(args.length > 0){
				if(args[0].equalsIgnoreCase("version")){			
					text.sendmsg(player, plugin.nombre+"&7The plugin version is &e"+plugin.version);
					return true;		
				
						}else if(args[0].equalsIgnoreCase("reload")){
							if(player.hasPermission("killsrewards.reload") || player.isOp()){		
								messages.reloadFile();
								config.reloadFile();
								text.sendmsg(player, plugin.nombre+messages.getString("reload-message"));
							}else{					
								text.sendmsg(player, plugin.nombre+messages.getString("no-permission"));
							}
						}else if(args[0].equalsIgnoreCase("help")){
							if(player.hasPermission("killsrewards.help") || player.isOp()) {
								text.sendmsg(player, "&7+------------&8&l[&c&lKills&e&lRewards&8&l]&7---------------+");
								text.sendmsg(player, "&e/kr version &7to see the plugin version.");
								text.sendmsg(player, "&e/kr reload &7to load the config.");
								text.sendmsg(player, "&e/kr rewards &7See the rewards awarded by a specific mob.");
								text.sendmsg(player, "&e/kr help &7Look at the available commands.");
								text.sendmsg(player, "&e/kr permissions &7Look at the plugin permissions.");
								text.sendmsg(player, "&e/bounty hunter &7Become a bounty hunter.");
								text.sendmsg(player, "&7+------------&8&l[&c&lKills&e&lRewards&8&l]&7---------------+");
								return true;
							}
						}else if(args[0].equalsIgnoreCase("permissions")){
							if(player.hasPermission("killsrewards.permissions") || player.isOp()) {
								text.sendmsg(player, "&7+------------&8&l[&c&lKills&e&lRewards&8&l]&7---------------+");
								text.sendmsg(player, "&e1. &7killsrewards.reload");
								text.sendmsg(player, "&e2. &7killsrewards.help");
								text.sendmsg(player, "&e3. &7killsrewards.permissions");
								text.sendmsg(player, "&e4. &7killsrewards.receive");
								text.sendmsg(player, "&e5. &7killsrewards.rewards");			
								text.sendmsg(player, "&e6. &7killsrewards.warnings");
								text.sendmsg(player, "&e7. &7killsrewards.hunter");
								text.sendmsg(player, "&7+------------&8&l[&c&lKills&e&lRewards&8&l]&7---------------+");
								return true;
							}
						}else if(args[0].equalsIgnoreCase("rewards")){
							if(player.hasPermission("killsrewards.rewards") || player.isOp()){	
								if(args.length > 1){
									String mob = args[1];		
									double value = config.getDouble(mob+".give-money");
									if(args[1].equalsIgnoreCase(mob)){
										if(config.contains(mob)) {
											text.sendmsg(player, plugin.nombre+messages.getString("reward-per-mob").replaceAll("%value%", String.valueOf(value)).replaceAll("%entity%", mob));
										}else {			
											text.sendmsg(player, plugin.nombre+messages.getString("not-exit-mob"));
										}
									}
								}else {
									text.sendmsg(player, plugin.nombre+messages.getString("use-correct-rewards"));	
								}
							}else {
								text.sendmsg(player, plugin.nombre+messages.getString("no-permission"));
							}
					}else{
						text.sendmsg(player, "&7+------------&8&l[&c&lKills&e&lRewards&8&l]&7---------------+");
						text.sendmsg(player, "&e/kr version &7to see the plugin version.");
						text.sendmsg(player, "&e/kr reload &7to load the config.");
						text.sendmsg(player, "&e/kr rewards &7See the rewards awarded by a specific mob.");
						text.sendmsg(player, "&e/kr help &7Look at the available commands.");
						text.sendmsg(player, "&e/kr permissions &7Look at the plugin permissions.");
						text.sendmsg(player, "&e/bounty hunter &7Become a bounty hunter.");
						text.sendmsg(player, "&7+------------&8&l[&c&lKills&e&lRewards&8&l]&7---------------+");
						return true;			
					}
				}else{
					text.sendmsg(player, plugin.nombre+messages.getString("command-help"));
					return true;	
				}			
			}
				return true;			
	}	
}
