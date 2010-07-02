package flexcom.casehistory.ticket.dao;

import java.util.List;

import flexcom.casehistory.ticket.entity.Licence;
import flexcom.casehistory.ticket.entity.Maintenance;
import flexcom.casehistory.ticket.search.Query;

/**
 * Interface providing methods (mainly CRUD operations and finders) for
 * implementing a {@link Licence} DAO
 * 
 * @author philippe
 * 
 */
public interface LicenceDAO {

	/**
	 * @param licence
	 *            {@link Licence} to create
	 */
	public void createLicence(Licence licence);

	/**
	 * @param licence
	 *            {@link Licence} to update
	 */
	public void updateLicence(Licence licence);

	/**
	 * @param licence
	 *            {@link Licence} to delete
	 */
	public void deleteLicence(Licence licence);

	/**
	 * @param id
	 *            {@link Licence} ID
	 * @return The {@link Licence} identified by the given ID
	 */
	public Licence findById(long id);

	/**
	 * Delete all licences
	 */
	public void deleteAll();

	/**
	 * Find all licences
	 * 
	 * @return A list containing all licences
	 */
	public List<Licence> findAll();

	/**
	 * Find all licences having this given key
	 * 
	 * @param key
	 *            an {@link Licence} key
	 * @return A list containing licences which have the given key
	 */
	public Licence findByLicenceKey(String key);

	/**
	 * Find all licences having this given maintenance type
	 * 
	 * @param maintenance
	 *            an {@link Licence} maintenance type
	 * @return A list containing licences who have the given maintenance type
	 */
	public List<Licence> findByMaintenance(Maintenance maintenance);

	/**
	 * Find all products matching the {@link Query}
	 * 
	 * @param query
	 *            The {@link Query} to execute
	 * @return A list containing the result of the given {@link Query}
	 */
	public List<Licence> filter(Query<Licence> query);

	/**
	 * Count the number of {@link Licence} entities which are managed by this
	 * DAO
	 * 
	 * @return The number of managed {@link Licence} entities
	 */
	public long count();
}
