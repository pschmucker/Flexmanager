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
import flexcom.casehistory.ticket.dao.TicketDAO;
import flexcom.casehistory.ticket.dao.UserDAO;
import flexcom.casehistory.ticket.entity.Ticket;
import flexcom.casehistory.ticket.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class TicketAssignmentTest {
	
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
	}

	@After
	public void tearDown() throws Exception {
		userDataSet.clear();
		ticketDataSet.clear();
	}

	@Test
	public void ticketsAssignedToUser() {
		User user = userDAO.findByLogin("user1");

		List<Ticket> tickets = ticketDAO.findAll();

		for (Ticket t : tickets) {
			Data.USER_1.assign(t);
		}
		userDAO.updateUser(Data.USER_1);

		List<Ticket> assignedTickets = ticketDAO.findByUserInCharge(user);
		assertEquals(tickets.size(), assignedTickets.size());
	}

	@Test
	public void usersInChargeOfTicket() {
		Ticket ticket = ticketDAO.findByTitle("T1").get(0);

		List<User> users = userDAO.findAll();

		for (User u : users) {
			u.assign(ticket);
			userDAO.updateUser(u);
		}

		List<User> usersInCharge = userDAO.findByAssignedTicket(ticket);
		assertEquals(users.size(), usersInCharge.size());
	}
}
