package flexcom.casehistory.ticket.dao;

/**
 * Generic interface for DAOs classes. This interface provides the 4 standard
 * CRUD operations : Create, Read, Update, Delete.
 * 
 * @author philippe
 * 
 * @param <T>
 *            Type of the entity
 * @param <I>
 *            Type of the identifier
 */
public interface GenericDAO<T, I> {

	/**
	 * Create the entity argument
	 * 
	 * @param entity
	 *            The entity to create
	 */
	public void create(T entity);

	/**
	 * Read an unique entity identified by the given ID
	 * 
	 * @param id
	 *            Identifier of the entity
	 * @return The entity identified by the given ID
	 */
	public T read(I id);

	/**
	 * Update the entity argument
	 * 
	 * @param entity
	 *            The entity to update
	 */
	public void update(T entity);

	/**
	 * Delete the entity argument
	 * 
	 * @param entity
	 *            The entity to delete
	 */
	public void delete(T entity);
}
