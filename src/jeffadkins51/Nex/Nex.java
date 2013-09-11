package jeffadkins51;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Nex extends JavaPlugin implements Listener {

	/**
	 * Format:
	 * PLAYER:BANTIME:BANNEDTIME
	 * TODO:
	 * 
	 */
	public static ArrayList<String> banList = new ArrayList<String>();
	
	public static boolean isBanned(Player p)
	{
		boolean result = false;
		for (int i = 0; i < banList.size(); i++){ if (banList.contains(p.getDisplayName())){result=true;} }
		return result;
	}
	
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent evt){
		//Simple ban check
		if (isBanned(evt.getPlayer())){evt.getPlayer().kickPlayer("You're banned from the server!");}
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent evt) {
		String[] args = evt.getMessage().split(" ");
		Player p = evt.getPlayer();
		if (args[0].startsWith("!") && args[0].contains("mb") || args[0].contains("hb") || args[0].contains("wb") && evt.getPlayer().hasPermission("Nex.10mb")){
			evt.setCancelled(true);
			String[] lnData = null;
			if (args.length >= 2)
			{
				String[] ln = args[0].split("!");
				if (args[0].contains("mb"))
				{
					lnData = ln[1].split("mb");
				} else if (args[0].contains("hb")){
					lnData = ln[1].split("hb");
				} else if (args[0].contains("wb")){
					lnData = ln[1].split("wb");
				}
				String reason = " ";
				for (int i = 2; i < args.length; i++) reason+=args[i] + " ";
				banList.add(args[1] + ":" + lnData[0]);
				p.kickPlayer(reason);
				p.sendMessage(ChatColor.GREEN + "You have successfully banned " + args[1] + " for (10) Minutes");
				Bukkit.broadcastMessage(ChatColor.RED + "Player '" + args[1] + "' has been banned for (10) Minutes by " + evt.getPlayer().getDisplayName() + ". Reason:" + reason);
			} else {
				evt.getPlayer().sendMessage("Invalid argument count, correct usage: !<ban_time><mb> username reason");
			}
		}
	}
	
}
