package flexcom.casehistory.mvc.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import flexcom.casehistory.mvc.editor.CompanyEditor;
import flexcom.casehistory.mvc.editor.ContactEditor;
import flexcom.casehistory.mvc.editor.PriorityEditor;
import flexcom.casehistory.mvc.editor.ProductEditor;
import flexcom.casehistory.mvc.editor.StatusEditor;
import flexcom.casehistory.mvc.editor.TicketEditor;
import flexcom.casehistory.mvc.editor.UserEditor;
import flexcom.casehistory.ticket.dao.ClientDAO;
import flexcom.casehistory.ticket.dao.ContactDAO;
import flexcom.casehistory.ticket.dao.EventDAO;
import flexcom.casehistory.ticket.dao.NoteDAO;
import flexcom.casehistory.ticket.dao.PriorityDAO;
import flexcom.casehistory.ticket.dao.ProductDAO;
import flexcom.casehistory.ticket.dao.StatusDAO;
import flexcom.casehistory.ticket.dao.TicketDAO;
import flexcom.casehistory.ticket.dao.UserDAO;
import flexcom.casehistory.ticket.entity.Client;
import flexcom.casehistory.ticket.entity.Contact;
import flexcom.casehistory.ticket.entity.Note;
import flexcom.casehistory.ticket.entity.Priority;
import flexcom.casehistory.ticket.entity.Product;
import flexcom.casehistory.ticket.entity.Status;
import flexcom.casehistory.ticket.entity.Ticket;
import flexcom.casehistory.ticket.entity.User;
import flexcom.casehistory.ticket.search.Query;
import flexcom.casehistory.ticket.search.filter.EqualFilter;
import flexcom.casehistory.ticket.search.filter.LikeFilter;

/**
 * 
 * Controller class for managing tickets
 * 
 * @author philippe
 * 
 */
@Controller
public class TicketController {

	/**
	 * User DAO
	 */
	@Autowired
	private UserDAO userDAO;

	/**
	 * Note DAO
	 */
	@Autowired
	private NoteDAO noteDAO;

	/**
	 * Event DAO
	 */
	@Autowired
	private EventDAO eventDAO;
	
	/**
	 * Priority DAO
	 */
	@Autowired
	private PriorityDAO priorityDAO;

	/**
	 * Priority DAO
	 */
	@Autowired
	private StatusDAO statusDAO;

	/**
	 * Ticket DAO
	 */
	@Autowired
	private TicketDAO ticketDAO;

	/**
	 * Client DAO
	 */
	@Autowired
	private ClientDAO clientDAO;

	/**
	 * Product DAO
	 */
	@Autowired
	private ProductDAO productDAO;

	/**
	 * Contact DAO
	 */
	@Autowired
	private ContactDAO contactDAO;

	/**
	 * User editor
	 */
	@Autowired
	private UserEditor userEditor;

	/**
	 * Client editor
	 */
	@Autowired
	private CompanyEditor clientEditor;

	/**
	 * Product editor
	 */
	@Autowired
	private ProductEditor productEditor;

	/**
	 * Priority editor
	 */
	@Autowired
	private PriorityEditor priorityEditor;

	/**
	 * Priority editor
	 */
	@Autowired
	private ContactEditor contactEditor;

	/**
	 * Status editor
	 */
	@Autowired
	private StatusEditor statusEditor;

	/**
	 * Ticket editor
	 */
	@Autowired
	private TicketEditor ticketEditor;

	/**
	 * Add the list of all tickets to the model
	 * 
	 * @param m
	 *            The model
	 */
	@RequestMapping(value = "ticket")
	public void list(Model m) {
		m.addAttribute("list", ticketDAO.findAll());
	}

	/**
	 * Add the {@link Ticket} object identified by the given ID
	 * 
	 * @param m
	 *            The model
	 * @param ticketId
	 *            The request parameter "id" for the {@link Ticket}
	 */
	@RequestMapping(value = "ticket/view")
	public void view(Model m, @RequestParam(required = true, value = "id") long ticketId) {
		
		Ticket ticket = ticketDAO.findById(ticketId);
		m.addAttribute("ticket", ticket);
		m.addAttribute("events", eventDAO.findByTicket(ticket));
		m.addAttribute("notes", noteDAO.findByTicket(ticket));
		
		Note n = new Note();
		String login = ((UserDetails) (SecurityContextHolder.getContext()).getAuthentication().getPrincipal()).getUsername();
		User u = userDAO.findByLogin(login);
		n.setAuthor(u);
		n.setTicket(ticket);
		m.addAttribute("note", n);
	}

	/**
	 * Add a new {@link Ticket} object to the model
	 * 
	 * @param m
	 *            The model
	 */
	@RequestMapping(value = "ticket/add")
	public void add(Model m) {
		Ticket ticket = new Ticket();
		String login = ((UserDetails) (SecurityContextHolder.getContext()).getAuthentication().getPrincipal()).getUsername();
		User user = userDAO.findByLogin(login);
		ticket.getUsersInCharge().add(user);
		m.addAttribute("ticket", ticket);
	}

	/**
	 * Process the creation of the {@link Ticket}
	 * 
	 * @param ticket
	 *            The {@link Ticket} object in the model
	 * @return The view which will be display
	 */
	@RequestMapping(value = "ticket/add", method = RequestMethod.POST)
	public String create(@ModelAttribute("ticket") @Valid Ticket ticket, BindingResult result) {
		if (result.hasErrors()) {
			return "ticket/add";
		}
		ticket.setCreationDate(new Date());
		ticketDAO.createTicket(ticket);
		Set<User> inCharge = ticket.getUsersInCharge();
		if (inCharge != null) {
			for (User u : inCharge) {
				u.assign(ticket);
				userDAO.updateUser(u);
			}
		}
		return "redirect:/ticket.html";
	}

	@ModelAttribute(value = "users")
	public List<User> users() {
		return userDAO.findAll();
	}
	
	/**
	 * Returns a list of all clients
	 * 
	 * @return The list of all clients
	 */
	@ModelAttribute(value = "clients")
	public List<Client> clients() {
		return clientDAO.findAll();
	}

	/**
	 * Returns a list of all products
	 * 
	 * @return The list of all products
	 */
	@ModelAttribute(value = "products")
	public List<Product> products() {
		return productDAO.findAll();
	}

	/**
	 * Returns a list of all contacts
	 * 
	 * @return The list of all contacts
	 */
	@ModelAttribute(value = "contacts")
	public List<Contact> contacts() {
		return contactDAO.findAll();
	}

	/**
	 * Returns a list of all priorities
	 * 
	 * @return The list of all priorities
	 */
	@ModelAttribute(value = "priorities")
	public List<Priority> priorities() {
		return priorityDAO.findAll();
	}

	/**
	 * Returns a list of all statuses
	 * 
	 * @return The list of all statuses
	 */
	@ModelAttribute(value = "statuses")
	public List<Status> statuses() {
		return statusDAO.findAll();
	}

	/**
	 * Register 6 editors :<br/>
	 * <ul>
	 * <li>for Date object</li>
	 * <li>for Client object</li>
	 * <li>for Product object</li>
	 * <li>for Priority object</li>
	 * <li>for Status object</li>
	 * <li>for User object</li>
	 * <li>for Ticket object</li>
	 * </ul>
	 * 
	 * @param binder
	 *            The WebDataBinder
	 */
	@InitBinder
	public void binder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
		binder.registerCustomEditor(Client.class, clientEditor);
		binder.registerCustomEditor(Product.class, productEditor);
		binder.registerCustomEditor(Contact.class, contactEditor);
		binder.registerCustomEditor(Priority.class, priorityEditor);
		binder.registerCustomEditor(Status.class, statusEditor);
		binder.registerCustomEditor(User.class, userEditor);
		binder.registerCustomEditor(Ticket.class, ticketEditor);
	}

	/**
	 * Delete the {@link Ticket} object identified by the given ID
	 * 
	 * @param ticketId
	 *            The request parameter "id" for the {@link Ticket}
	 * @return The view which will be display
	 */
	@RequestMapping(value = "ticket/delete")
	public String delete(@RequestParam(required = true, value = "id") long ticketId) {
		Ticket ticket = ticketDAO.findById(ticketId);
		for (User u : ticket.getUsersInCharge()){
			u.unassign(ticket);
			userDAO.updateUser(u);
		}
		ticket.setEnabled(false);
		ticketDAO.updateTicket(ticket);
		return "redirect:/ticket.html";
	}

	/**
	 * Add the {@link Ticket} object identified by the given ID to the model
	 * 
	 * @param m
	 *            The model
	 * @param ticketId
	 *            The request parameter "id" for the {@link Ticket}
	 */
	@RequestMapping(value = "ticket/edit")
	public void edit(Model m, @RequestParam(required = true, value = "id") long ticketId) {
		m.addAttribute("ticket", ticketDAO.findById(ticketId));
	}

	/**
	 * Process the update of the {@link Ticket}
	 * 
	 * @param ticket
	 *            The {@link Ticket} object in the model
	 * @return The view which will be display
	 */
	@RequestMapping(value = "ticket/edit", method = RequestMethod.POST)
	public String update(@ModelAttribute("ticket") @Valid Ticket ticket, BindingResult result) {
		if (result.hasErrors()) {
			return "ticket/edit";
		}
		
		List<User> inChargeOld = userDAO.findByAssignedTicket(ticket);
		Set<User> inChargeNew = ticket.getUsersInCharge();
		inChargeNew = (inChargeNew == null) ? new HashSet<User>() : inChargeNew;
		
		ticketDAO.updateTicket(ticket);
		
		Set<User> usersToRemove = new HashSet<User>();
		usersToRemove.addAll(inChargeOld);
		usersToRemove.removeAll(inChargeNew);
		
		Set<User> usersToAdd = new HashSet<User>();
		usersToAdd.addAll(inChargeNew);
		usersToAdd.removeAll(inChargeOld);
		
		for (User user : usersToRemove) {
			user.unassign(ticket);
			userDAO.updateUser(user);
		}

		for (User user : usersToAdd) {
			user.assign(ticket);
			userDAO.updateUser(user);
		}

		return "redirect:/ticket.html";
	}

	/**
	 * Display the form for ticket searching
	 * 
	 * @param m
	 *            The model
	 */
	@RequestMapping(value = "ticket/search")
	public void searchForm(Model m) {
		m.addAttribute("ticketQuery", new TicketQueryCommand());
	}

	/**
	 * Execute the query
	 * 
	 * @param command
	 *            The query command
	 * @return The view which will be display
	 */
	@RequestMapping(value = "ticket/search", method = RequestMethod.POST)
	public ModelAndView search(@ModelAttribute("ticketQuery") TicketQueryCommand command) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Ticket> result = null;
		Query<Ticket> query = new Query<Ticket>(Ticket.class);

		if (!command.getTitle().isEmpty()) {
			if (command.getTitleMatchMode() == ProductQueryCommand.EXACT_MATCH_MODE) {
				query.addFilter(new EqualFilter("title", command.getTitle()));
			} else {
				query.addFilter(new LikeFilter("title", command.getTitle()));
			}
		}
		
		if (!command.getStatusId().equals("select")){
			query.addFilter(new EqualFilter("status", statusDAO.findById(Long.parseLong(command.getStatusId()))));
		}
		
		if (!command.getPriorityId().equals("select")){
			query.addFilter(new EqualFilter("priority", priorityDAO.findById(Long.parseLong(command.getPriorityId()))));
		}
		
		if (!command.getClientId().equals("select")){
			query.addFilter(new EqualFilter("client", clientDAO.findById(Long.parseLong(command.getClientId()))));
		}
		
		if (!command.getProductId().equals("select")){
			query.addFilter(new EqualFilter("product", productDAO.findById(Long.parseLong(command.getProductId()))));
		}

		result = ticketDAO.filter(query);
		map.put("result", result);
		return new ModelAndView("ticket/result", map);
	}

	/**
	 * Mapping for result page
	 */
	@RequestMapping(value = "ticket/result")
	public void resultPage() {
	}

	/**
	 * Process the creation of the {@link Note}
	 * 
	 * @param note
	 *            The {@link Note} object in the model
	 * @return The view which will be display
	 * @throws IOException 
	 */
	@RequestMapping(value = "ticket/view", method = RequestMethod.POST)
	public String createNote(@ModelAttribute("note") @Valid Note note, BindingResult result, @RequestParam(required = true, value = "id") long ticketId, @RequestParam("file") MultipartFile file) throws IOException {
		if (result.hasErrors()){
			return "redirect:/ticket/view.html?id=" + ticketId;
		}
		
		Note newNote = new Note();
		newNote.setAuthor(note.getAuthor());
		newNote.setTicket(note.getTicket());
		newNote.setCreationDate(new Date());
		newNote.setNote(note.getNote());

		if (!file.isEmpty()) {
			File f = new File(file.getOriginalFilename());
        	file.transferTo(f);
        	newNote.setAttachment(f.getAbsolutePath());
        }
		
		noteDAO.createNote(newNote);
		
		return "redirect:/ticket/view.html?id=" + ticketId;
	}

}