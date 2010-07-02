package flexcom.casehistory.ticket.entity.test;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import flexcom.casehistory.ticket.dao.ProductDAO;
import flexcom.casehistory.ticket.entity.Product;

/**
 * Test class for the {@link Product} entity. Setters and getters are not tested
 * here. Setters are tested in {@link ProductConstraintsTest}
 * 
 * @author philippe
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class ProductTest {

	/**
	 * A {@link Product}
	 */
	private Product product;

	/**
	 * Product DAO
	 */
	@Autowired
	private ProductDAO productDAO;

	/**
	 * Set up the context
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		product = new Product();
		product.setName("Photoshop");
		product.setVersion("CS5");
		product.setBuild("20100531");

		productDAO.createProduct(product);
	}

	/**
	 * Clear the context
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		productDAO.deleteAll();
		product = null;
	}

	/**
	 * Test the hashCode() method
	 */
	@Test
	public void testHashCode() {
		Product p = productDAO.findByName("Photoshop").get(0);
		assertEquals(product.hashCode(), p.hashCode());
	}

	/**
	 * Test the equals(Object) method
	 */
	@Test
	public void testEqualsObject() {
		Product p = productDAO.findByName("Photoshop").get(0);
		assertEquals(product, p);
	}

	/**
	 * Test the toString() method
	 */
	@Test
	public void testToString() {
		Product p = productDAO.findByName("Photoshop").get(0);
		assertEquals("Photoshop CS5", p.toString());
	}

	/**
	 * Test the toString() method with a <code>null</code> version
	 */
	@Test
	public void testToStringWithoutVersion() {
		Product p = productDAO.findByName("Photoshop").get(0);
		p.setVersion(null);
		assertEquals("Photoshop", p.toString());
	}

}
