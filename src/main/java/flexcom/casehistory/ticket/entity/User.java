package flexcom.casehistory.ticket.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

/**
 * User entity class
 * 
 * @author philippe
 * 
 */
@Entity
@Table(name = "Users")
@NamedQueries(value = { 
	@NamedQuery(name = "user.findById", query = "select u from User u where u.id = :id"),
	@NamedQuery(name = "user.findByLogin", query = "select u from User u where u.login = :login"),
	@NamedQuery(name = "user.findByName", query = "select u from User u where u.name = :name"),
	@NamedQuery(name = "user.findByEmail", query = "select u from User u where u.email = :email"),
	@NamedQuery(name = "user.findByTitle", query = "select u from User u where u.title = :title"),
	@NamedQuery(name = "user.findByAccessLevel", query = "select u from User u where u.accessLevel = :accessLevel"),
	@NamedQuery(name = "user.findByAssignedTicket", query = "select u from User u where :ticket member of u.assignedTickets"),
	@NamedQuery(name = "user.count", query = "select count(*) from User u") 
})
public class User {

	/**
	 * User's id
	 */
	private long id;

	/**
	 * User's login
	 */
	private String login;

	/**
	 * User's password
	 */
	private String password;

	/**
	 * User's name
	 */
	private String name;

	/**
	 * User's email
	 */
	private String email;

	/**
	 * User's title
	 */
	private String title;

	/**
	 * User's access level
	 */
	private int accessLevel;
	
	/**
	 * User's creation date
	 */
	private Date creationDate;
	
	/**
	 * User's last update date
	 */
	private Date lastUpdate;
	
	/**
	 * User's last login date
	 */
	private Date lastLogin;
	
	/**
	 * User's roles
	 */
	private Set<Role> roles;

	/**
	 * Set of tickets assigned to this user
	 */
	private Set<Ticket> assignedTickets;
	
	/**
	 * Set of events thrown by this user
	 */
	private Set<Event> events;
	
	/**
	 * Set of notes written by this user
	 */
	private Set<Note> notes;
	
	private boolean enabled;

	/**
	 * Constructor without arguments. Put the default access level to 0 and
	 * initialize the set of tickets
	 */
	public User() {
		accessLevel = 0;
		assignedTickets = new HashSet<Ticket>();
		events = new HashSet<Event>();
		notes = new HashSet<Note>();
		roles = new HashSet<Role>();
		creationDate = new Date();
		enabled = true;
	}

	/**
	 * Gets the id
	 * 
	 * @return The user's id
	 */
	@Id
	@Column(name = "key_")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getId() {
		return id;
	}

	/**
	 * Sets the id
	 * 
	 * @param id
	 *            The user's id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Gets the login
	 * 
	 * @return The user's login
	 */
	@Column(unique = true, length = 20, nullable = false)
	@Length(min = 4, max = 20)
	@Pattern(regexp = "\\w+")
	@NotNull
	public String getLogin() {
		return login;
	}

	/**
	 * Sets the login
	 * 
	 * @param login
	 *            The user's login
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * Gets the password
	 * 
	 * @return The user's password
	 */
	@Column(length = 32, nullable = false)
	@NotNull
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password
	 * 
	 * @param password
	 *            The user's password
	 */
	public void setPassword(String password) {
		if (password == null){
			throw new IllegalArgumentException("Password can't be null");
		}
		this.password = password;
	}

	/**
	 * Gets the name
	 * 
	 * @return The user's name
	 */
	@Column(length = 30)
	@Length(max = 30)
	@NotEmpty
	public String getName() {
		return name;
	}

	/**
	 * Sets the name
	 * 
	 * @param name
	 *            The user's name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the email
	 * 
	 * @return The user's email
	 */
	@Column(length = 100, unique = true, nullable = false)
	@Length(max = 100)
	@Email
	@NotNull
	@NotEmpty
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email
	 * 
	 * @param email
	 *            The user's email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the title
	 * 
	 * @return The user's title
	 */
	@Column(length = 50)
	@Length(max = 50)
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title
	 * 
	 * @param title
	 *            The user's title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the access level
	 * 
	 * @return The user's access level
	 */
	@Column(name = "accesslevel")
	@Range(min = 0, max = 10)
	public int getAccessLevel() {
		return accessLevel;
	}

	/**
	 * Sets the access level
	 * 
	 * @param accessLevel
	 *            The user's access level
	 */
	public void setAccessLevel(int accessLevel) {
		this.accessLevel = accessLevel;
	}
	
	/**
	 * Gets the last login date
	 * 
	 * @return the last login date
	 */
	@Column(name = "lastlogin")
	@Temporal(value = TemporalType.TIMESTAMP)	
	public Date getLastLogin(){
		return lastLogin;
	}
	
	/**
	 * Sets the last login date
	 * 
	 * @param lastLogin the last login date to set
	 */
	public void setLastLogin(Date lastLogin){
		this.lastLogin = lastLogin;
	}
	
	/**
	 * Gets the creation date
	 * 
	 * @return The user's creation date
	 */
	@Column(name = "creationdate", nullable = false, updatable = false)
	@Temporal(value = TemporalType.TIMESTAMP)
	@NotNull
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * Sets the date creation
	 * 
	 * @param creationDate
	 *            The user's date creation
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * Gets the last update date
	 * 
	 * @return the last update date
	 */
	@Column(name = "lastupdate")
	@Temporal(value = TemporalType.TIMESTAMP)	
	public Date getLastUpdate(){
		return lastUpdate;
	}
	
	/**
	 * Sets the last update date
	 * 
	 * @param lastUpdate the last update date to set
	 */
	public void setLastUpdate(Date lastUpdate){
		this.lastUpdate = lastUpdate;
	}
	
	/**
	 * Gets the roles of this user
	 * 
	 * @return roles of this user
	 */
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "HasRoles", joinColumns = @JoinColumn(name = "user_key"), inverseJoinColumns = @JoinColumn(name = "role_key"))
	public Set<Role> getRoles(){
		return roles;
	}
	
	/**
	 * Sets the roles of this user
	 * 
	 * @param roles The roles to set
	 */
	public void setRoles(Set<Role> roles){
		this.roles = roles;
	}

	/**
	 * Gets the assigned tickets
	 * 
	 * @return The set of tickets assigned to this user
	 */
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "Responsibles", joinColumns = @JoinColumn(name = "user_key"), inverseJoinColumns = @JoinColumn(name = "ticket_key"))
	public Set<Ticket> getAssignedTickets() {
		return assignedTickets;
	}

	/**
	 * Sets the assigned tickets
	 * 
	 * @param assignedTickets
	 *            The set of tickets assigned to this user
	 */
	public void setAssignedTickets(Set<Ticket> assignedTickets) {
		this.assignedTickets = assignedTickets;
	}
	
	/**
	 * Gets the events thrown by this user
	 * 
	 * @return The set of events thrown by this user
	 */
	@OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
	public Set<Event> getEvents(){
		return events;
	}

	/**
	 * Sets the events thrown by this user
	 * 
	 * @param events The set of events thrown by this user
	 */
	public void setEvents(Set<Event> events){
		this.events = events;
	}

	/**
	 * Gets the notes written by this user
	 * 
	 * @return The set of notes written by this user
	 */
	@OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
	public Set<Note> getNotes(){
		return notes;
	}

	/**
	 * Sets the notes written by this user
	 * 
	 * @param events The set of notes written by this user
	 */
	public void setNotes(Set<Note> notes){
		this.notes = notes;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * Assign a {@link Ticket} to this user
	 * 
	 * @param ticket
	 *            A {@link Ticket} to assign
	 */
	public void assign(Ticket ticket) {
		if (ticket == null) {
			throw new IllegalArgumentException("Can't assign a null ticket");
		}
		assignedTickets.add(ticket);
	}
	
	/**
	 * Assign a {@link Ticket} to this user
	 * 
	 * @param ticket
	 *            A {@link Ticket} to assign
	 */
	public void unassign(Ticket ticket) {
		if (ticket == null) {
			throw new IllegalArgumentException("Can't unassign a null ticket");
		}
		assignedTickets.remove(ticket);
	}
	
	/**
	 * Add a role to this user
	 */
	public void addRole(Role role){
		if (role == null){
			throw new IllegalArgumentException("Can't add a null role");
		}
		roles.add(role);
	}

	/**
	 * Remove a role of this user
	 */
	public void removeRole(Role role){
		if (role == null){
			throw new IllegalArgumentException("Can't remove a null role");
		}
		roles.remove(role);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return The user's login followed by his name (if defined) in parenthesis
	 */
	@Override
	public String toString() {
		return login + " (" + (name == null ? "Unknown name" : name) + ")";
	}

	/**
	 * {@inheritDoc} Compare two users using their id
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj instanceof User) {
			User user = (User) obj;
			return (user.getId() == this.id) ? true : false;
		}
		return false;
	}

	/**
	 * {@inheritDoc} Calculates the hashCode using the user's id
	 */
	@Override
	public int hashCode() {
		return ((Long) id).hashCode();
	}
}
