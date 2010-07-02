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
import flexcom.casehistory.ticket.entity.Priority;
import flexcom.casehistory.ticket.entity.Status;
import flexcom.casehistory.ticket.entity.Ticket;
import flexcom.casehistory.ticket.search.Query;
import flexcom.casehistory.ticket.search.QueryProcessor;
import flexcom.casehistory.ticket.search.filter.AndFilter;
import flexcom.casehistory.ticket.search.filter.EqualFilter;
import flexcom.casehistory.ticket.search.filter.Filter;
import flexcom.casehistory.ticket.search.filter.NotFilter;
import flexcom.casehistory.ticket.search.filter.OrFilter;
import flexcom.casehistory.ticket.search.filter.XorFilter;

/**
 * Test class for {@link XorFilter}
 * 
 * @author philippe
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class XorFilterTest {

	/**
	 * First filter for XOR operator
	 */
	private Filter firstFilter;

	/**
	 * Second filter for XOR operator
	 */
	private Filter secondFilter;

	/**
	 * {@link XorFilter}
	 */
	private Filter xorFilter;

	/**
	 * Data set used for setting up the context
	 */
	@Autowired
	private TicketDataSet dataSet;

	/**
	 * {@link QueryProcessor}
	 */
	@Autowired
	private QueryProcessor<Ticket> processor;

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
	 * A {@link Query}
	 */
	private Query<Ticket> query;

	/**
	 * Set up the context
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		firstFilter = new EqualFilter("lightOn", true);
		secondFilter = new EqualFilter("lightOff", true);
		xorFilter = new XorFilter(firstFilter, secondFilter);

		dataSet.setup();

		query = new Query<Ticket>(Ticket.class);
	}

	/**
	 * Clear the context
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		dataSet.clear();
		firstFilter = null;
		secondFilter = null;
		xorFilter = null;
		query = null;
	}

	/**
	 * Test the constructor
	 */
	@Test
	public void testXorFilter() {
		assertNotNull(xorFilter);
	}

	/**
	 * Test the getHibernateCriterion() method
	 */
	@Test
	public void testGetHibernateCriterion() {
		assertEquals("lightOn=true and not lightOff=true or not lightOn=true and lightOff=true", xorFilter.getHibernateCriterion().toString());
	}

	/**
	 * Test an execution of a {@link Query} with a {@link XorFilter}. The
	 * following conditions are checked :<br/>
	 * <ul>
	 * <li>The list contains 3 tickets</li>
	 * <li>The list contains the ticket "T1"</li>
	 * <li>The list contains the ticket "T3"</li>
	 * <li>The list contains the ticket "T4"</li>
	 * </ul>
	 */
	@Test
	public void testXorFilterQuery() {
		Filter left = new EqualFilter("status", statusDAO.findByName("New"));
		Filter right = new EqualFilter("priority", priorityDAO.findByName("MEDIUM"));
		query.addFilter(new XorFilter(left, right));
		List<Ticket> tickets = processor.execute(query);

		assertEquals(3, tickets.size());
		assertTrue(tickets.contains(ticketDAO.findByTitle("T1").get(0)));
		assertTrue(tickets.contains(ticketDAO.findByTitle("T3").get(0)));
		assertTrue(tickets.contains(ticketDAO.findByTitle("T4").get(0)));
	}

	/**
	 * Test if an {@link IllegalArgumentException} is thrown by passing
	 * <code>null</code> arguments to {@link XorFilter}
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testXorFilterWithNullArgument() {
		query.addFilter(new XorFilter(null, null));
		processor.execute(query);
	}

	/**
	 * Test if the {@link XorFilter} is correct
	 */
	@Test
	public void testXor() {
		Filter left = new EqualFilter("status", statusDAO.findByName("New"));
		Filter right = new EqualFilter("priority", priorityDAO.findByName("MEDIUM"));
		query.addFilter(new XorFilter(left, right));
		List<Ticket> tickets = processor.execute(query);

		Query<Ticket> refQuery = new Query<Ticket>(Ticket.class);
		Priority prio = priorityDAO.findByName("MEDIUM");
		Status status = statusDAO.findByName("New");
		Filter lFilter = new AndFilter(new NotFilter(new EqualFilter("status", status)), new EqualFilter("priority",prio));
		Filter rFilter = new AndFilter(new EqualFilter("status", status), new NotFilter(new EqualFilter("priority", prio)));
		refQuery.addFilter(new OrFilter(lFilter, rFilter));
		List<Ticket> ref = processor.execute(query);

		assertEquals(ref.size(), tickets.size());
		assertTrue(tickets.containsAll(ref));
	}
}
