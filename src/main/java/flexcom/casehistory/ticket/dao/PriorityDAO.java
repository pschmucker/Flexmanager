package flexcom.casehistory.ticket.dao;

import java.util.List;

import flexcom.casehistory.ticket.entity.Priority;
import flexcom.casehistory.ticket.search.Query;

/**
 * Interface providing methods (mainly CRUD operations and finders) for
 * implementing a {@link Priority} DAO
 * 
 * @author philippe
 * 
 */
public interface PriorityDAO {

	/**
	 * @param priority
	 *            {@link Priority} to create
	 */
	public void createPriority(Priority priority);

	/**
	 * @param priority
	 *            {@link Priority} to update
	 */
	public void updatePriority(Priority priority);

	/**
	 * @param priority
	 *            {@link Priority} to delete
	 */
	public void deletePriority(Priority priority);

	/**
	 * @param id
	 *            {@link Priority} ID
	 * @return The {@link Priority} identified by the given ID
	 */
	public Priority findById(long id);

	/**
	 * Delete all priorities
	 */
	public void deleteAll();

	/**
	 * Find all priorities
	 * 
	 * @return A list containing all priorities
	 */
	public List<Priority> findAll();

	/**
	 * Find the priority having this given name
	 * 
	 * @param name
	 *            an {@link Priority} name
	 * @return The {@link Priority} which have the given name
	 */
	public Priority findByName(String name);

	/**
	 * Find all priorities matching the {@link Query}
	 * 
	 * @param query
	 *            The {@link Query} to execute
	 * @return A list containing the result of the given {@link Query}
	 */
	public List<Priority> filter(Query<Priority> query);

	/**
	 * Count the number of {@link Priority} entities which are managed by this
	 * DAO
	 * 
	 * @return The number of managed {@link Priority} entities
	 */
	public long count();

	/**
	 * Find all enabled priorities
	 * 
	 * @return A list containing all enabled priorities
	 */
	public List<Priority> findAllEnabled();
}
