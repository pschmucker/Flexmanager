package flexcom.casehistory.ticket.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import flexcom.casehistory.ticket.entity.Partner;

/**
 * Partner DAO implementation
 * @author philippe
 *
 */
@Transactional
@Repository
public class PartnerDAOImpl extends JPAGenericDAO<Partner, Long> implements PartnerDAO {

	/**
	 * Default constructor
	 */
	public PartnerDAOImpl() {
		super(Partner.class);
	}

	/**
	 * {@inheritDoc}
	 */
//	@Override
	public List<Partner> findAll() {
		return entityManager.createQuery("from Partner", Partner.class).getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createPartner(Partner partner) {
		create(partner);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Partner findByName(String name) {
		TypedQuery<Partner> q = entityManager.createNamedQuery("partner.findByName", Partner.class);
		q.setParameter("name", name);
		return q.getSingleResult();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deletePartner(Partner partner) {
		delete(entityManager.getReference(Partner.class, partner.getId()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteAll() {
		for (Partner c : findAll()) {
			entityManager.remove(c);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Partner findById(long id) {
		return read(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updatePartner(Partner partner) {
		update(partner);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long count() {
		return entityManager.createNamedQuery("partner.count", Long.class).getSingleResult();
	}

	@Override
	public List<Partner> findAllEnabled() {
		return entityManager.createQuery("select p from Partner p where p.enabled = true", Partner.class).getResultList();
	}
}
