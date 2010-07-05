package flexcom.casehistory.ticket.dao;

import java.util.List;

import flexcom.casehistory.ticket.entity.Contact;
import flexcom.casehistory.ticket.search.Query;

public interface ContactDAO {
	
	/**
	 * @param contact
	 *            {@link Contact} to create
	 */
	public void createContact(Contact contact);

	/**
	 * @param contact
	 *            {@link Contact} to update
	 */
	public void updateContact(Contact contact);

	/**
	 * @param contact
	 *            {@link Contact} to delete
	 */
	public void deleteContact(Contact contact);

	/**
	 * @param id
	 *            {@link Contact} ID
	 * @return The {@link Contact} identified by the given ID
	 */
	public Contact findById(long id);

	/**
	 * Delete all contacts
	 */
	public void deleteAll();

	/**
	 * Find all contacts
	 * 
	 * @return A list containing all contacts
	 */
	public List<Contact> findAll();

	/**
	 * Find all contacts matching the {@link Query}
	 * 
	 * @param query
	 *            The {@link Query} to execute
	 * @return A list containing the result of the given {@link Query}
	 */
	public List<Contact> filter(Query<Contact> query);

	/**
	 * Count the number of {@link Contact} entities which are managed by this DAO
	 * 
	 * @return The number of managed {@link Contact} entities
	 */
	public long count();

	/**
	 * Find all contacts with the given name
	 * @param name The name of contacts
	 * @return A list containing all contacts with the given name
	 */
	public List<Contact> findByName(String name);

	/**
	 * Find all enabled contacts
	 * 
	 * @return A list containing all enabled contacts
	 */
	public List<Contact> findAllEnabled();
}
