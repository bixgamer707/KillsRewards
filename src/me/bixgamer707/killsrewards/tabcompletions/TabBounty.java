package me.bixgamer707.killsrewards.tabcompletions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

public class TabBounty implements TabCompleter {
	@Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> completions = new ArrayList<>();
        List<String> commands = new ArrayList<>();	
	        if (args.length == 1) {
	                commands.add("hunter");
	            StringUtil.copyPartialMatches(args[0], commands, completions);
	        }
	        Collections.sort(completions);
	        return completions;
	}
}