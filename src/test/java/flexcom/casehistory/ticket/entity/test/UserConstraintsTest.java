package flexcom.casehistory.ticket.entity.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.regex.Pattern;

import javax.validation.ConstraintViolationException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import flexcom.casehistory.ticket.dao.UserDAO;
import flexcom.casehistory.ticket.entity.User;

/**
 * Class test for setters of the {@link User} class. Here we check all
 * constraints on every fields.
 * 
 * @author philippe
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class UserConstraintsTest {

	/**
	 * User DAO
	 */
	@Autowired
	private UserDAO userDAO;

	/**
	 * A {@link User}
	 */
	private User user;

	/**
	 * Set up the context
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		user = new User();
		user.setLogin("phil");
		user.setPassword("pwd");
		user.setName("Phil");
		user.setEmail("phil@gmail.com");
		user.setTitle("Software engineer");
	}

	/**
	 * Clear the context
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		user = null;
		userDAO.deleteAll();
	}

	/**
	 * Test if a {@link DataIntegrityViolationException} is thrown when we
	 * create 2 users with the same login
	 */
	@Test(expected = DataIntegrityViolationException.class)
	public void testSetLoginUnique() {
		user.setLogin("unique");
		userDAO.createUser(user);

		User copy = new User();
		copy.setLogin("unique");
		copy.setPassword("password");
		copy.setName("Phil");
		copy.setEmail("copy@yopmail.com");
		userDAO.createUser(copy);
	}

	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * {@link User} with a too short login
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSetLoginMinSize() {
		user.setLogin("joe");
		userDAO.createUser(user);
	}

	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * {@link User} with a too long login
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSetLoginMaxSize() {
		user.setLogin("AnExtremelyVeryLongLogin");
		userDAO.createUser(user);
	}

	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * {@link User} whose login doesn't match the {@link Pattern} \w+
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSetLoginPattern() {
		user.setLogin("Invalid login");
		userDAO.createUser(user);
	}

	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * {@link User} whose login is <code>null</code>
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSetLoginNull() {
		user.setLogin(null);
		userDAO.createUser(user);
	}

	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * {@link User} whose password is <code>null</code>
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetPasswordNull() {
		user.setPassword(null);
		userDAO.createUser(user);
	}

	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * {@link User} with a too long name
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSetNameMaxSize() {
		user.setName("This user has a very long name !");
		userDAO.createUser(user);
	}
	
	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * {@link User} whose email is <code>null</code>
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSetEmailNull() {
		user.setEmail(null);
		userDAO.createUser(user);
	}

	/**
	 * Test if a {@link DataIntegrityViolationException} is thrown when we
	 * create 2 users with the same email
	 */
	public void testSetEmailUnique(){
		user.setEmail("unique@yopmail.com");
		userDAO.createUser(user);

		User copy = new User();
		copy.setEmail("unique@yopmail.com");
		userDAO.createUser(copy);
	}

	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * {@link User} with a too long email
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSetEmailMaxSize() {
		user.setEmail("The_Longest_Username_I_Have_Never_Seen@The_Longest_Hostname_I_Have_Never_Seen.The_Longest_Domain_I_Have_Never_Seen");
		userDAO.createUser(user);
	}

	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * {@link User} with an invalid email
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSetEmailPattern() {
		user.setEmail("NotAValidEmail");
		userDAO.createUser(user);
	}

	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * {@link User} with a too long title
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSetTitleMaxSize() {
		user.setTitle("Again a long text, again, and again and again and again");
		userDAO.createUser(user);
	}

	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * {@link User} with a too low access level
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSetAccessLevelMin() {
		user.setAccessLevel(-1);
		userDAO.createUser(user);
	}

	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * {@link User} with a too high access level
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSetAccessLevelMax() {
		user.setAccessLevel(11);
		userDAO.createUser(user);
	}

	/**
	 * Test if the default access level is set to 0
	 */
	@Test
	public void testSetAccessLevelDefault() {
		userDAO.createUser(user);
		long id = user.getId();

		User u = userDAO.findById(id);
		assertEquals(0, u.getAccessLevel());
	}
	
	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * {@link User} whose creation date is <code>null</code>
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSetDateCreationNull() {
		user.setCreationDate(null);
		userDAO.createUser(user);
	}

	/**
	 * Test if the default creation date is set to the current date
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void testSetDateCreationDefault() throws InterruptedException {
		Date before = new Date();
		Thread.sleep(1000);

		User user = new User();
		user.setLogin("user");
		user.setPassword("pwd");
		user.setName("name");
		user.setEmail("user@yopmail.com");

		Thread.sleep(1000);
		Date after = new Date();

		userDAO.createUser(user);
		long id = user.getId();

		User u = userDAO.findById(id);
		assertNotNull(u.getCreationDate());
		assertTrue(u.getCreationDate().after(before));
		assertTrue(u.getCreationDate().before(after));
	}

	/**
	 * Test if the creation date of an {@link User} can't be updated
	 * @throws InterruptedException 
	 */
	// TODO @Test does not work
	public void testUpdateCreationDate() throws InterruptedException{
		Date d = user.getCreationDate();
		userDAO.createUser(user);
		long id = user.getId();
		Thread.sleep(1000);
		
		User u = userDAO.findById(id);
		u.setCreationDate(new Date());
		userDAO.updateUser(u);
		
		u = null;
		u = userDAO.findById(id);
		assertFalse(u.getCreationDate() != d);
	}

}
