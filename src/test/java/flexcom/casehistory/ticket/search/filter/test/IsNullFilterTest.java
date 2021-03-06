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
import org.springframework.orm.hibernate3.HibernateQueryException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import flexcom.casehistory.data.UserDataSet;
import flexcom.casehistory.ticket.dao.UserDAO;
import flexcom.casehistory.ticket.entity.User;
import flexcom.casehistory.ticket.search.Query;
import flexcom.casehistory.ticket.search.QueryProcessor;
import flexcom.casehistory.ticket.search.filter.Filter;
import flexcom.casehistory.ticket.search.filter.IsNotNullFilter;
import flexcom.casehistory.ticket.search.filter.IsNullFilter;

/**
 * Test class for {@link IsNullFilter}
 * 
 * @author philippe
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class IsNullFilterTest {

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
	 * {@link IsNullFilter}
	 */
	private Filter filter;

	/**
	 * Set up the context
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		filter = new IsNullFilter("property");
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
	public void testIsNullFilter() {
		assertNotNull(filter);
	}

	/**
	 * Test the getHibernateCriterion() method
	 */
	@Test
	public void testGetHibernateCriterion() {
		assertEquals("property is null", filter.getHibernateCriterion().toString());
	}

	/**
	 * Test an execution of a {@link Query} with a {@link IsNullFilter}. The
	 * following conditions are checked :<br/>
	 * <ul>
	 * <li>The list contains 1 user</li>
	 * <li>The list contains the user "U3"</li>
	 * </ul>
	 */
	@Test
	public void testIsNullFilterQuery() {
		query.addFilter(new IsNullFilter("title"));
		List<User> users = processor.execute(query);

		assertEquals(1, users.size());
		assertTrue(users.contains(userDAO.findByLogin("user3")));
	}

	/**
	 * Test if an {@link ClassCastException} is thrown by passing an inexistant
	 * property argument to {@link IsNullFilter}
	 */
	@Test(expected = HibernateQueryException.class)
	public void testIsNullFilterWithInexistantProperty() {
		query.addFilter(new IsNullFilter("test"));
		processor.execute(query);
	}

	/**
	 * Test if an {@link IllegalArgumentException} is thrown by passing a
	 * <code>null</code> property argument to {@link IsNullFilter}
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testIsNullFilterWithNullProperty() {
		query.addFilter(new IsNullFilter(null));
		processor.execute(query);
	}

	/**
	 * Test the inverse of IS NULL
	 */
	@Test
	public void testInverseOfIsNull() {
		query.addFilter(new IsNullFilter("title"));
		List<User> users = processor.execute(query);

		Query<User> refQuery = new Query<User>(User.class);
		refQuery.addFilter(new IsNotNullFilter("title"));
		List<User> usersNN = processor.execute(refQuery);
		List<User> ref = userDAO.findAll();
		ref.removeAll(usersNN);

		assertEquals(ref.size(), users.size());
		assertTrue(users.containsAll(ref));
	}
}
