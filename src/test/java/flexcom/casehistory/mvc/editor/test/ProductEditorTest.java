package flexcom.casehistory.mvc.editor.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import flexcom.casehistory.mvc.editor.ProductEditor;
import flexcom.casehistory.ticket.dao.ProductDAO;
import flexcom.casehistory.ticket.entity.Product;

/**
 * Test class for {@link ProductEditor}
 * 
 * @author philippe
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class ProductEditorTest {

	/**
	 * A {@link Product}
	 */
	private Product product;

	/**
	 * The ID of the {@link Product}
	 */
	private long productId;

	/**
	 * Product DAO
	 */
	@Autowired
	private ProductDAO productDAO;

	/**
	 * Product editor
	 */
	@Autowired
	private ProductEditor editor;

	/**
	 * Create and persist a {@link Product}
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		product = new Product();
		product.setName("p");
		product.setVersion("1.0");
		product.setBuild("1");
		productDAO.createProduct(product);
		productId = product.getId();
	}

	/**
	 * Clear the entity manager and the created {@link Product}. Put a
	 * <code>null</code> value for the editor
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		editor.setValue(null);
		productDAO.deleteAll();
		product = null;
	}

	/**
	 * Test if the editor return the correct {@link String} after a call to the
	 * setAsText() method
	 */
	@Test
	public void testGetAsText() {
		String id = "" + productId;
		editor.setAsText(id);
		assertEquals(id, editor.getAsText());
	}

	/**
	 * Test if the editor return <code>null</code> without a call to the
	 * setAsText() method
	 */
	@Test
	public void testGetAsTextNullValue() {
		assertNull(editor.getAsText());
	}

	/**
	 * Test if the editor return the correct {@link Product} after a call to the
	 * setAsText() method
	 */
	@Test
	public void testSetAsTextString() {
		editor.setAsText("" + productId);
		assertEquals(product, editor.getValue());
	}

	/**
	 * Test if the editor return <code>null</code> after a call to the
	 * setAsText() method with an invalid ID
	 */
	@Test
	public void testSetAsTextInvalidString() {
		editor.setAsText("" + Long.MAX_VALUE);
		assertNull(editor.getValue());
	}

}
