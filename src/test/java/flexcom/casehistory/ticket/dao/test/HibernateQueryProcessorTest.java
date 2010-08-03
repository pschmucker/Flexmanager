package flexcom.casehistory.ticket.dao.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import flexcom.casehistory.ticket.dao.HibernateQueryProcessor;
import flexcom.casehistory.ticket.dao.UserDAO;
import flexcom.casehistory.ticket.entity.User;
import flexcom.casehistory.ticket.search.Query;
import flexcom.casehistory.ticket.search.QueryProcessor;
import flexcom.casehistory.ticket.search.filter.EqualFilter;
import flexcom.casehistory.ticket.search.filter.Filter;
import flexcom.casehistory.ticket.search.filter.NotFilter;

/**
 * Test class for the {@link HibernateQueryProcessor} class
 * 
 * @author philippe
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class HibernateQueryProcessorTest {

	/**
	 * User DAO
	 */
	@Autowired
	private UserDAO userDAO;

	/**
	 * {@link QueryProcessor}
	 */
	@Autowired
	private QueryProcessor<User> processor;

	/**
	 * A {@link Filter}
	 */
	private Filter filter;

	/**
	 * Set up the context
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		filter = new EqualFilter("login", "user");

		User u1 = new User();
		u1.setAccessLevel(0);
		u1.setEmail("user@mail.com");
		u1.setLogin("user");
		u1.setName("Unknown User");
		u1.setPassword("azerty");
		u1.setTitle("simple user");

		User u2 = new User();
		u2.setAccessLevel(0);
		u2.setEmail("user@mail.net");
		u2.setLogin("jean");
		u2.setName("Other User");
		u2.setPassword("qwerty");
		u2.setTitle("another simple user");

		userDAO.createUser(u1);
		userDAO.createUser(u2);
	}

	/**
	 * Clear the context
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		userDAO.deleteAll();
	}

	/**
	 * Test an execution of the query with one filter. We check the following
	 * conditions :<br/>
	 * <ul>
	 * <li>The list contains 1 result</li>
	 * <li>This result is the {@link User} "user"</li>
	 * </ul>
	 */
	@Test
	public void testExecuteQueryWithOneFilter() {
		Query<User> query = new Query<User>(User.class);
		query.addFilter(filter);
		List<User> list = processor.execute(query);
		assertEquals(1, list.size());

		User u = list.get(0);
		assertEquals(u, userDAO.findByLogin("user"));
	}

	/**
	 * Test if an {@link InvalidDataAccessApiUsageException} is thrown when we
	 * try to execute a <code>null</code> {@link Query}
	 */
	@Test(expected = InvalidDataAccessApiUsageException.class)
	public void testExecuteNullQuery() {
		processor.execute(null);
	}

	/**
	 * Test an execution of the query with an inverse filter. We check the
	 * following conditions :<br/>
	 * <ul>
	 * <li>The list contains 1 result</li>
	 * <li>This result is not the {@link User} "user"</li>
	 * </ul>
	 */
	@Test
	public void testExecuteQueryWithOneInverseFilter() {
		Query<User> query = new Query<User>(User.class);
		query.addFilter(new NotFilter(filter));
		List<User> list = processor.execute(query);
		assertEquals(1, list.size());
		assertFalse(list.contains(userDAO.findByLogin("user")));
	}

	/**
	 * Test if an execution of query without filters has the same result as the
	 * findAll() method
	 */
	@Test
	public void testExecuteQueryWithoutFilter() {
		Query<User> query = new Query<User>(User.class);
		List<User> list = processor.execute(query);
		assertEquals(2, list.size());
		assertEquals(list, userDAO.findAll());
	}

}
