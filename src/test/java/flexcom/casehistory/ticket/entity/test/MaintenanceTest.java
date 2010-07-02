package flexcom.casehistory.ticket.entity.test;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import flexcom.casehistory.ticket.dao.MaintenanceDAO;
import flexcom.casehistory.ticket.entity.Maintenance;

/**
 * Test class for the {@link Maintenance} class.
 * 
 * @author philippe
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class MaintenanceTest {

	/**
	 * A {@link Maintenance}
	 */
	private Maintenance maintenance;

	/**
	 * Maintenance DAO
	 */
	@Autowired
	private MaintenanceDAO maintenanceDAO;

	/**
	 * Set up the context
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		maintenance = Maintenance.getMaintenance(Maintenance.FULL);
		maintenanceDAO.createMaintenance(maintenance);
	}

	/**
	 * Clear the context
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		maintenance = null;
		maintenanceDAO.deleteAll();
	}

	/**
	 * Test the hashCode method
	 */
	@Test
	public void testHashCode() {
		Maintenance m = maintenanceDAO.findByName("Full");
		assertEquals(maintenance.hashCode(), m.hashCode());
	}

	/**
	 * Test the toString() method
	 */
	@Test
	public void testToString() {
		assertEquals("Full", maintenance.toString());
	}

	/**
	 * Test the equals(Object) method
	 */
	@Test
	public void testEqualsObject() {
		Maintenance m = maintenanceDAO.findByName("Full");
		assertEquals(maintenance, m);
	}

}
