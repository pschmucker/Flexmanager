package flexcom.casehistory.ticket.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import flexcom.casehistory.ticket.entity.Note;
import flexcom.casehistory.ticket.entity.Ticket;
import flexcom.casehistory.ticket.entity.User;

/**
 * Note DAO implementation
 * 
 * @author philippe
 * 
 */
@Transactional
@Repository
public class NoteDAOImpl extends JPAGenericDAO<Note, Long> implements NoteDAO {

	/**
	 * Default constructor
	 */
	public NoteDAOImpl() {
		super(Note.class);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public long count() {
		return entityManager.createNamedQuery("note.count", Long.class).getSingleResult();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createNote(Note note) {
		create(note);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteAll() {
		for (Note n : findAll()) {
			entityManager.remove(n);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteNote(Note note) {
		delete(entityManager.getReference(Note.class, note.getId()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Note> findAll() {
		return entityManager.createQuery("from Note", Note.class).getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Note> findByAuthor(User author) {
		TypedQuery<Note> q = entityManager.createNamedQuery("note.findByAuthor", Note.class);
		q.setParameter("author", author);
		return q.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Note findById(long id) {
		return read(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Note> findByNote(String note) {
		TypedQuery<Note> q = entityManager.createNamedQuery("note.findByNote", Note.class);
		q.setParameter("note", note);
		return q.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Note> findByTicket(Ticket ticket) {
		TypedQuery<Note> q = entityManager.createNamedQuery("note.findByTicket", Note.class);
		q.setParameter("ticket", ticket);
		return q.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateNote(Note note) {
		update(note);
	}
	
}
