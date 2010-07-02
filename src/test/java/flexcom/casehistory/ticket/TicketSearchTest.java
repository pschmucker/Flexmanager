package flexcom.casehistory.ticket;

import static org.junit.Assert.assertEquals;

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
import flexcom.casehistory.ticket.dao.ClientDAO;
import flexcom.casehistory.ticket.dao.TicketDAO;
import flexcom.casehistory.ticket.dao.UserDAO;
import flexcom.casehistory.ticket.entity.Client;
import flexcom.casehistory.ticket.entity.Ticket;
import flexcom.casehistory.ticket.search.Query;
import flexcom.casehistory.ticket.search.filter.EqualFilter;
import flexcom.casehistory.ticket.search.filter.IsEmptyFilter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class TicketSearchTest {

	@Autowired
	private ClientDAO clientDAO;

	@Autowired
	private TicketDAO ticketDAO;

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private TicketDataSet ticketDataSet;
	
	@Autowired
	private UserDataSet userDataSet;
	
	@Before
	public void setUp() throws Exception {
		ticketDataSet.setup();
		userDataSet.setup();

		Data.USER_1.assign(Data.TICKET_1);
		Data.USER_1.assign(Data.TICKET_2);
		Data.USER_1.assign(Data.TICKET_5);
		Data.USER_2.assign(Data.TICKET_2);

		userDAO.updateUser(Data.USER_1);
		userDAO.updateUser(Data.USER_2);
	}

	@After
	public void tearDown() throws Exception {
		userDataSet.clear();
		ticketDataSet.clear();
	}

	@Test
	public void findAllTickets() {
		List<Ticket> tickets = ticketDAO.findAll();
		assertEquals(5, tickets.size());
	}

	@Test
	public void findAllNewTickets() {
		List<Ticket> openTickets = ticketDAO.findByStatus(Data.STATUS_NEW);
		assertEquals(3, openTickets.size());
	}

	@Test
	public void findAllHighPriorityTickets() {
		List<Ticket> highPriorityTickets = ticketDAO.findByPriority(Data.PRIORITY_HIGH);
		assertEquals(1, highPriorityTickets.size());
	}

	@Test
	public void findAllUnassignedTickets() {
		List<Ticket> unassignedTickets = ticketDAO.findAllUnassigned();
		assertEquals(2, unassignedTickets.size());
	}

	@Test
	public void findAllTicketsForOneClient() {
		Client client = clientDAO.findByName("C1");

		List<Ticket> ticketsForClient = ticketDAO.findByClient(client);
		assertEquals(4, ticketsForClient.size());
	}

	@Test
	public void findTicketsUsingFilters() {
		Query<Ticket> query = new Query<Ticket>(Ticket.class);
		query.addFilter(new EqualFilter("priority", Data.PRIORITY_HIGH));
		query.addFilter(new IsEmptyFilter("usersInCharge"));

		List<Ticket> tickets = ticketDAO.filter(query);
		assertEquals(1, tickets.size());
	}
}
