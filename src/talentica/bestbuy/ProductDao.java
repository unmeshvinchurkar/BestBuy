package talentica.bestbuy;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import talentica.bestbuy.Product;
import talentica.bestbuy.ProductCombo;
import talentica.bestbuy.datasource.IDataSource;

/**
 * A data access object to fetch data from data source.
 * 
 * @author UnmeshVinchurkar
 * 
 */
public class ProductDao {

	/**
	 * Instance of Data Source
	 */
	private IDataSource dsource;

	public ProductDao(IDataSource dsource) {
		this.dsource = dsource;
	}

	/**
	 * Load data
	 */
	public void load() {
		_connect();
	}

	void _connect() {
		if (!dsource.isConnected()) {
			dsource.connect();
		}
	}

	/**
	 * Get a collection of shops from the data source.
	 * 
	 * @return
	 */
	public Collection<Shop> getShops() {
		_connect();

		List<String[]> records = dsource.getRecords();
		Map<String, Shop> shopMap = new HashMap<String, Shop>();

		for (int i = 0; i < records.size(); i++) {
			String[] record = records.get(i);
			String shopId = record[0];

			Shop shop = shopMap.get(shopId);

			if (shop == null) {
				shop = new Shop(shopId);
				shopMap.put(shopId, shop);
			}

			IProduct product = null;

			if (record.length > 3) {

				float price = Float.parseFloat(record[1]);
				product = new ProductCombo(price);

				for (int j = 2; j < record.length; j++) {
					product.addProduct(record[j].trim());
				}

			} else {
				product = new Product(record[2].trim(),
						Float.parseFloat(record[1]));

			}
			shop.addProduct(product);
		}

		return shopMap.values();
	}
}
