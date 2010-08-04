package flexcom.casehistory.aspect;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import flexcom.casehistory.ticket.dao.EventDAO;
import flexcom.casehistory.ticket.dao.TicketDAO;
import flexcom.casehistory.ticket.dao.UserDAO;
import flexcom.casehistory.ticket.entity.Event;
import flexcom.casehistory.ticket.entity.Note;
import flexcom.casehistory.ticket.entity.Ticket;
import flexcom.casehistory.ticket.entity.User;

/**
 * Class used to generate {@link Event} entities when operations happen on {@link Ticket} entities
 * 
 * @author philippe
 *
 */
@Aspect
public class EventTrigger {
	
	/**
	 * Event DAO
	 */
	@Autowired
	private EventDAO eventDAO;
	
	/**
	 * User DAO
	 */
	@Autowired
	private UserDAO userDAO;
	
	/**
	 * User DAO
	 */
	@Autowired
	private TicketDAO ticketDAO;
	
	private static Map<Long, Ticket> map = new HashMap<Long, Ticket>();
	
	@PersistenceContext
	private EntityManager entityManager;
	
	private User getLoggedUser(){
		String login = ((UserDetails) (SecurityContextHolder.getContext()).getAuthentication().getPrincipal()).getUsername();
		return userDAO.findByLogin(login);
	}
	
	/**
	 * Event generated after a {@link Ticket} creation
	 * @param ticket The created {@link Ticket}
	 */
	@AfterReturning("execution(* flexcom.casehistory.ticket.dao.TicketDAOImpl.createTicket(flexcom.casehistory.ticket.entity.Ticket)) && args(ticket)")
	public void generateTicketCreationEvent(Ticket ticket){
		Event event = new Event();
		event.setAction("Ticket created");
		event.setTicket(ticket);
		event.setAuthor(getLoggedUser());
		event.setDetails(ticket.toString());
		eventDAO.createEvent(event);
	}

	/**
	 * Event generated after a {@link Note} was added on a {@link Ticket}
	 * @param ticket The new {@link Note}
	 */
	@AfterReturning("execution(* flexcom.casehistory.ticket.dao.NoteDAOImpl.createNote(flexcom.casehistory.ticket.entity.Note)) && args(note)")
	public void generateNoteAdditionEvent(Note note){
		Event event = new Event();
		event.setAction("Note added");
		event.setTicket(note.getTicket());
		event.setAuthor(getLoggedUser());
		event.setDetails("« " + note.getNote() + " »");
		eventDAO.createEvent(event);
	}
	
	@Before("execution(* flexcom.casehistory.ticket.dao.TicketDAOImpl.updateTicket(flexcom.casehistory.ticket.entity.Ticket)) && args(ticket)")
    public void beforeUpdate(Ticket ticket) {
		long id = ticket.getId();
		Ticket old = ticketDAO.findById(id);
		entityManager.detach(old);
		map.put(id, old);
    }
	
	@AfterReturning("execution(* flexcom.casehistory.ticket.dao.TicketDAOImpl.updateTicket(flexcom.casehistory.ticket.entity.Ticket)) && args(ticket)")
    public void afterUpdate(Ticket ticket) {
		Ticket old = map.get(ticket.getId());
		if (!old.getTitle().equals(ticket.getTitle())){
			Event event = new Event();
			event.setAction("Title changed");
			event.setTicket(ticket);
			event.setAuthor(getLoggedUser());
			event.setDetails("« " + old.getTitle() + " » => « " + ticket.getTitle() + " »");
			eventDAO.createEvent(event);
		}
		if (!old.getStatus().equals(ticket.getStatus())){
			Event event = new Event();
			event.setAction("Status changed");
			event.setTicket(ticket);
			event.setAuthor(getLoggedUser());
			event.setDetails("« " + old.getStatus() + " » => « " + ticket.getStatus() + " »");
			eventDAO.createEvent(event);
		}
		if (!old.getPriority().equals(ticket.getPriority())){
			Event event = new Event();
			event.setAction("Priority changed");
			event.setTicket(ticket);
			event.setAuthor(getLoggedUser());
			event.setDetails("« " + old.getPriority() + " » => « " + ticket.getPriority() + " »");
			eventDAO.createEvent(event);
		}
		if (!old.getContact().equals(ticket.getContact())){
			Event event = new Event();
			event.setAction("Contact changed");
			event.setTicket(ticket);
			event.setAuthor(getLoggedUser());
			event.setDetails("« " + old.getContact() + " » => « " + ticket.getContact() + " »");
			eventDAO.createEvent(event);
		}
		if (!old.getDescription().equals(ticket.getDescription())){
			Event event = new Event();
			event.setAction("Description changed");
			event.setTicket(ticket);
			event.setAuthor(getLoggedUser());
			event.setDetails("« " + old.getDescription() + " » => « " + ticket.getDescription() + " »");
			eventDAO.createEvent(event);
		}
		if (!old.getBuild().equals(ticket.getBuild())){
			Event event = new Event();
			event.setAction("Build changed");
			event.setTicket(ticket);
			event.setAuthor(getLoggedUser());
			event.setDetails("« " + old.getBuild() + " » => « " + ticket.getBuild() + " »");
			eventDAO.createEvent(event);
		}
		if (!old.getUsersInCharge().equals(ticket.getUsersInCharge())){
			Set<User> usersToRemove = new HashSet<User>();
			usersToRemove.addAll(old.getUsersInCharge());
			usersToRemove.removeAll(ticket.getUsersInCharge());
			
			Set<User> usersToAdd = new HashSet<User>();
			usersToAdd.addAll(ticket.getUsersInCharge());
			usersToAdd.removeAll(old.getUsersInCharge());
			
			for (User user : usersToRemove) {
				Event event = new Event();
				event.setAction("User removed");
				event.setTicket(ticket);
				event.setAuthor(getLoggedUser());
				event.setDetails(user.toString());
				eventDAO.createEvent(event);
			}

			for (User user : usersToAdd) {
				Event event = new Event();
				event.setAction("User added");
				event.setTicket(ticket);
				event.setAuthor(getLoggedUser());
				event.setDetails(user.toString());
				eventDAO.createEvent(event);
			}
		}
	}
}
