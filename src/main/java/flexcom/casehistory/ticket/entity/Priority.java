package flexcom.casehistory.ticket.entity;

import java.util.Collections;
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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Definition class for {@link Ticket} priority
 * @author philippe
 *
 */
@Entity
@Table(name = "Priorities")
@NamedQueries(value = { 
	@NamedQuery(name = "priority.findById", query = "select p from Priority p where p.id = :id"),
	@NamedQuery(name = "priority.findByName", query = "select p from Priority p where p.name = :name"),
	@NamedQuery(name = "priority.findByLevel", query = "select p from Priority p where p.level = :level"),
	@NamedQuery(name = "priority.count", query = "select count(*) from Priority p") 
})
public class Priority implements Comparable<Priority> {
	
	public final static Priority NONE = new Priority("NONE", 0);
	public final static Priority LOW = new Priority("LOW", 1);
	public final static Priority MEDIUM = new Priority("MEDIUM", 2);
	public final static Priority HIGH = new Priority("HIGH", 3);
	public final static Priority URGENT = new Priority("URGENT", 4);
	public final static Priority IMMEDIATE = new Priority("IMMEDIATE", 5);
	
	public static Priority getPriority(Priority type){
		return new Priority(type.getName(), type.getLevel()); 
	}
	
	/**
	 * ID of this priority
	 */
	private long id;
	
	/**
	 * Name of this priority
	 */
	private String name;
	
	/**
	 * Level of this priority
	 */
	private int level;

	/**
	 * Set of tickets with this priority
	 */
	private Set<Ticket> tickets;
	
	private boolean enabled;
	
	/**
	 * Default Constructor
	 */
	public Priority(){
		this(null, -1);
		tickets = Collections.emptySet();
	}
	
	/**
	 * Initialize the priority object with a name and a level
	 * @param name Name of the priority
	 * @param level Level of the priority
	 */
	private Priority(String name, int level){
		this.name = name;
		this.level = level;
		this.enabled = true;
	}

	/**
	 * Gets the id
	 * 
	 * @return The id of this priority
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
	 *            The id of this priority
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Gets the name
	 * @return the name
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
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the level
	 * @return the level
	 */
	@Column(nullable = false)
	@NotNull
	@Min(0)
	public int getLevel() {
		return level;
	}

	/**
	 * Sets the level
	 * @param level the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}
	
	/**
	 * Gets the tickets with this priority
	 * 
	 * @return The tickets set with this priority
	 */
	@OneToMany(mappedBy = "priority", fetch = FetchType.EAGER)
	public Set<Ticket> getTickets() {
		return tickets;
	}

	/**
	 * Sets the tickets with this priority
	 * 
	 * @param tickets
	 *            The tickets set with this priority
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
	public int compareTo(Priority p) {
		return this.level - p.getLevel();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj instanceof Priority) {
			Priority priority = (Priority) obj;
			if (priority.getLevel() != this.level){
				return false;
			}
			if (priority.getName() == null){
				return (this.name == null) ? true : false;
			}
			return priority.getName().equals(this.name);
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
