package flexcom.casehistory.ticket.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import flexcom.casehistory.ticket.entity.Client;

/**
 * Client DAO implementation
 * @author philippe
 *
 */
@Transactional
@Repository
public class ClientDAOImpl extends JPAGenericDAO<Client, Long> implements ClientDAO {

	/**
	 * Default constructor
	 */
	public ClientDAOImpl() {
		super(Client.class);
	}

	/**
	 * {@inheritDoc}
	 */
//	@Override
	public List<Client> findAll() {
		return entityManager.createQuery("from Client", Client.class).getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createClient(Client client) {
		create(client);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Client findByName(String name) {
		TypedQuery<Client> q = entityManager.createNamedQuery("client.findByName", Client.class);
		q.setParameter("name", name);
		return q.getSingleResult();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteClient(Client client) {
		delete(entityManager.getReference(Client.class, client.getId()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteAll() {
		for (Client c : findAll()) {
			entityManager.remove(c);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Client findById(long id) {
		return read(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateClient(Client client) {
		update(client);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long count() {
		return entityManager.createNamedQuery("client.count", Long.class).getSingleResult();
	}
}
