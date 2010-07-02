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
 * Test class for {@link IsNotNullFilter}
 * 
 * @author philippe
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class IsNotNullFilterTest {

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
	 * {@link IsNotNullFilter}
	 */
	private Filter filter;

	/**
	 * Set up the context
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		filter = new IsNotNullFilter("property");
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
	public void testIsNotNullFilter() {
		assertNotNull(filter);
	}

	/**
	 * Test the getHibernateCriterion() method
	 */
	@Test
	public void testGetHibernateCriterion() {
		assertEquals("property is not null", filter.getHibernateCriterion().toString());
	}

	/**
	 * Test an execution of a {@link Query} with a {@link IsNotNullFilter}. The
	 * following conditions are checked :<br/>
	 * <ul>
	 * <li>The list contains 2 users</li>
	 * <li>The list contains the user "U1"</li>
	 * <li>The list contains the user "U2"</li>
	 * </ul>
	 */
	@Test
	public void testIsNotNullFilterQuery(){
		query.addFilter(new IsNotNullFilter("title"));
		List<User> users = processor.execute(query);
		
		assertEquals(2, users.size());
		assertTrue(users.contains(userDAO.findByLogin("user1")));
		assertTrue(users.contains(userDAO.findByLogin("user2")));
	}
	
	/**
	 * Test if an {@link ClassCastException} is thrown by passing an inexistant
	 * property argument to {@link IsNotNullFilter}
	 */
	@Test(expected = HibernateQueryException.class)
	public void testIsNotNullFilterWithInexistantProperty(){
		query.addFilter(new IsNullFilter("test"));
		processor.execute(query);
	}
	
	/**
	 * Test if an {@link IllegalArgumentException} is thrown by passing a
	 * <code>null</code> property argument to {@link IsNotNullFilter}
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testIsNotNullFilterWithNullProperty(){
		query.addFilter(new IsNotNullFilter(null));
		processor.execute(query);
	}
	
	/**
	 * Test the inverse of IS NOT NULL
	 */
	@Test
	public void testInverseOfIsNotNull(){
		query.addFilter(new IsNotNullFilter("title"));
		List<User> users = processor.execute(query);
		
		Query<User> refQuery = new Query<User>(User.class);
		refQuery.addFilter(new IsNullFilter("title"));
		List<User> usersN = processor.execute(refQuery);
		List<User> ref = userDAO.findAll();
		ref.removeAll(usersN);
		
		assertEquals(ref.size(), users.size());
		assertTrue(users.containsAll(ref));
	}
}
