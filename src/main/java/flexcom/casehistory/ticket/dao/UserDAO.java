package flexcom.casehistory.ticket.dao;

import java.util.Date;
import java.util.List;

import flexcom.casehistory.ticket.entity.Ticket;
import flexcom.casehistory.ticket.entity.User;
import flexcom.casehistory.ticket.search.Query;

/**
 * Interface providing methods (mainly CRUD operations and finders) for implementing a {@link User} DAO 
 * @author philippe
 *
 */
public interface UserDAO {

	/**
	 * @param user
	 *            {@link User} to create
	 */
	public void createUser(User user);

	/**
	 * @param user
	 *            {@link User} to update
	 */
	public void updateUser(User user);

	/**
	 * @param user
	 *            {@link User} to delete
	 */
	public void deleteUser(User user);

	/**
	 * @param id
	 *            {@link User} ID
	 * @return The {@link User} identified by the given ID
	 */
	public User findById(long id);

	/**
	 * Delete all users
	 */
	public void deleteAll();

	/**
	 * Find all users
	 * 
	 * @return A list containing all users
	 */
	public List<User> findAll();

	/**
	 * Find all users having this given login
	 * 
	 * @param login
	 *            an {@link User} login
	 * @return A list containing users who have the given login
	 */
	public User findByLogin(String login);

	/**
	 * Find all users having this given name
	 * 
	 * @param name
	 *            an {@link User} name
	 * @return A list containing users who have the given name
	 */
	public List<User> findByName(String name);

	/**
	 * Find the user who have this given email
	 * 
	 * @param email
	 *            an {@link User} email
	 * @return The user who have the given email, or <code>null</code> if not found
	 */
	public User findByEmail(String email);

	/**
	 * Find all users having this given title
	 * 
	 * @param title
	 *            an {@link User} title
	 * @return A list containing users who have the given title
	 */
	public List<User> findByTitle(String title);

	/**
	 * Find all users having this given access level
	 * 
	 * @param accessLevel
	 *            an {@link User} access level
	 * @return A list containing users who have the given access level
	 */
	public List<User> findByAccessLevel(int accessLevel);

	/**
	 * Find all responsible users for the given {@link Ticket}
	 * 
	 * @param ticket
	 *            The assigned {@link Ticket}
	 * @return A list containing users who are responsible for the given
	 *         {@link Ticket}
	 */
	public List<User> findByAssignedTicket(Ticket ticket);

	/**
	 * Find all users matching the {@link Query}
	 * 
	 * @param query
	 *            The {@link Query} to execute
	 * @return A list containing the result of the given {@link Query}
	 */
	public List<User> filter(Query<User> query);

	/**
	 * Count the number of {@link User} entities which are managed by this DAO
	 * 
	 * @return The number of managed {@link User} entities
	 */
	public long count();

	/**
	 * Change the password for the given {@link User}
	 * @param user The user whose password will be changed
	 * @param pwd The new password
	 */
	public void changePassword(User user, String pwd);
	
	/**
	 * Check if the given string matches the user's password
	 * @param user The user whose password will be checked
	 * @param pwd The string to match with the user's password
	 * @return true if the string matches the user's password
	 */
	public boolean checkPassword(User user, String pwd);
	
	/**
	 * Update the last login for an user
	 * @param user 
	 * @param lastLogin 
	 */
	public void updateLastLogin(User user, Date lastLogin);

	/**
	 * Find all enabled users
	 * 
	 * @return A list containing all enabled users
	 */
	public List<User> findAllEnabled();
}
