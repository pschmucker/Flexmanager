package flexcom.casehistory.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import flexcom.casehistory.bootstrap.DatabaseBootstrapper;

/**
 * 
 * Controller class for bootstrapping the database
 * 
 * @author philippe
 * 
 */
@Controller
public class BootstrapController {

	/**
	 * Database bootstrapper
	 */
	@Autowired
	private DatabaseBootstrapper bootstrapper;

	/**
	 * Bootstrap the database
	 */
	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String bootstrap(@RequestParam(required = false, value = "bootstrap") boolean bootstrap) {
		if (bootstrap){
			bootstrapper.bootstrap();
		}
		return "redirect:/";
	}

}