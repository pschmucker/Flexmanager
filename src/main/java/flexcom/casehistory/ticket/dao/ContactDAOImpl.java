package flexcom.casehistory.ticket.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import flexcom.casehistory.ticket.entity.Contact;

/**
 * Contact DAO implementation
 * @author philippe
 *
 */
@Transactional
@Repository
public class ContactDAOImpl extends JPAGenericDAO<Contact, Long> implements ContactDAO {

	/**
	 * Default constructor
	 */
	public ContactDAOImpl() {
		super(Contact.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Contact> findAll() {
		return entityManager.createQuery("from Contact", Contact.class).getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createContact(Contact contact) {
		create(contact);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteContact(Contact contact) {
		delete(entityManager.getReference(Contact.class, contact.getId()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteAll() {
		for (Contact c : findAll()) {
			entityManager.remove(c);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Contact findById(long id) {
		return read(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateContact(Contact contact) {
		update(contact);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long count() {
		return entityManager.createNamedQuery("contact.count", Long.class).getSingleResult();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Contact> findByName(String name) {
		TypedQuery<Contact> q = entityManager.createNamedQuery("contact.findByName", Contact.class);
		q.setParameter("name", name);
		return q.getResultList();
	}
}
