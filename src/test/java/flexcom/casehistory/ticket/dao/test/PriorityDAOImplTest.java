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

import flexcom.casehistory.ticket.dao.PriorityDAO;
import flexcom.casehistory.ticket.dao.PriorityDAOImpl;
import flexcom.casehistory.ticket.entity.Priority;
import flexcom.casehistory.ticket.search.Query;

/**
 * Test class for {@link PriorityDAOImpl}
 * 
 * @author philippe
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class PriorityDAOImplTest {

	/**
	 * A {@link Priority} "HIGH"
	 */
	private Priority priorityHigh;

	/**
	 * A {@link Priority} "LOW"
	 */
	private Priority priorityLow;

	/**
	 * Priority DAO
	 */
	@Autowired
	private PriorityDAO priorityDAO;

	/**
	 * ID for the {@link Priority} "HIGH"
	 */
	private long priorityHighId;
	
	/**
	 * ID for the {@link Priority} "LOW"
	 */
	private long priorityLowId;

	/**
	 * Set up the context
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		priorityHigh = Priority.getPriority(Priority.HIGH);
		priorityDAO.createPriority(priorityHigh);
		priorityHighId = priorityHigh.getId();
		
		priorityLow = Priority.getPriority(Priority.LOW);
		priorityDAO.createPriority(priorityLow);
		priorityLowId = priorityLow.getId();
	}

	/**
	 * Clear the context
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		priorityDAO.deleteAll();
		priorityHigh = null;
		priorityLow = null;
	}

	/**
	 * Test if the findAll() method return a correct and complete list of all
	 * priorities. The following conditions are checked :<br/>
	 * <ul>
	 * <li>2 priorities are in the list</li>
	 * <li>The list contains the priority "HIGH"</li>
	 * <li>The list contains the priority "LOW"</li>
	 * </ul>
	 */
	@Test
	public void testFindAll() {
		List<Priority> priorities = priorityDAO.findAll();
		assertEquals(2, priorities.size());
		assertTrue(priorities.contains(priorityHigh));
		assertTrue(priorities.contains(priorityLow));
	}

	/**
	 * Test the createPriority(Priority) method : the DAO must have an additionnal
	 * entity after the creation
	 */
	@Test
	public void testCreatePriority() {
		int count = (int) priorityDAO.count();
		Priority p = Priority.getPriority(Priority.MEDIUM);
		priorityDAO.createPriority(p);
		assertEquals(count + 1, priorityDAO.count());
	}

	/**
	 * Test if the createPriority(Priority) method throws a
	 * {@link InvalidDataAccessApiUsageException} by passing a <code>null</code>
	 * {@link Priority}
	 */
	@Test(expected = InvalidDataAccessApiUsageException.class)
	public void testCreateNullPriority() {
		priorityDAO.createPriority(null);
	}

	/**
	 * Test if the priority with the specified name is found.
	 */
	@Test
	public void testFindByName() {
		Priority priority = priorityDAO.findByName("HIGH");
		assertEquals(priorityHigh, priority);
	}

	/**
	 * Test the deletion of an {@link Priority}. We check that the deleted
	 * {@link Priority} can't be found.
	 */
	@Test
	public void testDeletePriority() {
		priorityDAO.deletePriority(priorityLow);
		assertNull(priorityDAO.findById(priorityLowId));
	}

	/**
	 * Test if a {@link NullPointerException} is thrown by trying to delete a
	 * <code>null</code> {@link Priority}
	 */
	@Test(expected = NullPointerException.class)
	public void testDeleteNullPriority() {
		priorityDAO.deletePriority(null);
	}

	/**
	 * Test if the DAO have no more {@link Priority} entities after the call of
	 * deleteAll() method
	 */
	@Test
	public void testDeleteAll() {
		priorityDAO.deleteAll();
		assertEquals(0, priorityDAO.count());
	}

	/**
	 * Test if an {@link Priority} can be found by his ID
	 */
	@Test
	public void testFindById() {
		assertEquals(priorityHigh, priorityDAO.findById(priorityHighId));
	}

	/**
	 * Test if the modification of an entity has been correctly done
	 */
	@Test
	public void testUpdatePriority() {
		priorityLow.setName("VERY LOW");
		priorityDAO.updatePriority(priorityLow);
		assertEquals("VERY LOW", priorityDAO.findById(priorityLowId).getName());
	}

	/**
	 * Test if the updatePriority(Priority) method throws an
	 * {@link InvalidDataAccessApiUsageException} if the given argument is
	 * <code>null</code>
	 */
	@Test(expected = InvalidDataAccessApiUsageException.class)
	public void testUpdateNullPriority() {
		priorityDAO.updatePriority(null);
	}

	/**
	 * Test some {@link Priority} {@link Query}. First, we create a {@link Query}
	 * without filters. Then, after each execution of this query, we add a
	 * filter. The following scenario is executed :<br/>
	 * <ul>
	 * <li>0 filters : 2 priorities found</li>
	 * <li>Filter () added : 1 priority found</li>
	 * <li>Filter () added : no priority found</li>
	 * </ul>
	 */
	@Test
	public void testFilter() {
		List<Priority> priorities;
		Query<Priority> query = new Query<Priority>(Priority.class);

		priorities = priorityDAO.filter(query);
		assertEquals(2, priorities.size());

		// TODO test some filters
	}

	/**
	 * Test the count() method
	 */
	@Test
	public void testCount() {
		assertEquals(2, priorityDAO.count());
	}

}
