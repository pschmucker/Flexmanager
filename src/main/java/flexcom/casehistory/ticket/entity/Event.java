package flexcom.casehistory.ticket.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Event entity class
 * 
 * @author philippe
 * 
 */
@Entity
@Table(name = "Events")
@NamedQueries(value = {
	@NamedQuery(name = "event.findById", query = "select e from Event e where e.id = :id"),
	@NamedQuery(name = "event.findByAction", query = "select e from Event e where e.action = :action"),
	@NamedQuery(name = "event.count", query = "select count(*) from Event e"),
	@NamedQuery(name = "event.findByAuthor", query = "select e from Event e where e.author = :author"),
	@NamedQuery(name = "event.findByTicket", query = "select e from Event e where e.ticket = :ticket")
})
public class Event {
	
	/**
	 * Event's ID
	 */
	private long id;
	
	/**
	 * Action of this event
	 */
	private String action;
	
	/**
	 * Date of this event
	 */
	private Date date;
	
	/**
	 * {@link Ticket} concerned by this event
	 */
	private Ticket ticket;
	
	/**
	 * Author of this event
	 */
	private User author;
	
	private boolean enabled;
	
	/**
	 * Default constructor which initializes the date to current date
	 */
	public Event(){
		date = new Date();
		enabled = true;
	}

	/**
	 * Gets the ID
	 * @return the id
	 */
	@Id
	@Column(name = "key_")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getId() {
		return id;
	}

	/**
	 * Sets the ID
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Gets the action
	 * @return the action
	 */
	@Column(length = 50, nullable = false, updatable = false)
	@NotEmpty
	@Length(max = 50)
	@NotNull
	public String getAction() {
		return action;
	}

	/**
	 * Sets the action
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * Gets the date
	 * @return the date
	 */
	@Column(name = "date", nullable = false, updatable = false)
	@Temporal(value = TemporalType.TIMESTAMP)
	@NotNull
	public Date getDate() {
		return date;
	}

	/**
	 * Sets the date
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * Gets the ticket
	 * @return the ticket
	 */
	@ManyToOne
	@NotNull
	@Valid
	public Ticket getTicket() {
		return ticket;
	}

	/**
	 * Sets the ticket
	 * @param ticket the ticket to set
	 */
	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	/**
	 * Gets the author
	 * @return the author
	 */
	@ManyToOne
	@NotNull
	@Valid
	public User getAuthor() {
		return author;
	}

	/**
	 * Sets the author
	 * @param author the author to set
	 */
	public void setAuthor(User author) {
		this.author = author;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * {@inheritDoc} Compare two events using their id
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj instanceof Event) {
			Event event = (Event) obj;
			return (event.getId() == this.id) ? true : false;
		}
		return false;
	}

	/**
	 * {@inheritDoc} Calculates the hashCode using the event's id
	 */
	@Override
	public int hashCode() {
		return ((Long) id).hashCode();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return The action of this event
	 */
	@Override
	public String toString() {
		return action;
	}
}
