package jeffadkins51;
import java.io.File;
import org.tmatesoft.sqljet.core.table.ISqlJetTable;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

public class NexSQL {
	
	static SqlJetDb db;
	static ISqlJetTable table;
	private static File dbFile = new File(Nex.dir + "server.db");
	
	public static boolean dbExists()
	{
		if (!(dbFile.exists())) return false; else { return true; }
	}
	
	public static void addPlayerEntry(String args[]){
		try{
			db = SqlJetDb.open(dbFile , true);
			table = db.getTable("Players");
			/**
			 * args[0] = Player ID or Name
			 * args[1] = Cash
			 * args[2] = Stocks
			 * args[3] = Guild
			 * args[4] = Donator
			 * args[5] = Kills
			 * args[6] = Deaths
			 * args[7] = Home_Teleports (Separated with ":")
			 */
			table.insert(args[0],args[1],args[2],args[3],args[4],args[5],args[6],args[7]);
		} finally {
			db.commit();
			db.close();
		}
	}
	
	public static void addShopEntry(String[] args){
		try{
			db = SqlJetDb.open(dbFile, true);
			table = db.getTable("ShopData");
			/**
			 * args[0] = Player Id or Name
			 * args[1] = Shop X
			 * args[2] = Shop Y
			 * args[3] = Item[] (-1 to identify its empty)
			 * 
			 */
			table.insert(args[0],args[1],args[2],"-1");
		} finally {
			db.commit();
			db.close();
		}
	}
	
	public static void updateShopEntry(String[] args){
		try{
			
			
		} finally {
			db.commit();
			db.close();
		}
	}
	
	public static void init(){
		if (!(dbExists())) System.err.println("Error: DB File not found, NexSQL will terminate."); System.exit(3);
	}
	
}
