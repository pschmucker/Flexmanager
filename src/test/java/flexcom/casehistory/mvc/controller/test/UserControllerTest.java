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
import flexcom.casehistory.mvc.controller.TicketIdCommand;
import flexcom.casehistory.mvc.controller.UserController;
import flexcom.casehistory.mvc.controller.UserQueryCommand;
import flexcom.casehistory.mvc.editor.RoleEditor;
import flexcom.casehistory.mvc.editor.TicketEditor;
import flexcom.casehistory.ticket.dao.RoleDAO;
import flexcom.casehistory.ticket.dao.TicketDAO;
import flexcom.casehistory.ticket.dao.UserDAO;
import flexcom.casehistory.ticket.entity.Role;
import flexcom.casehistory.ticket.entity.Ticket;
import flexcom.casehistory.ticket.entity.User;

/**
 * Test class for the {@link UserController}
 * 
 * @author philippe
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class UserControllerTest {

	/**
	 * A model
	 */
	private static Model m;

	/**
	 * The ID for the {@link User}
	 */
	private long userId;

	/**
	 * User controller
	 */
	@Autowired
	private UserController controller;

	/**
	 * User DAO
	 */
	@Autowired
	private UserDAO userDAO;

	@Autowired
	private RoleDAO roleDAO;

	/**
	 * Ticket DAO
	 */
	@Autowired
	private TicketDAO ticketDAO;

	@Autowired
	private UserDataSet userDataSet;
	
	@Autowired
	private TicketDataSet ticketDataSet;
	
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
	 * Create and persist an {@link User} object
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		userDataSet.setup();
		userId = Data.USER_1.getId();
	}

	/**
	 * Clear the entity manager and the {@link User} object
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		userDataSet.clear();
	}

	/**
	 * Test if the model contains the list of all users. The following
	 * conditions are checked :<br/>
	 * <ul>
	 * <li>The model contains an attribute called "list"</li>
	 * <li>This list contains only one object</li>
	 * <li>This object is equal to the {@link User} created before in setUp()
	 * method</li>
	 * </ul>
	 */
	@Test
	public void testList() {
		controller.list(m);
		Map<String, Object> map = m.asMap();

		assertTrue(m.containsAttribute("list"));
		List<?> users = (List<?>) map.get("list");
		assertEquals(3, users.size());
		assertTrue(users.contains(Data.USER_1));
		assertTrue(users.contains(Data.USER_2));
		assertTrue(users.contains(Data.USER_3));
	}

	/**
	 * Test if the model contains the correct {@link User} object and a
	 * {@link TicketIdCommand} object. The following conditions are checked :<br/>
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
		controller.view(m, userId);
		Map<String, Object> map = m.asMap();

		assertTrue(m.containsAttribute("user"));
		assertTrue(m.containsAttribute("ticketIdCommand"));

		TicketIdCommand t = (TicketIdCommand) map.get("ticketIdCommand");
		assertTrue(t instanceof TicketIdCommand);

		User u = (User) map.get("user");
		assertEquals(Data.USER_1, u);
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

		assertTrue(m.containsAttribute("user"));
		assertNull(map.get("user"));
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

		assertTrue(m.containsAttribute("user"));
		User u = (User) map.get("user");
		assertNotNull(u);
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
		User u = (User) map.get("user");

		u.setLogin("temp");
		u.setPassword("password");
		u.setName("name");
		u.setEmail("temp@yopmail.com");
		String view = controller.create(u, new MapBindingResult(Collections.emptyMap(), ""));
		long id = u.getId();

		assertEquals(u, userDAO.findById(id));
		assertEquals("redirect:/user.html", view);
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
	public void testDeleteLoggedUser() {
		String view = controller.delete(userId);
		User user = userDAO.findById(userId);
		assertFalse(user.isEnabled());
		assertEquals("redirect:/j_spring_security_logout", view);
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
		controller.edit(m, userId);
		Map<String, Object> map = m.asMap();

		assertTrue(m.containsAttribute("user"));
		User u = (User) map.get("user");
		assertEquals(Data.USER_1, u);
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

		assertTrue(m.containsAttribute("user"));
		assertNull(map.get("user"));
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
		controller.edit(m, userId);
		Map<String, Object> map = m.asMap();
		User u = (User) map.get("user");

		u.setName("Super user");
		String view = controller.update(u, new MapBindingResult(Collections.emptyMap(), ""));

		assertEquals("Super user", userDAO.findById(userId).getName());
		assertEquals("redirect:/user.html", view);
	}

	/**
	 * Test if the updating throw a {@link NullPointerException} by passing an
	 * invalid ID argument
	 */
	@Test(expected = NullPointerException.class)
	public void testUpdateWithInvalidId() {
		controller.edit(m, Long.MAX_VALUE);
		Map<String, Object> map = m.asMap();
		User u = (User) map.get("user");

		u.setName("Super user");
		controller.update(u, new MapBindingResult(Collections.emptyMap(), ""));
	}

	/**
	 * Test if the returned list of tickets is correct and complete. First, we
	 * create 3 tickets, then we compare the result of the tickets() method and
	 * the findAll() method. At the end of this test, we clear the entity
	 * manager
	 */
	@Test
	public void testTickets() {
		ticketDataSet.setup();

		Object[] tickets = ticketDAO.findAllEnabled().toArray();
		assertArrayEquals(controller.tickets(userId).toArray(), tickets);

		ticketDataSet.clear();
	}

	/**
	 * Test if the returned list of tickets is correct and complete. First, we
	 * create 3 tickets, then we assign a ticket to a user. Finally, we compare
	 * the result of the tickets() with the unassigned tickets list. At the end
	 * of this test, we clear the entity manager
	 */
	@Test
	public void testNotAssignedTickets() {
		ticketDataSet.setup();
		
		Data.USER_1.assign(Data.TICKET_1);
		userDAO.updateUser(Data.USER_1);

		Object[] tickets = ticketDAO.findAllUnassigned().toArray();
		assertArrayEquals(controller.tickets(userId).toArray(), tickets);

		ticketDataSet.clear();
	}

	/**
	 * Test if the {@link TicketEditor} have been well registered. The following
	 * conditions are checked :<br/>
	 * <ul>
	 * <li>A property editor for {@link Ticket} object is found and is not
	 * <code>null</code></li>
	 * <li>This property editor is an instance of {@link TicketEditor}</li>
	 * </ul>
	 */
	@Test
	public void testBinder() {
		WebDataBinder binder = new WebDataBinder(null);
		controller.binder(binder);

		PropertyEditor dateEditor = binder.findCustomEditor(Date.class, null);
		PropertyEditor ticketEditor = binder.findCustomEditor(Ticket.class, null);
		PropertyEditor roleEditor = binder.findCustomEditor(Role.class, null);

		assertNotNull(dateEditor);
		assertNotNull(ticketEditor);
		assertNotNull(roleEditor);
		assertTrue(dateEditor instanceof CustomDateEditor);
		assertTrue(ticketEditor instanceof TicketEditor);
		assertTrue(roleEditor instanceof RoleEditor);
	}

	/**
	 * Test if a {@link Ticket} has been well assigned to a {@link User}. The
	 * following conditions are checked :<br/>
	 * <ul>
	 * <li>The {@link User} has one assigned {@link Ticket}</li>
	 * <li>This ticket is equal the {@link Ticket} which was created in this
	 * test</li>
	 * </ul>
	 * At the end of this test, we clear the entity manager
	 */
	@Test
	public void testAssign() {
		ticketDataSet.setup();
		

		TicketIdCommand ticketIdCommand = new TicketIdCommand();
		ticketIdCommand.setTicketId(Data.TICKET_1.getId());

		controller.assign(userId, ticketIdCommand);

		User u = userDAO.findById(userId);
		assertEquals(1, u.getAssignedTickets().size());
		assertTrue(u.getAssignedTickets().contains(Data.TICKET_1));

		ticketDataSet.clear();
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

		assertTrue(m.containsAttribute("userQuery"));
		UserQueryCommand command = (UserQueryCommand) map.get("userQuery");
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
		UserQueryCommand command = (UserQueryCommand) map.get("userQuery");

		command.setLogin("user1");
		command.setLoginMatchMode(UserQueryCommand.EXACT_MATCH_MODE);
		ModelAndView mav = controller.search(command);

		Map<String, Object> model = mav.getModel();
		assertTrue(model.containsKey("result"));
		List<?> result = (List<?>) model.get("result");
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(Data.USER_1, result.get(0));
		assertEquals("user/result", mav.getViewName());
	}
	
	/**
	 * Test if the returned list of roles is correct and complete. First, we
	 * create 2 roles, then we compare the result of the roles() method
	 * and the findAll() method. At the end of this test, we clear the entity
	 * manager
	 */
	@Test
	public void testRoles() {
		Object[] roles = roleDAO.findAllEnabled().toArray();
		assertArrayEquals(roles, controller.roles().toArray());
	}

}
