package flexcom.casehistory.ticket.entity.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import javax.validation.ConstraintViolationException;

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
import flexcom.casehistory.ticket.entity.Ticket;
import flexcom.casehistory.ticket.entity.User;

/**
 * Class test for setters of the {@link Note} class. Here we check all
 * constraints on every fields.
 * 
 * @author philippe
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class NoteConstraintsTest {

	/**
	 * Note DAO
	 */
	@Autowired
	private NoteDAO noteDAO;
	
	private Note note;

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
	}

	/**
	 * Clear the context
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		note = null;
		noteDAO.deleteAll();
		userDataSet.clear();
		ticketDataSet.clear();
	}

	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * {@link Note} with a <code>null</code> note
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSetNoteNull() {
		note.setNote(null);
		noteDAO.createNote(note);
	}

	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * {@link Note} with a too long attachment link
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSetNameMaxSize() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("/This/is/a/path/to/a/file/but/the/problem/is/that/this/path");
		buffer.append("/is/very/very/very/very/very/very/very/very/very/very/very/");
		buffer.append("/very/very/very/very/very/very/very/very/very/very/very/");
		buffer.append("/very/very/very/very/very/very/very/very/very/very/very/");
		buffer.append("/very/very/very/very/very/very/very/very/very/very/very/");
		buffer.append("/long/and/it/exceeds/the/limit/size/for/paths/in/Windows/filesystem.txt");
		note.setAttachment(buffer.toString());
		noteDAO.createNote(note);
	}

	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * {@link Note} with a <code>null</code> {@link Ticket}
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSetTicketNull() {
		note.setTicket(null);
		noteDAO.createNote(note);
	}

	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * {@link Note} with a <code>null</code> {@link User}
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSetAuthorNull() {
		note.setAuthor(null);
		noteDAO.createNote(note);
	}

	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * {@link Note} which creation date is <code>null</code>
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSetDateCreationNull() {
		note.setCreationDate(null);
		noteDAO.createNote(note);
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

		Note note = new Note();
		note.setNote("test");
		note.setTicket(Data.TICKET_1);
		note.setAuthor(Data.USER_1);

		Thread.sleep(1000);
		Date after = new Date();

		noteDAO.createNote(note);
		long id = note.getId();

		Note n = noteDAO.findById(id);
		assertNotNull(n.getCreationDate());
		assertTrue(n.getCreationDate().after(before));
		assertTrue(n.getCreationDate().before(after));
	}

	/**
	 * Test if the creation date of an {@link Note} can't be updated
	 * @throws InterruptedException 
	 */
	// TODO @Test does not work
	public void testUpdateCreationDate() throws InterruptedException{
		Date d = note.getCreationDate();
		noteDAO.createNote(note);
		long id = note.getId();
		Thread.sleep(1000);
		
		Note n = noteDAO.findById(id);
		n.setCreationDate(new Date());
		noteDAO.updateNote(n);
		
		n = null;
		n = noteDAO.findById(id);
		assertFalse(n.getCreationDate() != d);
	}

}
