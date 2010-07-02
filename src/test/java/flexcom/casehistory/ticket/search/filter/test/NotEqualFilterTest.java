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
import flexcom.casehistory.ticket.search.filter.EqualFilter;
import flexcom.casehistory.ticket.search.filter.Filter;
import flexcom.casehistory.ticket.search.filter.NotEqualFilter;

/**
 * Test class for {@link NotEqualFilter}
 * 
 * @author philippe
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class NotEqualFilterTest {

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
	 * {@link NotEqualFilter}
	 */
	private Filter filter;

	/**
	 * Set up the context
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		filter = new NotEqualFilter("value", 10);
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
	public void testNotEqualFilter() {
		assertNotNull(filter);
	}

	/**
	 * Test the getHibernateCriterion() method
	 */
	@Test
	public void testGetHibernateCriterion() {
		assertEquals("value<>10", filter.getHibernateCriterion().toString());
	}

	/**
	 * Test an execution of a {@link Query} with a {@link NotEqualFilter}. The
	 * following conditions are checked :<br/>
	 * <ul>
	 * <li>The list contains 2 users</li>
	 * <li>The list contains the user "U1"</li>
	 * <li>The list contains the user "U3"</li>
	 * </ul>
	 */
	@Test
	public void testNotEqualFilterQuery(){
		query.addFilter(new NotEqualFilter("accessLevel", 5));
		List<User> users = processor.execute(query);
		
		assertEquals(2, users.size());
		assertTrue(users.contains(userDAO.findByLogin("user1")));
		assertTrue(users.contains(userDAO.findByLogin("user3")));
	}
	
	/**
	 * Test an execution of a {@link Query} with a {@link NotEqualFilter} for a {@link String} property. The
	 * following conditions are checked :<br/>
	 * <ul>
	 * <li>The list contains 2 users</li>
	 * <li>The list contains the user "U1"</li>
	 * <li>The list contains the user "U3"</li>
	 * </ul>
	 */
	@Test
	public void testNotEqualFilterWithStringProperty(){
		query.addFilter(new NotEqualFilter("name", "U2"));
		List<User> users = processor.execute(query);
		
		assertEquals(2, users.size());
		assertTrue(users.contains(userDAO.findByLogin("user1")));
		assertTrue(users.contains(userDAO.findByLogin("user3")));
	}
	
	/**
	 * Test if an {@link ClassCastException} is thrown by passing
	 * an invalid value argument to {@link NotEqualFilter}
	 */
	@Test(expected = ClassCastException.class)
	public void testNotEqualFilterWithInvalidArguments(){
		query.addFilter(new NotEqualFilter("accessLevel", "five"));
		processor.execute(query);
	}
	
	/**
	 * Test if an {@link IllegalArgumentException} is thrown by passing
	 * a <code>null</code> property argument to {@link NotEqualFilter}
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testNotEqualFilterWithNullProperty(){
		query.addFilter(new NotEqualFilter(null, 5));
		processor.execute(query);
	}
	
	/**
	 * Test an execution of a {@link Query} with a {@link NotEqualFilter} with a <code>null</code> value. The
	 * following conditions are checked :<br/>
	 * <ul>
	 * <li>The list contains 2 users</li>
	 * <li>The list contains the user "U1"</li>
	 * <li>The list contains the user "U2"</li>
	 * </ul>
	 */
	@Test
	public void testNotEqualFilterWithNullValue(){
		query.addFilter(new NotEqualFilter("title", null));
		List<User> users = processor.execute(query);
		
		assertEquals(2, users.size());
		assertTrue(users.contains(userDAO.findByLogin("user1")));
		assertTrue(users.contains(userDAO.findByLogin("user2")));
	}
	
	/**
	 * Test the inverse of NOT EQUAL
	 */
	@Test
	public void testInverseOfNE(){
		query.addFilter(new NotEqualFilter("accessLevel", 5));
		List<User> users = processor.execute(query);
		
		Query<User> refQuery = new Query<User>(User.class);
		refQuery.addFilter(new EqualFilter("accessLevel", 5));
		List<User> usersE5 = processor.execute(refQuery);
		List<User> ref = userDAO.findAll();
		ref.removeAll(usersE5);
		
		assertEquals(ref.size(), users.size());
		assertTrue(users.containsAll(ref));
	}
}
