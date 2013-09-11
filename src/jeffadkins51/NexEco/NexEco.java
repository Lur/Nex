package jeffadkins51;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.Chest;
import org.bukkit.plugin.java.JavaPlugin;

public class NexEco extends JavaPlugin implements Listener {

	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
	}
	
	static File dir = new File(NexEco.class.getProtectionDomain().getCodeSource().getLocation().getPath());
	static FileInputStream fis;
	static DataInputStream dis;
	static BufferedReader br;
	
	public static boolean shopRegistered(Player p, String shopId)
	{
		boolean result=false;
		try {
			fis = new FileInputStream(dir + "/NexEco/shopdata/shopids.dat");
			dis = new DataInputStream(fis);
			br = new BufferedReader(new InputStreamReader(dis));
			String ln;
			while ((ln=br.readLine())!=null){if (ln.contains(shopId + ":" + p.getDisplayName())){ result=true; }}
			fis.close();
			dis.close();
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static void addShopId(String shopId)
	{
		
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Player p = e.getPlayer();
			Block b = e.getClickedBlock();
			
			if(b.getType() == Material.WALL_SIGN) {
				Sign sign = (Sign) b.getState();
				String[] lines = sign.getLines();
				if(lines[0].equalsIgnoreCase("[SHOP]") && !(lines[3] == null)) {
					p.sendMessage(ChatColor.GREEN + "Nex: Detected sign is a SHOP item and has a SHOP_KEY");
						
					//[SHOP]
					//Description
					//Description
					//UNIQUE_SHOP_ID
						
					if (!(shopRegistered(p,lines[3])))
					{
						p.sendMessage(ChatColor.GREEN + "NexEco: Detected Sign Shop was unregistered and has now been registered to you.");
							
					/** Shop IS registered, show the shop menu. */
					} else {
						
					}
				}
			}
		}
	}
	
}
