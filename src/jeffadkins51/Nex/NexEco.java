package jeffadkins51;
import java.io.BufferedReader;
import java.io.DataInputStream;
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
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.ISqlJetCursor;
import org.tmatesoft.sqljet.core.table.ISqlJetTable;

public class NexEco extends JavaPlugin implements Listener {
	
	static FileInputStream fis;
	static DataInputStream dis;
	static BufferedReader br;
	static ISqlJetTable table;
	
	private static int cashInInventory;

	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
	}
	
	public static int getItemPrice(Material mat){
		int result = 0;
		try{
			fis = new FileInputStream(Nex.dir + "eco\\item_values.ini");
			dis = new DataInputStream(fis);
			br = new BufferedReader(new InputStreamReader(dis));
			String[] ln;
			while (br.readLine() != null)
			{
				if (br.readLine().contains(mat.toString())) {
					ln = br.readLine().split(":");
					/** TODO: Take into account Supply & Demand */
					result = Integer.parseInt(ln[1]);
				}
			}
		} catch (IOException ioe) { System.err.println("Error: Could not read item values!"); }
		return result;
	}
	public static int getTaxPrice(Material mat){
		
		return 0;
	}
	
	public static int getCash(Player p)
	{
		int result=0;
		
		//TODO: Select the player from the users DB and get the amount of cash
		
		return result;
	}

	public static void setCash(Player p, int amt){ cashInInventory = amt; }
	public static void setBankCash(Player p, int amt)
	{
		Nex.sendQuery(new String[]{"s_key:" + Nex.s_key, "set:cash"});
		//TODO: Update player in SQLite db
	}
	
	public static boolean shopRegistered(Player p, String shopId)
	{
		boolean result=false;
		try{
			fis = new FileInputStream(Nex.dir + "/Nex/shopdata/shops.dat");
			dis = new DataInputStream(fis);
			br = new BufferedReader(new InputStreamReader(dis));
			String ln;
			while ((ln=br.readLine())!=null){
				if (ln.contains(shopId + ":" + p.getDisplayName())) result = true;
			}
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

	public static void purchaseItem(Player p, Material mat, int shopId, int amt)
	{
		if (cashInInventory >= getItemPrice(mat) + getTaxPrice(mat))
		Stocks.demand[mat.getId()] = Stocks.demand[mat.getId()] + amt;
		p.sendMessage(ChatColor.GREEN + "You have purchased " + amt + " of " + mat.toString() + ".");
	}
	
	public static void sellItem(Player p, Material item, int shopId, int amt)
	{
		Stocks.supply[item.getId()] = Stocks.supply[item.getId()] * amt;
		
		//TODO: Method logic
	}
	
	public static void addShopId(String shopId)
	{
		
	}
	@EventHandler
	public void onChat(AsyncPlayerChatEvent evt) {
		//!CMD ARG0 ARG1
		String[] args = evt.getMessage().split(" ");
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
						
					//[SHOP]
					//Description
					//Description
					//UNIQUE_SHOP_ID
						
					if (!(shopRegistered(p,lines[3])))
					{
						//TODO: Create a new entry in the sign shops data file, and add the player with the SHOP_ID to the file.
						p.sendMessage(ChatColor.GREEN + "NexEco: Detected Sign Shop was unregistered and has now been registered to you.");
							
					/** Shop IS registered, show the shop menu. */
					} else {
						
					}
				}
			}
		}
	}
	
	
}
