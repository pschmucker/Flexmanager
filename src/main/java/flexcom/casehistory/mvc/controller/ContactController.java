package flexcom.casehistory.mvc.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import flexcom.casehistory.mvc.editor.CompanyEditor;
import flexcom.casehistory.ticket.dao.ClientDAO;
import flexcom.casehistory.ticket.dao.ContactDAO;
import flexcom.casehistory.ticket.dao.PartnerDAO;
import flexcom.casehistory.ticket.entity.Company;
import flexcom.casehistory.ticket.entity.Contact;
import flexcom.casehistory.ticket.entity.Ticket;
import flexcom.casehistory.ticket.entity.User;
import flexcom.casehistory.ticket.search.Query;
import flexcom.casehistory.ticket.search.filter.EqualFilter;
import flexcom.casehistory.ticket.search.filter.LikeFilter;

/**
 * Controller class for managing contacts
 * 
 * @author philippe
 */
@Controller
public class ContactController {

	/**
	 * Contact DAO
	 */
	@Autowired
	private ContactDAO contactDAO;

	@Autowired
	private ClientDAO clientDAO;
	
	@Autowired
	private PartnerDAO partnerDAO;
	
	@Autowired
	private CompanyEditor companyEditor;

	/**
	 * Add the list of all contacts to the model
	 * 
	 * @param m
	 *            The model
	 */
	@RequestMapping(value = "contact")
	public void list(Model m) {
		m.addAttribute("list", contactDAO.findAll());
	}

	/**
	 * Returns a list of all companies
	 * 
	 * @return The list of all companies
	 */
	@ModelAttribute(value = "companies")
	public List<Company> companies() {
		List<Company> companies = new ArrayList<Company>();
		companies.addAll(clientDAO.findAll());
		companies.addAll(partnerDAO.findAll());
		return companies;
	}

	/**
	 * Add the {@link User} object identified by the request parameter "id" to
	 * the model. A {@link TicketIdCommand} object is added to the model too. It
	 * allows you to retrieve the ID of any {@link Ticket} you want to assign to
	 * a {@link User}.
	 * 
	 * @param m
	 *            The model
	 * @param contactId
	 *            The request parameter "id" for the {@link User}
	 */
	@RequestMapping(value = "contact/view")
	public void view(Model m, @RequestParam(required = true, value = "id") long contactId) {
		m.addAttribute("contact", contactDAO.findById(contactId));
	}

	/**
	 * Add a new {@link User} object to the model
	 * 
	 * @param m
	 *            The model
	 */
	@RequestMapping(value = "contact/add")
	public void add(Model m) {
		m.addAttribute("contact", new Contact());
	}

	/**
	 * Process the creation of the {@link User}
	 * 
	 * @param user
	 *            The {@link User} object in the model
	 * @return The view which will be display
	 */
	@RequestMapping(value = "contact/add", method = RequestMethod.POST)
	public String create(@ModelAttribute("contact") @Valid Contact contact, BindingResult result) {
		if (result.hasErrors()) {
			return "contact/add";
		}
		contactDAO.createContact(contact);
		return "redirect:/contact.html";
	}

	/**
	 * Delete the {@link User} identified by the given ID
	 * 
	 * @param contactId
	 *            The request parameter "id" for the {@link User}
	 * @return The view which will be display
	 */
	@RequestMapping(value = "contact/delete")
	public String delete(@RequestParam(required = true, value = "id") long contactId) {
		Contact contact = contactDAO.findById(contactId);
		contact.setEnabled(false);
		contactDAO.updateContact(contact);
		return "redirect:/contact.html";
	}

	/**
	 * Add the {@link User} object identified by the given ID to the model
	 * 
	 * @param m
	 *            The model
	 * @param contactId
	 *            The request parameter "id" for the {@link User}
	 */
	@RequestMapping(value = "contact/edit")
	public void edit(Model m, @RequestParam(required = true, value = "id") long contactId) {
		m.addAttribute("contact", contactDAO.findById(contactId));
	}

	/**
	 * Process the update of the {@link User}
	 * 
	 * @param user
	 *            The {@link User} object in the model
	 * @return The view which will be display
	 */
	@RequestMapping(value = "contact/edit", method = RequestMethod.POST)
	public String update(@ModelAttribute("contact") @Valid Contact contact, BindingResult result) {
		if (result.hasErrors()) {
			return "contact/edit";
		}
		contactDAO.updateContact(contact);
		return "redirect:/contact.html";
	}

	/**
	 * Display the form for user searching
	 * 
	 * @param m
	 *            The model
	 */
	@RequestMapping(value = "contact/search")
	public void searchForm(Model m) {
		m.addAttribute("contactQuery", new ContactQueryCommand());
	}

	/**
	 * Execute the query
	 * 
	 * @param command
	 *            The query command
	 * @return The view which will be display
	 */
	@RequestMapping(value = "contact/search", method = RequestMethod.POST)
	public ModelAndView search(@ModelAttribute("contactQuery") ContactQueryCommand command) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Contact> result = null;
		Query<Contact> query = new Query<Contact>(Contact.class);

		if (!command.getContactName().isEmpty()){
			if (command.getMatchMode() == ContactQueryCommand.EXACT_MATCH_MODE){
				query.addFilter(new EqualFilter("name", command.getContactName()));
			}
			else{
				query.addFilter(new LikeFilter("name", command.getContactName()));
			}
		}

		result = contactDAO.filter(query);
		map.put("result", result);
		return new ModelAndView("contact/result", map);
	}

	/**
	 * Mapping for result page
	 */
	@RequestMapping(value = "contact/result")
	public void resultPage() {
	}
	
	@InitBinder
	public void binder(WebDataBinder binder) {
		binder.registerCustomEditor(Company.class, companyEditor);
	}

}
