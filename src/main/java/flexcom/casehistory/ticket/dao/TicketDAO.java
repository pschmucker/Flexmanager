package flexcom.casehistory.ticket.dao;

import java.util.List;

import flexcom.casehistory.ticket.entity.Client;
import flexcom.casehistory.ticket.entity.Priority;
import flexcom.casehistory.ticket.entity.Product;
import flexcom.casehistory.ticket.entity.Status;
import flexcom.casehistory.ticket.entity.Ticket;
import flexcom.casehistory.ticket.entity.User;
import flexcom.casehistory.ticket.search.Query;

/**
 * Interface providing methods (mainly CRUD operations and finders) for implementing a {@link Ticket} DAO 
 * @author philippe
 *
 */
public interface TicketDAO {

	/**
	 * @param ticket {@link Ticket} to create
	 */
	public void createTicket(Ticket ticket);

	/**
	 * @param ticket {@link Ticket} to update
	 */
	public void updateTicket(Ticket ticket);

	/**
	 * @param ticket {@link Ticket} to delete
	 */
	public void deleteTicket(Ticket ticket);

	/**
	 * @param id {@link Ticket} ID
	 * @return The {@link Ticket} identified by the given ID
	 */
	public Ticket findById(long id);

	/**
	 * Delete all tickets
	 */
	public void deleteAll();

	/**
	 * Find all tickets
	 * @return A list containing all tickets
	 */
	public List<Ticket> findAll();

	/**
	 * Find all tickets having exactly this given title
	 * @param title an exact {@link Ticket} title
	 * @return A list containing tickets which have the given title
	 */
	public List<Ticket> findByTitle(String title);

	/**
	 * Find all tickets assigned to the given {@link User}
	 * @param user The responsible {@link User}
	 * @return A list containing tickets which are assigned to the given {@link User}
	 */
	public List<Ticket> findByUserInCharge(User user);

	/**
	 * Find all tickets having the given {@link Status}
	 * @param status {@link Ticket} status
	 * @return A list containing tickets which have the given {@link Status}
	 */
	public List<Ticket> findByStatus(Status status);

	/**
	 * Find all tickets having the given {@link Priority}
	 * @param priority {@link Ticket} priority
	 * @return A list containing tickets which have the given {@link Priority}
	 */
	public List<Ticket> findByPriority(Priority priority);

	/**
	 * Find all tickets which are not assigned
	 * @return A list containing tickets which are not assigned
	 */
	public List<Ticket> findAllUnassigned();

	/**
	 * Find all tickets concerning the given {@link Client}
	 * @param client The concerned {@link Client}
	 * @return A list containing tickets which concern the given {@link Client}
	 */
	public List<Ticket> findByClient(Client client);

	/**
	 * Find all tickets concerning the given {@link Product}
	 * @param product The concerned {@link Product}
	 * @return A list containing tickets which concern the given {@link Product}
	 */
	public List<Ticket> findByProduct(Product product);

	/**
	 * Find all tickets matching the {@link Query}
	 * @param query The {@link Query} to execute
	 * @return A list containing the result of the given {@link Query}
	 */
	public List<Ticket> filter(Query<Ticket> query);

	/**
	 * Count the number of {@link Ticket} entities which are managed by this DAO
	 * 
	 * @return The number of managed {@link Ticket} entities
	 */
	public long count();
}
