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

import flexcom.casehistory.data.Data;
import flexcom.casehistory.data.TicketDataSet;
import flexcom.casehistory.data.UserDataSet;
import flexcom.casehistory.ticket.dao.TicketDAO;
import flexcom.casehistory.ticket.dao.TicketDAOImpl;
import flexcom.casehistory.ticket.dao.UserDAO;
import flexcom.casehistory.ticket.entity.Client;
import flexcom.casehistory.ticket.entity.Priority;
import flexcom.casehistory.ticket.entity.Product;
import flexcom.casehistory.ticket.entity.Status;
import flexcom.casehistory.ticket.entity.Ticket;
import flexcom.casehistory.ticket.entity.User;
import flexcom.casehistory.ticket.search.Query;
import flexcom.casehistory.ticket.search.filter.EqualFilter;
import flexcom.casehistory.ticket.search.filter.LessThanFilter;

/**
 * Test class for {@link TicketDAOImpl}
 * 
 * @author philippe
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class TicketDAOImplTest {

	/**
	 * ID for the "First ticket"
	 */
	private long ticketId1;

	/**
	 * Date before the creation of all tickets
	 */
	private Date beforeCreation;
	
	@Autowired
	private TicketDataSet ticketDataSet;
	
	@Autowired
	private UserDataSet userDataSet;
	
	@Autowired
	private UserDAO userDAO;

	@Autowired
	private TicketDAO ticketDAO;

	/**
	 * Set up the context
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		beforeCreation = new Date();

		ticketDataSet.setup();

		ticketId1 = Data.TICKET_1.getId();

		userDataSet.setup();

		Data.USER_1.assign(Data.TICKET_2);
		Data.USER_1.assign(Data.TICKET_3);
		Data.USER_1.assign(Data.TICKET_5);
		userDAO.updateUser(Data.USER_1);
	}

	/**
	 * Clear the context
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		userDataSet.clear();
		ticketDataSet.clear();
		beforeCreation = null;
	}

	/**
	 * Test the createTicket(Ticket) method : the DAO must have an additionnal
	 * entity after the creation
	 */
	@Test
	public void testCreateTicket() {
		int count = (int) ticketDAO.count();
		Ticket t = new Ticket();
		t.setTitle("test");
		t.setClient(Data.CLIENT_1);
		t.setProduct(Data.PRODUCT_1);
		t.setPriority(Data.PRIORITY_MEDIUM);
		t.setStatus(Data.STATUS_ASSIGNED);
		t.setContact(Data.CONTACT_1);
		ticketDAO.createTicket(t);
		assertEquals(count + 1, ticketDAO.count());
	}

	/**
	 * Test if the createTicket(Ticket) method throws a
	 * {@link InvalidDataAccessApiUsageException} by passing a <code>null</code>
	 * {@link Ticket}
	 */
	@Test(expected = InvalidDataAccessApiUsageException.class)
	public void testCreateNullTicket() {
		ticketDAO.createTicket(null);
	}

	/**
	 * Test if the findAll() method return a correct and complete list of all
	 * tickets. The following conditions are checked :<br/>
	 * <ul>
	 * <li>5 tickets are in the list</li>
	 * <li>The list contains the ticket "First ticket"</li>
	 * <li>The list contains the ticket "Second ticket"</li>
	 * <li>The list contains the ticket "Third ticket"</li>
	 * <li>The list contains the ticket "Fourth ticket"</li>
	 * <li>The list contains the ticket "Fifth ticket"</li>
	 * </ul>
	 */
	@Test
	public void testFindAll() {
		List<Ticket> tickets = ticketDAO.findAll();
		assertEquals(5, tickets.size());
		assertTrue(tickets.contains(Data.TICKET_1));
		assertTrue(tickets.contains(Data.TICKET_2));
		assertTrue(tickets.contains(Data.TICKET_3));
		assertTrue(tickets.contains(Data.TICKET_4));
		assertTrue(tickets.contains(Data.TICKET_5));
	}

	/**
	 * Test if the modification of an entity has been correctly done
	 */
	@Test
	public void testUpdateTicket() {
		Data.TICKET_1.setPriority(Data.PRIORITY_MEDIUM);
		ticketDAO.updateTicket(Data.TICKET_1);
		assertEquals(Priority.MEDIUM, ticketDAO.findById(ticketId1).getPriority());
	}

	/**
	 * Test if the updateTicket(Ticket) method throws an
	 * {@link InvalidDataAccessApiUsageException} if the given argument is
	 * <code>null</code>
	 */
	@Test(expected = InvalidDataAccessApiUsageException.class)
	public void testUpdateNullTicket() {
		ticketDAO.updateTicket(null);
	}

	/**
	 * Test if all tickets with the specified title are found. The following
	 * conditions are checked : <br/>
	 * <ul>
	 * <li>The list contains 1 ticket</li>
	 * <li>The list contains the ticket "First ticket"</li>
	 * </ul>
	 */
	@Test
	public void testFindByTitle() {
		List<Ticket> tickets = ticketDAO.findByTitle("T1");

		assertEquals(1, tickets.size());
		assertEquals(Data.TICKET_1, tickets.get(0));
	}

	/**
	 * Test the deletion of an {@link Ticket}. We check that the deleted
	 * {@link Ticket} can't be found.
	 */
	@Test
	public void testDeleteTicket() {
		ticketDAO.deleteTicket(Data.TICKET_1);
		assertNull(ticketDAO.findById(ticketId1));
	}

	/**
	 * Test if a {@link NullPointerException} is thrown by trying to delete a
	 * <code>null</code> {@link Ticket}
	 */
	@Test(expected = NullPointerException.class)
	public void testDeleteNullTicket() {
		ticketDAO.deleteTicket(null);
	}

	/**
	 * Test if the DAO have no more {@link Ticket} entities after the call of
	 * deleteAll() method
	 */
	@Test
	public void testDeleteAll() {
		ticketDAO.deleteAll();
		assertEquals(0, ticketDAO.count());
	}

	/**
	 * Test if we found the correct tickets for one specific responsible
	 * {@link User}
	 */
	@Test
	public void testFindByUserInCharge() {
		List<Ticket> tickets = ticketDAO.findByUserInCharge(Data.USER_1);

		assertEquals(3, tickets.size());
		assertTrue(tickets.contains(Data.TICKET_2));
		assertTrue(tickets.contains(Data.TICKET_3));
		assertTrue(tickets.contains(Data.TICKET_5));
	}

	/**
	 * Test if nobody is responsible of a <code>null</code> {@link Ticket}
	 */
	@Test
	public void testFindByNullUserInCharge() {
		List<Ticket> tickets = ticketDAO.findByUserInCharge(null);
		assertEquals(0, tickets.size());
	}

	/**
	 * Test if all tickets with the specified {@link Status} are found. The
	 * following conditions are checked : <br/>
	 * <ul>
	 * <li>The list contains 4 tickets</li>
	 * <li>The list contains the ticket "First ticket"</li>
	 * <li>The list contains the ticket "Second ticket"</li>
	 * <li>The list contains the ticket "Fourth ticket"</li>
	 * <li>The list contains the ticket "Fifth ticket"</li>
	 * </ul>
	 */
	@Test
	public void testFindByStatus() {
		List<Ticket> tickets = ticketDAO.findByStatus(Data.STATUS_NEW);

		assertEquals(3, tickets.size());
		assertTrue(tickets.contains(Data.TICKET_1));
		assertTrue(tickets.contains(Data.TICKET_2));
		assertTrue(tickets.contains(Data.TICKET_4));
	}

	/**
	 * Test if all tickets with the specified {@link Priority} are found. The
	 * following conditions are checked : <br/>
	 * <ul>
	 * <li>The list contains 1 ticket</li>
	 * <li>The list contains the ticket "Fourth ticket"</li>
	 * </ul>
	 */
	@Test
	public void testFindByPriority() {
		List<Ticket> tickets = ticketDAO.findByPriority(Data.PRIORITY_HIGH);

		assertEquals(1, tickets.size());
		assertEquals(Data.TICKET_4, tickets.get(0));
	}

	/**
	 * Test if all unassigned tickets are found. The following conditions are
	 * checked : <br/>
	 * <ul>
	 * <li>The list contains 2 tickets</li>
	 * <li>The list contains the ticket "First ticket"</li>
	 * <li>The list contains the ticket "Fourth ticket"</li>
	 * </ul>
	 */
	@Test
	public void testFindAllUnassigned() {
		List<Ticket> tickets = ticketDAO.findAllUnassigned();

		assertEquals(2, tickets.size());
		assertTrue(tickets.contains(Data.TICKET_1));
		assertTrue(tickets.contains(Data.TICKET_4));
	}

	/**
	 * Test if all tickets concerning the specified {@link Client} are found.
	 * The following conditions are checked : <br/>
	 * <ul>
	 * <li>The list contains 1 ticket</li>
	 * <li>The list contains the ticket "Fifth ticket"</li>
	 * </ul>
	 */
	@Test
	public void testFindByClient() {
		List<Ticket> tickets = ticketDAO.findByClient(Data.CLIENT_2);

		assertEquals(1, tickets.size());
		assertTrue(tickets.contains(Data.TICKET_5));
	}

	/**
	 * Test if a {@link Ticket} with a <code>null</code> {@link Client} doesn't
	 * exist
	 */
	@Test
	public void testFindByNullClient() {
		List<Ticket> tickets = ticketDAO.findByClient(null);
		assertEquals(0, tickets.size());
	}

	/**
	 * Test if an {@link Ticket} can be found by his ID
	 */
	@Test
	public void testFindById() {
		assertEquals(Data.TICKET_1, ticketDAO.findById(ticketId1));
	}

	/**
	 * Test if all tickets concerning the specified {@link Product} are found.
	 * The following conditions are checked : <br/>
	 * <ul>
	 * <li>The list contains 3 tickets</li>
	 * <li>The list contains the ticket "First ticket"</li>
	 * <li>The list contains the ticket "Second ticket"</li>
	 * <li>The list contains the ticket "Third ticket"</li>
	 * </ul>
	 */
	@Test
	public void testFindByProduct() {
		List<Ticket> tickets = ticketDAO.findByProduct(Data.PRODUCT_1);

		assertEquals(3, tickets.size());
		assertTrue(tickets.contains(Data.TICKET_1));
		assertTrue(tickets.contains(Data.TICKET_2));
		assertTrue(tickets.contains(Data.TICKET_3));
	}

	/**
	 * Test if a {@link Ticket} with a <code>null</code> {@link Product} doesn't
	 * exist
	 */
	@Test
	public void testFindByNullProduct() {
		List<Ticket> tickets = ticketDAO.findByProduct(null);
		assertEquals(0, tickets.size());
	}

	/**
	 * Test some {@link Ticket} {@link Query}. First, we create a {@link Query}
	 * without filters. Then, after each execution of this query, we add a
	 * filter. The following scenario is executed :<br/>
	 * <ul>
	 * <li>0 filters : 5 tickets found</li>
	 * <li>Filter (client = client1) added : 4 tickets found</li>
	 * <li>Filter (product = product1) added : 3 tickets found</li>
	 * <li>Filter (status = Status.NEW) added : 2 tickets found</li>
	 * <li>Filter (priority = Priority.IMMEDIATE) added : 1 ticket found</li>
	 * <li>Filter (dateCreation < beforeCreation) added : no ticket found</li>
	 * </ul>
	 */
	@Test
	public void testFilter() {
		List<Ticket> tickets;
		Query<Ticket> query = new Query<Ticket>(Ticket.class);

		tickets = ticketDAO.filter(query);
		assertEquals(5, tickets.size());

		query.addFilter(new EqualFilter("client", Data.CLIENT_1));

		tickets = ticketDAO.filter(query);
		assertEquals(4, tickets.size());

		query.addFilter(new EqualFilter("product", Data.PRODUCT_1));

		tickets = ticketDAO.filter(query);
		assertEquals(3, tickets.size());

		query.addFilter(new EqualFilter("status", Data.STATUS_NEW));

		tickets = ticketDAO.filter(query);
		assertEquals(2, tickets.size());

		query.addFilter(new EqualFilter("priority", Data.PRIORITY_IMMEDIATE));

		tickets = ticketDAO.filter(query);
		assertEquals(1, tickets.size());

		query.addFilter(new LessThanFilter("creationDate", beforeCreation));

		tickets = ticketDAO.filter(query);
		assertEquals(0, tickets.size());
	}

	/**
	 * Test the count() method
	 */
	@Test
	public void testCount() {
		assertEquals(5, ticketDAO.count());
	}
}
