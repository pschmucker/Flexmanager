package flexcom.casehistory.ticket.entity.test;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import flexcom.casehistory.ticket.dao.PriorityDAO;
import flexcom.casehistory.ticket.entity.Priority;

/**
 * Class test for the {@link Priority} class
 * 
 * @author philippe
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class PriorityTest {

	/**
	 * A {@link Priority}
	 */
	private Priority priority;

	/**
	 * Priority DAO
	 */
	@Autowired
	private PriorityDAO priorityDAO;

	/**
	 * Set up the context
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		priority = Priority.getPriority(Priority.MEDIUM);
		priorityDAO.createPriority(priority);
	}

	/**
	 * Clear the context
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		priorityDAO.deleteAll();
		priority = null;
	}

	/**
	 * Test the hashCode method
	 */
	@Test
	public void testHashCode() {
		Priority p = priorityDAO.findByName("MEDIUM");
		assertEquals(priority.hashCode(), p.hashCode());
	}

	/**
	 * Test the toString() method
	 */
	@Test
	public void testToString() {
		assertEquals("MEDIUM", priority.toString());
	}

	/**
	 * Test the equals(Object) method
	 */
	@Test
	public void testEqualsObject() {
		Priority p = priorityDAO.findByName("MEDIUM");
		assertEquals(priority, p);
	}

}
