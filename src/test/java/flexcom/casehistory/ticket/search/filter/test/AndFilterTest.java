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

import flexcom.casehistory.data.TicketDataSet;
import flexcom.casehistory.ticket.dao.PriorityDAO;
import flexcom.casehistory.ticket.dao.StatusDAO;
import flexcom.casehistory.ticket.dao.TicketDAO;
import flexcom.casehistory.ticket.entity.Ticket;
import flexcom.casehistory.ticket.search.Query;
import flexcom.casehistory.ticket.search.QueryProcessor;
import flexcom.casehistory.ticket.search.filter.AndFilter;
import flexcom.casehistory.ticket.search.filter.EqualFilter;
import flexcom.casehistory.ticket.search.filter.Filter;

/**
 * Test class for {@link AndFilter}
 * 
 * @author philippe
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class AndFilterTest {

	/**
	 * Data set used for setting up the context
	 */
	@Autowired
	private TicketDataSet dataSet;

	/**
	 * Ticket DAO
	 */
	@Autowired
	private TicketDAO ticketDAO;

	/**
	 * Priority DAO
	 */
	@Autowired
	private PriorityDAO priorityDAO;

	/**
	 * Status DAO
	 */
	@Autowired
	private StatusDAO statusDAO;

	/**
	 * {@link QueryProcessor}
	 */
	@Autowired
	private QueryProcessor<Ticket> processor;

	/**
	 * A {@link Query}
	 */
	private Query<Ticket> query;

	/**
	 * First filter for AND operator
	 */
	private Filter firstFilter;

	/**
	 * Second filter for AND operator
	 */
	private Filter secondFilter;

	/**
	 * {@link AndFilter}
	 */
	private Filter andFilter;

	/**
	 * Set up the context
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		firstFilter = new EqualFilter("age", 18);
		secondFilter = new EqualFilter("height", 170);
		andFilter = new AndFilter(firstFilter, secondFilter);

		query = new Query<Ticket>(Ticket.class);

		dataSet.setup();
	}

	/**
	 * Clear the context
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		firstFilter = null;
		secondFilter = null;
		andFilter = null;
		query = null;

		dataSet.clear();
	}

	/**
	 * Test the constructor
	 */
	@Test
	public void testAndFilter() {
		assertNotNull(andFilter);
	}

	/**
	 * Test the getHibernateCriterion() method
	 */
	@Test
	public void testGetHibernateCriterion() {
		assertEquals("age=18 and height=170", andFilter.getHibernateCriterion().toString());
	}

	/**
	 * Test an execution of a {@link Query} with a {@link AndFilter}. The
	 * following conditions are checked :<br/>
	 * <ul>
	 * <li>The list contains 1 ticket</li>
	 * <li>The list contains the ticket "T2"</li>
	 * </ul>
	 */
	@Test
	public void testAndFilterQuery() {
		Filter left = new EqualFilter("status", statusDAO.findByName("New"));
		Filter right = new EqualFilter("priority", priorityDAO.findByName("MEDIUM"));
		query.addFilter(new AndFilter(left, right));
		List<Ticket> tickets = processor.execute(query);

		assertEquals(1, tickets.size());
		assertTrue(tickets.contains(ticketDAO.findByTitle("T2").get(0)));
	}

	/**
	 * Test if an {@link IllegalArgumentException} is thrown by passing
	 * <code>null</code> arguments to {@link AndFilter}
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAndFilterWithNullArgument() {
		query.addFilter(new AndFilter(null, null));
		processor.execute(query);
	}

	/**
	 * Test if the {@link AndFilter} is correct
	 */
	@Test
	public void testAnd() {
		Filter left = new EqualFilter("status", statusDAO.findByName("New"));
		Filter right = new EqualFilter("priority", priorityDAO.findByName("MEDIUM"));
		query.addFilter(new AndFilter(left, right));
		List<Ticket> tickets = processor.execute(query);

		Query<Ticket> refQuery = new Query<Ticket>(Ticket.class);
		refQuery.addFilter(left);
		refQuery.addFilter(right);
		List<Ticket> ref = processor.execute(refQuery);

		assertEquals(ref.size(), tickets.size());
		assertTrue(tickets.containsAll(ref));
	}

}
