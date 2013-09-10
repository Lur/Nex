package jeffadkins51;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
public final class Nex extends JavaPlugin implements Listener{
	
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Player p = e.getPlayer();
			Block b = e.getClickedBlock();
			Chest chest = new Chest();
			if(b.getType() == Material.WALL_SIGN) {
				p.sendMessage(ChatColor.GREEN + "Nex: Detected SIGN_MATERIAL");
				Sign sign = (Sign) b.getState();
				String[] lines = sign.getLines();
				String[] ln;
				if(lines[0].equalsIgnoreCase("[SHOP]")) {
					for (int i = 0; i < lines.length; i++) p.sendMessage("<Line #" + i + ">: " + lines[i]);
					p.sendMessage(ChatColor.GREEN + "Nex: Detected sign is a SHOP item");
					//lines[1] = [SHOP] Identifier, lines[3] = SHOP_ID
					if (!(lines[1].isEmpty()) && !(lines[3].isEmpty()))
					{
						p.sendMessage(ChatColor.GREEN + "Nex: Detected Lines[1] & Lines[2] are not empty");
						
						//[SHOP]
						//Description
						//Description
						//UNIQUE_SHOP_ID
						
						ln = lines[1].split(":");
						if (lines[3].equalsIgnoreCase("BUY"))
						{
							p.sendMessage(ChatColor.GREEN + "Nex: Detected sign is [BUY] type");
						} else if (lines[3].equalsIgnoreCase("SELL")){
							if (p.getInventory().contains(Material.getMaterial(ln[0]), Integer.parseInt(ln[1]))){
								//Delete the item and create a new item of equivalent quantity in the chest, then give the player the $ from Line #2
							}
						}
					}
				} else if (lines[0].equalsIgnoreCase("[TRADE]")) {
					
				} else if (lines[0].equalsIgnoreCase("[TELE]")){
					
				}
			}
		}
	}
	
}