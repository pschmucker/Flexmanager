package flexcom.casehistory.ticket.dao;

import java.util.List;

import flexcom.casehistory.ticket.entity.Note;
import flexcom.casehistory.ticket.entity.Ticket;
import flexcom.casehistory.ticket.entity.User;
import flexcom.casehistory.ticket.search.Query;

/**
 * Interface providing methods (mainly CRUD operations and finders) for implementing a {@link Note} DAO 
 * @author philippe
 *
 */
public interface NoteDAO {

	/**
	 * @param note {@link Note} to create
	 */
	public void createNote(Note note);

	/**
	 * @param note {@link Note} to update
	 */
	public void updateNote(Note note);

	/**
	 * @param note {@link Note} to delete
	 */
	public void deleteNote(Note note);

	/**
	 * @param id {@link Note} ID
	 * @return The {@link Note} identified by the given ID
	 */
	public Note findById(long id);

	/**
	 * Delete all notes
	 */
	public void deleteAll();

	/**
	 * Find all notes
	 * @return A list containing all notes
	 */
	public List<Note> findAll();

	/**
	 * Find all notes having exactly this given note
	 * @param notes an exact {@link Note} text
	 * @return A list containing notes which have the given note
	 */
	public List<Note> findByNote(String note);

	/**
	 * Find all notes concerning the given {@link Ticket}
	 * @param ticket The concerned {@link Ticket}
	 * @return A list containing notes which concern the given {@link Ticket}
	 */
	public List<Note> findByTicket(Ticket ticket);

	/**
	 * Find all notes written by the given {@link User}
	 * @param user The author
	 * @return A list containing notes written by the given {@link User}
	 */
	public List<Note> findByAuthor(User user);

	/**
	 * Find all notes matching the {@link Query}
	 * @param query The {@link Query} to execute
	 * @return A list containing the result of the given {@link Query}
	 */
	public List<Note> filter(Query<Note> query);

	/**
	 * Count the number of {@link Note} entities which are managed by this DAO
	 * 
	 * @return The number of managed {@link Note} entities
	 */
	public long count();
}
