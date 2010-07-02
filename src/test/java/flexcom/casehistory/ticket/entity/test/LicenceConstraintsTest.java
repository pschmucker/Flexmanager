package flexcom.casehistory.ticket.entity.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.Date;

import javax.validation.ConstraintViolationException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import flexcom.casehistory.ticket.dao.ClientDAO;
import flexcom.casehistory.ticket.dao.LicenceDAO;
import flexcom.casehistory.ticket.dao.MaintenanceDAO;
import flexcom.casehistory.ticket.dao.ProductDAO;
import flexcom.casehistory.ticket.entity.Client;
import flexcom.casehistory.ticket.entity.Licence;
import flexcom.casehistory.ticket.entity.Maintenance;
import flexcom.casehistory.ticket.entity.Product;

/**
 * Class test for setters of the {@link Licence} class. Here we check all
 * constraints on every fields.
 * 
 * @author philippe
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class LicenceConstraintsTest {

	/**
	 * Licence DAO
	 */
	@Autowired
	private LicenceDAO licenceDAO;

	/**
	 * Client DAO
	 */
	@Autowired
	private ClientDAO clientDAO;

	/**
	 * Product DAO
	 */
	@Autowired
	private ProductDAO productDAO;

	/**
	 * A {@link Licence}
	 */
	private Licence licence;

	/**
	 * Maintenance DAO
	 */
	@Autowired
	private MaintenanceDAO maintenanceDAO;
	
	/**
	 * A Maintenance
	 */
	private Maintenance maintenance;

	/**
	 * Set up the context
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		maintenance = Maintenance.getMaintenance(Maintenance.NONE);
		maintenanceDAO.createMaintenance(maintenance);
		
		Client c = new Client();
		c.setName("company");
		clientDAO.createClient(c);

		Product p = new Product();
		p.setName("p");
		p.setVersion("1.0");
		p.setBuild("1");
		productDAO.createProduct(p);

		licence = new Licence();
		licence.setLicenceKey("key");
		licence.setCreationDate(new Date());
		licence.setClient(c);
		licence.setProduct(p);
		licence.setMaintenance(maintenance);
	}

	/**
	 * Clear the context
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		licence = null;
		licenceDAO.deleteAll();
		clientDAO.deleteAll();
		productDAO.deleteAll();
		maintenanceDAO.deleteAll();
	}

	/**
	 * Test if a {@link DataIntegrityViolationException} is thrown when we
	 * create 2 licences with the same key
	 */
	@Test(expected = DataIntegrityViolationException.class)
	public void testSetLicenceKeyUnique() {
		licence.setLicenceKey("unique");
		licenceDAO.createLicence(licence);

		Licence copy = new Licence();
		copy.setLicenceKey("unique");
		copy.setCreationDate(licence.getCreationDate());
		copy.setClient(licence.getClient());
		copy.setProduct(licence.getProduct());
		copy.setMaintenance(licence.getMaintenance());
		licenceDAO.createLicence(copy);
	}

	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * {@link Licence} with a too long key
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSetLicenceKeyMaxSize() {
		licence.setLicenceKey("089098-098083-454545-809808-3543543-9808-208432-5865454");
		licenceDAO.createLicence(licence);
	}

	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * {@link Licence} whose key is <code>null</code>
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSetLicenceKeyNull() {
		licence.setLicenceKey(null);
		licenceDAO.createLicence(licence);
	}

	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * licence whose date creation is <code>null</code>
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSetDateCreationNull() {
		licence.setCreationDate(null);
		licenceDAO.createLicence(licence);
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

		Client c = new Client();
		c.setName("tmp");
		clientDAO.createClient(c);

		Product p = new Product();
		p.setName("p");
		p.setVersion("1.0");
		p.setBuild("2");
		productDAO.createProduct(p);

		licence = new Licence();
		licence.setLicenceKey("tmp");
		licence.setCreationDate(new Date());
		licence.setClient(c);
		licence.setProduct(p);
		licence.setMaintenance(maintenance);

		Thread.sleep(1000);
		Date after = new Date();

		licenceDAO.createLicence(licence);
		long id = licence.getId();

		Licence l = licenceDAO.findById(id);
		assertNotNull(l.getCreationDate());
		assertTrue(l.getCreationDate().after(before));
		assertTrue(l.getCreationDate().before(after));
	}

	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * {@link Licence} whose expiration date is not in the future
	 * @throws InterruptedException 
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSetDateExpiration() throws InterruptedException {
		Date before = new Date();
		Thread.sleep(1000);
		
		licence.setExpirationDate(before);
		licenceDAO.createLicence(licence);
	}

	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * licence with a <code>null</code> {@link Client}
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSetClientNull() {
		licence.setClient(null);
		licenceDAO.createLicence(licence);
	}

	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * licence with a <code>null</code> {@link Product}
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSetProductNull() {
		licence.setProduct(null);
		licenceDAO.createLicence(licence);
	}
	
	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * {@link Licence} with a <code>null</code> {@link Maintenance}
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSetMaintenanceNull() {
		licence.setMaintenance(null);
		licenceDAO.createLicence(licence);
	}

	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * licence with a expiration date which is not in the future
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSetExpirationDateNotInFuture() {
		licence.setExpirationDate(licence.getCreationDate());
		licenceDAO.createLicence(licence);
	}	

	/**
	 * Test if the creation date of a {@link Licence} can't be updated
	 * @throws InterruptedException 
	 */
	// TODO @Test does not work
	public void testUpdateCreationDate() throws InterruptedException{
		Date d = licence.getCreationDate();
		licenceDAO.createLicence(licence);
		long id = licence.getId();
		Thread.sleep(1000);
		
		Licence l = licenceDAO.findById(id);
		l.setCreationDate(new Date());
		licenceDAO.updateLicence(l);
		
		l = null;
		l = licenceDAO.findById(id);
		assertFalse(l.getCreationDate() != d);
	}

}
