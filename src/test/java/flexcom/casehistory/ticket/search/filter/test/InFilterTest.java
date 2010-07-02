package flexcom.casehistory.ticket.search.filter.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
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
import flexcom.casehistory.ticket.search.filter.InFilter;

/**
 * Test class for {@link InFilter}
 * 
 * @author philippe
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class InFilterTest {

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
	 * {@link InFilter} 1
	 */
	private Filter filter1;

	/**
	 * {@link InFilter} 2
	 */
	private Filter filter2;

	/**
	 * Set up the context
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		Object[] array = { 2, 3, 5, 7 };
		Collection<Object> collection = Arrays.asList(array);
		filter1 = new InFilter("accessLevel", array);
		filter2 = new InFilter("accessLevel", collection);
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
		filter1 = null;
		filter2 = null;
		query = null;

		dataSet.clear();
	}

	/**
	 * Test the constructor with a collection of values
	 */
	@Test
	public void testInFilterStringCollectionOfObject() {
		assertNotNull(filter1);
	}

	/**
	 * Test the constructor with an array of values
	 */
	@Test
	public void testInFilterStringObjectArray() {
		assertNotNull(filter2);
	}

	/**
	 * Test the getHibernateCriterion() method
	 */
	@Test
	public void testGetHibernateCriterion() {
		assertEquals("accessLevel in (2, 3, 5, 7)", filter1.getHibernateCriterion().toString());
		assertEquals("accessLevel in (2, 3, 5, 7)", filter2.getHibernateCriterion().toString());
	}

	/**
	 * Test the getHibernateCriterion() method with an empty collection
	 */
	@Test
	public void testGetHibernateCriterionWithEmptyCollection() {
		Filter f = new InFilter("accessLevel", Collections.emptyList());
		assertEquals("id = null", f.getHibernateCriterion().toString());
	}

	/**
	 * Test an execution of a {@link Query} with a {@link InFilter}. The
	 * following conditions are checked :<br/>
	 * <ul>
	 * <li>The list contains 2 users</li>
	 * <li>The list contains the user "U2"</li>
	 * <li>The list contains the user "U3"</li>
	 * </ul>
	 */
	@Test
	public void testInFilterQuery() {
		query.addFilter(filter1);
		List<User> users = processor.execute(query);

		assertEquals(2, users.size());
		assertTrue(users.contains(userDAO.findByLogin("user2")));
		assertTrue(users.contains(userDAO.findByLogin("user3")));
	}

	/**
	 * Test if an {@link IllegalArgumentException} is thrown by passing a
	 * <code>null</code> property argument to {@link InFilter}
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInFilterWithNullProperty() {
		query.addFilter(new InFilter(null, new Object[] {}));
		processor.execute(query);
	}

	/**
	 * Test if an {@link ClassCastException} is thrown by passing an array
	 * containing invalid values to {@link InFilter}
	 */
	@Test(expected = ClassCastException.class)
	public void testInFilterWithInvalidArgument() {
		query.addFilter(new InFilter("accessLevel", new Object[] { "", new Object(), null }));
		processor.execute(query);
	}

	/**
	 * Test if an {@link IllegalArgumentException} is thrown by passing a
	 * <code>null</code> value argument to {@link InFilter}
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInFilterWithNullArgument() {
		Object[] objects = null;
		query.addFilter(new InFilter("accessLevel", objects));
		processor.execute(query);
	}

	/**
	 * Test if the result list is empty when we use an empty collection
	 */
	@Test
	public void testInFilterWithoutArgument() {
		query.addFilter(new InFilter("accessLevel", Collections.emptyList()));
		List<User> users = processor.execute(query);

		assertEquals(0, users.size());
	}
}
