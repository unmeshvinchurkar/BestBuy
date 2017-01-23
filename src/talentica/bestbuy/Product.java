package talentica.bestbuy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import talentica.bestbuy.IProduct;

public class Product implements IProduct {

	private String _name = null;
	private Float _price = null;

	public Product(String name, Float price) {
		super();
		this._name = name;
		this._price = price;
	}

	public int size() {

		return 1;
	}

	public void addProduct(String productName) {
	}

	public IProduct merge(IProduct p1) {
		float price = p1.getPrice() + this.getPrice();

		Set<String> nameSet = new HashSet<String>();
		nameSet.addAll(this.getNames());
		nameSet.addAll(p1.getNames());
		return new ProductCombo(nameSet, price);
	}

	@Override
	public boolean isCombo() {
		return false;
	}

	@Override
	public String getName() {
		return _name;
	}

	public boolean contains(String pName) {
		if (pName == null)
			return false;
		return _name.equals(pName);
	}

	public boolean containsAll(Collection<String> nameSet) {
		if (nameSet == null || nameSet.size() > 1)
			return false;

		return nameSet.contains(_name);
	}

	@Override
	public List<String> getNames() {
		List<String> list = new ArrayList<String>();
		list.add(_name);
		return list;
	}

	@Override
	public Float getPrice() {
		return _price;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_name == null) ? 0 : _name.hashCode());
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
		Product other = (Product) obj;
		if (_name == null) {
			if (other._name != null)
				return false;
		} else if (!_name.equals(other._name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Product [name=" + _name + ", price=" + _price + "]";
	}

}
