package flexcom.casehistory.mvc.controller;

import flexcom.casehistory.ticket.entity.User;

/**
 * Command class for querying {@link User} entities
 * 
 * @author philippe
 * 
 */
public class UserQueryCommand {

	/**
	 * Constant for exact string matching
	 */
	public static final boolean EXACT_MATCH_MODE = true;
	
	/**
	 * Constant for substring matching
	 */
	public static final boolean SUBSTRING_MATCH_MODE = false;

	/**
	 * Constant for LESS THAN comparison
	 */
	public static final int LESS_THAN = 0;

	/**
	 * Constant for LESS OR EQUAL comparison
	 */
	public static final int LESS_OR_EQUAL = 1;

	/**
	 * Constant for EQUAL comparison
	 */
	public static final int EQUAL = 2;

	/**
	 * Constant for GREATER OR EQUAL comparison
	 */
	public static final int GREATER_OR_EQUAL = 3;

	/**
	 * Constant for GREATER THAN comparison
	 */
	public static final int GREATER_THAN = 4;

	/**
	 * Login of the user
	 */
	private String login;
	
	/**
	 * Name of the user
	 */
	private String name;

	/**
	 * Email of the user
	 */
	private String email;
	
	/**
	 * Title of the user
	 */
	private String title;
	
	/**
	 * Access level of the user
	 */
	private String accessLevel;
	
	/**
	 * Match mode of login
	 */
	private boolean loginMatchMode;
	
	/**
	 * Match mode of name
	 */
	private boolean nameMatchMode;
	
	/**
	 * Match mode of email
	 */
	private boolean emailMatchMode;
	
	/**
	 * Match mode of title
	 */
	private boolean titleMatchMode;
	
	/**
	 * Comparison operator for access level
	 */
	private int accessLevelCompOp;
	
	/**
	 * Default constructor which initialize the match mode
	 */
	public UserQueryCommand() {
		login = "";
		name = "";
		email = "";
		title = "";
		accessLevel = "select";
		loginMatchMode = EXACT_MATCH_MODE;
		nameMatchMode = EXACT_MATCH_MODE;
		emailMatchMode = EXACT_MATCH_MODE;
		titleMatchMode = EXACT_MATCH_MODE;
		setAccessLevelCompOp(EQUAL);
	}

	/**
	 * Gets the login
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * Sets the login
	 * @param login the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
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
	 * Gets the email
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
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
	 * Gets the access level
	 * @return the accessLevel
	 */
	public String getAccessLevel() {
		return accessLevel;
	}

	/**
	 * Sets the access level
	 * @param accessLevel the accessLevel to set
	 */
	public void setAccessLevel(String accessLevel) {
		this.accessLevel = accessLevel;
	}

	/**
	 * Gets the login match mode
	 * @return the loginMatchMode
	 */
	public boolean getLoginMatchMode() {
		return loginMatchMode;
	}

	/**
	 * Sets the login match mode
	 * @param loginMatchMode the loginMatchMode to set
	 */
	public void setLoginMatchMode(boolean loginMatchMode) {
		this.loginMatchMode = loginMatchMode;
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
	 * Gets the email match mode
	 * @return the emailMatchMode
	 */
	public boolean getEmailMatchMode() {
		return emailMatchMode;
	}

	/**
	 * Sets the email match mode
	 * @param emailMatchMode the emailMatchMode to set
	 */
	public void setEmailMatchMode(boolean emailMatchMode) {
		this.emailMatchMode = emailMatchMode;
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

	/**
	 * Sets the access level comparison operator
	 * @param accessLevelCompOp the accessLevelCompOp to set
	 */
	public void setAccessLevelCompOp(int accessLevelCompOp) {
		this.accessLevelCompOp = accessLevelCompOp;
	}

	/**
	 * Gets the access level comparison operator
	 * @return the accessLevelCompOp
	 */
	public int getAccessLevelCompOp() {
		return accessLevelCompOp;
	}
}
