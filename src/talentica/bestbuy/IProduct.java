package talentica.bestbuy;

import java.util.Collection;
import java.util.List;

/**
 * General Interface for Products
 * 
 * @author UnmeshVinchurkar
 * 
 */
public interface IProduct extends Cloneable {

	/**
	 * returns true if a product is a combo otherwise false
	 * 
	 * @return boolean
	 */
	boolean isCombo();

	/**
	 * Name of a product
	 * 
	 * @return String
	 */
	String getName();

	/**
	 * In case of combo get all the product names
	 * 
	 * @return List
	 */
	List<String> getNames();

	/**
	 * Price of a product or combo
	 * 
	 * @return Float
	 */
	Float getPrice();

	/**
	 * Check if a product with {pName} is present in a combo
	 * 
	 * @param pName
	 * @return
	 */
	boolean contains(String pName);

	/**
	 * Check if a collection of products is present in a combo.
	 * 
	 * @param nameSet
	 * @return
	 */
	public boolean containsAll(Collection<String> nameSet);

	/**
	 * Merge two products to generate a combo.
	 * 
	 * @param p1
	 * @return
	 */
	public IProduct merge(IProduct p1);

	/**
	 * Size of a combo. In case of a single product it is 1
	 * 
	 * @return int
	 */
	public int size();

	/**
	 * Add product to a combo
	 * @param productName String
	 */
	public void addProduct(String productName);

}
