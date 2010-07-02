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
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
public abstract class Company {
	
	protected long id;
	protected String name;
	protected String address;
	protected String country;
	protected Date creationDate;
	protected Date lastUpdate;
	protected Set<Contact> contacts;
	protected boolean enabled;
	
	protected Company(){
		creationDate = new Date();
		contacts = Collections.emptySet();
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

	@Column(length = 50, nullable = false, unique = true)
	@NotEmpty
	@Length(max = 50)
	@NotNull
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(length = 255)
	@Length(max = 255)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(length = 50)
	@Length(max = 50)
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Column(name = "creationdate", nullable = false, updatable = false)
	@Temporal(value = TemporalType.TIMESTAMP)
	@NotNull
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "lastupdate")
	@Temporal(value = TemporalType.TIMESTAMP)	
	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	@OneToMany(mappedBy = "company", fetch = FetchType.EAGER)
	public Set<Contact> getContacts() {
		return contacts;
	}
	
	public void setContacts(Set<Contact> contacts) {
		this.contacts = contacts;
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
		if (obj instanceof Company) {
			Company company = (Company) obj;
			return (company.getId() == this.id) ? true : false;
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
