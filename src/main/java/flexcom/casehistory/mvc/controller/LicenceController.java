package flexcom.casehistory.mvc.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
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
import flexcom.casehistory.mvc.editor.MaintenanceEditor;
import flexcom.casehistory.mvc.editor.ProductEditor;
import flexcom.casehistory.ticket.dao.ClientDAO;
import flexcom.casehistory.ticket.dao.LicenceDAO;
import flexcom.casehistory.ticket.dao.MaintenanceDAO;
import flexcom.casehistory.ticket.dao.ProductDAO;
import flexcom.casehistory.ticket.entity.Client;
import flexcom.casehistory.ticket.entity.Licence;
import flexcom.casehistory.ticket.entity.Maintenance;
import flexcom.casehistory.ticket.entity.Product;
import flexcom.casehistory.ticket.search.Query;
import flexcom.casehistory.ticket.search.filter.EqualFilter;
import flexcom.casehistory.ticket.search.filter.LikeFilter;

/**
 * 
 * Controller class for managing licences
 * 
 * @author philippe
 * 
 */
@Controller
public class LicenceController {

	/**
	 * Maintenance DAO
	 */
	@Autowired
	private MaintenanceDAO maintenanceDAO;

	/**
	 * Licence DAO
	 */
	@Autowired
	private LicenceDAO licenceDAO;

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
	 * Maintenance editor
	 */
	@Autowired
	private MaintenanceEditor maintenanceEditor;

	/**
	 * Add the list of all licences to the model
	 * 
	 * @param m
	 *            The model
	 */
	@RequestMapping(value = "licence")
	public void list(Model m) {
		m.addAttribute("list", licenceDAO.findAll());
	}

	/**
	 * Add the {@link Licence} object identified by the given ID
	 * 
	 * @param m
	 *            The model
	 * @param licenceId
	 *            The request parameter "id" for the {@link Licence}
	 */
	@RequestMapping(value = "licence/view")
	public void view(Model m, @RequestParam(required = true, value = "id") long licenceId) {
		m.addAttribute("licence", licenceDAO.findById(licenceId));
	}

	/**
	 * Add a new {@link Licence} object to the model
	 * 
	 * @param m
	 *            The model
	 */
	@RequestMapping(value = "licence/add")
	public void add(Model m) {
		m.addAttribute("licence", new Licence());
	}

	/**
	 * Process the creation of the {@link Licence}
	 * 
	 * @param licence
	 *            The {@link Licence} object in the model
	 * @return The view which will be display
	 */
	@RequestMapping(value = "licence/add", method = RequestMethod.POST)
	public String create(@ModelAttribute("licence") @Valid Licence licence, BindingResult result) {
		if (result.hasErrors()) {
			return "licence/add";
		}
		Date currentDate = new Date();
		licence.setCreationDate(currentDate);
		if (licence.getBeginningDate() == null){
			licence.setBeginningDate(currentDate);
		}
		licenceDAO.createLicence(licence);
		return "redirect:/licence.html";
	}

	/**
	 * Returns a list of all clients
	 * 
	 * @return The list of all clients
	 */
	@ModelAttribute(value = "clients")
	public List<Client> clients() {
		return clientDAO.findAllEnabled();
	}

	/**
	 * Returns a list of all products
	 * 
	 * @return The list of all products
	 */
	@ModelAttribute(value = "products")
	public List<Product> products() {
		return productDAO.findAllEnabled();
	}

	/**
	 * Register three editors :<br/>
	 * <ul>
	 * <li>for Date object</li>
	 * <li>for Client object</li>
	 * <li>for Product object</li>
	 * </ul>
	 * 
	 * @param binder
	 *            The WebDataBinder
	 */
	@InitBinder
	public void binder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Client.class, clientEditor);
		binder.registerCustomEditor(Product.class, productEditor);
		binder.registerCustomEditor(Maintenance.class, maintenanceEditor);
	}

	/**
	 * Delete the {@link Licence} object identified by the given ID
	 * 
	 * @param licenceId
	 *            The request parameter "id" for the {@link Licence}
	 * @return The view which will be display
	 */
	@RequestMapping(value = "licence/delete")
	public String delete(@RequestParam(required = true, value = "id") long licenceId) {
		Licence licence = licenceDAO.findById(licenceId);
		licence.setEnabled(false);
		licenceDAO.updateLicence(licence);
		return "redirect:/licence.html";
	}

	/**
	 * Add the {@link Licence} object identified by the given ID to the model
	 * 
	 * @param m
	 *            The model
	 * @param licenceId
	 *            The request parameter "id" for the {@link Licence}
	 */
	@RequestMapping(value = "licence/edit")
	public void edit(Model m, @RequestParam(required = true, value = "id") long licenceId) {
		m.addAttribute("licence", licenceDAO.findById(licenceId));
	}

	/**
	 * Process the update of the {@link Licence}
	 * 
	 * @param licence
	 *            The {@link Licence} object in the model
	 * @return The view which will be display
	 */
	@RequestMapping(value = "licence/edit", method = RequestMethod.POST)
	public String update(@ModelAttribute("licence") @Valid Licence licence, BindingResult result) {
		if (result.hasErrors()) {
			return "licence/edit";
		}
		licenceDAO.updateLicence(licence);
		return "redirect:/licence.html";
	}

	/**
	 * Display the form for licence searching
	 * 
	 * @param m
	 *            The model
	 */
	@RequestMapping(value = "licence/search")
	public void searchForm(Model m) {
		m.addAttribute("licenceQuery", new LicenceQueryCommand());
	}

	/**
	 * Execute the query
	 * 
	 * @param command
	 *            The query command
	 * @return The view which will be display
	 */
	@RequestMapping(value = "licence/search", method = RequestMethod.POST)
	public ModelAndView search(@ModelAttribute("licenceQuery") LicenceQueryCommand command) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Licence> result = null;
		Query<Licence> query = new Query<Licence>(Licence.class);

		if (!command.getKey().isEmpty()) {
			if (command.getKeyMatchMode() == LicenceQueryCommand.EXACT_MATCH_MODE) {
				query.addFilter(new EqualFilter("licenceKey", command.getKey()));
			} else {
				query.addFilter(new LikeFilter("licenceKey", command.getKey()));
			}
		}
		
		if (!command.getClientId().equals("select")){
			query.addFilter(new EqualFilter("client", clientDAO.findById(Long.parseLong(command.getClientId()))));
		}
		
		if (!command.getProductId().equals("select")){
			query.addFilter(new EqualFilter("product", productDAO.findById(Long.parseLong(command.getProductId()))));
		}

		result = licenceDAO.filter(query);
		map.put("result", result);
		return new ModelAndView("licence/result", map);
	}

	/**
	 * Mapping for result page
	 */
	@RequestMapping(value = "licence/result")
	public void resultPage() {
	}
	
	
	/**
	 * Returns a list of all maintenances
	 * 
	 * @return The list of all maintenances
	 */
	@ModelAttribute(value = "maintenances")
	public List<Maintenance> maintenances() {
		return maintenanceDAO.findAllEnabled();
	}

}