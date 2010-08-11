package flexcom.casehistory.mvc.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.hibernate3.HibernateJdbcException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import flexcom.casehistory.mvc.editor.RoleEditor;
import flexcom.casehistory.mvc.editor.TicketEditor;
import flexcom.casehistory.ticket.dao.RoleDAO;
import flexcom.casehistory.ticket.dao.TicketDAO;
import flexcom.casehistory.ticket.dao.UserDAO;
import flexcom.casehistory.ticket.entity.Role;
import flexcom.casehistory.ticket.entity.Ticket;
import flexcom.casehistory.ticket.entity.User;
import flexcom.casehistory.ticket.search.Query;
import flexcom.casehistory.ticket.search.filter.EqualFilter;
import flexcom.casehistory.ticket.search.filter.Filter;
import flexcom.casehistory.ticket.search.filter.GreaterOrEqualFilter;
import flexcom.casehistory.ticket.search.filter.GreaterThanFilter;
import flexcom.casehistory.ticket.search.filter.LessOrEqualFilter;
import flexcom.casehistory.ticket.search.filter.LessThanFilter;
import flexcom.casehistory.ticket.search.filter.LikeFilter;

/**
 * Controller class for managing users
 * 
 * @author philippe
 */
@Controller
public class UserController {

	/**
	 * User DAO
	 */
	@Autowired
	private UserDAO userDAO;

	@Autowired
	private RoleDAO roleDAO;

	/**
	 * Ticket DAO
	 */
	@Autowired
	private TicketDAO ticketDAO;

	/**
	 * Ticket editor
	 */
	@Autowired
	private TicketEditor ticketEditor;

	/**
	 * Ticket editor
	 */
	@Autowired
	private RoleEditor roleEditor;

	/**
	 * Add the list of all users to the model
	 * 
	 * @param m
	 *            The model
	 */
	@RequestMapping(value = "user")
	public void list(Model m) {
		m.addAttribute("list", userDAO.findAll());
	}

	/**
	 * Returns a list of all roles
	 * 
	 * @return The list of all roles
	 */
	@ModelAttribute(value = "roles")
	public List<Role> roles() {
		return roleDAO.findAll();
	}

	/**
	 * Add the {@link User} object identified by the request parameter "id" to
	 * the model. A {@link TicketIdCommand} object is added to the model too. It
	 * allows you to retrieve the ID of any {@link Ticket} you want to assign to
	 * a {@link User}.
	 * 
	 * @param m
	 *            The model
	 * @param userId
	 *            The request parameter "id" for the {@link User}
	 */
	@RequestMapping(value = "user/view")
	public void view(Model m, @RequestParam(required = true, value = "id") long userId) {
		m.addAttribute("user", userDAO.findById(userId));
		m.addAttribute("ticketIdCommand", new TicketIdCommand());
	}

	/**
	 * Add a new {@link User} object to the model
	 * 
	 * @param m
	 *            The model
	 */
	@RequestMapping(value = "user/add")
	public void add(Model m) {
		m.addAttribute("user", new User());
	}

	/**
	 * Process the creation of the {@link User}
	 * 
	 * @param user
	 *            The {@link User} object in the model
	 * @return The view which will be display
	 */
	@RequestMapping(value = "user/add", method = RequestMethod.POST)
	public String create(@ModelAttribute("user") @Valid User user, BindingResult result) {
		if (result.hasErrors()) {
			return "user/add";
		}
		try {
			userDAO.createUser(user);
		} catch (DataIntegrityViolationException e) {
			try{
				userDAO.findByLogin(user.getLogin());
				result.addError(new ObjectError("", "Login already exist"));
			}
			catch (EmptyResultDataAccessException e2) {
				result.addError(new ObjectError("", "Email already used"));
			}
			return "user/add";
		}
		return "redirect:/user.html";
	}

	/**
	 * Delete the {@link User} identified by the given ID
	 * 
	 * @param userId
	 *            The request parameter "id" for the {@link User}
	 * @return The view which will be display
	 */
	@RequestMapping(value = "user/delete")
	public String delete(@RequestParam(required = true, value = "id") long userId) {
		
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		UserDetails details = (UserDetails) authentication.getPrincipal();
		String login = details.getUsername();
		
		User loggedUser;
		try {
			loggedUser = userDAO.findByLogin(login);
		} catch (EmptyResultDataAccessException e) {
			loggedUser = null;
		}
		
		User user = userDAO.findById(userId);
		user.setEnabled(false);
		userDAO.updateUser(user);
		
		if (user.equals(loggedUser)){
			return "redirect:/j_spring_security_logout";
		}
		return "redirect:/user.html";
	}

	/**
	 * Add the {@link User} object identified by the given ID to the model
	 * 
	 * @param m
	 *            The model
	 * @param userId
	 *            The request parameter "id" for the {@link User}
	 */
	@RequestMapping(value = "user/edit")
	public void edit(Model m, @RequestParam(required = true, value = "id") long userId) {
		m.addAttribute("user", userDAO.findById(userId));
	}

	/**
	 * Process the update of the {@link User}
	 * 
	 * @param user
	 *            The {@link User} object in the model
	 * @return The view which will be display
	 */
	@RequestMapping(value = "user/edit", method = RequestMethod.POST)
	public String update(@ModelAttribute("user") @Valid User user, BindingResult result) {
		if (result.hasErrors()) {
			return "user/edit";
		}
		try {
			userDAO.updateUser(user);
		} catch (HibernateJdbcException e) {
			try{
				User u = userDAO.findByLogin(user.getLogin());
				if (u.getId() != user.getId()){
					result.addError(new ObjectError("", "Login already exist"));
				}
				else {
					result.addError(new ObjectError("", "Email already used"));
				}
			}
			catch (EmptyResultDataAccessException e2) {
				result.addError(new ObjectError("", "Email already used"));
			}
			return "user/edit";
		}
		return "redirect:/user.html";
	}

	/**
	 * Returns a list of all tickets
	 * 
	 * @return The list of all tickets
	 */
	@ModelAttribute(value = "tickets")
	public List<Ticket> tickets(@RequestParam(required = false, value = "id") Long userId) {
		List<Ticket> tickets = ticketDAO.findAllEnabled();
		if (userId != null) {
			Set<Ticket> assignedTickets = userDAO.findById(userId).getAssignedTickets();
			tickets.removeAll(assignedTickets);
		}
		return tickets;
	}

	/**
	 * Register an editor for {@link Ticket} and {@link Role} objects
	 * 
	 * @param binder
	 *            The WebDataBinder
	 */
	@InitBinder
	public void binder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Ticket.class, ticketEditor);
		binder.registerCustomEditor(Role.class, roleEditor);
	}

	/**
	 * Assign the {@link Ticket} identified by the command object
	 * {@link TicketIdCommand} to the {@link User} identified by the given ID
	 * 
	 * @param userId
	 *            The request parameter "id" for the {@link User}
	 * @param ticketId
	 *            The command object identifying the {@link Ticket}
	 * @return The view which will be display
	 */
	@RequestMapping(value = "user/view", method = RequestMethod.POST)
	public String assign(@RequestParam(required = true, value = "id") long userId, @ModelAttribute("ticketId") TicketIdCommand ticketId) {
		User user = userDAO.findById(userId);
		Ticket ticket = ticketDAO.findById(ticketId.getTicketId());
		if (ticket != null){
			user.assign(ticket);
			userDAO.updateUser(user);
		}
		return "redirect:/user/view.html?id=" + userId;
	}
	
	@RequestMapping(value = "user/view/remove")
	public String unassign(@RequestParam(required = true, value = "id") long userId, @RequestParam(required = true, value = "ticketId") long ticketId) {
		User user = userDAO.findById(userId);
		Ticket ticket = ticketDAO.findById(ticketId);
		if (ticket != null){
			user.unassign(ticket);
			userDAO.updateUser(user);
		}
		return "redirect:/user/view.html?id=" + userId;
	}

	/**
	 * Display the form for user searching
	 * 
	 * @param m
	 *            The model
	 */
	@RequestMapping(value = "user/search")
	public void searchForm(Model m) {
		m.addAttribute("userQuery", new UserQueryCommand());
	}

	/**
	 * Execute the query
	 * 
	 * @param command
	 *            The query command
	 * @return The view which will be display
	 */
	@RequestMapping(value = "user/search", method = RequestMethod.POST)
	public ModelAndView search(@ModelAttribute("userQuery") UserQueryCommand command) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<User> result = null;
		Query<User> query = new Query<User>(User.class);

		if (!command.getLogin().isEmpty()){
			if (command.getLoginMatchMode() ==
				ProductQueryCommand.EXACT_MATCH_MODE){
				query.addFilter(new EqualFilter("login", command.getLogin()));
			}
			else{
				query.addFilter(new LikeFilter("login", command.getLogin()));
			}
		}

		if (!command.getName().isEmpty()){
			if (command.getNameMatchMode() ==
				ProductQueryCommand.EXACT_MATCH_MODE){
				query.addFilter(new EqualFilter("name", command.getName()));
			}
			else{
				query.addFilter(new LikeFilter("name", command.getName()));
			}
		}

		if (!command.getEmail().isEmpty()){
			if (command.getEmailMatchMode() ==
				ProductQueryCommand.EXACT_MATCH_MODE){
				query.addFilter(new EqualFilter("email", command.getEmail()));
			}
			else{
				query.addFilter(new LikeFilter("email", command.getEmail()));
			}
		}

		if (!command.getTitle().isEmpty()){
			if (command.getTitleMatchMode() ==
				ProductQueryCommand.EXACT_MATCH_MODE){
				query.addFilter(new EqualFilter("title", command.getTitle()));
			}
			else{
				query.addFilter(new LikeFilter("title", command.getTitle()));
			}
		}

		if (!command.getAccessLevel().equals("select")){
			Filter f;
			int accessLevel = Integer.parseInt(command.getAccessLevel());
			switch (command.getAccessLevelCompOp()) {
			case UserQueryCommand.LESS_THAN:
				f = new LessThanFilter("accessLevel", accessLevel);
				break;
			case UserQueryCommand.LESS_OR_EQUAL:
				f = new LessOrEqualFilter("accessLevel", accessLevel);
				break;
			case UserQueryCommand.EQUAL:
				f = new EqualFilter("accessLevel", accessLevel);
				break;
			case UserQueryCommand.GREATER_OR_EQUAL:
				f = new GreaterOrEqualFilter("accessLevel", accessLevel);
				break;
			case UserQueryCommand.GREATER_THAN:
				f = new GreaterThanFilter("accessLevel", accessLevel);
				break;
			default:
				f = null;
				break;
			}
			query.addFilter(f);
		}

		result = userDAO.filter(query);
		map.put("result", result);
		return new ModelAndView("user/result", map);
	}

	/**
	 * Mapping for result page
	 */
	@RequestMapping(value = "user/result")
	public void resultPage() {}

	/**
	 * Add the {@link User} object identified by the request parameter "id" to the model
	 * for changing his password. 
	 * 
	 * @param m
	 *            The model
	 * @param userId
	 *            The request parameter "id" for the {@link User}
	 */
	@RequestMapping(value = "user/chgpwd")
	public void formPassword(Model m, @RequestParam(required = true, value = "id") long userId) {
		m.addAttribute("chgPwdCommand", new ChangePasswordCommand(userId));
	}

	/**
	 * Change the password of the {@link User}
	 * 
	 * @param user
	 *            The {@link User} object in the model
	 * @return The view which will be display
	 */
	@RequestMapping(value = "user/chgpwd", method = RequestMethod.POST)
	public String changePassword(@ModelAttribute("chgPwdCommand") @Valid ChangePasswordCommand cmd, BindingResult result) {
		if (result.hasErrors()) {
			return "user/chgpwd";
		}
		User user = userDAO.findById(cmd.getUserId());
		if (userDAO.checkPassword(user, cmd.getOldPassword())){
			if (cmd.getNewPassword().equals(cmd.getConfirmPassword())){
				userDAO.changePassword(user, cmd.getNewPassword());
				return "redirect:/user/view.html?id=" + cmd.getUserId();
			}
			result.addError(new ObjectError("", "Passwords are different"));
			return "user/chgpwd";
		}
		result.addError(new ObjectError("", "Wrong password"));
		return "user/chgpwd";
	}

}
