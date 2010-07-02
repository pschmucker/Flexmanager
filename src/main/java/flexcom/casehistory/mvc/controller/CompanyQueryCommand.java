package flexcom.casehistory.mvc.controller;

import flexcom.casehistory.ticket.entity.Company;

/**
 * Command class for querying {@link Company} entities
 * 
 * @author philippe
 * 
 */
public class CompanyQueryCommand {

	/**
	 * Constant for exact string matching
	 */
	public static final boolean EXACT_MATCH_MODE = true;
	
	/**
	 * Constant for substring matching
	 */
	public static final boolean SUBSTRING_MATCH_MODE = false;

	/**
	 * Name of the company
	 */
	private String companyName;
	
	/**
	 * Match mode : exact or substring
	 */
	private boolean matchMode;
	
	/**
	 * Default constructor which initialize the match mode
	 */
	public CompanyQueryCommand() {
		companyName = "";
		matchMode = EXACT_MATCH_MODE;
	}
	
	/**
	 * Gets the company name
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * Sets the company name
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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
