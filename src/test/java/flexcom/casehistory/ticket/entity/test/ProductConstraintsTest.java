package flexcom.casehistory.ticket.entity.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import javax.validation.ConstraintViolationException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import flexcom.casehistory.ticket.dao.ProductDAO;
import flexcom.casehistory.ticket.entity.Product;

/**
 * Class test for setters of the {@link Product} class. Here we check all
 * constraints on every fields.
 * 
 * @author philippe
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class ProductConstraintsTest {

	/**
	 * Product DAO
	 */
	@Autowired
	private ProductDAO productDAO;

	/**
	 * A {@link Product}
	 */
	private Product product;

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
		product.setBuild("642");
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
	 * Test if a {@link DataIntegrityViolationException} is thrown when we
	 * create 2 products with the same name, version and build
	 */
	@Test(expected = DataIntegrityViolationException.class)
	public void testSetLoginUnique() {
		product.setName("unique");
		product.setVersion("unique");
		product.setBuild("unique");
		productDAO.createProduct(product);

		Product copy = new Product();
		copy.setName("unique");
		copy.setVersion("unique");
		copy.setBuild("unique");
		productDAO.createProduct(copy);
	}

	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * {@link Product} with a <code>null</code> name
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSetNameNull() {
		product.setName(null);
		productDAO.createProduct(product);
	}

	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * {@link Product} with a too long name
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSetNameMaxSize() {
		product.setName("A good software but unfortunately bad name");
		productDAO.createProduct(product);
	}

	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * {@link Product} with an empty name
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSetEmptyName() {
		product.setName("");
		productDAO.createProduct(product);
	}

	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * {@link Product} with a <code>null</code> version
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSetVersionNull() {
		product.setVersion(null);
		productDAO.createProduct(product);
	}

	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * {@link Product} with an empty version
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSetEmptyVersion() {
		product.setVersion("");
		productDAO.createProduct(product);
	}

	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * {@link Product} with a too long version
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSetVersion() {
		product.setVersion("Long code name for this version");
		productDAO.createProduct(product);
	}

	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * {@link Product} with a <code>null</code> build
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSetBuildNull() {
		product.setBuild(null);
		productDAO.createProduct(product);
	}

	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * {@link Product} with an empty build
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSetEmptyBuild() {
		product.setBuild("");
		productDAO.createProduct(product);
	}

	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * {@link Product} with a too long build
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSetBuild() {
		product.setBuild("Too much complicated build");
		productDAO.createProduct(product);
	}

	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * {@link Product} whose creation date is <code>null</code>
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSetDateCreationNull() {
		product.setCreationDate(null);
		productDAO.createProduct(product);
	}

	/**
	 * Test if the default creation date is set to the current date
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void testSetDateCreationDefault() throws InterruptedException {
		Date before = new Date();
		Thread.sleep(1000);

		Product product = new Product();
		product.setName("p");
		product.setVersion("1.0");
		product.setBuild("1");

		Thread.sleep(1000);
		Date after = new Date();

		productDAO.createProduct(product);
		long id = product.getId();

		Product p = productDAO.findById(id);
		assertNotNull(p.getCreationDate());
		assertTrue(p.getCreationDate().after(before));
		assertTrue(p.getCreationDate().before(after));
	}

	/**
	 * Test if the creation date of an {@link Product} can't be updated
	 * @throws InterruptedException 
	 */
	// TODO @Test does not work
	public void testUpdateCreationDate() throws InterruptedException{
		Date d = product.getCreationDate();
		productDAO.createProduct(product);
		long id = product.getId();
		Thread.sleep(1000);
		
		Product p = productDAO.findById(id);
		p.setCreationDate(new Date());
		productDAO.updateProduct(p);
		
		p = null;
		p = productDAO.findById(id);
		assertFalse(p.getCreationDate() != d);
	}

}
