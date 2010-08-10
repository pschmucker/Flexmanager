package flexcom.casehistory.ticket.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import flexcom.casehistory.ticket.entity.Licence;
import flexcom.casehistory.ticket.entity.Maintenance;

/**
 * Licence DAO implementation
 * 
 * @author philippe
 *
 */
@Transactional
@Repository
public class LicenceDAOImpl extends JPAGenericDAO<Licence, Long> implements LicenceDAO {

	/**
	 * Default constructor
	 */
	public LicenceDAOImpl() {
		super(Licence.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long count() {
		return entityManager.createNamedQuery("licence.count", Long.class).getSingleResult();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createLicence(Licence licence) {
		create(licence);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteAll() {
		for (Licence l : findAll()) {
			entityManager.remove(l);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteLicence(Licence licence) {
		delete(entityManager.getReference(Licence.class, licence.getId()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Licence> findAll() {
		return entityManager.createQuery("from Licence", Licence.class).getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Licence findById(long id) {
		return read(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Licence findByLicenceKey(String key) {
		Licence l; 
		try {
			Query q = entityManager.createNamedQuery("licence.findByLicenceKey");
			q.setParameter("licenceKey", key);
			l = (Licence) q.getSingleResult();
		} catch (EmptyResultDataAccessException e) {
			l = null;
		}
		return l;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Licence> findByMaintenance(Maintenance maintenance) {
		TypedQuery<Licence> q = entityManager.createNamedQuery("licence.findByMaintenance", Licence.class);
		q.setParameter("maintenance", maintenance);
		return q.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateLicence(Licence licence) {
		licence.setLastUpdate(new Date());
		update(licence);
	}

}
