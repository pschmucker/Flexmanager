package flexcom.casehistory.ticket.entity.test;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import flexcom.casehistory.ticket.dao.StatusDAO;
import flexcom.casehistory.ticket.entity.Status;

/**
 * Test class for the {@link Status} class.
 * 
 * @author philippe
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class StatusTest {

	/**
	 * A {@link Status}
	 */
	private Status status;

	/**
	 * Status DAO
	 */
	@Autowired
	private StatusDAO statusDAO;

	/**
	 * Set up the context
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		status = Status.getStatus(Status.ASSIGNED);
		statusDAO.createStatus(status);
	}

	/**
	 * Clear the context
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		status = null;
		statusDAO.deleteAll();
	}

	/**
	 * Test the hashCode method
	 */
	@Test
	public void testHashCode() {
		Status s = statusDAO.findByName("Assigned");
		assertEquals(status.hashCode(), s.hashCode());
	}

	/**
	 * Test the toString() method
	 */
	@Test
	public void testToString() {
		assertEquals("Assigned", status.toString());
	}

	/**
	 * Test the equals(Object) method
	 */
	@Test
	public void testEqualsObject() {
		Status s = statusDAO.findByName("Assigned");
		assertEquals(status, s);
	}

}
