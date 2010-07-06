package flexcom.casehistory.ticket.dao.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import flexcom.casehistory.ticket.dao.ProductDAO;
import flexcom.casehistory.ticket.dao.ProductDAOImpl;
import flexcom.casehistory.ticket.entity.Product;
import flexcom.casehistory.ticket.search.Query;
import flexcom.casehistory.ticket.search.filter.AndFilter;
import flexcom.casehistory.ticket.search.filter.EqualFilter;
import flexcom.casehistory.ticket.search.filter.Filter;
import flexcom.casehistory.ticket.search.filter.LessOrEqualFilter;

/**
 * Test class for {@link ProductDAOImpl}
 * 
 * @author philippe
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class ProductDAOImplTest {

	/**
	 * A {@link Product} "Photoshop"
	 */
	private Product product1;

	/**
	 * A {@link Product} "Useless"
	 */
	private Product product2;

	/**
	 * Product DAO
	 */
	@Autowired
	private ProductDAO productDAO;

	/**
	 * ID for the {@link Product} "Photoshop"
	 */
	private long productId1;

	/**
	 * ID for the {@link Product} "Useless"
	 */
	private long productId2;

	/**
	 * Set up the context
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		product1 = new Product();
		product1.setName("Photoshop");
		product1.setVersion("CS5");
		productDAO.createProduct(product1);

		product2 = new Product();
		product2.setName("Useless");
		product2.setVersion("0.0");
		productDAO.createProduct(product2);

		productId1 = product1.getId();
		productId2 = product2.getId();
	}

	/**
	 * Clear the context
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		productDAO.deleteAll();
		product1 = null;
		product2 = null;
	}

	/**
	 * Test if the findAll() method return a correct and complete list of all
	 * products. The following conditions are checked :<br/>
	 * <ul>
	 * <li>2 tickets are in the list</li>
	 * <li>The list contains the product "Photoshop"</li>
	 * <li>The list contains the product "Useless"</li>
	 * </ul>
	 */
	@Test
	public void testFindAll() {
		List<Product> products = productDAO.findAll();
		assertEquals(2, products.size());
		assertTrue(products.contains(product1));
		assertTrue(products.contains(product2));
	}

	/**
	 * Test the createProduct(Product) method : the DAO must have an additionnal
	 * entity after the creation
	 */
	@Test
	public void testCreateProduct() {
		int count = (int) productDAO.count();
		Product p = new Product();
		p.setName("p");
		p.setVersion("1.0");
		productDAO.createProduct(p);
		assertEquals(count + 1, productDAO.count());
	}

	/**
	 * Test if the createProduct(Product) method throws a
	 * {@link InvalidDataAccessApiUsageException} by passing a <code>null</code>
	 * {@link Product}
	 */
	@Test(expected = InvalidDataAccessApiUsageException.class)
	public void testCreateNullProduct() {
		productDAO.createProduct(null);
	}

	/**
	 * Test if all products with the specified name are found. The following
	 * conditions are checked : <br/>
	 * <ul>
	 * <li>The list contains 1 product</li>
	 * <li>The list contains the product "Photoshop"</li>
	 * </ul>
	 */
	@Test
	public void testFindByName() {
		List<Product> products = productDAO.findByName("Photoshop");

		assertEquals(1, products.size());
		assertEquals(product1, products.get(0));
	}

	/**
	 * Test the deletion of an {@link Product}. We check that the deleted
	 * {@link Product} can't be found.
	 */
	@Test
	public void testDeleteProduct() {
		productDAO.deleteProduct(product2);
		assertNull(productDAO.findById(productId2));
	}

	/**
	 * Test if a {@link NullPointerException} is thrown by trying to delete a
	 * <code>null</code> {@link Product}
	 */
	@Test(expected = NullPointerException.class)
	public void testDeleteNullProduct() {
		productDAO.deleteProduct(null);
	}

	/**
	 * Test if the DAO have no more {@link Product} entities after the call of
	 * deleteAll() method
	 */
	@Test
	public void testDeleteAll() {
		productDAO.deleteAll();
		assertEquals(0, productDAO.count());
	}

	/**
	 * Test if an {@link Product} can be found by his ID
	 */
	@Test
	public void testFindById() {
		assertEquals(product1, productDAO.findById(productId1));
	}

	/**
	 * Test if the modification of an entity has been correctly done
	 */
	@Test
	public void testUpdateProduct() {
		product2.setName("Null");
		productDAO.updateProduct(product2);
		assertEquals("Null", productDAO.findById(productId2).getName());
	}

	/**
	 * Test if the updateProduct(Product) method throws an
	 * {@link InvalidDataAccessApiUsageException} if the given argument is
	 * <code>null</code>
	 */
	@Test(expected = InvalidDataAccessApiUsageException.class)
	public void testUpdateNullProduct() {
		productDAO.updateProduct(null);
	}

	/**
	 * Test some {@link Product} {@link Query}. First, we create a {@link Query}
	 * without filters. Then, after each execution of this query, we add a
	 * filter. The following scenario is executed :<br/>
	 * <ul>
	 * <li>0 filters : 2 products found</li>
	 * <li>Filter (name = Photoshop and version = CS5) added : 1 product found</li>
	 * <li>Filter (build = 910) added : no product found</li>
	 * </ul>
	 */
	@Test
	public void testFilter() {
		List<Product> products;
		Query<Product> query = new Query<Product>(Product.class);

		products = productDAO.filter(query);
		assertEquals(2, products.size());

		Filter filter = new AndFilter(new EqualFilter("name", "Photoshop"), new LessOrEqualFilter("creationDate", new Date()));
		query.addFilter(filter);

		products = productDAO.filter(query);
		assertEquals(1, products.size());

		query.addFilter(new EqualFilter("version", "CS3"));

		products = productDAO.filter(query);
		assertEquals(0, products.size());
	}

	/**
	 * Test the count() method
	 */
	@Test
	public void testCount() {
		assertEquals(2, productDAO.count());
	}

}
