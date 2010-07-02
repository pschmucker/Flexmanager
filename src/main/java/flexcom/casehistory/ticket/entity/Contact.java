package flexcom.casehistory.ticket.entity;

import java.util.Collections;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "Contacts")
@NamedQueries(value = { 
	@NamedQuery(name = "contact.findById", query = "select c from Contact c where c.id = :id"),
	@NamedQuery(name = "contact.findByName", query = "select c from Contact c where c.name = :name"),
	@NamedQuery(name = "contact.count", query = "select count(*) from Contact c") 
})
public class Contact {

	private long id;
	private String name;
	private Company company;
	private Set<Ticket> tickets;
	private boolean enabled;
	
	public Contact() {
		tickets = Collections.emptySet();
		enabled = true;
	}

	@Id
	@Column(name = "key_")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(length = 50)
	@Length(max = 50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne
	@NotNull
	@Valid
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@OneToMany(mappedBy = "contact", fetch = FetchType.EAGER)
	public Set<Ticket> getTickets() {
		return tickets;
	}

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
	 * {@inheritDoc} Compare two companies using their id
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj instanceof Contact) {
			Contact contact = (Contact) obj;
			return (contact.getId() == this.id) ? true : false;
		}
		return false;
	}

	/**
	 * {@inheritDoc} Calculates the hashCode using the company's id
	 */
	@Override
	public int hashCode() {
		return ((Long) id).hashCode();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return The company's name
	 */
	@Override
	public String toString() {
		return name;
	}

}
