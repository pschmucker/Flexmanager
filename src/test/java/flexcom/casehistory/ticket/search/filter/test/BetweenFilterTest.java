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
import flexcom.casehistory.ticket.search.filter.BetweenFilter;
import flexcom.casehistory.ticket.search.filter.Filter;
import flexcom.casehistory.ticket.search.filter.GreaterThanFilter;
import flexcom.casehistory.ticket.search.filter.LessThanFilter;
import flexcom.casehistory.ticket.search.filter.OrFilter;

/**
 * Test class for {@link BetweenFilter}
 * 
 * @author philippe
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class BetweenFilterTest {

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
	 * {@link BetweenFilter}
	 */
	private Filter filter;

	/**
	 * Set up the context
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		filter = new BetweenFilter("value", 10, 20);
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
	public void testBetweenFilter() {
		assertNotNull(filter);
	}

	/**
	 * Test the getHibernateCriterion() method
	 */
	@Test
	public void testGetHibernateCriterion() {
		assertEquals("value between 10 and 20", filter.getHibernateCriterion().toString());
	}

	/**
	 * Test an execution of a {@link Query} with a {@link BetweenFilter}. The
	 * following conditions are checked :<br/>
	 * <ul>
	 * <li>The list contains 2 users</li>
	 * <li>The list contains the user "U2"</li>
	 * <li>The list contains the user "U3"</li>
	 * </ul>
	 */
	@Test
	public void testBetweenFilterQuery(){
		query.addFilter(new BetweenFilter("accessLevel", 3, 6));
		List<User> users = processor.execute(query);
		
		assertEquals(2, users.size());
		assertTrue(users.contains(userDAO.findByLogin("user2")));
		assertTrue(users.contains(userDAO.findByLogin("user3")));
	}
	
	/**
	 * Test if an {@link ClassCastException} is thrown by passing invalid
	 * value arguments to {@link BetweenFilter}
	 */
	@Test(expected = ClassCastException.class)
	public void testBetweenFilterWithInvalidArguments(){
		query.addFilter(new BetweenFilter("accessLevel", "three", "five"));
		processor.execute(query);
	}
	
	/**
	 * Test if an {@link IllegalArgumentException} is thrown by passing a
	 * <code>null</code> property argument to {@link BetweenFilter}
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testBetweenFilterWithNullProperty(){
		query.addFilter(new BetweenFilter(null, 3, 5));
		processor.execute(query);
	}
	
	/**
	 * Test if an {@link IllegalArgumentException} is thrown by passing a
	 * <code>null</code> value argument to {@link BetweenFilter}
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testBetweenFilterWithNullValue(){
		query.addFilter(new BetweenFilter("accessLevel", 4, null));
		processor.execute(query);
	}
	
	/**
	 * Test the inverse of BETWEEN
	 */
	@Test
	public void testInverseOfBetween(){
		query.addFilter(new BetweenFilter("accessLevel", 3, 6));
		List<User> users = processor.execute(query);
		
		Query<User> refQuery = new Query<User>(User.class);
		refQuery.addFilter(new OrFilter(new LessThanFilter("accessLevel", 3), new GreaterThanFilter("accessLevel", 6)));
		List<User> usersNB3and6 = processor.execute(refQuery);
		List<User> ref = userDAO.findAll();
		ref.removeAll(usersNB3and6);
		
		assertEquals(ref.size(), users.size());
		assertTrue(users.containsAll(ref));
	}
}
