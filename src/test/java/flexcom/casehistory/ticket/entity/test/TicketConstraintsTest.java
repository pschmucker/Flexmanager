package flexcom.casehistory.ticket.entity.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
import flexcom.casehistory.ticket.dao.TicketDAO;
import flexcom.casehistory.ticket.entity.Client;
import flexcom.casehistory.ticket.entity.Priority;
import flexcom.casehistory.ticket.entity.Product;
import flexcom.casehistory.ticket.entity.Status;
import flexcom.casehistory.ticket.entity.Ticket;

/**
 * Class test for setters of the {@link Ticket} class. Here we check all
 * constraints on every fields.
 * 
 * @author philippe
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class TicketConstraintsTest {

	/**
	 * Ticket DAO
	 */
	@Autowired
	private TicketDAO ticketDAO;

	/**
	 * A {@link Ticket}
	 */
	private Ticket ticket;

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
		
		ticket = new Ticket();
		ticket.setTitle("test");
		ticket.setDescription("No interesting description");
		ticket.setClient(Data.CLIENT_1);
		ticket.setProduct(Data.PRODUCT_1);
		ticket.setPriority(Data.PRIORITY_HIGH);
		ticket.setStatus(Data.STATUS_ASSIGNED);
		ticket.setContact(Data.CONTACT_1);
	}

	/**
	 * Clear the context
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		ticket = null;
		ticketDataSet.clear();
	}

	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * ticket whose title is <code>null</code>
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSetTitleNull() {
		ticket.setTitle(null);
		ticketDAO.createTicket(ticket);
	}

	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * ticket whose title is too long
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSetTitleMaxSize() {
		ticket.setTitle("Ticket titles must not be too long ! The case must be described with precision in the description field");
		ticketDAO.createTicket(ticket);
	}

	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * ticket whose title is empty
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSetEmptyTitle() {
		ticket.setTitle("");
		ticketDAO.createTicket(ticket);
	}

	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * ticket whose date creation is <code>null</code>
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSetDateCreationNull() {
		ticket.setCreationDate(null);
		ticketDAO.createTicket(ticket);
	}

	/**
	 * Test if the default date creation is set to the current date
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void testSetDateCreationDefault() throws InterruptedException {
		Date before = new Date();
		Thread.sleep(1000);

		ticket = new Ticket();
		ticket.setTitle("test");
		ticket.setDescription("No interesting description");
		ticket.setClient(Data.CLIENT_1);
		ticket.setProduct(Data.PRODUCT_1);
		ticket.setPriority(Data.PRIORITY_HIGH);
		ticket.setStatus(Data.STATUS_ASSIGNED);
		ticket.setContact(Data.CONTACT_1);
		ticketDAO.createTicket(ticket);
		long id = ticket.getId();

		Thread.sleep(1000);
		Date after = new Date();

		Ticket t = ticketDAO.findById(id);
		assertNotNull(t.getCreationDate());
		assertTrue(t.getCreationDate().after(before));
		assertTrue(t.getCreationDate().before(after));
	}

	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * ticket with a <code>null</code> {@link Status}
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSetStatusNull() {
		ticket.setStatus(null);
		ticketDAO.createTicket(ticket);
	}

	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * ticket with a <code>null</code> {@link Priority}
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSetPriorityNull() {
		ticket.setPriority(null);
		ticketDAO.createTicket(ticket);
	}

	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * ticket with a <code>null</code> {@link Client}
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSetClientNull() {
		ticket.setClient(null);
		ticketDAO.createTicket(ticket);
	}

	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * ticket with a <code>null</code> {@link Product}
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSetProductNull() {
		ticket.setProduct(null);
		ticketDAO.createTicket(ticket);
	}
	
	/**
	 * Test if the creation date of a {@link Ticket} can't be updated
	 * @throws InterruptedException 
	 */
	// TODO @Test does not work
	public void testUpdateCreationDate() throws InterruptedException{
		Date d = ticket.getCreationDate();
		ticketDAO.createTicket(ticket);
		long id = ticket.getId();
		Thread.sleep(1000);
		
		Ticket t = ticketDAO.findById(id);
		t.setCreationDate(new Date());
		ticketDAO.updateTicket(t);
		
		t = null;
		t = ticketDAO.findById(id);
		assertFalse(t.getCreationDate() != d);
	}

}
