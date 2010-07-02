package flexcom.casehistory.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import flexcom.casehistory.ticket.dao.RoleDAO;
import flexcom.casehistory.ticket.entity.Maintenance;
import flexcom.casehistory.ticket.entity.Role;

/**
 * {@link Maintenance} data set
 * 
 * @author philippe
 * 
 */
@Component
public class RoleDataSet extends Data {
	
	@Autowired
	private RoleDAO roleDAO;
	
	/**
	 * Set up all roles
	 */
	public void setup() {
		if (roleDAO.count() == 0) {
			ROLE_ADMIN = Role.getRole(Role.ADMIN);
			ROLE_USER = Role.getRole(Role.USER);
			roleDAO.createRole(ROLE_ADMIN);
			roleDAO.createRole(ROLE_USER);
		}
	}

	/**
	 * Clear all roles
	 */
	public void clear() {
		roleDAO.deleteAll();
	}

}
