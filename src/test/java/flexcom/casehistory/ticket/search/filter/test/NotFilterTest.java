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
import flexcom.casehistory.ticket.dao.ProductDAO;
import flexcom.casehistory.ticket.dao.TicketDAO;
import flexcom.casehistory.ticket.entity.Product;
import flexcom.casehistory.ticket.entity.Ticket;
import flexcom.casehistory.ticket.search.Query;
import flexcom.casehistory.ticket.search.QueryProcessor;
import flexcom.casehistory.ticket.search.filter.EqualFilter;
import flexcom.casehistory.ticket.search.filter.Filter;
import flexcom.casehistory.ticket.search.filter.IsNullFilter;
import flexcom.casehistory.ticket.search.filter.NotFilter;

/**
 * Test class for {@link NotFilter}
 * 
 * @author philippe
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class NotFilterTest {

	/**
	 * Filter for NOT operator
	 */
	private Filter filter;

	/**
	 * {@link NotFilter}
	 */
	private Filter notFilter;

	/**
	 * A {@link Query}
	 */
	private Query<Ticket> query;

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
	 * Product DAO
	 */
	@Autowired
	private ProductDAO productDAO;

	/**
	 * Set up the context
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		dataSet.setup();

		query = new Query<Ticket>(Ticket.class);

		filter = new IsNullFilter("property");
		notFilter = new NotFilter(filter);
	}

	/**
	 * Clear the context
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		dataSet.clear();

		query = null;
		filter = null;
		notFilter = null;
	}

	/**
	 * Test the constructor
	 */
	@Test
	public void testNotFilter() {
		assertNotNull(notFilter);
	}

	/**
	 * Test the getHibernateCriterion() method
	 */
	@Test
	public void testGetHibernateCriterion() {
		assertEquals("not property is null", notFilter.getHibernateCriterion().toString());
	}

	/**
	 * Test an execution of a {@link Query} with a {@link NotFilter}. The
	 * following conditions are checked :<br/>
	 * <ul>
	 * <li>The list contains 2 tickets</li>
	 * <li>The list contains the ticket "T4"</li>
	 * <li>The list contains the ticket "T5"</li>
	 * </ul>
	 */
	@Test
	public void testNotFilterQuery() {
		Product p = productDAO.findByName("P1").get(0);
		query.addFilter(new NotFilter(new EqualFilter("product", p)));
		List<Ticket> tickets = processor.execute(query);

		assertEquals(2, tickets.size());
		assertTrue(tickets.contains(ticketDAO.findByTitle("T4").get(0)));
		assertTrue(tickets.contains(ticketDAO.findByTitle("T5").get(0)));
	}

	/**
	 * Test if an {@link IllegalArgumentException} is thrown by passing a
	 * <code>null</code> argument to {@link NotFilter}
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testNotFilterWithNullArgument() {
		query.addFilter(new NotFilter(null));
		processor.execute(query);
	}

	/**
	 * Test if the {@link NotFilter} is correct
	 */
	@Test
	public void testNot() {
		Product p = productDAO.findByName("P1").get(0);
		query.addFilter(new NotFilter(new EqualFilter("product", p)));
		List<Ticket> tickets = processor.execute(query);

		Query<Ticket> refQuery = new Query<Ticket>(Ticket.class);
		refQuery.addFilter(new EqualFilter("product", p));
		List<Ticket> ticketsForProduct = processor.execute(refQuery);
		List<Ticket> ref = ticketDAO.findAll();
		ref.removeAll(ticketsForProduct);

		assertEquals(ref.size(), tickets.size());
		assertTrue(tickets.containsAll(ref));
	}

}
