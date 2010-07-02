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

import flexcom.casehistory.ticket.dao.StatusDAO;
import flexcom.casehistory.ticket.entity.Status;
import flexcom.casehistory.ticket.search.Query;

/**
 * Test class for {@link StatusDAOImpl}
 * 
 * @author philippe
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class StatusDAOImplTest {

	/**
	 * A {@link Status} "New"
	 */
	private Status newStatus;

	/**
	 * A {@link Status} "Closed"
	 */
	private Status closedStatus;

	/**
	 * Status DAO
	 */
	@Autowired
	private StatusDAO statusDAO;

	/**
	 * ID for the {@link Status} "New"
	 */
	private long newStatusId;
	
	/**
	 * ID for the {@link Status} "Closed"
	 */
	private long closedStatusId;

	/**
	 * Set up the context
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		newStatus = Status.getStatus(Status.NEW);
		statusDAO.createStatus(newStatus);
		newStatusId = newStatus.getId();
		
		closedStatus = Status.getStatus(Status.CLOSED);
		statusDAO.createStatus(closedStatus);
		closedStatusId = closedStatus.getId();
	}

	/**
	 * Clear the context
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		statusDAO.deleteAll();
		newStatus = null;
		closedStatus = null;
	}

	/**
	 * Test if the findAll() method return a correct and complete list of all
	 * statuses. The following conditions are checked :<br/>
	 * <ul>
	 * <li>2 statuses are in the list</li>
	 * <li>The list contains the status "New"</li>
	 * <li>The list contains the status "Closed"</li>
	 * </ul>
	 */
	@Test
	public void testFindAll() {
		List<Status> statuses = statusDAO.findAll();
		assertEquals(2, statuses.size());
		assertTrue(statuses.contains(newStatus));
		assertTrue(statuses.contains(closedStatus));
	}

	/**
	 * Test the createStatus(Status) method : the DAO must have an additionnal
	 * entity after the creation
	 */
	@Test
	public void testCreateStatus() {
		int count = (int) statusDAO.count();
		Status s = Status.getStatus(Status.PENDING);
		statusDAO.createStatus(s);
		assertEquals(count + 1, statusDAO.count());
	}

	/**
	 * Test if the createStatus(Status) method throws a
	 * {@link InvalidDataAccessApiUsageException} by passing a <code>null</code>
	 * {@link Status}
	 */
	@Test(expected = InvalidDataAccessApiUsageException.class)
	public void testCreateNullStatus() {
		statusDAO.createStatus(null);
	}

	/**
	 * Test if the status with the specified name is found.
	 */
	@Test
	public void testFindByName() {
		Status status = statusDAO.findByName("New");
		assertEquals(newStatus, status);
	}

	/**
	 * Test the deletion of an {@link Status}. We check that the deleted
	 * {@link Status} can't be found.
	 */
	@Test
	public void testDeleteStatus() {
		statusDAO.deleteStatus(closedStatus);
		assertNull(statusDAO.findById(closedStatusId));
	}

	/**
	 * Test if a {@link NullPointerException} is thrown by trying to delete a
	 * <code>null</code> {@link Status}
	 */
	@Test(expected = NullPointerException.class)
	public void testDeleteNullStatus() {
		statusDAO.deleteStatus(null);
	}

	/**
	 * Test if the DAO have no more {@link Status} entities after the call of
	 * deleteAll() method
	 */
	@Test
	public void testDeleteAll() {
		statusDAO.deleteAll();
		assertEquals(0, statusDAO.count());
	}

	/**
	 * Test if an {@link Status} can be found by his ID
	 */
	@Test
	public void testFindById() {
		assertEquals(newStatus, statusDAO.findById(newStatusId));
	}

	/**
	 * Test if the modification of an entity has been correctly done
	 */
	@Test
	public void testUpdateStatus() {
		closedStatus.setName("Very new");
		statusDAO.updateStatus(closedStatus);
		assertEquals("Very new", statusDAO.findById(closedStatusId).getName());
	}

	/**
	 * Test if the updateStatus(Status) method throws an
	 * {@link InvalidDataAccessApiUsageException} if the given argument is
	 * <code>null</code>
	 */
	@Test(expected = InvalidDataAccessApiUsageException.class)
	public void testUpdateNullStatus() {
		statusDAO.updateStatus(null);
	}

	/**
	 * Test some {@link Status} {@link Query}. First, we create a {@link Query}
	 * without filters. Then, after each execution of this query, we add a
	 * filter. The following scenario is executed :<br/>
	 * <ul>
	 * <li>0 filters : 2 statuses found</li>
	 * <li>Filter () added : 1 status found</li>
	 * <li>Filter () added : no status found</li>
	 * </ul>
	 */
	@Test
	public void testFilter() {
		List<Status> statuses;
		Query<Status> query = new Query<Status>(Status.class);

		statuses = statusDAO.filter(query);
		assertEquals(2, statuses.size());

		// TODO test some filters
	}

	/**
	 * Test the count() method
	 */
	@Test
	public void testCount() {
		assertEquals(2, statusDAO.count());
	}

}
