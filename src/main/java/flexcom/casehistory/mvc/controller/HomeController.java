package flexcom.casehistory.mvc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import flexcom.casehistory.bootstrap.DatabaseBootstrapper;
import flexcom.casehistory.bootstrap.PriorityBootstrapper;
import flexcom.casehistory.ticket.dao.PriorityDAO;
import flexcom.casehistory.ticket.dao.TicketDAO;
import flexcom.casehistory.ticket.entity.Ticket;
import flexcom.casehistory.ticket.search.Query;
import flexcom.casehistory.ticket.search.filter.EqualFilter;
import flexcom.casehistory.ticket.search.filter.OrFilter;

/**
 * 
 * Controller class for bootstrapping the database
 * 
 * @author philippe
 * 
 */
@Controller
public class HomeController {

	/**
	 * Database bootstrapper
	 */
	@Autowired
	private DatabaseBootstrapper bootstrapper;
	
	@Autowired
	private PriorityBootstrapper priorityBootstrapper;
	
	@Autowired
	private TicketDAO ticketDAO;
	
	@Autowired
	private PriorityDAO priorityDAO;

	@RequestMapping(value = "home")
	public void home(Model m) {
		priorityBootstrapper.bootstrap();
		
		m.addAttribute("last", ticketDAO.last(10));
		
		Query<Ticket> query = new Query<Ticket>(Ticket.class);
		query.addFilter(new OrFilter(
			new EqualFilter("priority", priorityDAO.findByName("URGENT")), 
			new EqualFilter("priority", priorityDAO.findByName("IMMEDIATE"))
		));
		List<Ticket> urgentTickets = ticketDAO.filter(query);
		m.addAttribute("urgent", urgentTickets);
		
		m.addAttribute("unassigned", ticketDAO.findAllUnassigned());
	}

	/**
	 * Bootstrap the database
	 */
	@RequestMapping(value = "index/bootstrap")
	public String bootstrap() {
		bootstrapper.bootstrap();
		return "redirect:/";
	}

}