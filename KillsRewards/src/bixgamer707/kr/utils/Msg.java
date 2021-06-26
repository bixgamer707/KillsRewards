package bixgamer707.kr.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;


public class Msg {
	public void sendmsg(Player player, String text) {
	     player.sendMessage(ChatColor.translateAlternateColorCodes('&', text));
	   }
}
