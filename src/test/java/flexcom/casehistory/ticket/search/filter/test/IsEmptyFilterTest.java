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

import flexcom.casehistory.data.Data;
import flexcom.casehistory.data.TicketDataSet;
import flexcom.casehistory.data.UserDataSet;
import flexcom.casehistory.ticket.dao.UserDAO;
import flexcom.casehistory.ticket.entity.User;
import flexcom.casehistory.ticket.search.Query;
import flexcom.casehistory.ticket.search.QueryProcessor;
import flexcom.casehistory.ticket.search.filter.Filter;
import flexcom.casehistory.ticket.search.filter.IsEmptyFilter;
import flexcom.casehistory.ticket.search.filter.IsNotEmptyFilter;

/**
 * Test class for {@link IsEmptyFilter}
 * 
 * @author philippe
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class IsEmptyFilterTest {

	/**
	 * Data set used for setting up the context
	 */
	@Autowired
	private TicketDataSet ticketDataSet;

	@Autowired
	private UserDataSet userDataSet;

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
	 * {@link IsEmptyFilter}
	 */
	private Filter filter;

	/**
	 * Set up the context
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		filter = new IsEmptyFilter("list");
		query = new Query<User>(User.class);

		ticketDataSet.setup();
		userDataSet.setup();
		
		Data.USER_1.assign(Data.TICKET_1);
		Data.USER_3.assign(Data.TICKET_1);
		userDAO.updateUser(Data.USER_1);
		userDAO.updateUser(Data.USER_3);
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
		userDataSet.clear();
		ticketDataSet.clear();
	}

	/**
	 * Test the constructor
	 */
	@Test
	public void testIsEmptyFilter() {
		assertNotNull(filter);
	}

	/**
	 * Test the getHibernateCriterion() method
	 */
	@Test
	public void testGetHibernateCriterion() {
		assertEquals("list is empty", filter.getHibernateCriterion().toString());
	}

	/**
	 * Test an execution of a {@link Query} with a {@link IsEmptyFilter}. The
	 * following conditions are checked :<br/>
	 * <ul>
	 * <li>The list contains 1 user</li>
	 * <li>The list contains the user "U2"</li>
	 * </ul>
	 */
	@Test
	public void testIsEmptyFilterQuery() {
		query.addFilter(new IsEmptyFilter("assignedTickets"));
		List<User> users = processor.execute(query);

		assertEquals(1, users.size());
		assertTrue(users.contains(userDAO.findByLogin("user2")));
	}

	/**
	 * Test if an {@link IllegalArgumentException} is thrown by passing a
	 * <code>null</code> property argument to {@link IsEmptyFilter}
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testIsEmptyFilterWithNullProperty() {
		query.addFilter(new IsEmptyFilter(null));
		processor.execute(query);
	}

	/**
	 * Test the inverse of IS EMPTY
	 */
	@Test
	public void testInverseOfIsEmpty() {
		query.addFilter(new IsEmptyFilter("assignedTickets"));
		List<User> users = processor.execute(query);

		Query<User> refQuery = new Query<User>(User.class);
		refQuery.addFilter(new IsNotEmptyFilter("assignedTickets"));
		List<User> usersNE = processor.execute(refQuery);
		List<User> ref = userDAO.findAll();
		ref.removeAll(usersNE);

		assertEquals(ref.size(), users.size());
		assertTrue(users.containsAll(ref));
	}
}
