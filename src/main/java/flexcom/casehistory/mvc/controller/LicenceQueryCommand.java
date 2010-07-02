package flexcom.casehistory.mvc.controller;

import flexcom.casehistory.ticket.entity.Client;
import flexcom.casehistory.ticket.entity.Licence;
import flexcom.casehistory.ticket.entity.Product;

/**
 * Command class for querying {@link Licence} entities
 * 
 * @author philippe
 * 
 */
public class LicenceQueryCommand {

	/**
	 * Constant for exact string matching
	 */
	public static final boolean EXACT_MATCH_MODE = true;
	
	/**
	 * Constant for substring matching
	 */
	public static final boolean SUBSTRING_MATCH_MODE = false;
	
	/**
	 * Key of the licence
	 */
	private String key;
	
	/**
	 * {@link Client} ID of the licence
	 */
	private String clientId;
	
	/**
	 * {@link Product} ID of the licence
	 */
	private String productId;
	
	/**
	 * {@link Maintenance} ID of the licence
	 */
	private String maintenanceId;
	
	/**
	 * Match mode of licence key
	 */
	private boolean keyMatchMode;
		
	/**
	 * Default constructor which initialize the match mode
	 */
	public LicenceQueryCommand() {
		key = "";
		clientId = "select";
		productId = "select";
		maintenanceId = "select";
		keyMatchMode = EXACT_MATCH_MODE;
	}

	/**
	 * Gets the key
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Sets the key
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
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
	 * Gets the maintenance ID
	 * @return the maintenanceId
	 */
	public String getMaintenanceId() {
		return maintenanceId;
	}

	/**
	 * Sets the maintenance ID
	 * @param maintenanceId the maintenanceId to set
	 */
	public void setMaintenanceId(String maintenanceId) {
		this.maintenanceId = maintenanceId;
	}

	/**
	 * Gets the key match mode
	 * @return the keyMatchMode
	 */
	public boolean getKeyMatchMode() {
		return keyMatchMode;
	}

	/**
	 * Sets the key match mode
	 * @param keyMatchMode the keyMatchMode to set
	 */
	public void setKeyMatchMode(boolean keyMatchMode) {
		this.keyMatchMode = keyMatchMode;
	}
}