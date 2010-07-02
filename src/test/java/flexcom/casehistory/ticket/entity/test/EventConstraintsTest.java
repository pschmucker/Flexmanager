package flexcom.casehistory.ticket.entity.test;

import static org.junit.Assert.assertFalse;

import java.util.Date;

import javax.validation.ConstraintViolationException;

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
import flexcom.casehistory.ticket.entity.Ticket;
import flexcom.casehistory.ticket.entity.User;

/**
 * Class test for setters of the {@link Event} class. Here we check all
 * constraints on every fields.
 * 
 * @author philippe
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class EventConstraintsTest {

	/**
	 * Event DAO
	 */
	@Autowired
	private EventDAO eventDAO;

	/**
	 * A {@link Event}
	 */
	private Event event;

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
		event.setDate(new Date());
		event.setAction("action");
		event.setTicket(Data.TICKET_1);
		event.setAuthor(Data.USER_1);
	}

	/**
	 * Clear the context
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		event = null;
		eventDAO.deleteAll();
		userDataSet.clear();
		ticketDataSet.clear();
	}

	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * {@link Event} with a <code>null</code> action
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSetActionNull() {
		event.setAction(null);
		eventDAO.createEvent(event);
	}

	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * {@link Event} with a too long action
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSetNameMaxSize() {
		event.setAction("This is a too long event, so this message is not very usable");
		eventDAO.createEvent(event);
	}

	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * {@link Event} with a <code>null</code> {@link Ticket}
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSetTicketNull() {
		event.setTicket(null);
		eventDAO.createEvent(event);
	}

	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * {@link Event} with a <code>null</code> {@link User}
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSetAuthorNull() {
		event.setAuthor(null);
		eventDAO.createEvent(event);
	}

	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * {@link Event} with a <code>null</code> date
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSetDateNull() {
		event.setDate(null);
		eventDAO.createEvent(event);
	}

	/**
	 * Test if the action of an {@link Event} can't be updated
	 * @throws InterruptedException 
	 */
	@Test
	public void testUpdate() throws InterruptedException{
		String action = event.getAction();
		
		eventDAO.createEvent(event);
		long id = event.getId();
		
		Event e = eventDAO.findById(id);
		e.setAction("new action");
		eventDAO.updateEvent(e);
		
		e = null;
		e = eventDAO.findById(id);
		assertFalse(e.getAction() != action);
	}
}
