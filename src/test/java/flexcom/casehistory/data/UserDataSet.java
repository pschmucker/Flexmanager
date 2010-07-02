package flexcom.casehistory.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import flexcom.casehistory.ticket.dao.RoleDAO;
import flexcom.casehistory.ticket.dao.UserDAO;
import flexcom.casehistory.ticket.entity.User;

/**
 * {@link User} data set
 * 
 * @author philippe
 * 
 */
@Component
public class UserDataSet extends Data {

	/**
	 * User DAO
	 */
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private RoleDAO roleDAO;
	
	@Autowired
	private RoleDataSet roleDataSet;

	/**
	 * Set up all users
	 */
	public void setup() {
		if (roleDAO.count() == 0){
			roleDataSet.setup();
		}
		
		if (userDAO.count() == 0) {
			USER_1 = new User();
			USER_1.setLogin("user1");
			USER_1.setPassword("password");
			USER_1.setName("U1");
			USER_1.setEmail("user.1@yopmail.com");
			USER_1.setTitle("Manager");
			USER_1.setAccessLevel(10);
			USER_1.addRole(ROLE_USER);
			userDAO.createUser(USER_1);
			
			USER_2 = new User();
			USER_2.setLogin("user2");
			USER_2.setPassword("password");
			USER_2.setName("U2");
			USER_2.setEmail("user.2@yopmail.com");
			USER_2.setTitle("Engineer");
			USER_2.setAccessLevel(5);
			USER_2.addRole(ROLE_USER);
			userDAO.createUser(USER_2);
			
			USER_3 = new User();
			USER_3.setLogin("user3");
			USER_3.setPassword("password");
			USER_3.setName("U3");
			USER_3.setEmail("user.3@yopmail.com");
			USER_3.setTitle(null);
			USER_3.setAccessLevel(3);
			USER_3.addRole(ROLE_USER);
			userDAO.createUser(USER_3);
		}
	}

	/**
	 * Clear all users
	 */
	public void clear() {
		userDAO.deleteAll();
		roleDAO.deleteAll();
	}

}
