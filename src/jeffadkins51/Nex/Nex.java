package jeffadkins51;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Nex extends JavaPlugin implements Listener {

	/**
	 * Format:
	 * PLAYER:BANTIME
	 */
	ArrayList<String> banList = new ArrayList<String>();
	
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent evt) {
		String[] args = evt.getMessage().split(" ");
		Player p = evt.getPlayer();
		if (args[0].equalsIgnoreCase("!10mb") && evt.getPlayer().hasPermission("Nex.10mb")){
			evt.setCancelled(true);
			if (args.length >= 2)
			{
				String[] ln = args[0].split("!");
				String[] lnData = ln[1].split("mb");
				String reason = " ";
				for (int i = 2; i < args.length; i++) reason+=args[i] + " ";
				banList.add(args[1] + ":" + lnData[0]);
				p.kickPlayer(reason);
				p.sendMessage(ChatColor.GREEN + "You have successfully banned " + args[1] + " for (10) Minutes");
				Bukkit.broadcastMessage(ChatColor.RED + "Player '" + args[1] + "' has been banned for (10) Minutes by " + evt.getPlayer().getDisplayName() + ". Reason:" + reason);
			} else {
				evt.getPlayer().sendMessage("Invalid argument count, correct usage: !10mb username reason");
			}
		}
	}
	
}
