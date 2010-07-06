package flexcom.casehistory.ticket.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

/**
 * Licence entity class
 * 
 * @author philippe
 *
 */
@Entity
@Table(name = "Licences", uniqueConstraints = {@UniqueConstraint(columnNames = {"client", "product"})})
@NamedQueries(value = { 
	@NamedQuery(name = "licence.findById", query = "select l from Licence l where l.id = :id"),
	@NamedQuery(name = "licence.findByLicenceKey", query = "select l from Licence l where l.licenceKey = :licenceKey"),
	@NamedQuery(name = "licence.findByMaintenance", query = "select l from Licence l where l.maintenance = :maintenance"),
	@NamedQuery(name = "licence.count", query = "select count(*) from Licence l") 
})
public class Licence {
	
	/**
	 * Licence's ID
	 */
	private long id;
	
	/**
	 * The licence key
	 */
	private String licenceKey;
	
	/**
	 * Licence's creation date
	 */
	private Date creationDate;
	
	/**
	 * Licence's expiration date
	 */
	private Date expirationDate;
	
	/**
	 * Licence's last update date
	 */
	private Date lastUpdate;

	/**
	 * {@link Client} owner of this licence
	 */
	private Client client;
	
	/**
	 * {@link Product} concerned by this licence
	 */
	private Product product;
	
	/**
	 * Maintenance type
	 */
	private Maintenance maintenance;
	
	private boolean enabled;

	/**
	 * Default constructor which initializes the creation date to current date
	 */
	public Licence(){
		creationDate = new Date();
		enabled = true;
	}

	/**
	 * Gets the licence ID
	 * @return the id
	 */
	@Id
	@Column(name = "key_")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getId() {
		return id;
	}

	/**
	 * Sets the licence ID
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Gets the licence key
	 * @return the licenceKey
	 */
	@Column(unique = true, length = 50, nullable = false)
	@Length(max = 50)
	@NotNull
	public String getLicenceKey() {
		return licenceKey;
	}

	/**
	 * Sets the licence key
	 * @param licenceKey the licenceKey to set
	 */
	public void setLicenceKey(String licenceKey) {
		this.licenceKey = licenceKey;
	}

	/**
	 * Gets the creation date
	 * @return the dateCreation
	 */
	@Column(name = "datecreation", nullable = false)
	@Temporal(value = TemporalType.TIMESTAMP)
	@NotNull
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * Sets the creation date
	 * @param dateCreation the dateCreation to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * Gets the expiration date
	 * @return the dateExpiration
	 */
	@Column(name = "dateexpiration")
	@Temporal(value = TemporalType.TIMESTAMP)
	@Future
	public Date getExpirationDate() {
		return expirationDate;
	}

	/**
	 * Sets the expiration date
	 * @param dateExpiration the dateExpiration to set
	 */
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
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
	 * Gets the licence owner
	 * @return the client
	 */
	@JoinColumn(name = "client")
	@ManyToOne
	@NotNull
	@Valid
	public Client getClient() {
		return client;
	}

	/**
	 * Sets the licence owner
	 * @param client the client to set
	 */
	public void setClient(Client client) {
		this.client = client;
	}

	/**
	 * Gets the concerned {@link Product}
	 * @return the product
	 */
	@JoinColumn(name = "product")
	@ManyToOne
	@NotNull
	@Valid
	public Product getProduct() {
		return product;
	}

	/**
	 * Sets the concerned {@link Product}
	 * @param product the product to set
	 */
	public void setProduct(Product product) {
		this.product = product;
	}

	/**
	 * Gets the maintenance type
	 * 
	 * @return The maintenance type
	 */
	@NotNull
	@ManyToOne
	@Valid
	public Maintenance getMaintenance() {
		return maintenance;
	}

	/**
	 * Sets the maintenance type
	 * 
	 * @param maintenance
	 *            The maintenance type
	 */
	public void setMaintenance(Maintenance maintenance) {
		this.maintenance = maintenance;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return The licence's key
	 */
	@Override
	public String toString() {
		return licenceKey;
	}

	/**
	 * {@inheritDoc} Compare two licences using their id
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj instanceof Licence) {
			Licence licence = (Licence) obj;
			return (licence.getId() == this.id) ? true : false;
		}
		return false;
	}

	/**
	 * {@inheritDoc} Calculates the hashCode using the licence's id
	 */
	@Override
	public int hashCode() {
		return ((Long) id).hashCode();
	}
}
