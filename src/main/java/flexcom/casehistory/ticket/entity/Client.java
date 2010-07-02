package flexcom.casehistory.ticket.entity;

import java.util.Collections;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;

/**
 * Client entity class
 * 
 * @author philippe
 * 
 */
@Entity
@Table(name = "Clients")
@NamedQueries(value = { 
	@NamedQuery(name = "client.findById", query = "select c from Client c where c.id = :id"),
	@NamedQuery(name = "client.findByName", query = "select c from Client c where c.name = :name"),
	@NamedQuery(name = "client.count", query = "select count(*) from Client c") 
})
public class Client extends Company {

	/**
	 * Set of tickets concerning this client
	 */
	private Set<Ticket> tickets;

	/**
	 * Set of licences owned by this client
	 */
	private Set<Licence> licences;
	
	private Partner partner;

	/**
	 * Constructor without argument which initialize the tickets set
	 */
	public Client() {
		super();
		tickets = Collections.emptySet();
		licences = Collections.emptySet();
	}

	/**
	 * Gets the tickets concerning this client
	 * 
	 * @return The tickets set concerning this client
	 */
	@OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
	public Set<Ticket> getTickets() {
		return tickets;
	}

	/**
	 * Sets the tickets concerning this client
	 * 
	 * @param tickets
	 *            The licences owned by this client
	 */
	public void setTickets(Set<Ticket> tickets) {
		this.tickets = tickets;
	}

	/**
	 * Gets the licences set
	 * 
	 * @return The licences owned by this client
	 */
	@OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
	public Set<Licence> getLicences() {
		return licences;
	}

	/**
	 * Sets the licences set
	 * 
	 * @param licences
	 *            The concerned licences for this product
	 */
	public void setLicences(Set<Licence> licences) {
		this.licences = licences;
	}
	
	@ManyToOne
	@Valid
	public Partner getPartner() {
		return partner;
	}
	
	public void setPartner(Partner partner) {
		this.partner = partner;
	}
}
