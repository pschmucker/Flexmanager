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

import flexcom.casehistory.ticket.dao.PartnerDAO;
import flexcom.casehistory.ticket.entity.Partner;
import flexcom.casehistory.ticket.search.Query;
import flexcom.casehistory.ticket.search.filter.EqualFilter;
import flexcom.casehistory.ticket.search.filter.LikeFilter;

/**
 * 
 * Controller class for managing partners
 * 
 * @author philippe
 * 
 */
@Controller
public class PartnerController {
	
	/**
	 * Partner DAO
	 */
	@Autowired
	private PartnerDAO partnerDAO;
	
	/**
	 * Add a list of all partners to the model
	 * 
	 * @param m
	 *            The model
	 */
	@RequestMapping(value = "partner")
	public void list(Model m) {
		m.addAttribute("list", partnerDAO.findAll());
	}

	/**
	 * Add the {@link Partner} object identified by the given ID to the model
	 * 
	 * @param m
	 *            The model
	 * @param partnerId
	 *            The request parameter "id" for the {@link Partner}
	 */
	@RequestMapping(value = "partner/view")
	public void view(Model m, @RequestParam(required = true, value = "id") long partnerId) {
		m.addAttribute("partner", partnerDAO.findById(partnerId));
	}

	/**
	 * Add a new {@link Partner} object to the model
	 * 
	 * @param m
	 *            The model
	 */
	@RequestMapping(value = "partner/add")
	public void add(Model m) {
		m.addAttribute("partner", new Partner());
	}

	/**
	 * Process the creation of the {@link Partner}
	 * 
	 * @param partner
	 *            The {@link Partner} object in the model
	 * @return The view which will be display
	 */
	@RequestMapping(value = "partner/add", method = RequestMethod.POST)
	public String create(@ModelAttribute("partner") @Valid Partner partner, BindingResult result) {
		if (result.hasErrors()) {
			return "partner/add";
		}
		try {
			partnerDAO.createPartner(partner);
		} catch (DataIntegrityViolationException e) {
			result.addError(new ObjectError("", "Company with this name already exist"));
			return "partner/add";
		}
		return "redirect:/partner.html";
	}

	/**
	 * Delete the {@link Partner} object identified by the given ID
	 * 
	 * @param partnerId
	 *            The request parameter "id" for the {@link Partner}
	 * @return The view which will be display
	 */
	@RequestMapping(value = "partner/delete")
	public String delete(@RequestParam(required = true, value = "id") long partnerId) {
		Partner partner = partnerDAO.findById(partnerId);
		partner.setEnabled(false);
		partnerDAO.updatePartner(partner);
		return "redirect:/partner.html";
	}

	/**
	 * Add the {@link Partner} object identified by the given ID to the model
	 * 
	 * @param m
	 *            The model
	 * @param partnerId
	 *            The request parameter "id" for the {@link Partner}
	 */
	@RequestMapping(value = "partner/edit")
	public void edit(Model m, @RequestParam(required = true, value = "id") long partnerId) {
		m.addAttribute("partner", partnerDAO.findById(partnerId));
	}

	/**
	 * Process the update of the {@link Partner}
	 * 
	 * @param partner
	 *            The {@link Partner} object in the model
	 * @return The view which will be display
	 */
	@RequestMapping(value = "partner/edit", method = RequestMethod.POST)
	public String update(@ModelAttribute("partner") @Valid Partner partner, BindingResult result) {
		if (result.hasErrors()) {
			return "partner/edit";
		}
		try {
			partnerDAO.updatePartner(partner);
		} catch (HibernateJdbcException e) {
			result.addError(new ObjectError("", "Company with this name already exist"));
			return "partner/edit";
		}
		return "redirect:/partner.html";
	}

	/**
	 * Display the form for partner searching
	 * 
	 * @param m
	 *            The model
	 */
	@RequestMapping(value = "partner/search")
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
	@RequestMapping(value = "partner/search", method = RequestMethod.POST)
	public ModelAndView search(@ModelAttribute("companyQuery") CompanyQueryCommand command) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Partner> result = null;
		Query<Partner> query = new Query<Partner>(Partner.class);
		if (!command.getCompanyName().isEmpty()){
			if (command.getMatchMode() == CompanyQueryCommand.EXACT_MATCH_MODE) {
				query.addFilter(new EqualFilter("name", command.getCompanyName()));
			} else {
				query.addFilter(new LikeFilter("name", command.getCompanyName()));
			}
		}
		result = partnerDAO.filter(query);
		map.put("result", result);
		return new ModelAndView("partner/result", map);
	}

	/**
	 * Mapping for result page
	 */
	@RequestMapping(value = "partner/result")
	public void resultPage() {}

}