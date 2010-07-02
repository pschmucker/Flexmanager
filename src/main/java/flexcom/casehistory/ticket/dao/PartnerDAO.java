package flexcom.casehistory.ticket.dao;

import java.util.List;

import flexcom.casehistory.ticket.entity.Partner;
import flexcom.casehistory.ticket.search.Query;

/**
 * Interface providing methods (mainly CRUD operations and finders) for
 * implementing a {@link Partner} DAO
 * 
 * @author philippe
 * 
 */
public interface PartnerDAO {

	/**
	 * @param partner
	 *            {@link Partner} to create
	 */
	public void createPartner(Partner partner);

	/**
	 * @param partner
	 *            {@link Partner} to update
	 */
	public void updatePartner(Partner partner);

	/**
	 * @param partner
	 *            {@link Partner} to delete
	 */
	public void deletePartner(Partner partner);

	/**
	 * @param id
	 *            {@link Partner} ID
	 * @return The {@link Partner} identified by the given ID
	 */
	public Partner findById(long id);

	/**
	 * Delete all partners
	 */
	public void deleteAll();

	/**
	 * Find all partners
	 * 
	 * @return A list containing all partners
	 */
	public List<Partner> findAll();

	/**
	 * Find a partners who have this given company
	 * 
	 * @param name
	 *            an {@link Partner} name
	 * @return The partner who have the given name, or <code>null</code> if not found
	 */
	public Partner findByName(String name);

	/**
	 * Find all partners matching the {@link Query}
	 * 
	 * @param query
	 *            The {@link Query} to execute
	 * @return A list containing the result of the given {@link Query}
	 */
	public List<Partner> filter(Query<Partner> query);

	/**
	 * Count the number of {@link Partner} entities which are managed by this DAO
	 * 
	 * @return The number of managed {@link Partner} entities
	 */
	public long count();
}
