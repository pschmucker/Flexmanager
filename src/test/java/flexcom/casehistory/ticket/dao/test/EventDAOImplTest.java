package flexcom.casehistory.ticket.dao.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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
import flexcom.casehistory.ticket.dao.EventDAO;
import flexcom.casehistory.ticket.dao.EventDAOImpl;
import flexcom.casehistory.ticket.entity.Event;
import flexcom.casehistory.ticket.search.Query;

/**
 * Test class for {@link EventDAOImpl}
 * 
 * @author philippe
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class EventDAOImplTest {

	/**
	 * A {@link Event}
	 */
	private Event event;

	/**
	 * Another {@link Event}
	 */
	private Event tmp;

	/**
	 * ID for the {@link Event}
	 */
	private long eventId;

	/**
	 * Note DAO
	 */
	@Autowired
	private EventDAO eventDAO;

	@Autowired
	private TicketDataSet ticketDataSet;
	
	@Autowired
	private UserDataSet userDataSet;

	/**
	 * Set up the context
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		ticketDataSet.setup();
		userDataSet.setup();

		event = new Event();
		event.setAction("action");
		event.setTicket(Data.TICKET_1);
		event.setAuthor(Data.USER_1);
		eventDAO.createEvent(event);
		eventId = event.getId();
		
		tmp = new Event();
		tmp.setAction("tmp");
		tmp.setTicket(Data.TICKET_1);
		tmp.setAuthor(Data.USER_1);
		eventDAO.createEvent(tmp);
	}

	/**
	 * Clear the context
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		eventDAO.deleteAll();
		event = null;
		tmp = null;
		userDataSet.clear();
		ticketDataSet.clear();
	}

	/**
	 * Test the createEvent(Event) method : the DAO must have an additionnal
	 * entity after the creation
	 */
	@Test
	public void testCreateEvent() {
		int count = (int) eventDAO.count();
		Event e = new Event();
		e.setAction("test");
		e.setTicket(Data.TICKET_1);
		e.setAuthor(Data.USER_1);
		eventDAO.createEvent(e);
		assertEquals(count + 1, eventDAO.count());
	}

	/**
	 * Test if the createEvent(Event) method throws a
	 * {@link InvalidDataAccessApiUsageException} by passing a <code>null</code>
	 * {@link Event}
	 */
	@Test(expected = InvalidDataAccessApiUsageException.class)
	public void testCreateNullEvent() {
		eventDAO.createEvent(null);
	}

	/**
	 * Test if the findAll() method return a correct and complete list of all
	 * events. The following conditions are checked :<br/>
	 * <ul>
	 * <li>2 events are in the list</li>
	 * <li>The list contains the event "action"</li>
	 * <li>The list contains the event "tmp"</li>
	 * </ul>
	 */
	@Test
	public void testFindAll() {
		List<Event> events = eventDAO.findAll();
		assertEquals(2, events.size());
		assertTrue(events.contains(event));
		assertTrue(events.contains(tmp));
	}

	/**
	 * Test if the updateEvent(Event) method throws an
	 * {@link InvalidDataAccessApiUsageException} if the given argument is
	 * <code>null</code>
	 */
	@Test(expected = InvalidDataAccessApiUsageException.class)
	public void testUpdateNullEvent() {
		eventDAO.updateEvent(null);
	}

	/**
	 * Test if all events with the specified action are found. The following
	 * conditions are checked : <br/>
	 * <ul>
	 * <li>The list contains 1 event</li>
	 * <li>The list contains the event "action"</li>
	 * </ul>
	 */
	@Test
	public void testFindByNote() {
		List<Event> events = eventDAO.findByAction("action");

		assertEquals(1, events.size());
		assertEquals(event, events.get(0));
	}

	/**
	 * Test the deletion of an {@link Event}. We check that the deleted
	 * {@link Event} can't be found.
	 */
	@Test
	public void testDeleteEvent() {
		eventDAO.deleteEvent(event);
		assertNull(eventDAO.findById(eventId));
	}

	/**
	 * Test if a {@link NullPointerException} is thrown by trying to delete a
	 * <code>null</code> {@link Event}
	 */
	@Test(expected = NullPointerException.class)
	public void testDeleteNullEvent() {
		eventDAO.deleteEvent(null);
	}

	/**
	 * Test if the DAO have no more {@link Event} entities after the call of
	 * deleteAll() method
	 */
	@Test
	public void testDeleteAll() {
		eventDAO.deleteAll();
		assertEquals(0, eventDAO.count());
	}

	/**
	 * Test if an {@link Event} can be found by his ID
	 */
	@Test
	public void testFindById() {
		assertEquals(event, eventDAO.findById(eventId));
	}

	/**
	 * Test some {@link Event} {@link Query}. First, we create a {@link Query}
	 * without filters. Then, after each execution of this query, we add a
	 * filter. The following scenario is executed :<br/>
	 * <ul>
	 * <li>0 filters : 2 events found</li>
	 * <li>Filter () added : 1 event found</li>
	 * <li>Filter () added : no event found</li>
	 * </ul>
	 */
	@Test
	public void testFilter() {
		List<Event> events;
		Query<Event> query = new Query<Event>(Event.class);

		events = eventDAO.filter(query);
		assertEquals(2, events.size());

		// TODO test some filters
	}

	/**
	 * Test the count() method
	 */
	@Test
	public void testCount() {
		assertEquals(2, eventDAO.count());
	}
}
