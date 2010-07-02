package flexcom.casehistory.ticket.dao;

import java.util.List;

import flexcom.casehistory.ticket.entity.Product;
import flexcom.casehistory.ticket.search.Query;

/**
 * Interface providing methods (mainly CRUD operations and finders) for
 * implementing a {@link Product} DAO
 * 
 * @author philippe
 * 
 */
public interface ProductDAO {

	/**
	 * @param product
	 *            {@link Product} to create
	 */
	public void createProduct(Product product);

	/**
	 * @param product
	 *            {@link Product} to update
	 */
	public void updateProduct(Product product);

	/**
	 * @param product
	 *            {@link Product} to delete
	 */
	public void deleteProduct(Product product);

	/**
	 * @param id
	 *            {@link Product} ID
	 * @return The {@link Product} identified by the given ID
	 */
	public Product findById(long id);

	/**
	 * Delete all products
	 */
	public void deleteAll();

	/**
	 * Find all products
	 * 
	 * @return A list containing all products
	 */
	public List<Product> findAll();

	/**
	 * Find all products having this given name
	 * 
	 * @param name
	 *            an {@link Product} name
	 * @return A list containing products which have the given name
	 */
	public List<Product> findByName(String name);

	/**
	 * Find all products matching the {@link Query}
	 * 
	 * @param query
	 *            The {@link Query} to execute
	 * @return A list containing the result of the given {@link Query}
	 */
	public List<Product> filter(Query<Product> query);

	/**
	 * Count the number of {@link Product} entities which are managed by this
	 * DAO
	 * 
	 * @return The number of managed {@link Product} entities
	 */
	public long count();
}
