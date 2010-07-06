package flexcom.casehistory.mvc.controller.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.servlet.ModelAndView;

import flexcom.casehistory.mvc.controller.ProductController;
import flexcom.casehistory.mvc.controller.ProductQueryCommand;
import flexcom.casehistory.ticket.dao.ProductDAO;
import flexcom.casehistory.ticket.entity.Product;

/**
 * Test class for the {@link ProductController}
 * 
 * @author philippe
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class ProductControllerTest {

	/**
	 * A model
	 */
	private static Model m;

	/**
	 * A {@link Product}
	 */
	private Product product;

	/**
	 * The ID of the {@link Product}
	 */
	private long productId;

	/**
	 * Product controller
	 */
	@Autowired
	private ProductController controller;

	/**
	 * Product DAO
	 */
	@Autowired
	private ProductDAO productDAO;

	/**
	 * Setting up the context for all tests : instanciation of the model
	 */
	@BeforeClass
	public static void before() {
		m = new ExtendedModelMap();
	}

	/**
	 * Clearing the context
	 */
	@AfterClass
	public static void after() {
		m = null;
	}

	/**
	 * Create and persist an {@link Product} object
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		product = new Product();
		product.setName("product");
		product.setVersion("1.0");

		productDAO.createProduct(product);

		productId = product.getId();
	}

	/**
	 * Clear the entity manager and the {@link Product} object
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		productDAO.deleteAll();
		product = null;
	}

	/**
	 * Test if the model contains the list of all products. The following
	 * conditions are checked :<br/>
	 * <ul>
	 * <li>The model contains an attribute called "list"</li>
	 * <li>This list contains only one object</li>
	 * <li>This object is equal to the {@link Product} created before in setUp()
	 * method</li>
	 * </ul>
	 */
	@Test
	public void testList() {
		controller.list(m);
		Map<String, Object> map = m.asMap();

		assertTrue(m.containsAttribute("list"));
		List<?> products = (List<?>) map.get("list");
		assertEquals(1, products.size());
		assertEquals(product, products.get(0));
	}

	/**
	 * Test if the model contains the correct {@link Product} object. The
	 * following conditions are checked :<br/>
	 * <ul>
	 * <li>The model contains an attribute called "product"</li>
	 * <li>The attribute "product" is equal to the {@link Product} created
	 * before in setUp() method</li>
	 * </ul>
	 */
	@Test
	public void testView() {
		controller.view(m, productId);
		Map<String, Object> map = m.asMap();

		assertTrue(m.containsAttribute("product"));
		Product p = (Product) map.get("product");
		assertEquals(product, p);
	}

	/**
	 * Test if the model contains a <code>null</code> {@link Product} by passing
	 * an invalid ID argument. The following conditions are checked :<br/>
	 * <ul>
	 * <li>The model contains an attribute called "product"</li>
	 * <li>This attribute has a <code>null</code> value</li>
	 * </ul>
	 */
	@Test
	public void testViewWithInvalidId() {
		controller.view(m, Long.MAX_VALUE);
		Map<String, Object> map = m.asMap();

		assertTrue(m.containsAttribute("product"));
		assertNull(map.get("product"));
	}

	/**
	 * Test if the model contains a {@link Product} object. The following
	 * conditions are checked :<br/>
	 * <ul>
	 * <li>The model contains an attribute called "product"</li>
	 * <li>This attribute is not <code>null</code></li>
	 * </ul>
	 */
	@Test
	public void testAdd() {
		controller.add(m);
		Map<String, Object> map = m.asMap();

		assertTrue(m.containsAttribute("product"));
		Product p = (Product) map.get("product");
		assertNotNull(p);
	}

	/**
	 * Test if the {@link Product} has been well created. The following
	 * conditions are checked :<br/>
	 * <ul>
	 * <li>The created {@link Product} can be found with the {@link ProductDAO}</li>
	 * <li>The view is redirected to the product.html page</li>
	 * </ul>
	 */
	@Test
	public void testCreate() {
		controller.add(m);
		Map<String, Object> map = m.asMap();
		Product p = (Product) map.get("product");

		p.setName("p");
		p.setVersion("1.0");
		String view = controller.create(p, new MapBindingResult(Collections.emptyMap(), ""));
		long id = p.getId();

		assertEquals(p, productDAO.findById(id));
		assertEquals("redirect:/product.html", view);
	}

	/**
	 * Test if the {@link Product} has been well deleted. The following
	 * conditions are checked :<br/>
	 * <ul>
	 * <li>The deleted {@link Product} can't be found with the
	 * {@link ProductDAO}</li>
	 * <li>The view is redirected to the product.html page</li>
	 * </ul>
	 */
	@Test
	public void testDelete() {
		String view = controller.delete(productId);
		Product product = productDAO.findById(productId);
		assertFalse(product.isEnabled());
		assertEquals("redirect:/product.html", view);
	}

	/**
	 * Test if the deletion throw a {@link NullPointerException} by passing an
	 * invalid ID argument
	 */
	@Test(expected = NullPointerException.class)
	public void testDeleteWithInvalidId() {
		controller.delete(Long.MAX_VALUE);
	}

	/**
	 * Test if the model contains a {@link Product} object. The following
	 * conditions are checked :<br/>
	 * <ul>
	 * <li>The model contains an attribute called "product"</li>
	 * <li>This attribute is equal to the {@link Product} created before in
	 * setUp() method</li>
	 * </ul>
	 */
	@Test
	public void testEdit() {
		controller.edit(m, productId);
		Map<String, Object> map = m.asMap();

		assertTrue(m.containsAttribute("product"));
		Product p = (Product) map.get("product");
		assertEquals(product, p);
	}

	/**
	 * Test if the model contains a <code>null</code> {@link Product} by passing
	 * an invalid ID argument. The following conditions are checked :<br/>
	 * <ul>
	 * <li>The model contains an attribute called "product"</li>
	 * <li>This attribute has a <code>null</code> value</li>
	 * </ul>
	 */
	@Test
	public void testEditWithInvalidId() {
		controller.edit(m, Long.MAX_VALUE);
		Map<String, Object> map = m.asMap();

		assertTrue(m.containsAttribute("product"));
		assertNull(map.get("product"));
	}

	/**
	 * Test if the {@link Product} has been well updated. The following
	 * conditions are checked :<br/>
	 * <ul>
	 * <li>The new value for the updated property can be read with the
	 * {@link ProductDAO}</li>
	 * <li>The view is redirected to the product.html page</li>
	 * </ul>
	 */
	@Test
	public void testUpdate() {
		controller.edit(m, productId);
		Map<String, Object> map = m.asMap();
		Product p = (Product) map.get("product");

		p.setName("Photoshop");
		String view = controller.update(p, new MapBindingResult(Collections.emptyMap(), ""));

		assertEquals("Photoshop", productDAO.findById(productId).getName());
		assertEquals("redirect:/product.html", view);
	}

	/**
	 * Test if the updating throw a {@link NullPointerException} by passing an
	 * invalid ID argument
	 */
	@Test(expected = NullPointerException.class)
	public void testUpdateWithInvalidId() {
		controller.edit(m, Long.MAX_VALUE);
		Map<String, Object> map = m.asMap();
		Product p = (Product) map.get("product");

		p.setName("Photoshop");
		controller.update(p, new MapBindingResult(Collections.emptyMap(), ""));
	}
	
	/**
	 * Test if the model contains a {@link ProductQueryCommand} object. The following
	 * conditions are checked :<br/>
	 * <ul>
	 * <li>The model contains an attribute called "productQuery"</li>
	 * <li>This attribute is not <code>null</code></li>
	 * </ul>
	 */
	@Test
	public void testSearchForm(){
		controller.searchForm(m);
		Map<String, Object> map = m.asMap();

		assertTrue(m.containsAttribute("productQuery"));
		ProductQueryCommand command = (ProductQueryCommand) map.get("productQuery");
		assertNotNull(command);
	}
	
	/**
	 * Test if the result of search is correct. The following
	 * conditions are checked :<br/>
	 * <ul>
	 * <li>The model contains an attribute called "result"</li>
	 * <li>This attribute is not null</li>
	 * <li>This attribute is not empty</li>
	 * <li>The only element in the list is the expected product</li>
	 * <li>The view is redirected to the product/result.html page</li>
	 * </ul>
	 */
	@Test
	public void testSearchExactMatch(){
		controller.searchForm(m);
		Map<String, Object> map = m.asMap();
		ProductQueryCommand command = (ProductQueryCommand) map.get("productQuery");

		command.setName("product");
		command.setNameMatchMode(ProductQueryCommand.EXACT_MATCH_MODE);
		ModelAndView mav = controller.search(command);

		Map<String, Object> model = mav.getModel();
		assertTrue(model.containsKey("result"));
		List<?> result = (List<?>) model.get("result");
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(product, result.get(0));
		assertEquals("product/result", mav.getViewName());
	}
}
