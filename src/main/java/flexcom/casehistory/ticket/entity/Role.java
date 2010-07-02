package flexcom.casehistory.ticket.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Definition class for Role
 * 
 * @author philippe
 * 
 */
@Entity
@Table(name = "Roles")
@NamedQueries(value = { 
	@NamedQuery(name = "role.findById", query = "select r from Role r where r.id = :id"),
	@NamedQuery(name = "role.findByName", query = "select r from Role r where r.name = :name"),
	@NamedQuery(name = "role.count", query = "select count(*) from Role r") 
})
public class Role {
	
	public final static Role USER = new Role("ROLE_USER");
	public final static Role ADMIN = new Role("ROLE_ADMIN");

	public static Role getRole(Role type){
		return new Role(type.getName()); 
	}
	
	/**
	 * ID of this role
	 */
	private long id;
	
	/**
	 * Name of role
	 */
	private String name;
	
	/**
	 * Set of users with this role
	 */
	private Set<User> users;
	
	private boolean enabled;
	
	/**
	 * Default constructor
	 */
	public Role(){
		this.name = null;
		this.enabled = true;
	}
	
	/**
	 * Constructor with a given name
	 * 
	 * @param name
	 *            Name of role
	 */
	private Role(String name) {
		this.name = name;
		this.enabled = true;
	}

	/**
	 * Gets the id
	 * 
	 * @return The id of this role
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
	 *            The id of this role
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Gets the name
	 * @return Name of role
	 */
	@Column(length = 20, unique = true, nullable = false)
	@Length(max = 20)
	@Pattern(regexp = "ROLE_[A-Z]+")
	@NotNull
	@NotEmpty
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name
	 * @param name Name of role
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the users with this role
	 * 
	 * @return The users set with this role
	 */
	@ManyToMany(mappedBy = "roles")
	public Set<User> getUsers() {
		return users;
	}

	/**
	 * Sets the users with this role
	 * 
	 * @param users
	 *            The users set with this role
	 */
	public void setUsers(Set<User> users) {
		this.users = users;
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
		if (obj instanceof Role) {
			Role role = (Role) obj;
			if (role.getName() == null){
				return (this.name == null) ? true : false;
			}
			return role.getName().equals(this.name);
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
