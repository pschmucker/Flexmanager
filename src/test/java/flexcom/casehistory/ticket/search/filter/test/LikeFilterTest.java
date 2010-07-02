package flexcom.casehistory.ticket.search.filter.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import flexcom.casehistory.data.UserDataSet;
import flexcom.casehistory.ticket.dao.UserDAO;
import flexcom.casehistory.ticket.entity.User;
import flexcom.casehistory.ticket.search.Query;
import flexcom.casehistory.ticket.search.QueryProcessor;
import flexcom.casehistory.ticket.search.filter.Filter;
import flexcom.casehistory.ticket.search.filter.LessOrEqualFilter;
import flexcom.casehistory.ticket.search.filter.LikeFilter;

/**
 * Test class for {@link LikeFilter}
 * 
 * @author philippe
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class LikeFilterTest {

	/**
	 * Data set used for setting up the context
	 */
	@Autowired
	private UserDataSet dataSet;

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
	 * A {@link Query}
	 */
	private Query<User> query;

	/**
	 * {@link LikeFilter}
	 */
	private Filter filter;

	/**
	 * Set up the context
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		filter = new LikeFilter("property", "value");
		query = new Query<User>(User.class);

		dataSet.setup();
	}

	/**
	 * Clear the context
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		filter = null;
		query = null;

		dataSet.clear();
	}

	/**
	 * Test the constructor
	 */
	@Test
	public void testLikeFilter() {
		assertNotNull(filter);
	}

	/**
	 * Test the getHibernateCriterion() method
	 */
	@Test
	public void testGetHibernateCriterion() {
		assertEquals("property like %value%", filter.getHibernateCriterion().toString());
	}

	/**
	 * Test an execution of a {@link Query} with a {@link LikeFilter}. The
	 * following conditions are checked :<br/>
	 * <ul>
	 * <li>The list contains 2 users</li>
	 * <li>The list contains the user "U2"</li>
	 * <li>The list contains the user "U3"</li>
	 * </ul>
	 */
	@Test
	public void testLikeFilterQuery() {
		query.addFilter(new LikeFilter("login", "user"));
		List<User> users = processor.execute(query);

		assertEquals(3, users.size());
		assertTrue(users.contains(userDAO.findByLogin("user1")));
		assertTrue(users.contains(userDAO.findByLogin("user2")));
		assertTrue(users.contains(userDAO.findByLogin("user3")));
	}

	/**
	 * Test if an {@link IllegalArgumentException} is thrown by passing a
	 * <code>null</code> property argument to {@link LikeFilter}
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testLikeFilterWithNullProperty() {
		query.addFilter(new LikeFilter(null, ""));
		processor.execute(query);
	}

	/**
	 * Test if an {@link IllegalArgumentException} is thrown by passing a
	 * <code>null</code> value argument to {@link LikeFilter}
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testLikeFilterWithNullValue() {
		query.addFilter(new LessOrEqualFilter("login", null));
		processor.execute(query);
	}
}
