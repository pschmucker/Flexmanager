package flexcom.casehistory.aspect;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import flexcom.casehistory.ticket.dao.EventDAO;
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
	 * Event generated after a {@link Ticket} creation
	 * @param ticket The created {@link Ticket}
	 */
	@AfterReturning("execution(* flexcom.casehistory.ticket.dao.TicketDAOImpl.createTicket(flexcom.casehistory.ticket.entity.Ticket)) && args(ticket)")
	public void generateTicketCreationEvent(Ticket ticket){
		String login = ((UserDetails) (SecurityContextHolder.getContext()).getAuthentication().getPrincipal()).getUsername();
		User user = userDAO.findByLogin(login);
		
		Event event = new Event();
		event.setAction("create");
		event.setTicket(ticket);
		event.setAuthor(user);
		eventDAO.createEvent(event);
	}

	/**
	 * Event generated after a {@link Ticket} update
	 * @param ticket The updated {@link Ticket}
	 */
	@AfterReturning("execution(* flexcom.casehistory.ticket.dao.TicketDAOImpl.updateTicket(flexcom.casehistory.ticket.entity.Ticket)) && args(ticket)")
	public void generateTicketUpdateEvent(Ticket ticket){
		String login = ((UserDetails) (SecurityContextHolder.getContext()).getAuthentication().getPrincipal()).getUsername();
		User user = userDAO.findByLogin(login);
		
		Event event = new Event();
		event.setAction("update");
		event.setTicket(ticket);
		event.setAuthor(user);
		eventDAO.createEvent(event);
	}

	/**
	 * Event generated after a {@link Note} was added on a {@link Ticket}
	 * @param ticket The new {@link Note}
	 */
	@AfterReturning("execution(* flexcom.casehistory.ticket.dao.NoteDAOImpl.createNote(flexcom.casehistory.ticket.entity.Note)) && args(note)")
	public void generateNoteAdditionEvent(Note note){
		String login = ((UserDetails) (SecurityContextHolder.getContext()).getAuthentication().getPrincipal()).getUsername();
		User user = userDAO.findByLogin(login);
		
		Event event = new Event();
		event.setAction("note added");
		event.setTicket(note.getTicket());
		event.setAuthor(user);
		eventDAO.createEvent(event);
	}

}
