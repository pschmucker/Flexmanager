package flexcom.casehistory.ticket.entity;

import java.util.Collections;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Partners")
@NamedQueries(value = { 
	@NamedQuery(name = "partner.findById", query = "select p from Partner p where p.id = :id"),
	@NamedQuery(name = "partner.count", query = "select count(*) from Partner p") 
})
public class Partner extends Company {
	
	private Set<Client> clients;
	
	public Partner() {
		super();
		clients = Collections.emptySet();
	}
	
	@OneToMany(mappedBy = "partner", fetch = FetchType.EAGER)
	public Set<Client> getClients() {
		return clients;
	}
	
	public void setClients(Set<Client> clients) {
		this.clients = clients;
	}
	
}
