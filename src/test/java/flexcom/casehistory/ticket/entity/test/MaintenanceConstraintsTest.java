package flexcom.casehistory.ticket.entity.test;

import javax.validation.ConstraintViolationException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import flexcom.casehistory.ticket.dao.MaintenanceDAO;
import flexcom.casehistory.ticket.entity.Maintenance;

/**
 * Class test for setters of the {@link Maintenance} class. Here we check all
 * constraints on every fields.
 * 
 * @author philippe
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class MaintenanceConstraintsTest {

	/**
	 * Maintenance DAO
	 */
	@Autowired
	private MaintenanceDAO maintenanceDAO;

	/**
	 * A {@link Maintenance}
	 */
	private Maintenance maintenance;

	/**
	 * Set up the context
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		maintenance = Maintenance.getMaintenance(Maintenance.FULL);
	}

	/**
	 * Clear the context
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		maintenanceDAO.deleteAll();
		maintenance = null;
	}
	
	/**
	 * Test if a {@link DataIntegrityViolationException} is thrown when we
	 * create 2 maintenances with the same name
	 */
	@Test(expected = DataIntegrityViolationException.class)
	public void testSetNameUnique() {
		maintenance.setName("unique");
		maintenanceDAO.createMaintenance(maintenance);

		Maintenance copy = new Maintenance();
		copy.setName("unique");
		maintenanceDAO.createMaintenance(copy);
	}

	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * {@link Maintenance} with a <code>null</code> name
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSetNameNull() {
		maintenance.setName(null);
		maintenanceDAO.createMaintenance(maintenance);
	}

	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * {@link Maintenance} with a too long name
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSetNameMaxSize() {
		maintenance.setName("This maintenance has a too long name");
		maintenanceDAO.createMaintenance(maintenance);
	}

	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * {@link Maintenance} with an empty name
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSetEmptyName() {
		maintenance.setName("");
		maintenanceDAO.createMaintenance(maintenance);
	}

}
