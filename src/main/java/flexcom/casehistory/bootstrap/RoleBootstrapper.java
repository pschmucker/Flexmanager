package flexcom.casehistory.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import flexcom.casehistory.ticket.dao.RoleDAO;
import flexcom.casehistory.ticket.entity.Role;


@Component
public class RoleBootstrapper implements Bootstrapper {
	
	@Autowired
	private RoleDAO roleDAO;
	
	@Override
	public void bootstrap() {
		if (roleDAO.count() == 0){
			roleDAO.createRole(Role.getRole(Role.USER));
			roleDAO.createRole(Role.getRole(Role.ADMIN));
		}
	}
	
}
