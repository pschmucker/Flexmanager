package flexcom.casehistory.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import flexcom.casehistory.ticket.dao.ClientDAO;
import flexcom.casehistory.ticket.dao.ContactDAO;
import flexcom.casehistory.ticket.dao.PriorityDAO;
import flexcom.casehistory.ticket.dao.ProductDAO;
import flexcom.casehistory.ticket.dao.StatusDAO;
import flexcom.casehistory.ticket.dao.TicketDAO;
import flexcom.casehistory.ticket.entity.Client;
import flexcom.casehistory.ticket.entity.Contact;
import flexcom.casehistory.ticket.entity.Product;
import flexcom.casehistory.ticket.entity.Ticket;


@Component
public class TicketBootstrapper implements Bootstrapper {
	
	@Autowired
	private TicketDAO ticketDAO;
	
	@Autowired
	private ProductDAO productDAO;
	
	@Autowired
	private ClientDAO clientDAO;
	
	@Autowired
	private PriorityDAO priorityDAO;
	
	@Autowired
	private StatusDAO statusDAO;
	
	@Autowired
	private ContactDAO contactDAO;
	
	@Autowired
	private ClientBootstrapper clientBootstrapper;
	
	@Autowired
	private ProductBootstrapper productBootstrapper;
	
	@Autowired
	private PriorityBootstrapper priorityBootstrapper;
	
	@Autowired
	private StatusBootstrapper statusBootstrapper;
	
	@Autowired
	private ContactBootstrapper contactBootstrapper;
	
	@Override
	public void bootstrap() {
		if (priorityDAO.count() == 0){
			priorityBootstrapper.bootstrap();
		}
		
		if (statusDAO.count() == 0){
			statusBootstrapper.bootstrap();
		}
		
		if (clientDAO.count() == 0){
			clientBootstrapper.bootstrap();
		}
		
		if (productDAO.count() == 0){
			productBootstrapper.bootstrap();
		}
		
		if (contactDAO.count() == 0){
			contactBootstrapper.bootstrap();
		}
		
		if (ticketDAO.count() == 0){
			Client client = clientDAO.findByName("Flexcom");
			Product product = productDAO.findByName("CaseHistory").get(0);
			Contact contact = contactDAO.findByName("M. Y").get(0);
			
			Ticket ticket = new Ticket();
			ticket.setTitle("First bug");
			ticket.setStatus(statusDAO.findByName("New"));
			ticket.setPriority(priorityDAO.findByName("MEDIUM"));
			ticket.setDescription("This is the first ticket !");
			ticket.setClient(client);
			ticket.setProduct(product);
			ticket.setContact(contact);
			ticketDAO.createTicket(ticket);
			
			ticket = new Ticket();
			ticket.setTitle("Implement notes");
			ticket.setStatus(statusDAO.findByName("Closed"));
			ticket.setPriority(priorityDAO.findByName("HIGH"));
			ticket.setDescription("Add the management of notes. This includes entity, DAO, controller and Web pages");
			ticket.setClient(client);
			ticket.setProduct(product);
			ticket.setContact(contact);
			ticketDAO.createTicket(ticket);
		}
	}
	
}
