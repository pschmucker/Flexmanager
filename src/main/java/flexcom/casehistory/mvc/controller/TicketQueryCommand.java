package flexcom.casehistory.mvc.controller;

import flexcom.casehistory.ticket.entity.Priority;
import flexcom.casehistory.ticket.entity.Status;
import flexcom.casehistory.ticket.entity.Ticket;

/**
 * Command class for querying {@link Ticket} entities
 * 
 * @author philippe
 * 
 */
public class TicketQueryCommand {

	/**
	 * Constant for exact string matching
	 */
	public static final boolean EXACT_MATCH_MODE = true;
	
	/**
	 * Constant for substring matching
	 */
	public static final boolean SUBSTRING_MATCH_MODE = false;
	
	/**
	 * Title of the ticket
	 */
	private String title;
	
	/**
	 * {@link Status} ID of the ticket
	 */
	private String statusId;
	
	/**
	 * {@link Priority} ID of the ticket
	 */
	private String priorityId;
	
	/**
	 * {@link Client} ID of the ticket
	 */
	private String clientId;
	
	/**
	 * {@link Product} ID of the ticket
	 */
	private String productId;
	
	/**
	 * {@link Contact} ID of the ticket
	 */
	private String contactId;
	
	/**
	 * Match mode of title
	 */
	private boolean titleMatchMode;
		
	/**
	 * Default constructor which initialize the match mode
	 */
	public TicketQueryCommand() {
		title = "";
		statusId = "select";
		priorityId = "select";
		clientId = "select";
		productId = "select";
		contactId = "select";
		titleMatchMode = EXACT_MATCH_MODE;
	}

	/**
	 * Gets the title
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the status
	 * @return the status
	 */
	public String getStatusId() {
		return statusId;
	}

	/**
	 * Sets the status
	 * @param statusId the status to set
	 */
	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}

	/**
	 * Gets the priority
	 * @return the priority
	 */
	public String getPriorityId() {
		return priorityId;
	}

	/**
	 * Sets the priority
	 * @param priorityId the priority to set
	 */
	public void setPriorityId(String priorityId) {
		this.priorityId = priorityId;
	}

	/**
	 * Gets the client ID
	 * @return the clientId
	 */
	public String getClientId() {
		return clientId;
	}

	/**
	 * Sets the client ID
	 * @param clientId the clientId to set
	 */
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	/**
	 * Gets the product ID
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}

	/**
	 * Sets the product ID
	 * @param productId the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}

	/**
	 * Gets the contact ID
	 * @return the contactId
	 */
	public String getContactId() {
		return contactId;
	}

	/**
	 * Sets the contact ID
	 * @param contactId the contactId to set
	 */
	public void setContactId(String contactId) {
		this.contactId = contactId;
	}

	/**
	 * Gets the title match mode
	 * @return the titleMatchMode
	 */
	public boolean getTitleMatchMode() {
		return titleMatchMode;
	}

	/**
	 * Sets the title match mode
	 * @param titleMatchMode the titleMatchMode to set
	 */
	public void setTitleMatchMode(boolean titleMatchMode) {
		this.titleMatchMode = titleMatchMode;
	}
}