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

import flexcom.casehistory.data.Data;
import flexcom.casehistory.data.TicketDataSet;
import flexcom.casehistory.data.UserDataSet;
import flexcom.casehistory.ticket.dao.NoteDAO;
import flexcom.casehistory.ticket.dao.TicketDAOImpl;
import flexcom.casehistory.ticket.entity.Note;
import flexcom.casehistory.ticket.search.Query;

/**
 * Test class for {@link TicketDAOImpl}
 * 
 * @author philippe
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class NoteDAOImplTest {

	/**
	 * A {@link Note}
	 */
	private Note note;

	/**
	 * Another {@link Note}
	 */
	private Note tmp;

	/**
	 * ID for the {@link Note}
	 */
	private long noteId;

	/**
	 * Note DAO
	 */
	@Autowired
	private NoteDAO noteDAO;
	
	@Autowired
	private TicketDataSet ticketDataSet;
	
	@Autowired
	private UserDataSet userDataSet;

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
		
		tmp = new Note();
		tmp.setNote("tmp");
		tmp.setTicket(Data.TICKET_1);
		tmp.setAuthor(Data.USER_1);
		noteDAO.createNote(tmp);
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
		tmp = null;
		userDataSet.clear();
		ticketDataSet.clear();
	}

	/**
	 * Test the createNote(Note) method : the DAO must have an additionnal
	 * entity after the creation
	 */
	@Test
	public void testCreateNote() {
		int count = (int) noteDAO.count();
		Note n = new Note();
		n.setNote("test");
		n.setTicket(Data.TICKET_1);
		n.setAuthor(Data.USER_1);
		noteDAO.createNote(n);
		assertEquals(count + 1, noteDAO.count());
	}

	/**
	 * Test if the createNote(Note) method throws a
	 * {@link InvalidDataAccessApiUsageException} by passing a <code>null</code>
	 * {@link Note}
	 */
	@Test(expected = InvalidDataAccessApiUsageException.class)
	public void testCreateNullNote() {
		noteDAO.createNote(null);
	}

	/**
	 * Test if the findAll() method return a correct and complete list of all
	 * notes. The following conditions are checked :<br/>
	 * <ul>
	 * <li>2 notes are in the list</li>
	 * <li>The list contains the note "test"</li>
	 * <li>The list contains the note "tmp"</li>
	 * </ul>
	 */
	@Test
	public void testFindAll() {
		List<Note> notes = noteDAO.findAll();
		assertEquals(2, notes.size());
		assertTrue(notes.contains(note));
		assertTrue(notes.contains(tmp));
	}

	/**
	 * Test if the modification of an entity has been correctly done
	 */
	@Test
	public void testUpdateNote() {
		note.setNote("new test");
		noteDAO.updateNote(note);
		assertEquals("new test", noteDAO.findById(noteId).getNote());
	}

	/**
	 * Test if the updateNote(Note) method throws an
	 * {@link InvalidDataAccessApiUsageException} if the given argument is
	 * <code>null</code>
	 */
	@Test(expected = NullPointerException.class)
	public void testUpdateNullNote() {
		noteDAO.updateNote(null);
	}

	/**
	 * Test if all notes with the specified text are found. The following
	 * conditions are checked : <br/>
	 * <ul>
	 * <li>The list contains 1 note</li>
	 * <li>The list contains the ticket "test"</li>
	 * </ul>
	 */
	@Test
	public void testFindByNote() {
		List<Note> notes = noteDAO.findByNote("test");

		assertEquals(1, notes.size());
		assertEquals(note, notes.get(0));
	}

	/**
	 * Test the deletion of an {@link Note}. We check that the deleted
	 * {@link Note} can't be found.
	 */
	@Test
	public void testDeleteNote() {
		noteDAO.deleteNote(note);
		assertNull(noteDAO.findById(noteId));
	}

	/**
	 * Test if a {@link NullPointerException} is thrown by trying to delete a
	 * <code>null</code> {@link Note}
	 */
	@Test(expected = NullPointerException.class)
	public void testDeleteNullNote() {
		noteDAO.deleteNote(null);
	}

	/**
	 * Test if the DAO have no more {@link Note} entities after the call of
	 * deleteAll() method
	 */
	@Test
	public void testDeleteAll() {
		noteDAO.deleteAll();
		assertEquals(0, noteDAO.count());
	}

	/**
	 * Test if an {@link Note} can be found by his ID
	 */
	@Test
	public void testFindById() {
		assertEquals(note, noteDAO.findById(noteId));
	}

	/**
	 * Test some {@link Note} {@link Query}. First, we create a {@link Query}
	 * without filters. Then, after each execution of this query, we add a
	 * filter. The following scenario is executed :<br/>
	 * <ul>
	 * <li>0 filters : 2 notes found</li>
	 * <li>Filter () added : 1 note found</li>
	 * <li>Filter () added : no note found</li>
	 * </ul>
	 */
	@Test
	public void testFilter() {
		List<Note> notes;
		Query<Note> query = new Query<Note>(Note.class);

		notes = noteDAO.filter(query);
		assertEquals(2, notes.size());

		// TODO test some filters
	}

	/**
	 * Test the count() method
	 */
	@Test
	public void testCount() {
		assertEquals(2, noteDAO.count());
	}
}
