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
	
	/**
	 * Supply = The amount of the item obtained and the amount of the item in the sign shops collectively at the time of the values update.
	 * Demand = The amount of the item purchased from the sign shops collectively
	 */
	public static int[] supply,demand;
	
	@SuppressWarnings("deprecation")
	public static void updateValues()
	{
		Material mat;
		String[] lnData;
		
		for (int i = 0; i < stocks.length; i++){
			lnData = stocks[i].split(":");
			mat = Material.getMaterial(lnData[0]);
			stocks[mat.getId()] = mat.name() + ":" + NexEco.getItemPrice(mat);
		}
	}

	public static void fetchValues()
	{
		try{
			/** Base Values for Materials */
			FileInputStream fis = new FileInputStream(Nex.dir + "/NexEco/stockdata/base_item_values.dat");
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
