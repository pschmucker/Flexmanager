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

import flexcom.casehistory.data.ClientDataSet;
import flexcom.casehistory.data.Data;
import flexcom.casehistory.ticket.dao.ClientDAO;
import flexcom.casehistory.ticket.dao.ClientDAOImpl;
import flexcom.casehistory.ticket.entity.Client;
import flexcom.casehistory.ticket.search.Query;
import flexcom.casehistory.ticket.search.filter.EqualFilter;
import flexcom.casehistory.ticket.search.filter.IsNullFilter;

/**
 * Test class for {@link ClientDAOImpl}
 * 
 * @author philippe
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class ClientDAOImplTest {

	/**
	 * Client DAO
	 */
	@Autowired
	private ClientDAO clientDAO;

	/**
	 * ID for the {@link Client} "Unknown"
	 */
	private long clientId1;
	
	@Autowired
	private ClientDataSet clientDataSet;
	
	/**
	 * Set up the context
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		clientDataSet.setup();
		clientId1 = Data.CLIENT_1.getId();
	}

	/**
	 * Clear the context
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		clientDataSet.clear();
	}

	/**
	 * Test if the findAll() method return a correct and complete list of all
	 * clients. The following conditions are checked :<br/>
	 * <ul>
	 * <li>2 clients are in the list</li>
	 * <li>The list contains the client "Unknown"</li>
	 * <li>The list contains the client "Another company"</li>
	 * </ul>
	 */
	@Test
	public void testFindAll() {
		List<Client> clients = clientDAO.findAll();
		assertEquals(2, clients.size());
		assertTrue(clients.contains(Data.CLIENT_1));
		assertTrue(clients.contains(Data.CLIENT_2));
	}

	/**
	 * Test the createClient(Client) method : the DAO must have an additionnal
	 * entity after the creation
	 */
	@Test
	public void testCreateClient() {
		int count = (int) clientDAO.count();
		Client c = new Client();
		c.setName("company");
		clientDAO.createClient(c);
		assertEquals(count + 1, clientDAO.count());
	}

	/**
	 * Test if the createClient(Client) method throws a
	 * {@link InvalidDataAccessApiUsageException} by passing a <code>null</code>
	 * {@link Client}
	 */
	@Test(expected = InvalidDataAccessApiUsageException.class)
	public void testCreateNullClient() {
		clientDAO.createClient(null);
	}

	/**
	 * Test if all clients with the specified company are found. The following
	 * conditions are checked : <br/>
	 * <ul>
	 * <li>The list contains 1 client</li>
	 * <li>The list contains the client "Unknown"</li>
	 * </ul>
	 */
	@Test
	public void testFindByCompany() {
		Client client = clientDAO.findByName("C1");
		assertEquals(Data.CLIENT_1, client);
	}

	/**
	 * Test the deletion of an {@link Client}. We check that the deleted
	 * {@link Client} can't be found.
	 */
	@Test
	public void testDeleteClient() {
		clientDAO.deleteClient(Data.CLIENT_1);
		assertNull(clientDAO.findById(clientId1));
	}

	/**
	 * Test if a {@link NullPointerException} is thrown by trying to delete a
	 * <code>null</code> {@link Client}
	 */
	@Test(expected = NullPointerException.class)
	public void testDeleteNullClient() {
		clientDAO.deleteClient(null);
	}

	/**
	 * Test if the DAO have no more {@link Client} entities after the call of
	 * deleteAll() method
	 */
	@Test
	public void testDeleteAll() {
		clientDAO.deleteAll();
		assertEquals(0, clientDAO.count());
	}

	/**
	 * Test if an {@link Client} can be found by his ID
	 */
	@Test
	public void testFindById() {
		assertEquals(Data.CLIENT_1, clientDAO.findById(clientId1));
	}

	/**
	 * Test if the modification of an entity has been correctly done
	 */
	@Test
	public void testUpdateClient() {
		Data.CLIENT_1.setCountry("Luxembourg");
		clientDAO.updateClient(Data.CLIENT_1);
		assertEquals("Luxembourg", clientDAO.findById(clientId1).getCountry());
	}

	/**
	 * Test if the updateClient(Client) method throws an
	 * {@link InvalidDataAccessApiUsageException} if the given argument is
	 * <code>null</code>
	 */
	@Test(expected = InvalidDataAccessApiUsageException.class)
	public void testUpdateNullClient() {
		clientDAO.updateClient(null);
	}

	/**
	 * Test some {@link Client} {@link Query}. First, we create a {@link Query}
	 * without filters. Then, after each execution of this query, we add a
	 * filter. The following scenario is executed :<br/>
	 * <ul>
	 * <li>0 filters : 2 clients found</li>
	 * <li>Filter (contactName = Jones) added : 1 client found</li>
	 * <li>Filter (contactEmail is null) added : no client found</li>
	 * </ul>
	 */
	@Test
	public void testFilter() {
		List<Client> clients;
		Query<Client> query = new Query<Client>(Client.class);

		clients = clientDAO.filter(query);
		assertEquals(2, clients.size());

		query.addFilter(new EqualFilter("name", "C1"));

		clients = clientDAO.filter(query);
		assertEquals(1, clients.size());

		query.addFilter(new IsNullFilter("name"));

		clients = clientDAO.filter(query);
		assertEquals(0, clients.size());
	}

	/**
	 * Test the count() method
	 */
	@Test
	public void testCount() {
		assertEquals(2, clientDAO.count());
	}

}
