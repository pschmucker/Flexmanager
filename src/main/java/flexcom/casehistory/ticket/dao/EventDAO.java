package flexcom.casehistory.ticket.dao;

import java.util.List;

import flexcom.casehistory.ticket.entity.Event;
import flexcom.casehistory.ticket.entity.Ticket;
import flexcom.casehistory.ticket.entity.User;
import flexcom.casehistory.ticket.search.Query;

/**
 * Interface providing methods (mainly CRUD operations and finders) for implementing a {@link Event} DAO 
 * @author philippe
 *
 */
public interface EventDAO {

	/**
	 * @param event {@link Event} to create
	 */
	public void createEvent(Event event);

	/**
	 * @param event {@link Event} to update
	 */
	public void updateEvent(Event event);

	/**
	 * @param event {@link Event} to delete
	 */
	public void deleteEvent(Event event);

	/**
	 * @param id {@link Event} ID
	 * @return The {@link Event} identified by the given ID
	 */
	public Event findById(long id);

	/**
	 * Delete all events
	 */
	public void deleteAll();

	/**
	 * Find all events
	 * @return A list containing all events
	 */
	public List<Event> findAll();

	/**
	 * Find all events having exactly this given action
	 * @param action an {@link Event} action
	 * @return A list containing events which have the given action
	 */
	public List<Event> findByAction(String action);

	/**
	 * Find all events concerning the given {@link Ticket}
	 * @param ticket The concerned {@link Ticket}
	 * @return A list containing events which concern the given {@link Ticket}
	 */
	public List<Event> findByTicket(Ticket ticket);

	/**
	 * Find all events thrown by the given {@link User}
	 * @param user The author
	 * @return A list containing events thrown by the given {@link User}
	 */
	public List<Event> findByAuthor(User user);

	/**
	 * Find all events matching the {@link Query}
	 * @param query The {@link Query} to execute
	 * @return A list containing the result of the given {@link Query}
	 */
	public List<Event> filter(Query<Event> query);

	/**
	 * Count the number of {@link Event} entities which are managed by this DAO
	 * 
	 * @return The number of managed {@link Event} entities
	 */
	public long count();
}
