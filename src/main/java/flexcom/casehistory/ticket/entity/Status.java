package flexcom.casehistory.ticket.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Definition class for {@link Ticket} status
 * 
 * @author philippe
 * 
 */
@Entity
@Table(name = "Statuses")
@NamedQueries(value = { 
	@NamedQuery(name = "status.findById", query = "select s from Status s where s.id = :id"),
	@NamedQuery(name = "status.findByName", query = "select s from Status s where s.name = :name"),
	@NamedQuery(name = "status.count", query = "select count(*) from Status s") 
})
public class Status {
	
	public final static Status NEW = new Status("New");
	public final static Status PENDING = new Status("Pending");
	public final static Status ASSIGNED = new Status("Assigned");
	public final static Status REOPEN = new Status("Reopen");
	public final static Status RESOLVED = new Status("Resolved");
	public final static Status CUSTOMER_FEEDBACK = new Status("Customer Feedback");
	public final static Status FLEXCOM_FEEDBACK = new Status("Flexcom Feedback");
	public final static Status CLOSED = new Status("Closed");

	public static Status getStatus(Status type){
		return new Status(type.getName()); 
	}
	
	/**
	 * ID of this status
	 */
	private long id;
	
	/**
	 * Name of status
	 */
	private String name;
	
	/**
	 * Set of tickets with this status
	 */
	private Set<Ticket> tickets;
	
	private boolean enabled;
	
	/**
	 * Default constructor
	 */
	public Status(){
		this.name = null;
		this.enabled = true;
	}
	
	/**
	 * Constructor with a given name
	 * 
	 * @param name
	 *            Name of status
	 */
	private Status(String name) {
		this.name = name;
		this.enabled = true;
	}

	/**
	 * Gets the id
	 * 
	 * @return The id of this status
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
	 *            The id of this status
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Gets the name
	 * @return Name of status
	 */
	@Column(length = 20, unique = true, nullable = false)
	@Length(max = 20)
	@NotNull
	@NotEmpty
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name
	 * @param name Name of status
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the tickets with this status
	 * 
	 * @return The tickets set with this status
	 */
	@OneToMany(mappedBy = "status", fetch = FetchType.EAGER)
	public Set<Ticket> getTickets() {
		return tickets;
	}

	/**
	 * Sets the tickets with this status
	 * 
	 * @param tickets
	 *            The tickets set with this status
	 */
	public void setTickets(Set<Ticket> tickets) {
		this.tickets = tickets;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj instanceof Status) {
			Status status = (Status) obj;
			if (status.getName() == null){
				return (this.name == null) ? true : false;
			}
			return status.getName().equals(this.name);
		}
		return false;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return ((Long) id).hashCode();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return name;
	}
}
