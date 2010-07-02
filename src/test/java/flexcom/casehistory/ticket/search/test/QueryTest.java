package flexcom.casehistory.ticket.search.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import flexcom.casehistory.ticket.entity.User;
import flexcom.casehistory.ticket.search.Query;
import flexcom.casehistory.ticket.search.filter.Filter;
import flexcom.casehistory.ticket.search.filter.IsEmptyFilter;
import flexcom.casehistory.ticket.search.filter.IsNullFilter;

/**
 * Test class for the {@link Query} class.
 * 
 * @author philippe
 * 
 */
public class QueryTest {

	/**
	 * A {@link Query} for users
	 */
	private Query<User> query;

	/**
	 * Set up the context
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		query = new Query<User>(User.class);
	}

	/**
	 * Clear the context
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		query = null;
	}

	/**
	 * Test the constructor
	 */
	@Test
	public void testQuery() {
		assertNotNull(query);
	}

	/**
	 * Test if an {@link IllegalArgumentException} is thrown when we create a
	 * {@link Query} with <code>null</code> argument
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testQueryWithNullType() {
		query = new Query<User>(null);
	}

	/**
	 * Test the addFilter({@link Filter}) method
	 */
	@Test
	public void testAddFilter() {
		query.addFilter(new IsNullFilter("object"));
		assertEquals(1, query.getFilters().size());
	}

	/**
	 * Test if no filter was added when we add a <code>null</code>
	 * {@link Filter}
	 */
	@Test
	public void testAddNullFilter() {
		query.addFilter(null);
		assertEquals(0, query.getFilters().size());
	}

	/**
	 * Test the getType() method
	 */
	@Test
	public void testGetType() {
		assertEquals(User.class, query.getType());
	}

	/**
	 * Test the getFilters() method
	 */
	@Test
	public void testGetFilters() {
		Filter[] filters = { new IsNullFilter("object"), new IsEmptyFilter("list") };
		for (Filter filter : filters) {
			query.addFilter(filter);
		}
		assertArrayEquals(filters, query.getFilters().toArray());
	}

}
