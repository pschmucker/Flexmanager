package flexcom.casehistory.ticket.dao;

import java.util.List;

import flexcom.casehistory.ticket.entity.Role;
import flexcom.casehistory.ticket.search.Query;

/**
 * Interface providing methods (mainly CRUD operations and finders) for
 * implementing a {@link Role} DAO
 * 
 * @author philippe
 * 
 */
public interface RoleDAO {

	/**
	 * @param role
	 *            {@link Role} to create
	 */
	public void createRole(Role role);

	/**
	 * @param role
	 *            {@link Role} to update
	 */
	public void updateRole(Role role);

	/**
	 * @param role
	 *            {@link Role} to delete
	 */
	public void deleteRole(Role role);

	/**
	 * @param id
	 *            {@link Role} ID
	 * @return The {@link Role} identified by the given ID
	 */
	public Role findById(long id);

	/**
	 * Delete all roles
	 */
	public void deleteAll();

	/**
	 * Find all roles
	 * 
	 * @return A list containing all roles
	 */
	public List<Role> findAll();

	/**
	 * Find the role having this given name
	 * 
	 * @param name
	 *            an {@link Role} name
	 * @return The {@link Role} which have the given name
	 */
	public Role findByName(String name);

	/**
	 * Find all roles matching the {@link Query}
	 * 
	 * @param query
	 *            The {@link Query} to execute
	 * @return A list containing the result of the given {@link Query}
	 */
	public List<Role> filter(Query<Role> query);

	/**
	 * Count the number of {@link Role} entities which are managed by this
	 * DAO
	 * 
	 * @return The number of managed {@link Role} entities
	 */
	public long count();

	/**
	 * Find all enabled roles
	 * 
	 * @return A list containing all enabled roles
	 */
	public List<Role> findAllEnabled();

}
