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
 * Test class for {@link EqualFilter}
 * 
 * @author philippe
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class EqualFilterTest {

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
	 * {@link EqualFilter}
	 */
	private Filter filter;

	/**
	 * Set up the context
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		filter = new EqualFilter("value", 10);
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
	public void testEqualFilter() {
		assertNotNull(filter);
	}

	/**
	 * Test the getHibernateCriterion() method
	 */
	@Test
	public void testGetHibernateCriterion() {
		assertEquals("value=10", filter.getHibernateCriterion().toString());
	}

	/**
	 * Test an execution of a {@link Query} with a {@link EqualFilter}. The
	 * following conditions are checked :<br/>
	 * <ul>
	 * <li>The list contains 1 user</li>
	 * <li>The list contains the user "U2"</li>
	 * </ul>
	 */
	@Test
	public void testEqualFilterQuery(){
		query.addFilter(new EqualFilter("accessLevel", 5));
		List<User> users = processor.execute(query);
		
		assertEquals(1, users.size());
		assertTrue(users.contains(userDAO.findByLogin("user2")));
	}
	
	/**
	 * Test an execution of a {@link Query} with a {@link EqualFilter} for a {@link String} property. The
	 * following conditions are checked :<br/>
	 * <ul>
	 * <li>The list contains 1 user</li>
	 * <li>The list contains the user "U1"</li>
	 * </ul>
	 */
	@Test
	public void testEqualFilterWithStringProperty(){
		query.addFilter(new EqualFilter("title", "Manager"));
		List<User> users = processor.execute(query);
		
		assertEquals(1, users.size());
		assertTrue(users.contains(userDAO.findByLogin("user1")));
	}
	
	/**
	 * Test if an {@link ClassCastException} is thrown by passing
	 * an invalid value argument to {@link EqualFilter}
	 */
	@Test(expected = ClassCastException.class)
	public void testEqualFilterWithInvalidArguments(){
		query.addFilter(new EqualFilter("accessLevel", "five"));
		processor.execute(query);
	}
	
	/**
	 * Test if an {@link IllegalArgumentException} is thrown by passing
	 * a <code>null</code> property argument to {@link EqualFilter}
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testEqualFilterWithNullProperty(){
		query.addFilter(new EqualFilter(null, 5));
		processor.execute(query);
	}
	
	/**
	 * Test an execution of a {@link Query} with a {@link EqualFilter} with a <code>null</code> value. The
	 * following conditions are checked :<br/>
	 * <ul>
	 * <li>The list contains 1 user</li>
	 * <li>The list contains the user "U3"</li>
	 * </ul>
	 */
	@Test
	public void testEqualFilterWithNullValue(){
		query.addFilter(new EqualFilter("title", null));
		List<User> users = processor.execute(query);
		
		assertEquals(1, users.size());
		assertTrue(users.contains(userDAO.findByLogin("user3")));
	}
	
	/**
	 * Test the inverse of EQUAL
	 */
	@Test
	public void testInverseOfEqual(){
		query.addFilter(new EqualFilter("accessLevel", 5));
		List<User> users = processor.execute(query);
		
		Query<User> refQuery = new Query<User>(User.class);
		refQuery.addFilter(new NotEqualFilter("accessLevel", 5));
		List<User> usersNE5 = processor.execute(refQuery);
		List<User> ref = userDAO.findAll();
		ref.removeAll(usersNE5);
		
		assertEquals(ref.size(), users.size());
		assertTrue(users.containsAll(ref));
	}
}
