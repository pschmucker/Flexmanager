package flexcom.casehistory.ticket;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

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
import flexcom.casehistory.ticket.dao.ClientDAO;
import flexcom.casehistory.ticket.dao.ProductDAO;
import flexcom.casehistory.ticket.dao.TicketDAO;
import flexcom.casehistory.ticket.entity.Client;
import flexcom.casehistory.ticket.entity.Product;
import flexcom.casehistory.ticket.entity.Ticket;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class TicketCreationTest {

	@Autowired
	private TicketDataSet ticketDataSet;
	
	@Autowired
	private ClientDAO clientDAO;

	@Autowired
	private ProductDAO productDAO;

	@Autowired
	private TicketDAO ticketDAO;

	@Before
	public void setUp() throws Exception {
		ticketDataSet.setup();
	}

	@After
	public void tearDown() throws Exception {
		ticketDataSet.clear();
	}

	@Test
	public void createNewClient() {
		Client client = new Client();
		client.setAddress("100 rue Mai");
		client.setName("TestClient");
		clientDAO.createClient(client);

		Client c = clientDAO.findByName("TestClient");
		assertFalse(c == null);
	}

	@Test
	public void createNewProduct() {
		Product product = new Product();
		product.setName("TestProduct");
		product.setVersion("0.0");

		productDAO.createProduct(product);

		List<Product> products = productDAO.findByName("TestProduct");
		assertEquals(1, products.size());
	}

	@Test
	public void createNewTicket() {
		Ticket ticket = new Ticket();
		ticket.setTitle("TestTicket");
		ticket.setStatus(Data.STATUS_NEW);
		ticket.setPriority(Data.PRIORITY_LOW);
		ticket.setDescription("No description");
		ticket.setClient(Data.CLIENT_1);
		ticket.setProduct(Data.PRODUCT_1);
		ticket.setContact(Data.CONTACT_1);
		ticketDAO.createTicket(ticket);

		List<Ticket> tickets = ticketDAO.findByTitle("TestTicket");
		assertEquals(1, tickets.size());
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void deleteTicket() {
		Ticket ticket = ticketDAO.findByTitle("T1").get(0);
		ticketDAO.deleteTicket(ticket);
		ticket = ticketDAO.findByTitle("T1").get(0);
	}
}
