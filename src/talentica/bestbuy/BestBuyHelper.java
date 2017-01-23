package talentica.bestbuy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * This class has business logic to find minimum price for a set of requested
 * products.
 * 
 * @author UnmeshVinchurkar
 * 
 */
public class BestBuyHelper {

	private _ProductComparator _prodComparator = new _ProductComparator();

	/**
	 * Returns a map containing shopId and min cost of required products.
	 * Returns -1 in case a shop doesn't have full list of products.
	 * 
	 * @param shops
	 * @param reqProducts
	 * @return
	 */
	public Map<String, Float> findMinCost(Collection<Shop> shops,
			List<String> reqProducts) {

		Map<String, Float> shopPriceMap = new HashMap<String, Float>();

		for (Shop shop : shops) {
			float price = this.findMinCost(shop, reqProducts);

			if (price > 0) {
				shopPriceMap.put(shop.getShopId(), price);
			}
		}

		return shopPriceMap;
	}

	/**
	 * Gets the min cost to purchse products from a shop.
	 * 
	 * Assumptions: 1. A shop can offer more than one combo. 2. A product in a
	 * shop can be both separately sold or a part of combo.
	 * 
	 * Algorithm: 1. For each requested product find all the combos(combo or
	 * separately sold instance) in a shop. This can be called domain of that
	 * product.
	 * 
	 * 2. If a product is present only in one combo then select that combo, now
	 * if any other requested product also appears in that combo then there is
	 * no need to select that particular product.
	 * 
	 * 3. Main Logic: Now iterate over products, for a product(p1) find the
	 * combo(combo or individual product) with least price, then combine this
	 * combo with domain(check point 1) of next product(P2) to generate a domain
	 * for (p1,P2). Now select combo with minimum price (from the domain of
	 * (P1,p2))and then again combine this combo with domain of P3, continue
	 * this process up to Pn (nth product).
	 * 
	 * 
	 * 
	 * @param shop
	 *            Shop
	 * @param reqProducts
	 *            List<String>
	 * @return
	 */
	public float findMinCost(Shop shop, List<String> reqProducts) {

		Collection<IProduct> allShopProducts = shop.getProducts();

		// This map stores productName as key, and list of all
		// product-combos(Domain) in which it falls
		Map<String, List<IProduct>> productRangeMap = new LinkedHashMap<String, List<IProduct>>();

		// Final min-cost product list
		List<IProduct> selectedProds = new ArrayList<IProduct>();
		IProduct lastMinCostprod = null;

		// Find all product combos in which a product appears
		// populate "productRangeMap" with all {product name, list of combos in
		// which it appears}
		for (String reqProduct : reqProducts) {
			for (IProduct prod : allShopProducts) {
				if (prod.contains(reqProduct)) {
					List<IProduct> prodRange = productRangeMap.get(reqProduct);
					if (prodRange == null) {
						prodRange = new ArrayList<IProduct>();
						productRangeMap.put(reqProduct, prodRange);
					}
					prodRange.add(prod);
				}
			}

			// If for a particular product there is only one choice(Combo or
			// individual product) then select
			// it and if it is a combo-product then remove all other products
			// from productRangeMap as they will not be considered as they are
			// already selected
			if (productRangeMap.get(reqProduct) != null) {

				if (productRangeMap.get(reqProduct).size() == 1) {
					IProduct prod = productRangeMap.get(reqProduct).get(0);
					if (prod.isCombo()) {
						List<String> names = prod.getNames();

						// Reduce productRangeMap by removing products which are
						// already
						// selected
						for (String name : names) {
							productRangeMap.remove(name);
						}

					} else {
						productRangeMap.remove(reqProduct);
					}
					selectedProds.add(prod);
				}
			} else {

				// If a product is not available in a shop then return -1
				return -1;
			}
		}

		List<String> keys = new ArrayList<String>(productRangeMap.keySet());

		if (keys.size() > 0) {

			Collections.sort(productRangeMap.get(keys.get(0)), _prodComparator);
			Set<String> keysUsed = new HashSet<String>();
			keysUsed.add(keys.get(0));

			SortedSet<IProduct> tmp = new TreeSet<IProduct>(_prodComparator);
			lastMinCostprod = productRangeMap.get(keys.get(0)).get(0);

			for (int i = 1; i < keys.size(); i++) {
				List<IProduct> iList = productRangeMap.get(keys.get(i));
				keysUsed.add(keys.get(i));

				for (IProduct iprod : iList) {

					// Merge least price combo from the domain of last product with the
					// domain(all combos in which a product appears) of the
					// current product
					// and store minimum of new domain in "lastMinCostprod".
					boolean iProdContains = iprod.containsAll(keysUsed);
					boolean lastProdContains = lastMinCostprod
							.containsAll(keysUsed);

					if (iProdContains && lastProdContains) {

						if (iprod.getPrice() < lastMinCostprod.getPrice()) {
							tmp.add(iprod);
						} else if (iprod.getPrice() > lastMinCostprod
								.getPrice()) {
							tmp.add(lastMinCostprod);
						} else {
							if (iprod.size() > lastMinCostprod.size()) {
								tmp.add(lastMinCostprod);
							} else {
								tmp.add(iprod);
							}
						}

					} else if (iProdContains) {
						tmp.add(iprod);
					} else if (lastProdContains) {
						tmp.add(lastMinCostprod);

					} else {
						if (iprod != lastMinCostprod) {
							tmp.add(iprod.merge(lastMinCostprod));
						} else {
							tmp.add(iprod);
						}
					}
				}

				lastMinCostprod = tmp.first();
				tmp.clear();
			}
		}

		float price = 0;
		if (lastMinCostprod != null) {
			price = price + lastMinCostprod.getPrice().floatValue();
		}

		for (IProduct prod : selectedProds) {
			price = price + prod.getPrice().floatValue();
		}
		return price;
	}

	private class _ProductComparator implements Comparator<IProduct> {
		@Override
		public int compare(IProduct i1, IProduct i2) {
			return (int) (i1.getPrice().floatValue() < i2.getPrice()
					.floatValue() ? -1 : i1.getPrice() == i2.getPrice() ? 0 : 1);
		}
	}
}
