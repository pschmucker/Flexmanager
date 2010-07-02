package flexcom.casehistory.mvc.controller;

import flexcom.casehistory.ticket.entity.Client;

/**
 * Command class for querying {@link Client} entities
 * 
 * @author philippe
 * 
 */
public class ContactQueryCommand {

	/**
	 * Constant for exact string matching
	 */
	public static final boolean EXACT_MATCH_MODE = true;
	
	/**
	 * Constant for substring matching
	 */
	public static final boolean SUBSTRING_MATCH_MODE = false;

	/**
	 * Name of the contact
	 */
	private String contactName;
	
	/**
	 * Match mode : exact or substring
	 */
	private boolean matchMode;
	
	/**
	 * Default constructor which initialize the match mode
	 */
	public ContactQueryCommand() {
		contactName = "";
		matchMode = EXACT_MATCH_MODE;
	}
	
	/**
	 * Gets the contact name
	 * @return the contactName
	 */
	public String getContactName() {
		return contactName;
	}

	/**
	 * Sets the contact name
	 * @param contactName the contactName to set
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	/**
	 * Gets the match mode
	 * @return the matchMode
	 */
	public boolean getMatchMode() {
		return matchMode;
	}

	/**
	 * Sets the match mode
	 * @param matchMode the matchMode to set
	 */
	public void setMatchMode(boolean matchMode) {
		this.matchMode = matchMode;
	}
}
