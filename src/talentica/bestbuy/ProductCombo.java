package talentica.bestbuy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import talentica.bestbuy.IProduct;

public class ProductCombo implements IProduct {

	private Set<String> _names = new HashSet<String>();
	private Float _price;

	public ProductCombo(Float price) {
		super();
		this._price = price;
	}

	public ProductCombo(Collection<String> names, float price) {
		super();
		if(names!=null){
		_names.addAll(names);
		this._price = price;
		}
	}

	@Override
	public void addProduct(String productName) {
		_names.add(productName);
	}

	@Override
	public int size() {
		return _names.size();
	}

	@Override
	public IProduct merge(IProduct p1) {
		float price = p1.getPrice() + this.getPrice();

		Set<String> nameSet = new HashSet<String>();
		nameSet.addAll(this.getNames());
		nameSet.addAll(p1.getNames());
		return new ProductCombo(nameSet, price);
	}

	@Override
	public boolean isCombo() {
		return true;
	}

	@Override
	public boolean containsAll(Collection<String> nameSet) {
		if (nameSet == null)
			return false;

		return _names.containsAll(nameSet);
	}

	@Override
	public boolean contains(String pName) {
		if (pName == null)
			return false;
		return _names.contains(pName);
	}

	@Override
	public String getName() {
		return _names.toString();
	}

	@Override
	public List<String> getNames() {
		List<String> list = new ArrayList<String>();
		list.addAll(_names);
		return list;
	}

	@Override
	public Float getPrice() {
		return this._price;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_names == null) ? 0 : _names.hashCode());
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
		ProductCombo other = (ProductCombo) obj;
		if (_names == null) {
			if (other._names != null)
				return false;
		} else if (!_names.equals(other._names))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProductCombo [names=" + _names + ", price=" + _price + "]";
	}

}
