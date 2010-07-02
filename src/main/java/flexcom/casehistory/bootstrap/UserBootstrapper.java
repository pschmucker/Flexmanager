package flexcom.casehistory.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import flexcom.casehistory.ticket.dao.RoleDAO;
import flexcom.casehistory.ticket.dao.UserDAO;
import flexcom.casehistory.ticket.entity.Role;
import flexcom.casehistory.ticket.entity.User;


@Component
public class UserBootstrapper implements Bootstrapper {
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private RoleDAO roleDAO;
	
	@Autowired
	private RoleBootstrapper roleBootstrapper;
	
	@Override
	public void bootstrap() {
		if (roleDAO.count() == 0){
			roleBootstrapper.bootstrap();
		}
		
		if (userDAO.count() == 0){
			Role adminRole = roleDAO.findByName("ROLE_ADMIN");
			Role userRole = roleDAO.findByName("ROLE_USER");
			
			User user = new User();
			user.setLogin("admin");
			user.setPassword("admin");
			user.setName("Mr. Admin");
			user.setEmail("admin@yopmail.com");
			user.setTitle("administrator");
			user.setAccessLevel(10);
			user.addRole(userRole);
			user.addRole(adminRole);
			userDAO.createUser(user);
			
			user = new User();
			user.setLogin("phil");
			user.setPassword("phil");
			user.setName("Philippe Schmucker");
			user.setEmail("phil@yopmail.com");
			user.setTitle("software engineer");
			user.addRole(userRole);
			user.setAccessLevel(5);
			userDAO.createUser(user);
		}
	}
	
}
