package flexcom.casehistory.mvc.controller;

import flexcom.casehistory.ticket.entity.Product;

/**
 * Command class for querying {@link Product} entities
 * 
 * @author philippe
 * 
 */
public class ProductQueryCommand {

	/**
	 * Constant for exact string matching
	 */
	public static final boolean EXACT_MATCH_MODE = true;
	
	/**
	 * Constant for substring matching
	 */
	public static final boolean SUBSTRING_MATCH_MODE = false;

	/**
	 * Name of the product
	 */
	private String name;
	
	/**
	 * Version of the product
	 */
	private String version;
	
	/**
	 * Match mode of name
	 */
	private boolean nameMatchMode;
	
	/**
	 * Match mode of version
	 */
	private boolean versionMatchMode;
	
	/**
	 * Default constructor which initialize the match mode
	 */
	public ProductQueryCommand() {
		name = "";
		version = "";
		nameMatchMode = EXACT_MATCH_MODE;
		versionMatchMode = EXACT_MATCH_MODE;
	}
	
	/**
	 * Gets the name
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the version
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Sets the version
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * Gets the name match mode
	 * @return the nameMatchMode
	 */
	public boolean getNameMatchMode() {
		return nameMatchMode;
	}

	/**
	 * Sets the name match mode
	 * @param nameMatchMode the nameMatchMode to set
	 */
	public void setNameMatchMode(boolean nameMatchMode) {
		this.nameMatchMode = nameMatchMode;
	}

	/**
	 * Gets the version match mode
	 * @return the versionMatchMode
	 */
	public boolean getVersionMatchMode() {
		return versionMatchMode;
	}

	/**
	 * Sets the version match mode
	 * @param versionMatchMode the versionMatchMode to set
	 */
	public void setVersionMatchMode(boolean versionMatchMode) {
		this.versionMatchMode = versionMatchMode;
	}
}
