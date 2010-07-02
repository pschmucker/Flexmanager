package flexcom.casehistory.ticket.dao;

import java.util.List;

import flexcom.casehistory.ticket.entity.Status;
import flexcom.casehistory.ticket.search.Query;

/**
 * Interface providing methods (mainly CRUD operations and finders) for
 * implementing a {@link Status} DAO
 * 
 * @author philippe
 * 
 */
public interface StatusDAO {

	/**
	 * @param status
	 *            {@link Status} to create
	 */
	public void createStatus(Status status);

	/**
	 * @param status
	 *            {@link Status} to update
	 */
	public void updateStatus(Status status);

	/**
	 * @param status
	 *            {@link Status} to delete
	 */
	public void deleteStatus(Status status);

	/**
	 * @param id
	 *            {@link Status} ID
	 * @return The {@link Status} identified by the given ID
	 */
	public Status findById(long id);

	/**
	 * Delete all statuses
	 */
	public void deleteAll();

	/**
	 * Find all statuses
	 * 
	 * @return A list containing all statuses
	 */
	public List<Status> findAll();

	/**
	 * Find the status having this given name
	 * 
	 * @param name
	 *            an {@link Status} name
	 * @return The {@link Status} which have the given name
	 */
	public Status findByName(String name);

	/**
	 * Find all statuses matching the {@link Query}
	 * 
	 * @param query
	 *            The {@link Query} to execute
	 * @return A list containing the result of the given {@link Query}
	 */
	public List<Status> filter(Query<Status> query);

	/**
	 * Count the number of {@link Status} entities which are managed by this
	 * DAO
	 * 
	 * @return The number of managed {@link Status} entities
	 */
	public long count();

}
