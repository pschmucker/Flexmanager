package flexcom.casehistory.mvc.controller.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.beans.PropertyEditor;
import java.io.IOException;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.servlet.ModelAndView;

import flexcom.casehistory.data.Data;
import flexcom.casehistory.data.TicketDataSet;
import flexcom.casehistory.data.UserDataSet;
import flexcom.casehistory.mvc.controller.TicketController;
import flexcom.casehistory.mvc.controller.TicketQueryCommand;
import flexcom.casehistory.mvc.controller.UserQueryCommand;
import flexcom.casehistory.mvc.editor.CompanyEditor;
import flexcom.casehistory.mvc.editor.ContactEditor;
import flexcom.casehistory.mvc.editor.PriorityEditor;
import flexcom.casehistory.mvc.editor.ProductEditor;
import flexcom.casehistory.mvc.editor.StatusEditor;
import flexcom.casehistory.mvc.editor.TicketEditor;
import flexcom.casehistory.mvc.editor.UserEditor;
import flexcom.casehistory.ticket.dao.ClientDAO;
import flexcom.casehistory.ticket.dao.ContactDAO;
import flexcom.casehistory.ticket.dao.EventDAO;
import flexcom.casehistory.ticket.dao.NoteDAO;
import flexcom.casehistory.ticket.dao.PriorityDAO;
import flexcom.casehistory.ticket.dao.ProductDAO;
import flexcom.casehistory.ticket.dao.StatusDAO;
import flexcom.casehistory.ticket.dao.TicketDAO;
import flexcom.casehistory.ticket.entity.Client;
import flexcom.casehistory.ticket.entity.Contact;
import flexcom.casehistory.ticket.entity.Event;
import flexcom.casehistory.ticket.entity.Note;
import flexcom.casehistory.ticket.entity.Priority;
import flexcom.casehistory.ticket.entity.Product;
import flexcom.casehistory.ticket.entity.Status;
import flexcom.casehistory.ticket.entity.Ticket;
import flexcom.casehistory.ticket.entity.User;
import flexcom.casehistory.ticket.search.Query;
import flexcom.casehistory.ticket.search.filter.BetweenFilter;

/**
 * Test class for the {@link TicketController}
 * 
 * @author philippe
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class TicketControllerTest {

	/**
	 * A model
	 */
	private static Model m;

	/**
	 * A {@link Note}
	 */
	private Note note;
	
	/**
	 * A {@link Event}
	 */
	private Event event;
	
	/**
	 * The ID of the {@link Ticket}
	 */
	private long ticketId;

	/**
	 * Ticket controller
	 */
	@Autowired
	private TicketController controller;

	/**
	 * Note DAO
	 */
	@Autowired
	private NoteDAO noteDAO;
	
	@Autowired
	private ClientDAO clientDAO;

	@Autowired
	private ProductDAO productDAO;

	@Autowired
	private PriorityDAO priorityDAO;

	@Autowired
	private StatusDAO statusDAO;

	@Autowired
	private TicketDAO ticketDAO;
	
	@Autowired
	private ContactDAO contactDAO;

	/**
	 * Event DAO
	 */
	@Autowired
	private EventDAO eventDAO;
	
	@Autowired
	private TicketDataSet ticketDataSet;
	
	@Autowired
	private UserDataSet userDataSet;

	/**
	 * Setting up the context for all tests : instanciation of the model
	 */
	@SuppressWarnings("deprecation")
	@BeforeClass
	public static void before() {
		m = new ExtendedModelMap();
		org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User("user1", "", true, true, true, true, new GrantedAuthority[]{});
		Authentication auth = new UsernamePasswordAuthenticationToken(user, null) ;
		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	/**
	 * Clearing the context
	 */
	@AfterClass
	public static void after() {
		m = null;
	}

	/**
	 * Create and persist an {@link Ticket} object
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		ticketDataSet.setup();
		userDataSet.setup();

		ticketId = Data.TICKET_1.getId();
		
		event = new Event();
		event.setAction("Action");
		event.setAuthor(Data.USER_1);
		event.setTicket(Data.TICKET_1);
		eventDAO.createEvent(event);
		
		note = new Note();
		note.setNote("Note");
		note.setAuthor(Data.USER_1);
		note.setTicket(Data.TICKET_1);
		noteDAO.createNote(note);
	}

	/**
	 * Clear the entity manager and the created objects
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		eventDAO.deleteAll();
		noteDAO.deleteAll();
		userDataSet.clear();
		ticketDataSet.clear();
	}

	/**
	 * Test if the model contains the list of all tickets. The following
	 * conditions are checked :<br/>
	 * <ul>
	 * <li>The model contains an attribute called "list"</li>
	 * <li>This list contains only one object</li>
	 * <li>This object is equal to the {@link Ticket} created before in setUp()
	 * method</li>
	 * </ul>
	 */
	@Test
	public void testList() {
		controller.list(m);
		Map<String, Object> map = m.asMap();

		assertTrue(m.containsAttribute("list"));
		List<?> tickets = (List<?>) map.get("list");
		assertEquals(5, tickets.size());
		assertEquals(Data.TICKET_1, tickets.get(0));
	}

	/**
	 * Test if the model contains the correct {@link Ticket} object, 
	 * the list of all events for this given ticket, and 
	 * the list of all notes for this given ticket. The following conditions are checked :<br/>
	 * <ul>
	 * <li>The model contains an attribute called "ticket"</li>
	 * <li>The attribute "ticket" is equal to the {@link Ticket} created before
	 * in setUp() method</li>
	 * <li>The model contains an attribute called "events"</li>
	 * <li>This list contains only one object</li>
	 * <li>This object is equal to the {@link Event} created before in setUp()
	 * method</li>
	 * <li>The model contains an attribute called "notes"</li>
	 * <li>This list contains only one object</li>
	 * <li>This object is equal to the {@link Note} created before in setUp()
	 * method</li>
	 * <li>The model contains an attribute called "note"</li>
	 * <li>This attribute is not <code>null</code></li>
	 * </ul>
	 */
	@Test
	public void testView() {
		controller.view(m, ticketId);
		Map<String, Object> map = m.asMap();

		assertTrue(m.containsAttribute("ticket"));
		Ticket t = (Ticket) map.get("ticket");
		assertEquals(Data.TICKET_1, t);

		assertTrue(m.containsAttribute("events"));
		List<?> events = (List<?>) map.get("events");
		assertEquals(1, events.size());
		assertEquals(event, events.get(0));

		assertTrue(m.containsAttribute("notes"));
		List<?> notes = (List<?>) map.get("notes");
		assertEquals(1, notes.size());
		assertEquals(note, notes.get(0));
		
		assertTrue(m.containsAttribute("note"));
		Note n = (Note) map.get("note");
		assertNotNull(n);
	}

	/**
	 * Test if the model contains a <code>null</code> {@link Ticket} by passing
	 * an invalid ID argument. The following conditions are checked :<br/>
	 * <ul>
	 * <li>The model contains an attribute called "ticket"</li>
	 * <li>This attribute has a <code>null</code> value</li>
	 * </ul>
	 */
	@Test
	public void testViewWithInvalidId() {
		controller.view(m, Long.MAX_VALUE);
		Map<String, Object> map = m.asMap();

		assertTrue(m.containsAttribute("ticket"));
		assertNull(map.get("ticket"));
	}

	/**
	 * Test if the model contains a {@link Ticket} object. The following
	 * conditions are checked :<br/>
	 * <ul>
	 * <li>The model contains an attribute called "ticket"</li>
	 * <li>This attribute is not <code>null</code></li>
	 * </ul>
	 */
	@Test
	public void testAdd() {
		controller.add(m);
		Map<String, Object> map = m.asMap();

		assertTrue(m.containsAttribute("ticket"));
		Ticket t = (Ticket) map.get("ticket");
		assertNotNull(t);
	}

	/**
	 * Test if the {@link Ticket} has been well created. The following
	 * conditions are checked :<br/>
	 * <ul>
	 * <li>The created {@link Ticket} can be found with the {@link TicketDAO}</li>
	 * <li>The view is redirected to the ticket.html page</li>
	 * </ul>
	 */
	@Test
	public void testCreate() throws InterruptedException {
		controller.add(m);
		Map<String, Object> map = m.asMap();
		Ticket t = (Ticket) map.get("ticket");

		Thread.sleep(1000);
		Date before = new Date();
		t.setTitle("test");
		t.setClient(Data.CLIENT_1);
		t.setProduct(Data.PRODUCT_1);
		t.setPriority(Data.PRIORITY_HIGH);
		t.setStatus(Data.STATUS_ASSIGNED);
		t.setContact(Data.CONTACT_1);
		String view = controller.create(t, new MapBindingResult(Collections.emptyMap(), ""));
		Date after = new Date();
		long id = t.getId();

		Query<Ticket> query = new Query<Ticket>(Ticket.class);
		query.addFilter(new BetweenFilter("creationDate", before, after));
		List<Ticket> tickets = ticketDAO.filter(query);

		assertEquals(1, tickets.size());
		assertEquals(id, tickets.get(0).getId());
		assertEquals(t, tickets.get(0));
		assertEquals("redirect:/ticket.html", view);
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
		assertArrayEquals(clients, controller.clients().toArray());
	}

	/**
	 * Test if the returned list of products is correct and complete. First, we
	 * create 2 products, then we compare the result of the products() method
	 * and the findAll() method. At the end of this test, we clear the entity
	 * manager
	 */
	@Test
	public void testProducts() {
		Object[] products = productDAO.findAll().toArray();
		assertArrayEquals(products, controller.products().toArray());
	}

	/**
	 * Test if the returned list of contacts is correct and complete. First, we
	 * create 2 contacts, then we compare the result of the contacts() method
	 * and the findAll() method. At the end of this test, we clear the entity
	 * manager
	 */
	@Test
	public void testContacts() {
		Object[] contacts = contactDAO.findAll().toArray();
		assertArrayEquals(contacts, controller.contacts().toArray());
	}

	/**
	 * Test if the returned list of priorities is correct and complete. First, we
	 * create 2 priorities, then we compare the result of the priorities() method
	 * and the findAll() method. At the end of this test, we clear the entity
	 * manager
	 */
	@Test
	public void testPriorities() {
		Object[] priorities = priorityDAO.findAll().toArray();
		assertArrayEquals(priorities, controller.priorities().toArray());
	}

	/**
	 * Test if the returned list of statuses is correct and complete. First, we
	 * create 2 statuses, then we compare the result of the statuses() method
	 * and the findAll() method. At the end of this test, we clear the entity
	 * manager
	 */
	@Test
	public void testStatuses() {
		Object[] statuses = statusDAO.findAll().toArray();
		assertArrayEquals(statuses, controller.statuses().toArray());
	}

	/**
	 * Test if the 5 {@link PropertyEditor} have been well registered. The
	 * following conditions are checked :<br/>
	 * <ul>
	 * <li>A property editor for {@link Client} object is found and is not
	 * <code>null</code></li>
	 * <li>A property editor for {@link Product} object is found and is not
	 * <code>null</code></li>
	 * <li>A property editor for {@link Priority} object is found and is not
	 * <code>null</code></li>
	 * <li>A property editor for {@link Status} object is found and is not
	 * <code>null</code></li>
	 * <li>A property editor for {@link Ticket} object is found and is not
	 * <code>null</code></li>
	 * <li>A property editor for {@link Date} object is found and is not
	 * <code>null</code></li>
	 * <li>The {@link PropertyEditor} clientEditor is an instance of
	 * {@link CompanyEditor}</li>
	 * <li>The {@link PropertyEditor} productEditor is an instance of
	 * {@link ProductEditor}</li>
	 * <li>The {@link PropertyEditor} priorityEditor is an instance of
	 * {@link PriorityEditor}</li>
	 * <li>The {@link PropertyEditor} statusEditor is an instance of
	 * {@link StatusEditor}</li>
	 * <li>The {@link PropertyEditor} userEditor is an instance of
	 * {@link UserEditor}</li>
	 * <li>The {@link PropertyEditor} ticketEditor is an instance of
	 * {@link TicketEditor}</li>
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
		PropertyEditor contactEditor = binder.findCustomEditor(Contact.class, null);
		PropertyEditor priorityEditor = binder.findCustomEditor(Priority.class, null);
		PropertyEditor statusEditor = binder.findCustomEditor(Status.class, null);
		PropertyEditor userEditor = binder.findCustomEditor(User.class, null);
		PropertyEditor dateEditor = binder.findCustomEditor(Date.class, null);
		PropertyEditor ticketEditor = binder.findCustomEditor(Ticket.class, null);

		assertNotNull(clientEditor);
		assertNotNull(productEditor);
		assertNotNull(contactEditor);
		assertNotNull(priorityEditor);
		assertNotNull(statusEditor);
		assertNotNull(userEditor);
		assertNotNull(ticketEditor);
		assertNotNull(dateEditor);
		assertTrue(clientEditor instanceof CompanyEditor);
		assertTrue(productEditor instanceof ProductEditor);
		assertTrue(contactEditor instanceof ContactEditor);
		assertTrue(priorityEditor instanceof PriorityEditor);
		assertTrue(statusEditor instanceof StatusEditor);
		assertTrue(userEditor instanceof UserEditor);
		assertTrue(ticketEditor instanceof TicketEditor);
		assertTrue(dateEditor instanceof CustomDateEditor);
	}

	/**
	 * Test if the {@link Ticket} has been well deleted. The following
	 * conditions are checked :<br/>
	 * <ul>
	 * <li>The deleted {@link Ticket} can't be found with the {@link TicketDAO}</li>
	 * <li>The view is redirected to the ticket.html page</li>
	 * </ul>
	 */
	@Test
	public void testDelete() {
		String view = controller.delete(ticketId);
		Ticket ticket = ticketDAO.findById(ticketId);
		assertFalse(ticket.isEnabled());
		assertEquals("redirect:/ticket.html", view);
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
	 * Test if the model contains a {@link Ticket} object. The following
	 * conditions are checked :<br/>
	 * <ul>
	 * <li>The model contains an attribute called "ticket"</li>
	 * <li>This attribute is equal to the {@link Ticket} created before in
	 * setUp() method</li>
	 * </ul>
	 */
	@Test
	public void testEdit() {
		controller.edit(m, ticketId);
		Map<String, Object> map = m.asMap();

		assertTrue(m.containsAttribute("ticket"));
		Ticket t = (Ticket) map.get("ticket");
		assertEquals(Data.TICKET_1, t);
	}

	/**
	 * Test if the model contains a <code>null</code> {@link Ticket} by passing
	 * an invalid ID argument. The following conditions are checked :<br/>
	 * <ul>
	 * <li>The model contains an attribute called "ticket"</li>
	 * <li>This attribute has a <code>null</code> value</li>
	 * </ul>
	 */
	@Test
	public void testEditWithInvalidId() {
		controller.edit(m, Long.MAX_VALUE);
		Map<String, Object> map = m.asMap();

		assertTrue(m.containsAttribute("ticket"));
		assertNull(map.get("ticket"));
	}

	/**
	 * Test if the {@link Ticket} has been well updated. The following
	 * conditions are checked :<br/>
	 * <ul>
	 * <li>The new value for the updated property can be read with the
	 * {@link TicketDAO}</li>
	 * <li>The view is redirected to the ticket.html page</li>
	 * </ul>
	 */
	@Test
	public void testUpdate() {
		controller.edit(m, ticketId);
		Map<String, Object> map = m.asMap();
		Ticket t = (Ticket) map.get("ticket");

		t.setTitle("test");
		String view = controller.update(t, new MapBindingResult(Collections.emptyMap(), ""));

		assertEquals("test", ticketDAO.findById(ticketId).getTitle());
		assertEquals("redirect:/ticket.html", view);
	}

	/**
	 * Test if the updating throw a {@link NullPointerException} by passing an
	 * invalid ID argument
	 */
	@Test(expected = NullPointerException.class)
	public void testUpdateWithInvalidId() {
		controller.edit(m, Long.MAX_VALUE);
		Map<String, Object> map = m.asMap();
		Ticket t = (Ticket) map.get("ticket");

		t.setTitle("test");
		controller.update(t, new MapBindingResult(Collections.emptyMap(), ""));
	}
	
	/**
	 * Test if the model contains a {@link TicketQueryCommand} object. The following
	 * conditions are checked :<br/>
	 * <ul>
	 * <li>The model contains an attribute called "ticketQuery"</li>
	 * <li>This attribute is not <code>null</code></li>
	 * </ul>
	 */
	@Test
	public void testSearchForm(){
		controller.searchForm(m);
		Map<String, Object> map = m.asMap();

		assertTrue(m.containsAttribute("ticketQuery"));
		TicketQueryCommand command = (TicketQueryCommand) map.get("ticketQuery");
		assertNotNull(command);
	}
	
	/**
	 * Test if the result of search is correct. The following
	 * conditions are checked :<br/>
	 * <ul>
	 * <li>The model contains an attribute called "result"</li>
	 * <li>This attribute is not null</li>
	 * <li>This attribute is not empty</li>
	 * <li>The only element in the list is the expected ticket</li>
	 * <li>The view is redirected to the ticket/result.html page</li>
	 * </ul>
	 */
	@Test
	public void testSearchExactMatch(){
		controller.searchForm(m);
		Map<String, Object> map = m.asMap();
		TicketQueryCommand command = (TicketQueryCommand) map.get("ticketQuery");

		command.setTitle("T1");
		command.setTitleMatchMode(UserQueryCommand.EXACT_MATCH_MODE);
		ModelAndView mav = controller.search(command);

		Map<String, Object> model = mav.getModel();
		assertTrue(model.containsKey("result"));
		List<?> result = (List<?>) model.get("result");
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(Data.TICKET_1, result.get(0));
		assertEquals("ticket/result", mav.getViewName());
	}
	
	/**
	 * Test if the {@link Note} has been well created. The following
	 * conditions are checked :<br/>
	 * <ul>
	 * <li>The created {@link Note} can be found with the {@link NoteDAO}</li>
	 * <li>The view is redirected to the ticket/view.html page</li>
	 * </ul>
	 * @throws IOException 
	 */
	@Test
	public void testCreateNote() throws IOException {
		controller.view(m, ticketId);
		Map<String, Object> map = m.asMap();
		Note n = (Note) map.get("note");

		n.setNote("test");
		n.setAuthor(Data.USER_1);
		n.setTicket(Data.TICKET_1);
		String view = controller.createNote(n, new MapBindingResult(Collections.emptyMap(), ""), ticketId, new MockMultipartFile("f", new byte[]{}));

		List<Note> notes = noteDAO.findByNote("test");

		assertEquals(1, notes.size());
		assertEquals(Data.USER_1, notes.get(0).getAuthor());
		assertEquals(Data.TICKET_1, notes.get(0).getTicket());
		assertEquals("redirect:/ticket/view.html?id=" + ticketId, view);
	}

}
