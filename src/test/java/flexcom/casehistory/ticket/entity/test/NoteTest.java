package flexcom.casehistory.ticket.entity.test;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import flexcom.casehistory.data.Data;
import flexcom.casehistory.data.TicketDataSet;
import flexcom.casehistory.data.UserDataSet;
import flexcom.casehistory.ticket.dao.NoteDAO;
import flexcom.casehistory.ticket.entity.Note;

/**
 * Test class for the {@link Note} entity. Setters and getters are not tested
 * here. Setters are tested in {@link NoteConstraintsTest}
 * 
 * @author philippe
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class NoteTest {

	/**
	 * A {@link Note}
	 */
	private Note note;
	
	/**
	 * ID for licence
	 */
	private long noteId;

	/**
	 * Licence DAO
	 */
	@Autowired
	private NoteDAO noteDAO;
	
	@Autowired
	private UserDataSet userDataSet;
	
	@Autowired
	private TicketDataSet ticketDataSet;

	/**
	 * Set up the context
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		ticketDataSet.setup();
		userDataSet.setup();
		
		note = new Note();
		note.setNote("test");
		note.setTicket(Data.TICKET_1);
		note.setAuthor(Data.USER_1);

		noteDAO.createNote(note);
		noteId = note.getId();
	}

	/**
	 * Clear the context
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		noteDAO.deleteAll();
		note = null;
		userDataSet.clear();
		ticketDataSet.clear();
	}

	/**
	 * Test the hashCode method
	 */
	@Test
	public void testHashCode() {
		Note n = noteDAO.findById(noteId);
		assertEquals(note.hashCode(), n.hashCode());
	}

	/**
	 * Test the toString() method
	 */
	@Test
	public void testToString() {
		Note n = noteDAO.findById(noteId);
		assertEquals("test", n.toString());
	}

	/**
	 * Test the equals(Object) method
	 */
	@Test
	public void testEqualsObject() {
		Note n = noteDAO.findById(noteId);
		assertEquals(note, n);
	}

}
