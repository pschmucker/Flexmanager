package flexcom.casehistory.ticket.dao;

import java.util.List;

import flexcom.casehistory.ticket.entity.Client;
import flexcom.casehistory.ticket.search.Query;

/**
 * Interface providing methods (mainly CRUD operations and finders) for
 * implementing a {@link Client} DAO
 * 
 * @author philippe
 * 
 */
public interface ClientDAO {

	/**
	 * @param client
	 *            {@link Client} to create
	 */
	public void createClient(Client client);

	/**
	 * @param client
	 *            {@link Client} to update
	 */
	public void updateClient(Client client);

	/**
	 * @param client
	 *            {@link Client} to delete
	 */
	public void deleteClient(Client client);

	/**
	 * @param id
	 *            {@link Client} ID
	 * @return The {@link Client} identified by the given ID
	 */
	public Client findById(long id);

	/**
	 * Delete all clients
	 */
	public void deleteAll();

	/**
	 * Find all clients
	 * 
	 * @return A list containing all clients
	 */
	public List<Client> findAll();

	/**
	 * Find a clients who have this given company
	 * 
	 * @param name
	 *            an {@link Client} name
	 * @return The client who have the given name, or <code>null</code> if not found
	 */
	public Client findByName(String name);

	/**
	 * Find all clients matching the {@link Query}
	 * 
	 * @param query
	 *            The {@link Query} to execute
	 * @return A list containing the result of the given {@link Query}
	 */
	public List<Client> filter(Query<Client> query);

	/**
	 * Count the number of {@link Client} entities which are managed by this DAO
	 * 
	 * @return The number of managed {@link Client} entities
	 */
	public long count();
}
