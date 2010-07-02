package flexcom.casehistory.ticket.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import flexcom.casehistory.ticket.entity.Status;

@Transactional
@Repository
public class StatusDAOImpl extends JPAGenericDAO<Status, Long> implements StatusDAO {
	
	/**
	 * Default constructor
	 */
	public StatusDAOImpl(){
		super(Status.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long count() {
		return entityManager.createNamedQuery("status.count", Long.class).getSingleResult();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createStatus(Status status) {
		create(status);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteAll() {
		for (Status s : findAll()) {
			entityManager.remove(s);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteStatus(Status status) {
		delete(entityManager.getReference(Status.class, status.getId()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Status> findAll() {
		return entityManager.createQuery("from Status", Status.class).getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Status findById(long id) {
		return read(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Status findByName(String name) {
		TypedQuery<Status> q = entityManager.createNamedQuery("status.findByName", Status.class);
		q.setParameter("name", name);
		return q.getSingleResult();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateStatus(Status status) {
		update(status);
	}

}
