package flexcom.casehistory.mvc.controller.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.beans.PropertyEditor;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.servlet.ModelAndView;

import flexcom.casehistory.data.Data;
import flexcom.casehistory.data.LicenceDataSet;
import flexcom.casehistory.mvc.controller.LicenceController;
import flexcom.casehistory.mvc.controller.LicenceQueryCommand;
import flexcom.casehistory.mvc.editor.CompanyEditor;
import flexcom.casehistory.mvc.editor.MaintenanceEditor;
import flexcom.casehistory.mvc.editor.ProductEditor;
import flexcom.casehistory.ticket.dao.ClientDAO;
import flexcom.casehistory.ticket.dao.LicenceDAO;
import flexcom.casehistory.ticket.dao.MaintenanceDAO;
import flexcom.casehistory.ticket.dao.ProductDAO;
import flexcom.casehistory.ticket.entity.Client;
import flexcom.casehistory.ticket.entity.Licence;
import flexcom.casehistory.ticket.entity.Maintenance;
import flexcom.casehistory.ticket.entity.Product;
import flexcom.casehistory.ticket.search.Query;
import flexcom.casehistory.ticket.search.filter.BetweenFilter;

/**
 * Test class for the {@link LicenceController}
 * 
 * @author philippe
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class LicenceControllerTest {

	/**
	 * A model
	 */
	private static Model m;

	/**
	 * The ID of the {@link Licence}
	 */
	private long licenceId;

	/**
	 * Ticket controller
	 */
	@Autowired
	private LicenceController controller;

	/**
	 * Licence DAO
	 */
	@Autowired
	private LicenceDAO licenceDAO;
	
	@Autowired
	private ProductDAO productDAO;
	
	@Autowired
	private ClientDAO clientDAO;
	
	@Autowired
	private MaintenanceDAO maintenanceDAO;
	
	@Autowired
	private LicenceDataSet licenceDataSet;

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
	 * Create and persist an {@link Licence} object
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		licenceDataSet.setup();
		licenceId = Data.LICENCE_1.getId();
	}

	/**
	 * Clear the entity manager and the created objects
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		licenceDataSet.clear();
	}

	/**
	 * Test if the model contains the list of all licences. The following
	 * conditions are checked :<br/>
	 * <ul>
	 * <li>The model contains an attribute called "list"</li>
	 * <li>This list contains only one object</li>
	 * <li>This object is equal to the {@link Licence} created before in setUp()
	 * method</li>
	 * </ul>
	 */
	@Test
	public void testList() {
		controller.list(m);
		Map<String, Object> map = m.asMap();

		assertTrue(m.containsAttribute("list"));
		List<?> licences = (List<?>) map.get("list");
		assertEquals(2, licences.size());
		assertTrue(licences.contains(Data.LICENCE_1));
		assertTrue(licences.contains(Data.LICENCE_2));
	}

	/**
	 * Test if the model contains the correct {@link Licence} object. The
	 * following conditions are checked :<br/>
	 * <ul>
	 * <li>The model contains an attribute called "licence"</li>
	 * <li>The attribute "licence" is equal to the {@link Licence} created before
	 * in setUp() method</li>
	 * </ul>
	 */
	@Test
	public void testView() {
		controller.view(m, licenceId);
		Map<String, Object> map = m.asMap();

		assertTrue(m.containsAttribute("licence"));
		Licence l = (Licence) map.get("licence");
		assertEquals(Data.LICENCE_1, l);
	}

	/**
	 * Test if the model contains a <code>null</code> {@link Licence} by passing
	 * an invalid ID argument. The following conditions are checked :<br/>
	 * <ul>
	 * <li>The model contains an attribute called "licence"</li>
	 * <li>This attribute has a <code>null</code> value</li>
	 * </ul>
	 */
	@Test
	public void testViewWithInvalidId() {
		controller.view(m, Long.MAX_VALUE);
		Map<String, Object> map = m.asMap();

		assertTrue(m.containsAttribute("licence"));
		assertNull(map.get("licence"));
	}

	/**
	 * Test if the model contains a {@link Licence} object. The following
	 * conditions are checked :<br/>
	 * <ul>
	 * <li>The model contains an attribute called "licence"</li>
	 * <li>This attribute is not <code>null</code></li>
	 * </ul>
	 */
	@Test
	public void testAdd() {
		controller.add(m);
		Map<String, Object> map = m.asMap();

		assertTrue(m.containsAttribute("licence"));
		Licence l = (Licence) map.get("licence");
		assertNotNull(l);
	}

	/**
	 * Test if the {@link Licence} has been well created. The following
	 * conditions are checked :<br/>
	 * <ul>
	 * <li>The created {@link Licence} can be found with the {@link LicenceDAO}</li>
	 * <li>The view is redirected to the licence.html page</li>
	 * </ul>
	 */
	@Test
	public void testCreate() throws InterruptedException {
		controller.add(m);
		Map<String, Object> map = m.asMap();
		Licence l = (Licence) map.get("licence");

		Thread.sleep(1000);
		Date before = new Date();
		l.setLicenceKey("lk");
		l.setClient(Data.CLIENT_2);
		l.setProduct(Data.PRODUCT_2);
		l.setMaintenance(Data.MAINTENANCE_FULL);
		String view = controller.create(l, new MapBindingResult(Collections.emptyMap(), ""));
		Date after = new Date();
		long id = l.getId();

		Query<Licence> query = new Query<Licence>(Licence.class);
		query.addFilter(new BetweenFilter("creationDate", before, after));
		List<Licence> licences = licenceDAO.filter(query);

		assertEquals(1, licences.size());
		assertEquals(id, licences.get(0).getId());
		assertEquals(l, licences.get(0));
		assertEquals("redirect:/licence.html", view);
	}

	/**
	 * Test if the returned list of clients is correct and complete. First, we
	 * create 2 clients, then we compare the result of the clients() method and
	 * the findAll() method. At the end of this test, we clear the entity
	 * manager
	 */
	@Test
	public void testClients() {
		Object[] clients = clientDAO.findAllEnabled().toArray();
		assertArrayEquals(controller.clients().toArray(), clients);
	}

	/**
	 * Test if the returned list of products is correct and complete. First, we
	 * create 2 products, then we compare the result of the products() method
	 * and the findAll() method. At the end of this test, we clear the entity
	 * manager
	 */
	@Test
	public void testProducts() {
		Object[] products = productDAO.findAllEnabled().toArray();
		assertArrayEquals(controller.products().toArray(), products);
	}

	/**
	 * Test if the 4 {@link PropertyEditor} have been well registered. The
	 * following conditions are checked :<br/>
	 * <ul>
	 * <li>A property editor for {@link Client} object is found and is not
	 * <code>null</code></li>
	 * <li>A property editor for {@link Product} object is found and is not
	 * <code>null</code></li>
	 * <li>A property editor for {@link Maintenance} object is found and is not
	 * <code>null</code></li>
	 * <li>A property editor for {@link Date} object is found and is not
	 * <code>null</code></li>
	 * <li>The {@link PropertyEditor} clientEditor is an instance of
	 * {@link CompanyEditor}</li>
	 * <li>The {@link PropertyEditor} productEditor is an instance of
	 * {@link ProductEditor}</li>
	 * <li>The {@link PropertyEditor} maintenanceEditor is an instance of
	 * {@link MaintenanceEditor}</li>
	 * <li>The {@link PropertyEditor} dateEditor is an instance of
	 * {@link CustomDateEditor}</li>
	 * </ul>
	 */
	@Test
	public void testBinder() {
		WebDataBinder binder = new WebDataBinder(null);
		controller.binder(binder);

		PropertyEditor clientEditor = binder.findCustomEditor(Client.class, null);
		PropertyEditor productEditor = binder.findCustomEditor(Product.class, null);
		PropertyEditor maintenanceEditor = binder.findCustomEditor(Maintenance.class, null);
		PropertyEditor dateEditor = binder.findCustomEditor(Date.class, null);

		assertNotNull(clientEditor);
		assertNotNull(productEditor);
		assertNotNull(maintenanceEditor);
		assertNotNull(dateEditor);
		assertTrue(clientEditor instanceof CompanyEditor);
		assertTrue(productEditor instanceof ProductEditor);
		assertTrue(maintenanceEditor instanceof MaintenanceEditor);
		assertTrue(dateEditor instanceof CustomDateEditor);
	}

	/**
	 * Test if the {@link Licence} has been well deleted. The following
	 * conditions are checked :<br/>
	 * <ul>
	 * <li>The deleted {@link Licence} can't be found with the {@link LicenceDAO}</li>
	 * <li>The view is redirected to the licence.html page</li>
	 * </ul>
	 */
	@Test
	public void testDelete() {
		String view = controller.delete(licenceId);
		Licence licence = licenceDAO.findById(licenceId);
		assertFalse(licence.isEnabled());
		assertEquals("redirect:/licence.html", view);
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
	 * Test if the model contains a {@link Licence} object. The following
	 * conditions are checked :<br/>
	 * <ul>
	 * <li>The model contains an attribute called "licence"</li>
	 * <li>This attribute is equal to the {@link Licence} created before in
	 * setUp() method</li>
	 * </ul>
	 */
	@Test
	public void testEdit() {
		controller.edit(m, licenceId);
		Map<String, Object> map = m.asMap();

		assertTrue(m.containsAttribute("licence"));
		Licence l = (Licence) map.get("licence");
		assertEquals(Data.LICENCE_1, l);
	}

	/**
	 * Test if the model contains a <code>null</code> {@link Licence} by passing
	 * an invalid ID argument. The following conditions are checked :<br/>
	 * <ul>
	 * <li>The model contains an attribute called "licence"</li>
	 * <li>This attribute has a <code>null</code> value</li>
	 * </ul>
	 */
	@Test
	public void testEditWithInvalidId() {
		controller.edit(m, Long.MAX_VALUE);
		Map<String, Object> map = m.asMap();

		assertTrue(m.containsAttribute("licence"));
		assertNull(map.get("licence"));
	}

	/**
	 * Test if the {@link Licence} has been well updated. The following
	 * conditions are checked :<br/>
	 * <ul>
	 * <li>The new value for the updated property can be read with the
	 * {@link LicenceDAO}</li>
	 * <li>The view is redirected to the licence.html page</li>
	 * </ul>
	 */
	@Test
	public void testUpdate() {
		controller.edit(m, licenceId);
		Map<String, Object> map = m.asMap();
		Licence l = (Licence) map.get("licence");

		l.setLicenceKey("test");
		String view = controller.update(l, new MapBindingResult(Collections.emptyMap(), ""));

		assertEquals("test", licenceDAO.findById(licenceId).getLicenceKey());
		assertEquals("redirect:/licence.html", view);
	}

	/**
	 * Test if the updating throw a {@link NullPointerException} by passing an
	 * invalid ID argument
	 */
	@Test(expected = NullPointerException.class)
	public void testUpdateWithInvalidId() {
		controller.edit(m, Long.MAX_VALUE);
		Map<String, Object> map = m.asMap();
		Licence l = (Licence) map.get("licence");

		l.setLicenceKey("licenceKey");
		controller.update(l, new MapBindingResult(Collections.emptyMap(), ""));
	}
	
	/**
	 * Test if the model contains a {@link LicenceQueryCommand} object. The following
	 * conditions are checked :<br/>
	 * <ul>
	 * <li>The model contains an attribute called "licenceQuery"</li>
	 * <li>This attribute is not <code>null</code></li>
	 * </ul>
	 */
	@Test
	public void testSearchForm(){
		controller.searchForm(m);
		Map<String, Object> map = m.asMap();

		assertTrue(m.containsAttribute("licenceQuery"));
		LicenceQueryCommand command = (LicenceQueryCommand) map.get("licenceQuery");
		assertNotNull(command);
	}
	
	/**
	 * Test if the result of search is correct. The following
	 * conditions are checked :<br/>
	 * <ul>
	 * <li>The model contains an attribute called "result"</li>
	 * <li>This attribute is not null</li>
	 * <li>This attribute is not empty</li>
	 * <li>The only element in the list is the expected licence</li>
	 * <li>The view is redirected to the licence/result.html page</li>
	 * </ul>
	 */
	@Test
	public void testSearchExactMatch(){
		controller.searchForm(m);
		Map<String, Object> map = m.asMap();
		LicenceQueryCommand command = (LicenceQueryCommand) map.get("licenceQuery");

		command.setKey("key");
		command.setKeyMatchMode(LicenceQueryCommand.EXACT_MATCH_MODE);
		ModelAndView mav = controller.search(command);

		Map<String, Object> model = mav.getModel();
		assertTrue(model.containsKey("result"));
		List<?> result = (List<?>) model.get("result");
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(Data.LICENCE_2, result.get(0));
		assertEquals("licence/result", mav.getViewName());
	}
	
	/**
	 * Test if the returned list of maintenances is correct and complete. First, we
	 * create 2 maintenances, then we compare the result of the maintenances() method and
	 * the findAll() method. At the end of this test, we clear the entity
	 * manager
	 */
	@Test
	public void testMaintenances() {
		Object[] maintenances = maintenanceDAO.findAllEnabled().toArray();
		assertArrayEquals(maintenances, controller.maintenances().toArray());
	}

}
