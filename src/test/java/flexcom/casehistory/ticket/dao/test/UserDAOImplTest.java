package flexcom.casehistory.ticket.dao.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import flexcom.casehistory.data.Data;
import flexcom.casehistory.data.TicketDataSet;
import flexcom.casehistory.data.UserDataSet;
import flexcom.casehistory.ticket.dao.UserDAO;
import flexcom.casehistory.ticket.dao.UserDAOImpl;
import flexcom.casehistory.ticket.entity.Ticket;
import flexcom.casehistory.ticket.entity.User;
import flexcom.casehistory.ticket.search.Query;
import flexcom.casehistory.ticket.search.filter.EqualFilter;
import flexcom.casehistory.ticket.search.filter.GreaterOrEqualFilter;
import flexcom.casehistory.ticket.search.filter.GreaterThanFilter;

/**
 * Test class for {@link UserDAOImpl}
 * @author philippe
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class UserDAOImplTest {
	
	/**
	 * ID for an {@link User}
	 */
	private long userId;
	
	/**
	 * User DAO
	 */
	@Autowired
	private UserDAO userDAO;

	@Autowired
	private UserDataSet userDataSet;
	
	@Autowired
	private TicketDataSet ticketDataSet;

	/**
	 * Set up the context
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		userDataSet.setup();
		userId = Data.USER_1.getId();
	}

	/**
	 * Clear the context
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		userDataSet.clear();
	}

	/**
	 * Test the createUser(User) method : the DAO must have an additionnal entity after the creation
	 */
	@Test
	public void testCreateUser() {
		int count = (int) userDAO.count();
		User u = new User();
		u.setLogin("user");
		u.setPassword("password");
		u.setEmail("user@yopmail.com");
		u.setName("name");
		userDAO.createUser(u);
		assertEquals(count + 1, userDAO.count());
	}

	/**
	 * Test if the createUser(User) method throws a {@link NullPointerException}
	 * by passing a <code>null</code> {@link User}
	 */
	@Test(expected = NullPointerException.class)
	public void testCreateNullUser() {
		userDAO.createUser(null);
	}

	/**
	 * Test if the DAO have no more {@link User} entities after the call of deleteAll() method
	 */
	@Test
	public void testDeleteAll() {
		User u = new User();
		u.setLogin("user");
		u.setPassword("password");
		u.setName("name");
		u.setEmail("user@yopmail.com");
		userDAO.createUser(u);
		userDAO.deleteAll();
		assertEquals(0, userDAO.count());
	}

	/**
	 * Test if the findAll() method return a correct and complete list of all users. The following conditions are checked :<br/>
	 * <ul>
	 * <li>3 users are in the list</li>
	 * <li>The list contains the user "Phil"</li>
	 * <li>The list contains the user "Alex"</li>
	 * <li>The list contains the user "Tmp"</li>
	 * </ul>
	 */
	@Test
	public void testFindAll() {
		List<User> users = userDAO.findAll();
		assertEquals(3, users.size());
		assertTrue(users.contains(Data.USER_1));
		assertTrue(users.contains(Data.USER_2));
		assertTrue(users.contains(Data.USER_3));
	}

	/**
	 * Test if the result of findByLogin(String) is correct
	 */
	@Test
	public void testFindByLogin() {
		assertEquals(Data.USER_1, userDAO.findByLogin("user1"));
	}

	/**
	 * Test if the findByLogin(String) method throws an {@link EmptyResultDataAccessException} if the given argument is null
	 */
	@Test(expected = EmptyResultDataAccessException.class)
	public void testFindByNullLogin() {
		userDAO.findByLogin(null);
	}

	/**
	 * Test if the modification of an entity has been correctly done
	 */
	@Test
	public void testUpdateUser() {
		Data.USER_1.setAccessLevel(7);
		userDAO.updateUser(Data.USER_1);
		assertEquals(7, userDAO.findByLogin("user1").getAccessLevel());
	}

	/**
	 * Test if the updateUser(User) method throws an {@link InvalidDataAccessApiUsageException} if the given argument is null
	 */
	@Test(expected = NullPointerException.class)
	public void testUpdateNullUser() {
		userDAO.updateUser(null);
	}

	/**
	 * In this test, we create a {@link Ticket}, then we assign it to the {@link User} "Phil".
	 * Then, we checked that the User "Phil" is the only responsible of this {@link Ticket}.
	 * At the end, we clear the entity manager.
	 */
	@Test
	public void testFindByAssignedTicket() {
		ticketDataSet.setup();
		
		Data.USER_1.assign(Data.TICKET_1);
		userDAO.updateUser(Data.USER_1);
		
		List<User> users = userDAO.findByAssignedTicket(Data.TICKET_1);
		assertEquals(1, users.size());
		assertEquals(Data.USER_1, users.get(0));

		ticketDataSet.clear();
	}

	/**
	 * Test if nobody is responsible of a <code>null</code> {@link Ticket}
	 */
	@Test
	public void testFindByNullAssignedTicket() {
		List<User> users = userDAO.findByAssignedTicket(null);
		assertEquals(0, users.size());
	}

	/**
	 * Test the deletion of an {@link User}. We check that the deleted {@link User} can't be found.
	 */
	@Test
	public void testDeleteUser() {
		userDAO.deleteUser(Data.USER_1);
		assertNull(userDAO.findById(userId));
	}

	/**
	 * Test if a {@link NullPointerException} is thrown by trying to delete a <code>null</code> {@link User}
	 */
	@Test(expected = NullPointerException.class)
	public void testDeleteNullUser() {
		userDAO.deleteUser(null);
	}

	/**
	 * Test if an {@link User} can be found by his ID
	 */
	@Test
	public void testFindById() {
		assertEquals(Data.USER_1, userDAO.findById(userId));
	}

	/**
	 * Test if all users with the specified access level are found. The following conditions are checked : <br/>
	 * <ul>
	 * <li>The list contains 1 user</li>
	 * <li>The list contains the user "1"</li>
	 * </ul>
	 */
	@Test
	public void testFindByAccessLevel() {
		List<User> users = userDAO.findByAccessLevel(10);
		assertEquals(1, users.size());
		assertTrue(users.contains(Data.USER_1));
	}

	/**
	 * Test if an user with the specified email are found.
	 */
	@Test
	public void testFindByEmail() {
		User user = userDAO.findByEmail("user.1@yopmail.com");
		assertEquals(Data.USER_1, user);
	}

	/**
	 * Test if all users with the specified name are found. The following
	 * conditions are checked : <br/>
	 * <ul>
	 * <li>The list contains 1 user</li>
	 * <li>The list contains the user "Phil"</li>
	 * </ul>
	 */
	@Test
	public void testFindByName() {
		List<User> users = userDAO.findByName("U1");
		assertEquals(1, users.size());
		assertEquals(Data.USER_1, users.get(0));
	}

	/**
	 * Test if all users with the specified title are found. The following conditions are checked : <br/>
	 * <ul>
	 * <li>The list contains 1 user</li>
	 * <li>The list contains the user "Phil"</li>
	 * </ul>
	 */
	@Test
	public void testFindByTitle() {
		List<User> users = userDAO.findByTitle("Manager");
		assertEquals(1, users.size());
		assertEquals(Data.USER_1, users.get(0));
	}

	/**
	 * Test some {@link User} {@link Query}.
	 * First, we create a {@link Query} without filters.
	 * Then, after each execution of this query, we add a filter.
	 * The following scenario is executed :<br/>
	 * <ul>
	 * <li>0 filters : 3 users found</li>
	 * <li>Filter (accessLevel >= 1) added : 2 users found</li>
	 * <li>Filter (name = Alex) added : 1 user found</li>
	 * <li>Filter (accessLevel > 1) added : no user found</li>
	 * </ul>
	 */
	@Test
	public void testFilter() {
		List<User> users;
		Query<User> query = new Query<User>(User.class);
		
		users = userDAO.filter(query);
		assertEquals(3, users.size());
		
		query.addFilter(new GreaterOrEqualFilter("accessLevel", 5));
		
		users = userDAO.filter(query);
		assertEquals(2, users.size());
		
		query.addFilter(new EqualFilter("name", "U2"));
		
		users = userDAO.filter(query);
		assertEquals(1, users.size());
		
		query.addFilter(new GreaterThanFilter("accessLevel", 5));
		
		users = userDAO.filter(query);
		assertEquals(0, users.size());
	}
	
	/**
	 * Test the count() method
	 */
	@Test
	public void testCount(){
		assertEquals(3, userDAO.count());
	}
	
	/**
	 * Test the change of password
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 */
	@Test
	public void testChangePassword() throws NoSuchAlgorithmException, UnsupportedEncodingException{
		String newpass = "newpass";
		userDAO.changePassword(Data.USER_1, newpass);
		
		User user = userDAO.findById(userId);
		String hashedPassword = user.getPassword();
		
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		String hash = encoder.encodePassword(newpass, null);
		
		assertEquals(hashedPassword, hash);
	}

}
