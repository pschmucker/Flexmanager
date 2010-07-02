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
import flexcom.casehistory.ticket.dao.MaintenanceDAO;
import flexcom.casehistory.ticket.entity.Client;
import flexcom.casehistory.ticket.entity.Maintenance;

/**
 * Class test for setters of the {@link Client} class. Here we check all
 * constraints on every fields.
 * 
 * @author philippe
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class ClientConstraintsTest {

	/**
	 * Client DAO
	 */
	@Autowired
	private ClientDAO clientDAO;

	/**
	 * A {@link Client}
	 */
	private Client client;

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
		maintenance = Maintenance.getMaintenance(Maintenance.TEST);
		maintenanceDAO.createMaintenance(maintenance);
		
		client = new Client();
		client.setName("A simple company");
		client.setAddress("Somewhere on earth");
	}

	/**
	 * Clear the context
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		client = null;
		clientDAO.deleteAll();
		maintenanceDAO.deleteAll();
	}

	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * {@link Client} with a <code>null</code> company
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSetCompanyNull() {
		client.setName(null);
		clientDAO.createClient(client);
	}

	/**
	 * Test if a {@link DataIntegrityViolationException} is thrown when we
	 * create 2 clients with the same commpany name
	 */
	@Test(expected = DataIntegrityViolationException.class)
	public void testSetCompanyUnique() {
		client.setName("unique");
		clientDAO.createClient(client);

		Client copy = new Client();
		copy.setName("unique");
		clientDAO.createClient(copy);
	}

	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * {@link Client} with a too long company
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSetCompanyMaxSize() {
		client.setName("This is an original company with an extremely long name");
		clientDAO.createClient(client);
	}
	
	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * {@link Client} with an empty company name
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSetEmptyCompanyName(){
		client.setName("");
		clientDAO.createClient(client);
	}

	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * {@link Client} with a too long address
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSetAddressMaxSize() {
		StringBuffer address = new StringBuffer();
		address.append("n° 120, 121, 122, 123, 124, 125, 126, 127, 128, et 129 \n");
		address.append("the longest and unbelievable avenue name you will never see \n");
		address.append("The six hundred and sixty sixth apartment on the sixth stage \n");
		address.append("The most little imaginary town with the longest name in this world \n");
		address.append("The country where all names all too long and don't fit in software's form");
		client.setAddress(address.toString());
		clientDAO.createClient(client);
	}

	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * {@link Client} with a too long country
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSetCountryMaxSize() {
		client.setCountry("This_Is_The_Longest_Country_Name_I_Have_Never_Seen!");
		clientDAO.createClient(client);
	}

	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * {@link Client} whose creation date is <code>null</code>
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSetDateCreationNull() {
		client.setCreationDate(null);
		clientDAO.createClient(client);
	}

	/**
	 * Test if the default creation date is set to the current date
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void testSetDateCreationDefault() throws InterruptedException {
		Date before = new Date();
		Thread.sleep(1000);

		Client client = new Client();
		client.setName("tmp");

		Thread.sleep(1000);
		Date after = new Date();

		clientDAO.createClient(client);
		long id = client.getId();

		Client p = clientDAO.findById(id);
		assertNotNull(p.getCreationDate());
		assertTrue(p.getCreationDate().after(before));
		assertTrue(p.getCreationDate().before(after));
	}

	/**
	 * Test if the creation date of an {@link Client} can't be updated
	 * @throws InterruptedException 
	 */
	// TODO @Test does not work
	public void testUpdateCreationDate() throws InterruptedException{
		Date d = client.getCreationDate();
		clientDAO.createClient(client);
		long id = client.getId();
		Thread.sleep(1000);
		
		Client c = clientDAO.findById(id);
		c.setCreationDate(new Date());
		clientDAO.updateClient(c);
		
		c = null;
		c = clientDAO.findById(id);
		assertFalse(c.getCreationDate() != d);
	}

}
