package flexcom.casehistory.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import flexcom.casehistory.bootstrap.DatabaseBootstrapper;
import flexcom.casehistory.ticket.dao.TicketDAO;

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
	private TicketDAO ticketDAO;

	@RequestMapping(value = "home")
	public void home(Model m) {
		m.addAttribute("last", ticketDAO.last(10));
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