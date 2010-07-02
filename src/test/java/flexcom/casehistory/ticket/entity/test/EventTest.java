package flexcom.casehistory.ticket.entity.test;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import flexcom.casehistory.data.Data;
import flexcom.casehistory.data.TicketDataSet;
import flexcom.casehistory.data.UserDataSet;
import flexcom.casehistory.ticket.dao.EventDAO;
import flexcom.casehistory.ticket.entity.Event;

/**
 * Test class for the {@link Event} entity. Setters and getters are not tested
 * here. Setters are tested in {@link EventConstraintsTest}
 * 
 * @author philippe
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class EventTest {

	/**
	 * A {@link Event}
	 */
	private Event event;
	
	/**
	 * ID for event
	 */
	private long eventId;

	/**
	 * Event DAO
	 */
	@Autowired
	private EventDAO eventDAO;

	@Autowired
	private UserDataSet userDataSet;
	
	@Autowired
	private TicketDataSet ticketDataSet;

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
		event.setDate(new Date());
		event.setAction("action");
		event.setTicket(Data.TICKET_1);
		event.setAuthor(Data.USER_1);

		eventDAO.createEvent(event);
		eventId = event.getId();
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
		userDataSet.clear();
		ticketDataSet.clear();
	}

	/**
	 * Test the hashCode method
	 */
	@Test
	public void testHashCode() {
		Event e = eventDAO.findById(eventId);
		assertEquals(event.hashCode(), e.hashCode());
	}

	/**
	 * Test the toString() method
	 */
	@Test
	public void testToString() {
		Event e = eventDAO.findById(eventId);
		assertEquals("action", e.toString());
	}

	/**
	 * Test the equals(Object) method
	 */
	@Test
	public void testEqualsObject() {
		Event e = eventDAO.findById(eventId);
		assertEquals(event, e);
	}

}
