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
import flexcom.casehistory.ticket.search.filter.GreaterOrEqualFilter;
import flexcom.casehistory.ticket.search.filter.LessThanFilter;

/**
 * Test class for {@link LessThanFilter}
 * 
 * @author philippe
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class LessThanFilterTest {

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
	 * {@link LessThanFilter}
	 */
	private Filter filter;

	/**
	 * Set up the context
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		filter = new LessThanFilter("value", 10);
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
	public void testLessThanFilter() {
		assertNotNull(filter);
	}

	/**
	 * Test the getHibernateCriterion() method
	 */
	@Test
	public void testGetHibernateCriterion() {
		assertEquals("value<10", filter.getHibernateCriterion().toString());
	}

	/**
	 * Test an execution of a {@link Query} with a {@link LessThanFilter}. The
	 * following conditions are checked :<br/>
	 * <ul>
	 * <li>The list contains 1 user</li>
	 * <li>The list contains the user "U3"</li>
	 * </ul>
	 */
	@Test
	public void testLessThanFilterQuery() {
		query.addFilter(new LessThanFilter("accessLevel", 5));
		List<User> users = processor.execute(query);

		assertEquals(1, users.size());
		assertTrue(users.contains(userDAO.findByLogin("user3")));
	}

	/**
	 * Test if an {@link ClassCastException} is thrown by passing an invalid
	 * value argument to {@link LessThanFilter}
	 */
	@Test(expected = ClassCastException.class)
	public void testLessThanFilterWithInvalidArguments() {
		query.addFilter(new LessThanFilter("accessLevel", "five"));
		processor.execute(query);
	}

	/**
	 * Test if an {@link IllegalArgumentException} is thrown by passing a
	 * <code>null</code> property argument to {@link LessThanFilter}
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testLessThanFilterWithNullProperty() {
		query.addFilter(new LessThanFilter(null, 5));
		processor.execute(query);
	}

	/**
	 * Test if an {@link IllegalArgumentException} is thrown by passing a
	 * <code>null</code> value argument to {@link LessThanFilter}
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testLessThanFilterWithNullValue() {
		query.addFilter(new LessThanFilter("accessLevel", null));
		processor.execute(query);
	}

	/**
	 * Test the inverse of LESS THAN
	 */
	@Test
	public void testInverseOfLT() {
		query.addFilter(new LessThanFilter("accessLevel", 5));
		List<User> users = processor.execute(query);

		Query<User> refQuery = new Query<User>(User.class);
		refQuery.addFilter(new GreaterOrEqualFilter("accessLevel", 5));
		List<User> usersGE5 = processor.execute(refQuery);
		List<User> ref = userDAO.findAll();
		ref.removeAll(usersGE5);

		assertEquals(ref.size(), users.size());
		assertTrue(users.containsAll(ref));
	}
}
