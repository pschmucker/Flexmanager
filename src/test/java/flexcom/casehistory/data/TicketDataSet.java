package flexcom.casehistory.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import flexcom.casehistory.ticket.dao.ClientDAO;
import flexcom.casehistory.ticket.dao.ContactDAO;
import flexcom.casehistory.ticket.dao.PriorityDAO;
import flexcom.casehistory.ticket.dao.ProductDAO;
import flexcom.casehistory.ticket.dao.StatusDAO;
import flexcom.casehistory.ticket.dao.TicketDAO;
import flexcom.casehistory.ticket.entity.Ticket;

/**
 * {@link Ticket} data set
 * 
 * @author philippe
 * 
 */
@Component
public class TicketDataSet extends Data {

	/**
	 * Ticket DAO
	 */
	@Autowired
	private TicketDAO ticketDAO;

	@Autowired
	private ClientDAO clientDAO;
	
	@Autowired
	private ProductDAO productDAO;
	
	@Autowired
	private StatusDAO statusDAO;
	
	@Autowired
	private PriorityDAO priorityDAO;
	
	@Autowired
	private ContactDAO contactDAO;

	@Autowired
	private ClientDataSet clientDataSet;

	@Autowired
	private ProductDataSet productDataSet;

	@Autowired
	private StatusDataSet statusDataSet;

	@Autowired
	private PriorityDataSet priorityDataSet;

	@Autowired
	private ContactDataSet contactDataSet;

	/**
	 * Set up all tickets
	 */
	public void setup() {
		if (priorityDAO.count() == 0){
			priorityDataSet.setup();
		}
		
		if (statusDAO.count() == 0){
			statusDataSet.setup();
		}
		
		if (productDAO.count() == 0){
			productDataSet.setup();
		}
		
		if (clientDAO.count() == 0){
			clientDataSet.setup();
		}
		
		if (contactDAO.count() == 0){
			contactDataSet.setup();
		}
		
		if (ticketDAO.count() == 0) {
			TICKET_1 = new Ticket();
			TICKET_1.setTitle("T1");
			TICKET_1.setDescription("this is a test ticket");
			TICKET_1.setPriority(PRIORITY_IMMEDIATE);
			TICKET_1.setStatus(STATUS_NEW);
			TICKET_1.setClient(CLIENT_1);
			TICKET_1.setProduct(PRODUCT_1);
			TICKET_1.setContact(CONTACT_1);
			ticketDAO.createTicket(TICKET_1);
			
			TICKET_2 = new Ticket();
			TICKET_2.setTitle("T2");
			TICKET_2.setDescription("this is a test ticket");
			TICKET_2.setPriority(PRIORITY_MEDIUM);
			TICKET_2.setStatus(STATUS_NEW);
			TICKET_2.setClient(CLIENT_1);
			TICKET_2.setProduct(PRODUCT_1);
			TICKET_2.setContact(CONTACT_1);
			ticketDAO.createTicket(TICKET_2);
			
			TICKET_3 = new Ticket();
			TICKET_3.setTitle("T3");
			TICKET_3.setDescription("this is a test ticket");
			TICKET_3.setPriority(PRIORITY_MEDIUM);
			TICKET_3.setStatus(STATUS_ASSIGNED);
			TICKET_3.setClient(CLIENT_1);
			TICKET_3.setProduct(PRODUCT_1);
			TICKET_3.setContact(CONTACT_1);
			ticketDAO.createTicket(TICKET_3);
			
			TICKET_4 = new Ticket();
			TICKET_4.setTitle("T4");
			TICKET_4.setDescription("this is a test ticket");
			TICKET_4.setPriority(PRIORITY_HIGH);
			TICKET_4.setStatus(STATUS_NEW);
			TICKET_4.setClient(CLIENT_1);
			TICKET_4.setProduct(PRODUCT_2);
			TICKET_4.setContact(CONTACT_1);
			ticketDAO.createTicket(TICKET_4);
			
			TICKET_5 = new Ticket();
			TICKET_5.setTitle("T5");
			TICKET_5.setDescription("this is a test ticket");
			TICKET_5.setPriority(PRIORITY_URGENT);
			TICKET_5.setStatus(STATUS_ASSIGNED);
			TICKET_5.setClient(CLIENT_2);
			TICKET_5.setProduct(PRODUCT_2);
			TICKET_5.setContact(CONTACT_1);
			ticketDAO.createTicket(TICKET_5);
		}
	}

	/**
	 * Clear all tickets
	 */
	public void clear() {
		ticketDAO.deleteAll();
		contactDAO.deleteAll();
		productDAO.deleteAll();
		clientDAO.deleteAll();
		priorityDAO.deleteAll();
		statusDAO.deleteAll();
	}

}
