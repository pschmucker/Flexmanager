package flexcom.casehistory.ticket.entity.test;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
import flexcom.casehistory.ticket.entity.User;

/**
 * Test class for the {@link Licence} entity. Setters and getters are not tested
 * here. Setters are tested in {@link LicenceConstraintsTest}
 * 
 * @author philippe
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class LicenceTest {

	/**
	 * A {@link User}
	 */
	private Licence licence;
	
	/**
	 * ID for licence
	 */
	private long licenceId;

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

		licenceDAO.createLicence(licence);
		licenceId = licence.getId();
	}

	/**
	 * Clear the context
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		licenceDAO.deleteAll();
		licence = null;
		clientDAO.deleteAll();
		productDAO.deleteAll();
		maintenanceDAO.deleteAll();
	}

	/**
	 * Test the hashCode method
	 */
	@Test
	public void testHashCode() {
		Licence l = licenceDAO.findById(licenceId);
		assertEquals(licence.hashCode(), l.hashCode());
	}

	/**
	 * Test the toString() method
	 */
	@Test
	public void testToString() {
		Licence l = licenceDAO.findById(licenceId);
		assertEquals("key", l.toString());
	}

	/**
	 * Test the equals(Object) method
	 */
	@Test
	public void testEqualsObject() {
		Licence l = licenceDAO.findById(licenceId);
		assertEquals(licence, l);
	}

}
