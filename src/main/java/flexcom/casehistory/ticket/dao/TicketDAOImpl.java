package flexcom.casehistory.ticket.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import flexcom.casehistory.ticket.entity.Client;
import flexcom.casehistory.ticket.entity.Priority;
import flexcom.casehistory.ticket.entity.Product;
import flexcom.casehistory.ticket.entity.Status;
import flexcom.casehistory.ticket.entity.Ticket;
import flexcom.casehistory.ticket.entity.User;

/**
 * Ticket DAO implementation
 * 
 * @author philippe
 * 
 */
@Transactional
@Repository
public class TicketDAOImpl extends JPAGenericDAO<Ticket, Long> implements TicketDAO {

	/**
	 * Default constructor
	 */
	public TicketDAOImpl() {
		super(Ticket.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createTicket(Ticket ticket) {
		create(ticket);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Ticket> findAll() {
		return entityManager.createQuery("from Ticket", Ticket.class).getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateTicket(Ticket ticket) {
		update(ticket);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Ticket> findByTitle(String title) {
		TypedQuery<Ticket> q = entityManager.createNamedQuery("ticket.findByTitle", Ticket.class);
		q.setParameter("title", title);
		return q.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteTicket(Ticket ticket) {
		delete(entityManager.getReference(Ticket.class, ticket.getId()));
	}

	/**
	 * {@inheritDoc} Before removing the {@link Ticket} from the
	 * {@link EntityManager}, we remove this {@link Ticket} from all users who
	 * are responsible of this {@link Ticket}
	 */
	@Override
	public void deleteAll() {
		for (Ticket t : findAll()) {
			for (User u : t.getUsersInCharge()) {
				u.getAssignedTickets().remove(t);
			}
			entityManager.remove(t);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Ticket> findByUserInCharge(User user) {
		TypedQuery<Ticket> q = entityManager.createNamedQuery("ticket.findByUserInCharge", Ticket.class);
		q.setParameter("user", user);
		return q.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Ticket> findByStatus(Status status) {
		TypedQuery<Ticket> q = entityManager.createNamedQuery("ticket.findByStatus", Ticket.class);
		q.setParameter("status", status);
		return q.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Ticket> findByPriority(Priority priority) {
		TypedQuery<Ticket> q = entityManager.createNamedQuery("ticket.findByPriority", Ticket.class);
		q.setParameter("priority", priority);
		return q.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Ticket> findAllUnassigned() {
		TypedQuery<Ticket> q = entityManager.createNamedQuery("ticket.findAllUnassigned", Ticket.class);
		return q.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Ticket> findByClient(Client client) {
		TypedQuery<Ticket> q = entityManager.createNamedQuery("ticket.findByClient", Ticket.class);
		q.setParameter("client", client);
		return q.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Ticket findById(long id) {
		return read(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Ticket> findByProduct(Product product) {
		TypedQuery<Ticket> q = entityManager.createNamedQuery("ticket.findByProduct", Ticket.class);
		q.setParameter("product", product);
		return q.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long count() {
		return entityManager.createNamedQuery("ticket.count", Long.class).getSingleResult();
	}

	@Override
	public List<Ticket> findAllEnabled() {
		return entityManager.createQuery("select t from Ticket t where t.enabled = true", Ticket.class).getResultList();
	}
}
