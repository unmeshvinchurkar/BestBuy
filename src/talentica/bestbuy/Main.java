package talentica.bestbuy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import talentica.bestbuy.datasource.DataSourceException;
import talentica.bestbuy.datasource.DataSourceFactory;
import talentica.bestbuy.datasource.IDataSource;

public final class Main {

	public static void main(String[] args) {

		if (args.length < 2) {
			throw new RuntimeException("Invalid Arguments");
			// args = new String[] { "resource\\shopData.csv", "scissor",
			// "bath_towel" };

		}

		String fileName = ".\\" + args[0];

		// Read file name and requested products
		List<String> products = new ArrayList<String>();
		// String fileName = args[0];
		for (int i = 1; i < args.length; i++) {
			products.add(args[i]);
		}

		try {
			// Load shop data from files
			IDataSource dsource = DataSourceFactory.getInstance()
					.getDataSource("csv", fileName, null, null);
			ProductDao dao = new ProductDao(dsource);
			Collection<Shop> shops = dao.getShops();
			BestBuyHelper helper = new BestBuyHelper();

			// For each shop find minimum price
			Map<String, Float> shopPriceMap = helper.findMinCost(shops,
					products);

			printMinPrice(shopPriceMap);

		} catch (DataSourceException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Find the shop which offer least price for the
	 * 
	 * @param shopPriceMap
	 */
	private static void printMinPrice(Map<String, Float> shopPriceMap) {
		String minShopId = null;
		float minPrice = 0;

		if (shopPriceMap.size() == 0) {
			System.out.println("No Shop Found");
			return;
		}

		List<String> shopIds = new ArrayList<String>(shopPriceMap.keySet());
		minShopId = shopIds.get(0);
		minPrice = shopPriceMap.get(minShopId);

		for (int i = 1; i < shopIds.size(); i++) {
			if (shopPriceMap.get(shopIds.get(i)) < minPrice) {
				minPrice = shopPriceMap.get(shopIds.get(i));
				minShopId = shopIds.get(i);
			}
		}
		System.out.println("OUTPUT::    " + minShopId + " , " + minPrice);
	}
}
