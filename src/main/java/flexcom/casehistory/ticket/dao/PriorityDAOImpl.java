package flexcom.casehistory.ticket.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import flexcom.casehistory.ticket.entity.Priority;

/**
 * Priority DAO implementation
 * @author philippe
 *
 */
@Transactional
@Repository
public class PriorityDAOImpl extends JPAGenericDAO<Priority, Long> implements PriorityDAO {

	/**
	 * Default constructor
	 */
	public PriorityDAOImpl() {
		super(Priority.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long count() {
		return entityManager.createNamedQuery("priority.count", Long.class).getSingleResult();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createPriority(Priority priority) {
		create(priority);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteAll() {
		for (Priority p : findAll()) {
			entityManager.remove(p);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deletePriority(Priority priority) {
		delete(entityManager.getReference(Priority.class, priority.getId()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Priority> findAll() {
		return entityManager.createQuery("from Priority", Priority.class).getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Priority findById(long id) {
		return read(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Priority findByName(String name) {
		TypedQuery<Priority> q = entityManager.createNamedQuery("priority.findByName", Priority.class);
		q.setParameter("name", name);
		return q.getSingleResult();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updatePriority(Priority priority) {
		update(priority);
	}
}
