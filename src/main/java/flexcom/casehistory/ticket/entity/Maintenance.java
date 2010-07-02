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
 * Definition class for {@link Client} maintenance
 * 
 * @author philippe
 * 
 */
@Entity
@Table(name = "Maintenances")
@NamedQueries(value = { 
	@NamedQuery(name = "maintenance.findById", query = "select m from Maintenance m where m.id = :id"),
	@NamedQuery(name = "maintenance.findByName", query = "select m from Maintenance m where m.name = :name"),
	@NamedQuery(name = "maintenance.count", query = "select count(*) from Maintenance m") 
})
public class Maintenance {
	
	public final static Maintenance FULL = new Maintenance("Full");
	public final static Maintenance TEST = new Maintenance("Test");
	public final static Maintenance NONE = new Maintenance("None");

	public static Maintenance getMaintenance(Maintenance type){
		return new Maintenance(type.getName()); 
	}
	
	/**
	 * ID of this maintenance
	 */
	private long id;
	
	/**
	 * Name of maintenance
	 */
	private String name;
	
	/**
	 * Set of licences with this maintenance
	 */
	private Set<Licence> licences;
	
	private boolean enabled;
	
	/**
	 * Default constructor
	 */
	public Maintenance(){
		this.name = null;
		this.enabled = true;
	}
	
	/**
	 * Constructor with a given name
	 * 
	 * @param name
	 *            Name of maintenance
	 */
	private Maintenance(String name) {
		this.name = name;
		this.enabled = true;
	}

	/**
	 * Gets the id
	 * 
	 * @return The id of this maintenance
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
	 *            The id of this maintenance
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Gets the name
	 * @return Name of maintenance
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
	 * @param name Name of maintenance
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the licences with this maintenance
	 * 
	 * @return The licences set with this maintenance
	 */
	@OneToMany(mappedBy = "maintenance", fetch = FetchType.EAGER)
	public Set<Licence> getLicences() {
		return licences;
	}

	/**
	 * Sets the licences with this maintenance
	 * 
	 * @param licences
	 *            The licences set with this maintenance
	 */
	public void setLicences(Set<Licence> licences) {
		this.licences = licences;
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
		if (obj instanceof Maintenance) {
			Maintenance maintenance = (Maintenance) obj;
			if (maintenance.getName() == null){
				return (this.name == null) ? true : false;
			}
			return maintenance.getName().equals(this.name);
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
