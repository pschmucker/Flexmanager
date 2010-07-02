package flexcom.casehistory.mvc.controller;

import flexcom.casehistory.ticket.entity.Ticket;

/**
 * Command class for retrieving the ID of the ticket we want to assign to a user
 * 
 * @author philippe
 * 
 */
public class TicketIdCommand {

	/**
	 * {@link Ticket} ID
	 */
	private long ticketId;

	/**
	 * Gets the {@link Ticket} ID
	 * 
	 * @return {@link Ticket} ID
	 */
	public long getTicketId() {
		return ticketId;
	}

	/**
	 * Sets the {@link Ticket} ID
	 * 
	 * @param ticketId
	 *            {@link Ticket} ID
	 */
	public void setTicketId(long ticketId) {
		this.ticketId = ticketId;
	}
}
