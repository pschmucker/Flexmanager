package flexcom.casehistory.ticket.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Ticket entity class
 * 
 * @author philippe
 * 
 */
@Entity
@Table(name = "Tickets")
@NamedQueries(value = {
	@NamedQuery(name = "ticket.findById", query = "select t from Ticket t where t.id = :id"),
	@NamedQuery(name = "ticket.findByTitle", query = "select t from Ticket t where t.title = :title"),
	@NamedQuery(name = "ticket.findByStatus", query = "select t from Ticket t where t.status = :status"),
	@NamedQuery(name = "ticket.findByPriority", query = "select t from Ticket t where t.priority = :priority"),
	@NamedQuery(name = "ticket.findByClient", query = "select t from Ticket t where t.client = :client"),
	@NamedQuery(name = "ticket.findByProduct", query = "select t from Ticket t where t.product = :product"),
	@NamedQuery(name = "ticket.findByUserInCharge", query = "select t from Ticket t where :user member of t.usersInCharge"),
	@NamedQuery(name = "ticket.findAllUnassigned", query = "select t from Ticket t where t.usersInCharge is empty and t.enabled = true"),
	@NamedQuery(name = "ticket.count", query = "select count(*) from Ticket t") 
})
public class Ticket {

	/**
	 * Ticket's id
	 */
	private long id;

	/**
	 * Ticket's title
	 */
	private String title;

	/**
	 * Ticket's creation date
	 */
	private Date creationDate;

	/**
	 * Ticket's last update date
	 */
	private Date lastUpdate;

	/**
	 * Ticket's status
	 */
	private Status status;

	/**
	 * Ticket's priority
	 */
	private Priority priority;

	/**
	 * Ticket's description
	 */
	private String description;

	/**
	 * {@link Client} concerned by this ticket
	 */
	private Client client;

	/**
	 * {@link Product} concerned by this ticket
	 */
	private Product product;

	/**
	 * Set of users responsible of this ticket
	 */
	private Set<User> usersInCharge;

	/**
	 * Set of events concerning this ticket
	 */
	private Set<Event> events;
	
	/**
	 * Set of notes concerning this ticket
	 */
	private Set<Note> notes;
	
	private Contact contact;
	
	private boolean enabled;
	
	/**
	 * Product's build
	 */
	private String build;

	/**
	 * Constructor without argument. The following initializations are done :<br/>
	 * <ul>
	 * <li>The date creation is initialized to the current system date</li>
	 * <li>Status is set to NEW</li>
	 * <li>Priority is set to MEDIUM</li>
	 * <li>Set of users is created</li>
	 * </ul>
	 */
	public Ticket() {
		creationDate = new Date();
		usersInCharge = new HashSet<User>();
		description = "";
		build = "";
		enabled = true;
	}

	/**
	 * Gets the id
	 * 
	 * @return The ticket's id
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
	 *            The ticket's id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Gets the title
	 * 
	 * @return The ticket's title
	 */
	@Column(length = 100, nullable = false)
	@NotEmpty
	@Length(max = 100)
	@NotNull
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title
	 * 
	 * @param title
	 *            The ticket's title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the creation date
	 * 
	 * @return The ticket's creation date
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
	 *            The ticket's date creation
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
	 * Gets the status
	 * 
	 * @return The ticket's status
	 */
	@NotNull
	@ManyToOne
	@Valid
	public Status getStatus() {
		return status;
	}

	/**
	 * Sets the status
	 * 
	 * @param status
	 *            The ticket's status
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * Gets the priority
	 * 
	 * @return The ticket's priority
	 */
	@NotNull
	@ManyToOne
	@Valid
	public Priority getPriority() {
		return priority;
	}

	/**
	 * Sets the priority
	 * 
	 * @param priority
	 *            The ticket's priority
	 */
	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	/**
	 * Gets the description
	 * 
	 * @return The ticket's description
	 */
	@Lob
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description
	 * 
	 * @param description
	 *            The ticket's description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the {@link Client}
	 * 
	 * @return The concerned {@link Client}
	 */
	@ManyToOne
	@NotNull
	@Valid
	public Client getClient() {
		return client;
	}

	/**
	 * Sets the {@link Client}
	 * 
	 * @param client
	 *            The concerned {@link Client}
	 */
	public void setClient(Client client) {
		this.client = client;
	}

	/**
	 * Gets the {@link Product}
	 * 
	 * @return The concerned {@link Product}
	 */
	@ManyToOne
	@NotNull
	@Valid
	public Product getProduct() {
		return product;
	}

	/**
	 * Sets the {@link Product}
	 * 
	 * @param product
	 *            The concerned {@link Product}
	 */
	public void setProduct(Product product) {
		this.product = product;
	}

	/**
	 * Gets the responsible users
	 * 
	 * @return The set of users responsible of this ticket
	 */
	@ManyToMany(mappedBy = "assignedTickets", fetch = FetchType.EAGER)
	public Set<User> getUsersInCharge() {
		return usersInCharge;
	}

	/**
	 * Sets the responsible users
	 * 
	 * @param usersInCharge
	 *            The set of users responsible of this ticket
	 */
	public void setUsersInCharge(Set<User> usersInCharge) {
		this.usersInCharge = usersInCharge;
	}

	/**
	 * Gets the events concerning this ticket
	 * 
	 * @return The set of events concerning this ticket
	 */
	@OneToMany(mappedBy = "ticket", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	public Set<Event> getEvents(){
		return events;
	}

	/**
	 * Sets the events concerning this ticket
	 * 
	 * @param events The set of events concerning this ticket
	 */
	public void setEvents(Set<Event> events){
		this.events = events;
	}

	/**
	 * Gets the notes concerning this ticket
	 * 
	 * @return The set of notes concerning this ticket
	 */
	@OneToMany(mappedBy = "ticket", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	public Set<Note> getNotes(){
		return notes;
	}

	/**
	 * Sets the notes concerning this ticket
	 * 
	 * @param events The set of notes concerning this ticket
	 */
	public void setNotes(Set<Note> notes){
		this.notes = notes;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return The ticket's id followed by its title
	 */
	@Override
	public String toString() {
		return "Ticket #" + id + " : " + title;
	}

	/**
	 * {@inheritDoc} Compare two tickets using their id
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj instanceof Ticket) {
			Ticket ticket = (Ticket) obj;
			return (ticket.getId() == this.id) ? true : false;
		}
		return false;
	}

	/**
	 * {@inheritDoc} Calculates the hashCode using the ticket's id
	 */
	@Override
	public int hashCode() {
		return ((Long) id).hashCode();
	}
	
	@ManyToOne
	@Valid
	@NotNull
	public Contact getContact() {
		return contact;
	}
	
	public void setContact(Contact contact) {
		this.contact = contact;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setBuild(String build) {
		this.build = build;
	}

	@Column(length = 20)
	@Length(max = 20)
	public String getBuild() {
		return build;
	}
}
