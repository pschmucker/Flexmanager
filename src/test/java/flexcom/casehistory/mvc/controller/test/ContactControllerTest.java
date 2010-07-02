package flexcom.casehistory.mvc.controller.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.beans.PropertyEditor;
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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.servlet.ModelAndView;

import flexcom.casehistory.data.ContactDataSet;
import flexcom.casehistory.data.Data;
import flexcom.casehistory.mvc.controller.ContactController;
import flexcom.casehistory.mvc.controller.ContactQueryCommand;
import flexcom.casehistory.mvc.controller.TicketIdCommand;
import flexcom.casehistory.mvc.controller.UserQueryCommand;
import flexcom.casehistory.mvc.editor.CompanyEditor;
import flexcom.casehistory.ticket.dao.ClientDAO;
import flexcom.casehistory.ticket.dao.ContactDAO;
import flexcom.casehistory.ticket.dao.UserDAO;
import flexcom.casehistory.ticket.entity.Company;
import flexcom.casehistory.ticket.entity.Contact;
import flexcom.casehistory.ticket.entity.User;

/**
 * Test class for the {@link ContactController}
 * 
 * @author philippe
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class ContactControllerTest {

	/**
	 * A model
	 */
	private static Model m;

	/**
	 * The ID for the {@link Contact}
	 */
	private long contactId;

	/**
	 * Contact controller
	 */
	@Autowired
	private ContactController controller;

	/**
	 * Contact DAO
	 */
	@Autowired
	private ContactDAO contactDAO;
	
	@Autowired
	private ClientDAO clientDAO;
	
	@Autowired
	private ContactDataSet contactDataSet;
	
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
	 * Create and persist an {@link Contact} object
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		contactDataSet.setup();
		contactId = Data.CONTACT_1.getId();
	}

	/**
	 * Clear the entity manager and the {@link Contact} object
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		contactDataSet.clear();
	}

	/**
	 * Test if the model contains the list of all contacts. The following
	 * conditions are checked :<br/>
	 * <ul>
	 * <li>The model contains an attribute called "list"</li>
	 * <li>This list contains only one object</li>
	 * <li>This object is equal to the {@link Contact} created before in setUp()
	 * method</li>
	 * </ul>
	 */
	@Test
	public void testList() {
		controller.list(m);
		Map<String, Object> map = m.asMap();

		assertTrue(m.containsAttribute("list"));
		List<?> contacts = (List<?>) map.get("list");
		assertEquals(2, contacts.size());
		assertTrue(contacts.contains(Data.CONTACT_1));
		assertTrue(contacts.contains(Data.CONTACT_2));
	}

	/**
	 * Test if the model contains the correct {@link Contact} object. The following conditions are checked :<br/>
	 * <ul>
	 * <li>The model contains an attribute called "user"</li>
	 * <li>The model contains an attribute called "ticketIdCommand"</li>
	 * <li>The attribute "ticketIdCommand" is an instance of
	 * {@link TicketIdCommand}</li>
	 * <li>The attribute "user" is equal to the {@link User} created before in
	 * setUp() method</li>
	 * </ul>
	 */
	@Test
	public void testView() {
		controller.view(m, contactId);
		Map<String, Object> map = m.asMap();

		assertTrue(m.containsAttribute("contact"));

		Contact c = (Contact) map.get("contact");
		assertEquals(Data.CONTACT_1, c);
	}

	/**
	 * Test if the model contains a <code>null</code> {@link User} by passing an
	 * invalid ID argument. The following conditions are checked :<br/>
	 * <ul>
	 * <li>The model contains an attribute called "user"</li>
	 * <li>This attribute has a <code>null</code> value</li>
	 * </ul>
	 */
	@Test
	public void testViewWithInvalidId() {
		controller.view(m, Long.MAX_VALUE);
		Map<String, Object> map = m.asMap();

		assertTrue(m.containsAttribute("contact"));
		assertNull(map.get("contact"));
	}

	/**
	 * Test if the model contains a {@link User} object. The following
	 * conditions are checked :<br/>
	 * <ul>
	 * <li>The model contains an attribute called "user"</li>
	 * <li>This attribute is not <code>null</code></li>
	 * </ul>
	 */
	@Test
	public void testAdd() {
		controller.add(m);
		Map<String, Object> map = m.asMap();

		assertTrue(m.containsAttribute("contact"));
		Contact c = (Contact) map.get("contact");
		assertNotNull(c);
	}

	/**
	 * Test if the {@link User} has been well created. The following conditions
	 * are checked :<br/>
	 * <ul>
	 * <li>The created {@link User} can be found with the {@link UserDAO}</li>
	 * <li>The view is redirected to the user.html page</li>
	 * </ul>
	 */
	@Test
	public void testCreate() {
		controller.add(m);
		Map<String, Object> map = m.asMap();
		Contact c = (Contact) map.get("contact");

		c.setName("temp");
		c.setCompany(Data.CLIENT_1);
		
		String view = controller.create(c, new MapBindingResult(Collections.emptyMap(), ""));
		long id = c.getId();

		assertEquals(c, contactDAO.findById(id));
		assertEquals("redirect:/contact.html", view);
	}

	/**
	 * Test if the {@link User} has been well deleted. The following conditions
	 * are checked :<br/>
	 * <ul>
	 * <li>The deleted {@link User} can't be found with the {@link UserDAO}</li>
	 * <li>The view is redirected to the user.html page</li>
	 * </ul>
	 */
	@Test
	public void testDelete() {
		String view = controller.delete(contactId);
		Contact contact = contactDAO.findById(contactId);
		assertFalse(contact.isEnabled());
		assertEquals("redirect:/contact.html", view);
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
	 * Test if the model contains a {@link User} object. The following
	 * conditions are checked :<br/>
	 * <ul>
	 * <li>The model contains an attribute called "user"</li>
	 * <li>This attribute is equal to the {@link User} created before in setUp()
	 * method</li>
	 * </ul>
	 */
	@Test
	public void testEdit() {
		controller.edit(m, contactId);
		Map<String, Object> map = m.asMap();

		assertTrue(m.containsAttribute("contact"));
		Contact c = (Contact) map.get("contact");
		assertEquals(Data.CONTACT_1, c);
	}

	/**
	 * Test if the model contains a <code>null</code> {@link User} by passing an
	 * invalid ID argument. The following conditions are checked :<br/>
	 * <ul>
	 * <li>The model contains an attribute called "user"</li>
	 * <li>This attribute has a <code>null</code> value</li>
	 * </ul>
	 */
	@Test
	public void testEditWithInvalidId() {
		controller.edit(m, Long.MAX_VALUE);
		Map<String, Object> map = m.asMap();

		assertTrue(m.containsAttribute("contact"));
		assertNull(map.get("contact"));
	}

	/**
	 * Test if the {@link User} has been well updated. The following conditions
	 * are checked :<br/>
	 * <ul>
	 * <li>The new value for the updated property can be read with the
	 * {@link UserDAO}</li>
	 * <li>The view is redirected to the user.html page</li>
	 * </ul>
	 */
	@Test
	public void testUpdate() {
		controller.edit(m, contactId);
		Map<String, Object> map = m.asMap();
		Contact c = (Contact) map.get("contact");

		c.setName("Super contact");
		String view = controller.update(c, new MapBindingResult(Collections.emptyMap(), ""));

		assertEquals("Super contact", contactDAO.findById(contactId).getName());
		assertEquals("redirect:/contact.html", view);
	}

	/**
	 * Test if the updating throw a {@link NullPointerException} by passing an
	 * invalid ID argument
	 */
	@Test(expected = NullPointerException.class)
	public void testUpdateWithInvalidId() {
		controller.edit(m, Long.MAX_VALUE);
		Map<String, Object> map = m.asMap();
		Contact c = (Contact) map.get("contact");

		c.setName("Super contact");
		controller.update(c, new MapBindingResult(Collections.emptyMap(), ""));
	}

	/**
	 * Test if the model contains a {@link UserQueryCommand} object. The following
	 * conditions are checked :<br/>
	 * <ul>
	 * <li>The model contains an attribute called "userQuery"</li>
	 * <li>This attribute is not <code>null</code></li>
	 * </ul>
	 */
	@Test
	public void testSearchForm(){
		controller.searchForm(m);
		Map<String, Object> map = m.asMap();

		assertTrue(m.containsAttribute("contactQuery"));
		ContactQueryCommand command = (ContactQueryCommand) map.get("contactQuery");
		assertNotNull(command);
	}
	
	/**
	 * Test if the result of search is correct. The following
	 * conditions are checked :<br/>
	 * <ul>
	 * <li>The model contains an attribute called "result"</li>
	 * <li>This attribute is not null</li>
	 * <li>This attribute is not empty</li>
	 * <li>The only element in the list is the expected user</li>
	 * <li>The view is redirected to the user/result.html page</li>
	 * </ul>
	 */
	@Test
	public void testSearchExactMatch(){
		controller.searchForm(m);
		Map<String, Object> map = m.asMap();
		ContactQueryCommand command = (ContactQueryCommand) map.get("contactQuery");

		command.setContactName("X");
		command.setMatchMode(ContactQueryCommand.SUBSTRING_MATCH_MODE);
		ModelAndView mav = controller.search(command);

		Map<String, Object> model = mav.getModel();
		assertTrue(model.containsKey("result"));
		List<?> result = (List<?>) model.get("result");
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(Data.CONTACT_1, result.get(0));
		assertEquals("contact/result", mav.getViewName());
	}
	
	/**
	 * Test if the returned list of clients is correct and complete. First, we
	 * create 2 clients, then we compare the result of the clients() method and
	 * the findAll() method. At the end of this test, we clear the entity
	 * manager
	 */
	@Test
	public void testClients() {
		Object[] clients = clientDAO.findAll().toArray();
		assertArrayEquals(clients, controller.companies().toArray());
	}

	@Test
	public void testBinder() {
		WebDataBinder binder = new WebDataBinder(null);
		controller.binder(binder);

		PropertyEditor clientEditor = binder.findCustomEditor(Company.class, null);
		assertNotNull(clientEditor);
		assertTrue(clientEditor instanceof CompanyEditor);
	}
}
