package flexcom.casehistory.ticket.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import flexcom.casehistory.ticket.search.Query;
import flexcom.casehistory.ticket.search.QueryProcessor;

/**
 * Generic DAO for a JPA implementation, using an {@link EntityManager}. In
 * addition of the 4 CRUD operations, this class provides a filter method which
 * is usefull for executing complex query.
 * 
 * @author philippe
 * 
 * @param <T>
 *            Type of the entity
 * @param <I>
 *            Type of the identifier
 */
@Transactional
public abstract class JPAGenericDAO<T, I> implements GenericDAO<T, I> {

	/**
	 * The entity manager
	 */
	@PersistenceContext
	protected EntityManager entityManager;

	/**
	 * The query processor
	 */
	@Autowired
	protected QueryProcessor<T> queryProcessor;

	/**
	 * Type of the managed entities
	 */
	protected Class<? extends T> clazz;

	/**
	 * Single constructor for this generic abstract DAO
	 * 
	 * @param clazz
	 *            Type of the managed entities
	 */
	protected JPAGenericDAO(Class<? extends T> clazz) {
		this.clazz = clazz;
	}

	/**
	 * {@inheritDoc} Make the entity persistant
	 */
	@Override
	public void create(T entity) {
		entityManager.persist(entity);
	}

	/**
	 * {@inheritDoc} Remove the entity from the {@link EntityManager}
	 */
	@Override
	public void delete(T entity) {
		entityManager.remove(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T read(I id) {
		return entityManager.find(clazz, id);
	}

	/**
	 * {@inheritDoc} Merge the entity into the {@link EntityManager}
	 */
	@Override
	public void update(T entity) {
		entityManager.merge(entity);
	}

	/**
	 * Execute a {@link Query} using filters, and return the result as a list
	 * 
	 * @param query
	 *            The query to execute
	 * @return The result list
	 */
	public List<T> filter(Query<T> query) {
		return queryProcessor.execute(query);
	}

}
