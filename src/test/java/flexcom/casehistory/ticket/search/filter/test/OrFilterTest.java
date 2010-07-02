package flexcom.casehistory.ticket.search.filter.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import flexcom.casehistory.ticket.search.filter.EqualFilter;
import flexcom.casehistory.ticket.search.filter.Filter;
import flexcom.casehistory.ticket.search.filter.GreaterThanFilter;
import flexcom.casehistory.ticket.search.filter.LessThanFilter;
import flexcom.casehistory.ticket.search.filter.OrFilter;

/**
 * Test class for {@link OrFilter}
 * 
 * @author philippe
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class OrFilterTest {

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
	 * First filter for OR operator
	 */
	private Filter firstFilter;

	/**
	 * Second filter for OR operator
	 */
	private Filter secondFilter;

	/**
	 * {@link OrFilter}
	 */
	private Filter orFilter;

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
		query = new Query<Ticket>(Ticket.class);

		firstFilter = new LessThanFilter("value", 5);
		secondFilter = new GreaterThanFilter("value", 15);
		orFilter = new OrFilter(firstFilter, secondFilter);

		dataSet.setup();
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
		orFilter = null;
		query = null;
	}

	/**
	 * Test the constructor
	 */
	@Test
	public void testOrFilter() {
		assertNotNull(orFilter);
	}

	/**
	 * Test the getHibernateCriterion() method
	 */
	@Test
	public void testGetHibernateCriterion() {
		assertEquals("value<5 or value>15", orFilter.getHibernateCriterion().toString());
	}

	/**
	 * Test an execution of a {@link Query} with a {@link OrFilter}. The
	 * following conditions are checked :<br/>
	 * <ul>
	 * <li>The list contains 3 tickets</li>
	 * <li>The list contains the ticket "T2"</li>
	 * <li>The list contains the ticket "T3"</li>
	 * <li>The list contains the ticket "T5"</li>
	 * </ul>
	 */
	@Test
	public void testOrFilterQuery() {
		Filter left = new EqualFilter("status", statusDAO.findByName("Assigned"));
		Filter right = new EqualFilter("priority", priorityDAO.findByName("MEDIUM"));
		query.addFilter(new OrFilter(left, right));
		List<Ticket> tickets = processor.execute(query);

		assertEquals(3, tickets.size());
		assertTrue(tickets.contains(ticketDAO.findByTitle("T2").get(0)));
		assertTrue(tickets.contains(ticketDAO.findByTitle("T3").get(0)));
		assertTrue(tickets.contains(ticketDAO.findByTitle("T5").get(0)));
	}

	/**
	 * Test if an {@link IllegalArgumentException} is thrown by passing
	 * <code>null</code> arguments to {@link OrFilter}
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testOrFilterWithNullArgument() {
		query.addFilter(new OrFilter(null, null));
		processor.execute(query);
	}

	/**
	 * Test if the {@link OrFilter} is correct
	 */
	@Test
	public void testOr() {
		Filter left = new EqualFilter("status", statusDAO.findByName("Assigned"));
		Filter right = new EqualFilter("priority", priorityDAO.findByName("MEDIUM"));
		query.addFilter(new OrFilter(left, right));
		List<Ticket> tickets = processor.execute(query);

		List<Ticket> assignedTickets = ticketDAO.findByStatus(statusDAO.findByName("Assigned"));
		List<Ticket> mediumTickets = ticketDAO.findByPriority(priorityDAO.findByName("MEDIUM"));
		Set<Ticket> ref = new HashSet<Ticket>();
		ref.addAll(assignedTickets);
		ref.addAll(mediumTickets);

		assertEquals(ref.size(), tickets.size());
		assertTrue(tickets.containsAll(ref));
	}

}
