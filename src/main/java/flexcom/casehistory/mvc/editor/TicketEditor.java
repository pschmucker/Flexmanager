package flexcom.casehistory.mvc.editor;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import flexcom.casehistory.ticket.dao.TicketDAO;
import flexcom.casehistory.ticket.entity.Ticket;

/**
 * Editor for {@link Ticket} objects
 * 
 * @author philippe
 * 
 */
@Component
public class TicketEditor extends PropertyEditorSupport {

	/**
	 * Ticket DAO
	 */
	@Autowired
	private TicketDAO ticketDAO;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getAsText() {
		Ticket t = (Ticket) getValue();
		return (t == null) ? null : "" + t.getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setAsText(String text) {
		setValue((text == null) ? null : ticketDAO.findById(Long.parseLong(text)));
	}
}
