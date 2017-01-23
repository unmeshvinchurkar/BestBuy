package talentica.bestbuy;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Shop {

	private String _shopID = null;

	private Set<IProduct> _products = new HashSet<IProduct>();

	public Shop(String shopID) {
		super();
		this._shopID = shopID;
	}

	public String getShopId() {

		return _shopID;
	}

	public void addProduct(IProduct product) {
		_products.add(product);
	}

	public void addProducts(Collection<? extends IProduct> productList) {
		_products.addAll(productList);
	}

	public Shop(String shopID, Collection<? extends IProduct> productList) {
		super();
		this._shopID = shopID;
		this._products.addAll(productList);
	}

	public Collection<IProduct> getProducts() {

		return _products;
	}

	@Override
	public String toString() {
		return "Shop [_shopID=" + _shopID + ", _products=" + _products + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_shopID == null) ? 0 : _shopID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Shop other = (Shop) obj;
		if (_shopID == null) {
			if (other._shopID != null)
				return false;
		} else if (!_shopID.equals(other._shopID))
			return false;
		return true;
	}

}
