package flexcom.casehistory.ticket.dao;

import java.util.List;

import flexcom.casehistory.ticket.entity.Maintenance;
import flexcom.casehistory.ticket.search.Query;

/**
 * Interface providing methods (mainly CRUD operations and finders) for
 * implementing a {@link Maintenance} DAO
 * 
 * @author philippe
 * 
 */
public interface MaintenanceDAO {

	/**
	 * @param maintenance
	 *            {@link Maintenance} to create
	 */
	public void createMaintenance(Maintenance maintenance);

	/**
	 * @param maintenance
	 *            {@link Maintenance} to update
	 */
	public void updateMaintenance(Maintenance maintenance);

	/**
	 * @param maintenance
	 *            {@link Maintenance} to delete
	 */
	public void deleteMaintenance(Maintenance maintenance);

	/**
	 * @param id
	 *            {@link Maintenance} ID
	 * @return The {@link Maintenance} identified by the given ID
	 */
	public Maintenance findById(long id);

	/**
	 * Delete all maintenances
	 */
	public void deleteAll();

	/**
	 * Find all maintenances
	 * 
	 * @return A list containing all maintenances
	 */
	public List<Maintenance> findAll();

	/**
	 * Find the maintenance having this given name
	 * 
	 * @param name
	 *            an {@link Maintenance} name
	 * @return The {@link Maintenance} which have the given name
	 */
	public Maintenance findByName(String name);

	/**
	 * Find all maintenances matching the {@link Query}
	 * 
	 * @param query
	 *            The {@link Query} to execute
	 * @return A list containing the result of the given {@link Query}
	 */
	public List<Maintenance> filter(Query<Maintenance> query);

	/**
	 * Count the number of {@link Maintenance} entities which are managed by this
	 * DAO
	 * 
	 * @return The number of managed {@link Maintenance} entities
	 */
	public long count();
	
	/**
	 * Find all enabled maintenances
	 * 
	 * @return A list containing all enabled maintenances
	 */
	public List<Maintenance> findAllEnabled();

}
