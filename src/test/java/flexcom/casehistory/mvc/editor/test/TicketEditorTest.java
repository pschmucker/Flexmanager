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

import flexcom.casehistory.data.Data;
import flexcom.casehistory.data.TicketDataSet;
import flexcom.casehistory.mvc.editor.TicketEditor;
import flexcom.casehistory.ticket.entity.Ticket;

/**
 * Test class for {@link TicketEditor}
 * 
 * @author philippe
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class TicketEditorTest {

	/**
	 * The ID of the {@link Ticket}
	 */
	private long ticketId;

	/**
	 * Ticket editor
	 */
	@Autowired
	private TicketEditor editor;
	
	@Autowired
	private TicketDataSet ticketDataSet;

	/**
	 * Create and persist a {@link Ticket}
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		ticketDataSet.setup();
		ticketId = Data.TICKET_1.getId();
	}

	/**
	 * Clear the entity manager and the created {@link Ticket}. Put a
	 * <code>null</code> value for the editor
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		ticketDataSet.clear();
		editor.setValue(null);
	}

	/**
	 * Test if the editor return the correct {@link String} after a call to the
	 * setAsText() method
	 */
	@Test
	public void testGetAsText() {
		String id = "" + ticketId;
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
	 * Test if the editor return the correct {@link Ticket} after a call to the
	 * setAsText() method
	 */
	@Test
	public void testSetAsTextString() {
		editor.setAsText("" + ticketId);
		assertEquals(Data.TICKET_1, editor.getValue());
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
