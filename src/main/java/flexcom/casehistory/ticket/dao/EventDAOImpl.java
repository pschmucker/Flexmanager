package flexcom.casehistory.ticket.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import flexcom.casehistory.ticket.entity.Event;
import flexcom.casehistory.ticket.entity.Ticket;
import flexcom.casehistory.ticket.entity.User;

/**
 * Event DAO implementation
 * 
 * @author philippe
 * 
 */
@Transactional
@Repository
public class EventDAOImpl extends JPAGenericDAO<Event, Long> implements EventDAO {

	/**
	 * Default constructor
	 */
	public EventDAOImpl() {
		super(Event.class);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public long count() {
		return entityManager.createNamedQuery("event.count", Long.class).getSingleResult();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createEvent(Event event) {
		create(event);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteAll() {
		for (Event e : findAll()) {
			entityManager.remove(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteEvent(Event event) {
		delete(entityManager.getReference(Event.class, event.getId()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Event> findAll() {
		return entityManager.createQuery("from Event", Event.class).getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Event> findByAuthor(User author) {
		TypedQuery<Event> q = entityManager.createNamedQuery("event.findByAuthor", Event.class);
		q.setParameter("author", author);
		return q.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Event findById(long id) {
		return read(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Event> findByAction(String action) {
		TypedQuery<Event> q = entityManager.createNamedQuery("event.findByAction", Event.class);
		q.setParameter("action", action);
		return q.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Event> findByTicket(Ticket ticket) {
		TypedQuery<Event> q = entityManager.createNamedQuery("event.findByTicket", Event.class);
		q.setParameter("ticket", ticket);
		return q.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateEvent(Event event) {
		update(event);
	}
	
}
