package flexcom.casehistory.mvc.editor.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import flexcom.casehistory.mvc.editor.CompanyEditor;
import flexcom.casehistory.ticket.dao.ClientDAO;
import flexcom.casehistory.ticket.dao.MaintenanceDAO;
import flexcom.casehistory.ticket.entity.Client;
import flexcom.casehistory.ticket.entity.Maintenance;

/**
 * Test class for {@link CompanyEditor}
 * 
 * @author philippe
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class CompanyEditorTest {

	/**
	 * A {@link Client}
	 */
	private Client client;

	/**
	 * The ID of the {@link Client}
	 */
	private long clientId;

	/**
	 * Client DAO
	 */
	@Autowired
	private ClientDAO clientDAO;

	/**
	 * Client editor
	 */
	@Autowired
	private CompanyEditor editor;

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
	 * Create and persist a {@link Client}
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		maintenance = Maintenance.getMaintenance(Maintenance.NONE);
		maintenanceDAO.createMaintenance(maintenance);
		
		client = new Client();
		client.setName("company");
		clientDAO.createClient(client);
		clientId = client.getId();
	}

	/**
	 * Clear the entity manager and the created {@link Client}. Put a
	 * <code>null</code> value for the editor
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		editor.setValue(null);
		clientDAO.deleteAll();
		maintenanceDAO.deleteAll();
		client = null;
	}

	/**
	 * Test if the editor return the correct {@link String} after a call to the
	 * setAsText() method
	 */
	@Test
	public void testGetAsText() {
		String id = "" + clientId;
		editor.setAsText(id);
		assertEquals(id, editor.getAsText());
	}

	/**
	 * Test if the editor return <code>null</code> without a call to the
	 * setAsText() method
	 */
	@Test
	public void testGetAsTextNullValue() {
		assertNull(editor.getAsText());
	}

	/**
	 * Test if the editor return the correct {@link Client} after a call to the
	 * setAsText() method
	 */
	@Test
	public void testSetAsTextString() {
		editor.setAsText("" + clientId);
		assertEquals(client, editor.getValue());
	}

	/**
	 * Test if the editor return <code>null</code> after a call to the
	 * setAsText() method with an invalid ID
	 */
	@Test
	public void testSetAsTextInvalidString() {
		editor.setAsText("" + Long.MAX_VALUE);
		assertNull(editor.getValue());
	}

}
