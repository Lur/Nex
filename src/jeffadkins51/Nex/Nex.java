package jeffadkins51;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
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

	public static String website;
	
	/**
	 * Format:
	 * PLAYER:BANTIME:TIMEOFBAN
	 */
	//public static ArrayList<String> banList = new ArrayList<String>();
	public static File dir;
	
	public static boolean isBanned(Player p) {
		return Boolean.parseBoolean(sendQueryWithResult(new String[]{"s_key:1698316","is_banned:" + p.getDisplayName()}));
	}
	
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
		String path = NexEco.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		String decodedPath = null;
		try {
			decodedPath = URLDecoder.decode(path, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		dir = new File(decodedPath);
		//NexSQL.init();
	}
	
	public static void loadConfig()
	{
		
		//if(ln.contains('website = ')){}
		
	}
	
	public static String sendQueryWithResult(String args[]){
		String result=null;
		BufferedReader bufferedReader;
		String ln = args[0] + "&";
		String lnData[] = null;
		int counter;
		try {
			for (int i = 0; i < args.length; i++){ 
				counter = args.length - i;
				if(!(i == 0)) lnData = args[i].split(":"); ln += lnData[0] + "=" + lnData[1];
				if (!(counter == 1)){ ln += "&"; }
			}
			bufferedReader = new BufferedReader(new InputStreamReader(new URL(website + ln).openConnection().getInputStream()));
			String line = null;
			if ((line = bufferedReader.readLine()) != null){
				result = line;
			}
			bufferedReader.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	public static void sendQuery(String args[]){
		BufferedReader bufferedReader;
		String ln = args[0] + "&";
		String lnData[] = null;
		int counter;
		try {
			for (int i = 0; i < args.length; i++){ 
				counter = args.length - i;
				if(!(i == 0)) lnData = args[i].split(":"); ln += lnData[0] + "=" + lnData[1];
				if (!(counter == 1)){ ln += "&"; }
			}
			bufferedReader = new BufferedReader(new InputStreamReader(new URL(website + ln).openConnection().getInputStream()));
			bufferedReader.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent evt){
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
				sendQuery(new String[]{""});
				//banList.add(args[1] + ":" + lnData[0]);
				p.kickPlayer(reason);
				p.sendMessage(ChatColor.GREEN + "You have successfully banned " + args[1] + " for (10) Minutes");
				Bukkit.broadcastMessage(ChatColor.RED + "Player '" + args[1] + "' has been banned for (10) Minutes by " + evt.getPlayer().getDisplayName() + ". Reason:" + reason);
			} else {
				evt.getPlayer().sendMessage("Invalid argument count, correct usage: !<ban_time><mb> username reason");
			}
		}
	}
	
}
