package jeffadkins51;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.bukkit.Material;

//Default prices in a list, default_prices.dat

public class Stocks {

	/**
	 * Stocks = Base Item Value + Demand - Supply
	 */
	public static String[] stocks;
	//Notes: Investor name, Stock invested in, Amount invested
	
	@SuppressWarnings("deprecation")
	public static void updateValues()
	{
		Material mat;
		String[] lnData;
		
		for (int i = 0; i < stocks.length; i++){
			lnData = stocks[i].split(":");
			mat = Material.getMaterial(lnData[0]);
			stocks[mat.getId()] = mat.name() + ":" + "";
		}
	}
	
	public static void fetchValues()
	{
		try{
			/** Base Values for Materials */
			FileInputStream fis = new FileInputStream(NexEco.dir + "/NexEco/stockdata/base_item_values.dat");
			DataInputStream dis = new DataInputStream(fis);
			BufferedReader br = new BufferedReader(new InputStreamReader(dis));
			String ln=null;
			String[] lnData;
			System.out.println("Loading Base Item values...");
			while ((ln = br.readLine()) != null){
				lnData = ln.split(":");
				stocks[Material.getMaterial(lnData[0]).getId()] = lnData[1];
			}
			fis.close();
			dis.close();
			br.close();
		} catch (IOException ioe){ioe.printStackTrace();}
	}
	
}
