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

import flexcom.casehistory.ticket.dao.MaintenanceDAO;
import flexcom.casehistory.ticket.dao.StatusDAOImpl;
import flexcom.casehistory.ticket.entity.Maintenance;
import flexcom.casehistory.ticket.search.Query;

/**
 * Test class for {@link StatusDAOImpl}
 * 
 * @author philippe
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class MaintenanceDAOImplTest {

	/**
	 * A {@link Maintenance} "Full"
	 */
	private Maintenance fullMaintenance;

	/**
	 * A {@link Maintenance} "None"
	 */
	private Maintenance noneMaintenance;

	/**
	 * Maintenance DAO
	 */
	@Autowired
	private MaintenanceDAO maintenanceDAO;

	/**
	 * ID for the {@link Maintenance} "Full"
	 */
	private long fullMaintenanceId;
	
	/**
	 * ID for the {@link Maintenance} "None"
	 */
	private long noneMaintenanceId;

	/**
	 * Set up the context
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		fullMaintenance = Maintenance.getMaintenance(Maintenance.FULL);
		maintenanceDAO.createMaintenance(fullMaintenance);
		fullMaintenanceId = fullMaintenance.getId();
		
		noneMaintenance = Maintenance.getMaintenance(Maintenance.NONE);
		maintenanceDAO.createMaintenance(noneMaintenance);
		noneMaintenanceId = noneMaintenance.getId();
	}

	/**
	 * Clear the context
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		maintenanceDAO.deleteAll();
		fullMaintenance = null;
		noneMaintenance = null;
	}

	/**
	 * Test if the findAll() method return a correct and complete list of all
	 * maintenances. The following conditions are checked :<br/>
	 * <ul>
	 * <li>2 maintenances are in the list</li>
	 * <li>The list contains the maintenance "Full"</li>
	 * <li>The list contains the maintenance "None"</li>
	 * </ul>
	 */
	@Test
	public void testFindAll() {
		List<Maintenance> maintenances = maintenanceDAO.findAll();
		assertEquals(2, maintenances.size());
		assertTrue(maintenances.contains(fullMaintenance));
		assertTrue(maintenances.contains(noneMaintenance));
	}

	/**
	 * Test the createMaintenance(Maintenance) method : the DAO must have an additionnal
	 * entity after the creation
	 */
	@Test
	public void testCreateMaintenance() {
		int count = (int) maintenanceDAO.count();
		Maintenance m = Maintenance.getMaintenance(Maintenance.TEST);
		maintenanceDAO.createMaintenance(m);
		assertEquals(count + 1, maintenanceDAO.count());
	}

	/**
	 * Test if the createMaintenance(Maintenance) method throws a
	 * {@link InvalidDataAccessApiUsageException} by passing a <code>null</code>
	 * {@link Maintenance}
	 */
	@Test(expected = InvalidDataAccessApiUsageException.class)
	public void testCreateNullMaintenance() {
		maintenanceDAO.createMaintenance(null);
	}

	/**
	 * Test if the maintenance with the specified name is found.
	 */
	@Test
	public void testFindByName() {
		Maintenance maintenance = maintenanceDAO.findByName("Full");
		assertEquals(fullMaintenance, maintenance);
	}

	/**
	 * Test the deletion of an {@link Maintenance}. We check that the deleted
	 * {@link Maintenance} can't be found.
	 */
	@Test
	public void testDeleteMaintenance() {
		maintenanceDAO.deleteMaintenance(noneMaintenance);
		assertNull(maintenanceDAO.findById(noneMaintenanceId));
	}

	/**
	 * Test if a {@link NullPointerException} is thrown by trying to delete a
	 * <code>null</code> {@link Maintenance}
	 */
	@Test(expected = NullPointerException.class)
	public void testDeleteNullMaintenance() {
		maintenanceDAO.deleteMaintenance(null);
	}

	/**
	 * Test if the DAO have no more {@link Maintenance} entities after the call of
	 * deleteAll() method
	 */
	@Test
	public void testDeleteAll() {
		maintenanceDAO.deleteAll();
		assertEquals(0, maintenanceDAO.count());
	}

	/**
	 * Test if an {@link Maintenance} can be found by his ID
	 */
	@Test
	public void testFindById() {
		assertEquals(fullMaintenance, maintenanceDAO.findById(fullMaintenanceId));
	}

	/**
	 * Test if the modification of an entity has been correctly done
	 */
	@Test
	public void testUpdateMaintenance() {
		noneMaintenance.setName("Very full");
		maintenanceDAO.updateMaintenance(noneMaintenance);
		assertEquals("Very full", maintenanceDAO.findById(noneMaintenanceId).getName());
	}

	/**
	 * Test if the updateMaintenance(Maintenance) method throws an
	 * {@link InvalidDataAccessApiUsageException} if the given argument is
	 * <code>null</code>
	 */
	@Test(expected = InvalidDataAccessApiUsageException.class)
	public void testUpdateNullMaintenance() {
		maintenanceDAO.updateMaintenance(null);
	}

	/**
	 * Test some {@link Maintenance} {@link Query}. First, we create a {@link Query}
	 * without filters. Then, after each execution of this query, we add a
	 * filter. The following scenario is executed :<br/>
	 * <ul>
	 * <li>0 filters : 2 maintenances found</li>
	 * <li>Filter () added : 1 maintenance found</li>
	 * <li>Filter () added : no maintenance found</li>
	 * </ul>
	 */
	@Test
	public void testFilter() {
		List<Maintenance> maintenances;
		Query<Maintenance> query = new Query<Maintenance>(Maintenance.class);

		maintenances = maintenanceDAO.filter(query);
		assertEquals(2, maintenances.size());

		// TODO test some filters
	}

	/**
	 * Test the count() method
	 */
	@Test
	public void testCount() {
		assertEquals(2, maintenanceDAO.count());
	}

}
