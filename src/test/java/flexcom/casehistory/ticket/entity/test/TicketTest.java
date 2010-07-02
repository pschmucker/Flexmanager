package flexcom.casehistory.ticket.entity.test;

import static org.junit.Assert.assertEquals;

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
import flexcom.casehistory.ticket.entity.Ticket;

/**
 * Test class for the {@link Ticket} entity. Setters and getters are not tested
 * here. Setters are tested in {@link TicketConstraintsTest}
 * 
 * @author philippe
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class TicketTest {
	
	@Autowired
	private TicketDAO ticketDAO;

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
	}

	/**
	 * Clear the context
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		ticketDataSet.clear();
	}

	/**
	 * Test the hashCode() method
	 */
	@Test
	public void testHashCode() {
		Ticket t = ticketDAO.findByTitle("T1").get(0);
		assertEquals(Data.TICKET_1.hashCode(), t.hashCode());
	}

	/**
	 * Test the toString() method
	 */
	@Test
	public void testToString() {
		Ticket t = ticketDAO.findByTitle("T1").get(0);
		assertEquals("Ticket #" + Data.TICKET_1.getId() + " : " + Data.TICKET_1.getTitle(), t.toString());
	}

	/**
	 * Test the equals(Object) method
	 */
	@Test
	public void testEqualsObject() {
		Ticket t = ticketDAO.findByTitle("T1").get(0);
		assertEquals(Data.TICKET_1, t);
	}

}
