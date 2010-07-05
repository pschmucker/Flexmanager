package flexcom.casehistory.ticket.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import flexcom.casehistory.ticket.entity.Maintenance;

@Transactional
@Repository
public class MaintenanceDAOImpl extends JPAGenericDAO<Maintenance, Long> implements MaintenanceDAO {
	
	/**
	 * Default constructor
	 */
	public MaintenanceDAOImpl(){
		super(Maintenance.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long count() {
		return entityManager.createNamedQuery("maintenance.count", Long.class).getSingleResult();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createMaintenance(Maintenance maintenance) {
		create(maintenance);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteAll() {
		for (Maintenance m : findAll()) {
			entityManager.remove(m);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteMaintenance(Maintenance maintenance) {
		delete(entityManager.getReference(Maintenance.class, maintenance.getId()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Maintenance> findAll() {
		return entityManager.createQuery("from Maintenance", Maintenance.class).getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Maintenance findById(long id) {
		return read(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Maintenance findByName(String name) {
		TypedQuery<Maintenance> q = entityManager.createNamedQuery("maintenance.findByName", Maintenance.class);
		q.setParameter("name", name);
		return q.getSingleResult();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateMaintenance(Maintenance maintenance) {
		update(maintenance);
	}

	@Override
	public List<Maintenance> findAllEnabled() {
		return entityManager.createQuery("select m from Maintenance m where m.enabled = true", Maintenance.class).getResultList();
	}

}
