package flexcom.casehistory.ticket.entity.test;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import flexcom.casehistory.ticket.dao.UserDAO;
import flexcom.casehistory.ticket.entity.Ticket;
import flexcom.casehistory.ticket.entity.User;

/**
 * Test class for the {@link User} entity. Setters and getters are not tested
 * here. Setters are tested in {@link UserConstraintsTest}
 * 
 * @author philippe
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class UserTest {

	/**
	 * A {@link User}
	 */
	private User user;

	/**
	 * User DAO
	 */
	@Autowired
	private UserDAO userDAO;

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
		user.setTitle("Software engineer");
		user.setEmail("phil@gmail.com");

		userDAO.createUser(user);
	}

	/**
	 * Clear the context
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		userDAO.deleteAll();
		user = null;
	}

	/**
	 * Test the hashCode method
	 */
	@Test
	public void testHashCode() {
		User u = userDAO.findByLogin("phil");
		assertEquals(user.hashCode(), u.hashCode());
	}

	/**
	 * Test if a {@link Ticket} is correctly assigned to a {@link User}
	 */
	@Test
	public void testAssign() {
		user.assign(new Ticket());
		assertEquals(1, user.getAssignedTickets().size());
	}

	/**
	 * Test if an {@link IllegalArgumentException} is thrown when we assign a
	 * <code>null</code> {@link Ticket}
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAssignNull() {
		user.assign(null);
	}

	/**
	 * Test the toString() method
	 */
	@Test
	public void testToString() {
		User u = userDAO.findByLogin("phil");
		assertEquals("phil (Phil)", u.toString());
	}

	/**
	 * Test the toString() method with a <code>null</code> name
	 */
	@Test
	public void testToStringWithNullName() {
		User u = userDAO.findByLogin("phil");
		u.setName(null);
		assertEquals("phil (Unknown name)", u.toString());
	}

	/**
	 * Test the equals(Object) method
	 */
	@Test
	public void testEqualsObject() {
		User u = userDAO.findByLogin("phil");
		assertEquals(user, u);
	}

}
