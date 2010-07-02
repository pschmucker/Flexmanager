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

import flexcom.casehistory.mvc.controller.ClientController;
import flexcom.casehistory.mvc.controller.CompanyQueryCommand;
import flexcom.casehistory.ticket.dao.ClientDAO;
import flexcom.casehistory.ticket.dao.MaintenanceDAO;
import flexcom.casehistory.ticket.entity.Client;
import flexcom.casehistory.ticket.entity.Maintenance;

/**
 * Test class for the {@link ClientController}
 * 
 * @author philippe
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class ClientControllerTest {

	/**
	 * A model
	 */
	private static Model m;

	/**
	 * A {@link Client}
	 */
	private Client client;

	/**
	 * The ID of the {@link Client}
	 */
	private long clientId;

	/**
	 * Client controller
	 */
	@Autowired
	private ClientController controller;

	/**
	 * Client DAO
	 */
	@Autowired
	private ClientDAO clientDAO;

	/**
	 * Maintenance DAO
	 */
	@Autowired
	private MaintenanceDAO maintenanceDAO;
	
	/**
	 * A Maintenance
	 */
	private Maintenance maintenance;

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
	 * Create and persist an {@link Client} object
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		maintenance = Maintenance.getMaintenance(Maintenance.TEST);
		maintenanceDAO.createMaintenance(maintenance);
		
		client = new Client();
		client.setName("client");
		clientDAO.createClient(client);

		clientId = client.getId();
	}

	/**
	 * Clear the entity manager and the {@link Client} object
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		clientDAO.deleteAll();
		maintenanceDAO.deleteAll();
		client = null;
	}

	/**
	 * Test if the model contains the list of all products. The following
	 * conditions are checked :<br/>
	 * <ul>
	 * <li>The model contains an attribute called "list"</li>
	 * <li>This list contains only one object</li>
	 * <li>This object is equal to the {@link Client} created before in setUp()
	 * method</li>
	 * </ul>
	 */
	@Test
	public void testList() {
		controller.list(m);
		Map<String, Object> map = m.asMap();

		assertTrue(m.containsAttribute("list"));
		List<?> clients = (List<?>) map.get("list");
		assertEquals(1, clients.size());
		assertEquals(client, clients.get(0));
	}

	/**
	 * Test if the model contains the correct {@link Client} object. The
	 * following conditions are checked :<br/>
	 * <ul>
	 * <li>The model contains an attribute called "client"</li>
	 * <li>The attribute "client" is equal to the {@link Client} created before
	 * in setUp() method</li>
	 * </ul>
	 */
	@Test
	public void testView() {
		controller.view(m, clientId);
		Map<String, Object> map = m.asMap();

		assertTrue(m.containsAttribute("client"));
		Client c = (Client) map.get("client");
		assertEquals(client, c);
	}

	/**
	 * Test if the model contains a <code>null</code> {@link Client} by passing
	 * an invalid ID argument. The following conditions are checked :<br/>
	 * <ul>
	 * <li>The model contains an attribute called "client"</li>
	 * <li>This attribute has a <code>null</code> value</li>
	 * </ul>
	 */
	@Test
	public void testViewWithInvalidId() {
		controller.view(m, Long.MAX_VALUE);
		Map<String, Object> map = m.asMap();

		assertTrue(m.containsAttribute("client"));
		assertNull(map.get("client"));
	}

	/**
	 * Test if the model contains a {@link Client} object. The following
	 * conditions are checked :<br/>
	 * <ul>
	 * <li>The model contains an attribute called "client"</li>
	 * <li>This attribute is not <code>null</code></li>
	 * </ul>
	 */
	@Test
	public void testAdd() {
		controller.add(m);
		Map<String, Object> map = m.asMap();

		assertTrue(m.containsAttribute("client"));
		Client c = (Client) map.get("client");
		assertNotNull(c);
	}

	/**
	 * Test if the {@link Client} has been well created. The following
	 * conditions are checked :<br/>
	 * <ul>
	 * <li>The created {@link Client} can be found with the {@link ClientDAO}</li>
	 * <li>The view is redirected to the client.html page</li>
	 * </ul>
	 */
	@Test
	public void testCreate() {
		controller.add(m);
		Map<String, Object> map = m.asMap();
		Client c = (Client) map.get("client");

		c.setName("company");
		String view = controller.create(c, new MapBindingResult(Collections.emptyMap(), ""));
		long id = c.getId();

		assertEquals(c, clientDAO.findById(id));
		assertEquals("redirect:/client.html", view);
	}

	/**
	 * Test if the {@link Client} has been well deleted. The following
	 * conditions are checked :<br/>
	 * <ul>
	 * <li>The deleted {@link Client} can't be found with the {@link ClientDAO}</li>
	 * <li>The view is redirected to the client.html page</li>
	 * </ul>
	 */
	@Test
	public void testDelete() {
		String view = controller.delete(clientId);
		Client client = clientDAO.findById(clientId);
		assertFalse(client.isEnabled());
		assertEquals("redirect:/client.html", view);
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
	 * Test if the model contains a {@link Client} object. The following
	 * conditions are checked :<br/>
	 * <ul>
	 * <li>The model contains an attribute called "client"</li>
	 * <li>This attribute is equal to the {@link Client} created before in
	 * setUp() method</li>
	 * </ul>
	 */
	@Test
	public void testEdit() {
		controller.edit(m, clientId);
		Map<String, Object> map = m.asMap();

		assertTrue(m.containsAttribute("client"));
		Client c = (Client) map.get("client");
		assertEquals(client, c);
	}

	/**
	 * Test if the model contains a <code>null</code> {@link Client} by passing
	 * an invalid ID argument. The following conditions are checked :<br/>
	 * <ul>
	 * <li>The model contains an attribute called "client"</li>
	 * <li>This attribute has a <code>null</code> value</li>
	 * </ul>
	 */
	@Test
	public void testEditWithInvalidId() {
		controller.edit(m, Long.MAX_VALUE);
		Map<String, Object> map = m.asMap();

		assertTrue(m.containsAttribute("client"));
		assertNull(map.get("client"));
	}

	/**
	 * Test if the {@link Client} has been well updated. The following
	 * conditions are checked :<br/>
	 * <ul>
	 * <li>The new value for the updated property can be read with the
	 * {@link ClientDAO}</li>
	 * <li>The view is redirected to the client.html page</li>
	 * </ul>
	 */
	@Test
	public void testUpdate() {
		controller.edit(m, clientId);
		Map<String, Object> map = m.asMap();
		Client c = (Client) map.get("client");

		c.setName("Sun");
		String view = controller.update(c, new MapBindingResult(Collections.emptyMap(), ""));

		assertEquals("Sun", clientDAO.findById(clientId).getName());
		assertEquals("redirect:/client.html", view);
	}

	/**
	 * Test if the updating throw a {@link NullPointerException} by passing an
	 * invalid ID argument
	 */
	@Test(expected = NullPointerException.class)
	public void testUpdateWithInvalidId() {
		controller.edit(m, Long.MAX_VALUE);
		Map<String, Object> map = m.asMap();
		Client c = (Client) map.get("client");

		c.setName("Sun");
		controller.update(c, new MapBindingResult(Collections.emptyMap(), ""));
	}

	/**
	 * Test if the model contains a {@link CompanyQueryCommand} object. The
	 * following conditions are checked :<br/>
	 * <ul>
	 * <li>The model contains an attribute called "clientQuery"</li>
	 * <li>This attribute is not <code>null</code></li>
	 * </ul>
	 */
	@Test
	public void testSearchForm() {
		controller.searchForm(m);
		Map<String, Object> map = m.asMap();

		assertTrue(m.containsAttribute("companyQuery"));
		CompanyQueryCommand command = (CompanyQueryCommand) map.get("companyQuery");
		assertNotNull(command);
	}

	/**
	 * Test if the result of search is correct. The following conditions are
	 * checked :<br/>
	 * <ul>
	 * <li>The model contains an attribute called "result"</li>
	 * <li>This attribute is not null</li>
	 * <li>This attribute is not empty</li>
	 * <li>The only element in the list is the expected client</li>
	 * <li>The view is redirected to the client/result.html page</li>
	 * </ul>
	 */
	@Test
	public void testSearchExactMatch() {
		controller.searchForm(m);
		Map<String, Object> map = m.asMap();
		CompanyQueryCommand command = (CompanyQueryCommand) map.get("companyQuery");

		command.setCompanyName("client");
		command.setMatchMode(CompanyQueryCommand.EXACT_MATCH_MODE);
		ModelAndView mav = controller.search(command);

		Map<String, Object> model = mav.getModel();
		assertTrue(model.containsKey("result"));
		List<?> result = (List<?>) model.get("result");
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(client, result.get(0));
		assertEquals("client/result", mav.getViewName());
	}

}
