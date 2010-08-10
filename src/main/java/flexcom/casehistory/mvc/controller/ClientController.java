package flexcom.casehistory.mvc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.HibernateJdbcException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import flexcom.casehistory.ticket.dao.ClientDAO;
import flexcom.casehistory.ticket.entity.Client;
import flexcom.casehistory.ticket.search.Query;
import flexcom.casehistory.ticket.search.filter.EqualFilter;
import flexcom.casehistory.ticket.search.filter.LikeFilter;

/**
 * 
 * Controller class for managing clients
 * 
 * @author philippe
 * 
 */
@Controller
public class ClientController {
	
	/**
	 * Client DAO
	 */
	@Autowired
	private ClientDAO clientDAO;
	
	/**
	 * Add a list of all clients to the model
	 * 
	 * @param m
	 *            The model
	 */
	@RequestMapping(value = "client")
	public void list(Model m) {
		m.addAttribute("list", clientDAO.findAll());
	}

	/**
	 * Add the {@link Client} object identified by the given ID to the model
	 * 
	 * @param m
	 *            The model
	 * @param clientId
	 *            The request parameter "id" for the {@link Client}
	 */
	@RequestMapping(value = "client/view")
	public void view(Model m, @RequestParam(required = true, value = "id") long clientId) {
		m.addAttribute("client", clientDAO.findById(clientId));
	}

	/**
	 * Add a new {@link Client} object to the model
	 * 
	 * @param m
	 *            The model
	 */
	@RequestMapping(value = "client/add")
	public void add(Model m) {
		m.addAttribute("client", new Client());
	}

	/**
	 * Process the creation of the {@link Client}
	 * 
	 * @param client
	 *            The {@link Client} object in the model
	 * @return The view which will be display
	 */
	@RequestMapping(value = "client/add", method = RequestMethod.POST)
	public String create(@ModelAttribute("client") @Valid Client client, BindingResult result) {
		if (result.hasErrors()) {
			return "client/add";
		}
		try {
			clientDAO.createClient(client);
		} catch (DataIntegrityViolationException e) {
			result.addError(new ObjectError("", "Company with this name already exist"));
			return "client/add";
		}
		return "redirect:/client.html";
	}

	/**
	 * Delete the {@link Client} object identified by the given ID
	 * 
	 * @param clientId
	 *            The request parameter "id" for the {@link Client}
	 * @return The view which will be display
	 */
	@RequestMapping(value = "client/delete")
	public String delete(@RequestParam(required = true, value = "id") long clientId) {
		Client client = clientDAO.findById(clientId);
		client.setEnabled(false);
		clientDAO.updateClient(client);
		return "redirect:/client.html";
	}

	/**
	 * Add the {@link Client} object identified by the given ID to the model
	 * 
	 * @param m
	 *            The model
	 * @param clientId
	 *            The request parameter "id" for the {@link Client}
	 */
	@RequestMapping(value = "client/edit")
	public void edit(Model m, @RequestParam(required = true, value = "id") long clientId) {
		m.addAttribute("client", clientDAO.findById(clientId));
	}

	/**
	 * Process the update of the {@link Client}
	 * 
	 * @param client
	 *            The {@link Client} object in the model
	 * @return The view which will be display
	 */
	@RequestMapping(value = "client/edit", method = RequestMethod.POST)
	public String update(@ModelAttribute("client") @Valid Client client, BindingResult result) {
		if (result.hasErrors()) {
			return "client/edit";
		}
		try {
			clientDAO.updateClient(client);
		} catch (HibernateJdbcException e) {
			result.addError(new ObjectError("", "Company with this name already exist"));
			return "client/edit";
		}
		return "redirect:/client.html";
	}

	/**
	 * Display the form for client searching
	 * 
	 * @param m
	 *            The model
	 */
	@RequestMapping(value = "client/search")
	public void searchForm(Model m) {
		m.addAttribute("companyQuery", new CompanyQueryCommand());
	}

	/**
	 * Execute the query
	 * 
	 * @param command
	 *            The query command
	 * @return The view which will be display
	 */
	@RequestMapping(value = "client/search", method = RequestMethod.POST)
	public ModelAndView search(@ModelAttribute("companyQuery") CompanyQueryCommand command) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Client> result = null;
		Query<Client> query = new Query<Client>(Client.class);
		if (!command.getCompanyName().isEmpty()){
			if (command.getMatchMode() == CompanyQueryCommand.EXACT_MATCH_MODE) {
				query.addFilter(new EqualFilter("name", command.getCompanyName()));
			} else {
				query.addFilter(new LikeFilter("name", command.getCompanyName()));
			}
		}
		result = clientDAO.filter(query);
		map.put("result", result);
		return new ModelAndView("client/result", map);
	}

	/**
	 * Mapping for result page
	 */
	@RequestMapping(value = "client/result")
	public void resultPage() {}

}