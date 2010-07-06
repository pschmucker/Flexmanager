package flexcom.casehistory.ticket.entity;

import java.util.Collections;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Product entity class
 * 
 * @author philippe
 * 
 */
@Entity
@Table(name = "Products", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "version"})})
@NamedQueries(value = { 
	@NamedQuery(name = "product.findById", query = "select p from Product p where p.id = :id"),
	@NamedQuery(name = "product.findByName", query = "select p from Product p where p.name = :name"),
	@NamedQuery(name = "product.count", query = "select count(*) from Product p") 
})
public class Product {

	/**
	 * Product's id
	 */
	private long id;

	/**
	 * Product's name
	 */
	private String name;

	/**
	 * Product's version
	 */
	private String version;

	/**
	 * Set of tickets concerning this product
	 */
	private Set<Ticket> tickets;

	/**
	 * Set of licences concerning this product
	 */
	private Set<Licence> licences;
	
	/**
	 * Product's creation date
	 */
	private Date creationDate;
	
	/**
	 * Product's last update date
	 */
	private Date lastUpdate;
	
	private boolean enabled;

	/**
	 * Constructor without argument which initialize the tickets set
	 */
	public Product() {
		tickets = Collections.emptySet();
		licences = Collections.emptySet();
		creationDate = new Date();
		enabled = true;
	}

	/**
	 * Gets the id
	 * 
	 * @return The product's id
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
	 *            The product's id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Gets the name
	 * 
	 * @return The product's name
	 */
	@Column(length = 30, nullable = false)
	@NotEmpty
	@Length(max = 30)
	@NotNull
	public String getName() {
		return name;
	}

	/**
	 * Sets the name
	 * 
	 * @param name
	 *            The product's name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the version
	 * 
	 * @return The product's version
	 */
	@Column(length = 20, nullable = false)
	@NotEmpty
	@Length(max = 20)
	@NotNull
	public String getVersion() {
		return version;
	}

	/**
	 * Sets the version
	 * 
	 * @param version
	 *            The product's version
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * Gets the tickets set
	 * 
	 * @return The concerned tickets for this product
	 */
	@OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
	public Set<Ticket> getTickets() {
		return tickets;
	}

	/**
	 * Sets the tickets set
	 * 
	 * @param tickets
	 *            The concerned tickets for this product
	 */
	public void setTickets(Set<Ticket> tickets) {
		this.tickets = tickets;
	}

	/**
	 * Gets the licences set
	 * 
	 * @return The concerned licences for this product
	 */
	@OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
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

	/**
	 * Gets the creation date
	 * 
	 * @return The product's creation date
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
	 *            The product's date creation
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
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	/**
	 * {@inheritDoc} Compare two products using their id
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj instanceof Product) {
			Product product = (Product) obj;
			return (product.getId() == this.id) ? true : false;
		}
		return false;
	}

	/**
	 * {@inheritDoc} Calculates the hashCode using the product's id
	 */
	@Override
	public int hashCode() {
		return ((Long) id).hashCode();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return The product's name followed by its version (if defined)
	 */
	@Override
	public String toString() {
		return name + (version == null ? "" : " " + version);
	}
}
