package flexcom.casehistory.ticket.entity.test;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import flexcom.casehistory.data.ClientDataSet;
import flexcom.casehistory.data.Data;
import flexcom.casehistory.ticket.dao.ClientDAO;
import flexcom.casehistory.ticket.entity.Client;

/**
 * Test class for the {@link Client} entity. Setters and getters are not tested
 * here. Setters are tested in {@link ClientConstraintsTest}
 * 
 * @author philippe
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class ClientTest {

	/**
	 * Client DAO
	 */
	@Autowired
	private ClientDAO clientDAO;
	
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
	 * Test the hashCode() method
	 */
	@Test
	public void testHashCode() {
		Client c = clientDAO.findByName("C1");
		assertEquals(Data.CLIENT_1.hashCode(), c.hashCode());
	}

	/**
	 * Test the equals(Object) method
	 */
	@Test
	public void testEqualsObject() {
		Client c = clientDAO.findByName("C1");
		assertEquals(Data.CLIENT_1, c);
	}

	/**
	 * Test the toString() method
	 */
	@Test
	public void testToString() {
		Client c = clientDAO.findByName("C1");
		assertEquals("C1", c.toString());
	}

}
