package me.bixgamer707.killsrewards.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.bixgamer707.killsrewards.KillsRewards;
import me.bixgamer707.killsrewards.utils.Msg;
import me.bixgamer707.killsrewards.utils.YamlFile;

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
			YamlFile messages = new YamlFile(plugin, "messages");
			YamlFile config = new YamlFile(plugin, "config");
			if(args.length > 0){
				if(args[0].equalsIgnoreCase("version")){			
					player.sendMessage(Msg.text(plugin.name+"&7The plugin version is &e"+plugin.version));
					return true;		
				
						}else if(args[0].equalsIgnoreCase("reload")){
							if(player.hasPermission("killsrewards.reload")){		
								messages.reload();
								config.reload();
								player.sendMessage(Msg.text(plugin.name+messages.getString("reload-message")));
							}else{					
								player.sendMessage(Msg.text(plugin.name+messages.getString("no-permission")));
							}
						}else if(args[0].equalsIgnoreCase("help")){
							if(player.hasPermission("killsrewards.help")) {
								player.sendMessage(Msg.text("&7+------------&8&l[&c&lKills&e&lRewards&8&l]&7---------------+"));
								player.sendMessage(Msg.text("&e/kr version &7to see the plugin version."));
								player.sendMessage(Msg.text("&e/kr reload &7to load the config."));
								player.sendMessage(Msg.text("&e/kr rewards &7See the rewards awarded by a specific mob."));
								player.sendMessage(Msg.text("&e/kr help &7Look at the available commands."));
								player.sendMessage(Msg.text("&e/kr permissions &7Look at the plugin permissions."));
								player.sendMessage(Msg.text("&e/bounty hunter &7Become a bounty hunter."));
								player.sendMessage(Msg.text("&7+------------&8&l[&c&lKills&e&lRewards&8&l]&7---------------+"));
								return true;
							}
						}else if(args[0].equalsIgnoreCase("permissions")){
							if(player.hasPermission("killsrewards.permissions")) {
								player.sendMessage(Msg.text("&7+------------&8&l[&c&lKills&e&lRewards&8&l]&7---------------+"));
								player.sendMessage(Msg.text("&e1. &7killsrewards.reload"));
								player.sendMessage(Msg.text("&e2. &7killsrewards.help"));
								player.sendMessage(Msg.text("&e3. &7killsrewards.permissions"));
								player.sendMessage(Msg.text("&e4. &7killsrewards.receive"));
								player.sendMessage(Msg.text("&e5. &7killsrewards.rewards"));			
								player.sendMessage(Msg.text("&e6. &7killsrewards.warnings"));
								player.sendMessage(Msg.text("&e7. &7killsrewards.hunter"));
								player.sendMessage(Msg.text("&7+------------&8&l[&c&lKills&e&lRewards&8&l]&7---------------+"));
								return true;
							}
						}else if(args[0].equalsIgnoreCase("rewards")){
							if(player.hasPermission("killsrewards.rewards")){	
								if(args.length > 1){
									String mob = args[1];		
									double value = config.getDouble(mob+".give-money");
									if(args[1].equalsIgnoreCase(mob)){
										if(config.contains(mob)) {
											player.sendMessage(Msg.text(plugin.name+messages.getString("reward-per-mob").replaceAll("%value%", String.valueOf(value)).replaceAll("%entity%", mob)));
										}else {			
											player.sendMessage(Msg.text(plugin.name+messages.getString("not-exit-mob")));
										}
									}
								}else {
									player.sendMessage(Msg.text(plugin.name+messages.getString("use-correct-rewards")));	
								}
							}else {
								player.sendMessage(Msg.text(plugin.name+messages.getString("no-permission")));
							}
					}else{
						player.sendMessage(Msg.text("&7+------------&8&l[&c&lKills&e&lRewards&8&l]&7---------------+"));
						player.sendMessage(Msg.text("&e/kr version &7to see the plugin version."));
						player.sendMessage(Msg.text("&e/kr reload &7to load the config."));
						player.sendMessage(Msg.text("&e/kr rewards &7See the rewards awarded by a specific mob."));
						player.sendMessage(Msg.text("&e/kr help &7Look at the available commands."));
						player.sendMessage(Msg.text("&e/kr permissions &7Look at the plugin permissions."));
						player.sendMessage(Msg.text("&e/bounty hunter &7Become a bounty hunter."));
						player.sendMessage(Msg.text("&7+------------&8&l[&c&lKills&e&lRewards&8&l]&7---------------+"));
						return true;			
					}
				}else{
					player.sendMessage(Msg.text(plugin.name+messages.getString("command-help")));
					return true;	
				}			
			}
				return true;			
	}	
}
