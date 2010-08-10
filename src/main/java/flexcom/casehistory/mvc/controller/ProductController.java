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

import flexcom.casehistory.ticket.dao.ProductDAO;
import flexcom.casehistory.ticket.entity.Product;
import flexcom.casehistory.ticket.search.Query;
import flexcom.casehistory.ticket.search.filter.EqualFilter;
import flexcom.casehistory.ticket.search.filter.LikeFilter;

/**
 * 
 * Controller class for managing products
 * 
 * @author philippe
 * 
 */
@Controller
public class ProductController {

	/**
	 * Product DAO
	 */
	@Autowired
	private ProductDAO productDAO;

	/**
	 * Add a list of all products to the model
	 * 
	 * @param m
	 *            The model
	 */
	@RequestMapping(value = "product")
	public void list(Model m) {
		m.addAttribute("list", productDAO.findAll());
	}

	/**
	 * Add the {@link Product} object identified by the given ID to the model
	 * 
	 * @param m
	 *            The model
	 * @param productId
	 *            The request parameter "id" for the {@link Product}
	 */
	@RequestMapping(value = "product/view")
	public void view(Model m, @RequestParam(required = true, value = "id") long productId) {
		m.addAttribute("product", productDAO.findById(productId));
	}

	/**
	 * Add a new {@link Product} object to the model
	 * 
	 * @param m
	 *            The model
	 */
	@RequestMapping(value = "product/add")
	public void add(Model m) {
		m.addAttribute("product", new Product());
	}

	/**
	 * Process the creation of the {@link Product}
	 * 
	 * @param product
	 *            The {@link Product} object in the model
	 * @return The view which will be display
	 */
	@RequestMapping(value = "product/add", method = RequestMethod.POST)
	public String create(@ModelAttribute("product") @Valid Product product, BindingResult result) {
		if (result.hasErrors()) {
			return "product/add";
		}
		try {
			productDAO.createProduct(product);
		} catch (DataIntegrityViolationException e) {
			result.addError(new ObjectError("", "Product with this version already exist"));
			return "product/add";
		}
		return "redirect:/product.html";
	}

	/**
	 * Delete the {@link Product} object identified by the given ID
	 * 
	 * @param productId
	 *            The request parameter "id" for the {@link Product}
	 * @return The view which will be display
	 */
	@RequestMapping(value = "product/delete")
	public String delete(@RequestParam(required = true, value = "id") long productId) {
		Product product = productDAO.findById(productId);
		product.setEnabled(false);
		productDAO.updateProduct(product);
		return "redirect:/product.html";
	}

	/**
	 * Add the {@link Product} object identified by the given ID to the model
	 * 
	 * @param m
	 *            The model
	 * @param productId
	 *            The request parameter "id" for the {@link Product}
	 */
	@RequestMapping(value = "product/edit")
	public void edit(Model m, @RequestParam(required = true, value = "id") long productId) {
		m.addAttribute("product", productDAO.findById(productId));
	}

	/**
	 * Process the update of the {@link Product} object
	 * 
	 * @param product
	 *            The {@link Product} object in the model
	 * @return The view which will be display
	 */
	@RequestMapping(value = "product/edit", method = RequestMethod.POST)
	public String update(@ModelAttribute("product") @Valid Product product, BindingResult result) {
		if (result.hasErrors()) {
			return "product/edit";
		}
		try {
			productDAO.updateProduct(product);
		} catch (HibernateJdbcException e) {
			result.addError(new ObjectError("", "Product with this version already exist"));
			return "product/edit";
		}
		return "redirect:/product.html";
	}

	/**
	 * Display the form for product searching
	 * 
	 * @param m
	 *            The model
	 */
	@RequestMapping(value = "product/search")
	public void searchForm(Model m) {
		m.addAttribute("productQuery", new ProductQueryCommand());
	}

	/**
	 * Execute the query
	 * 
	 * @param command
	 *            The query command
	 * @return The view which will be display
	 */
	@RequestMapping(value = "product/search", method = RequestMethod.POST)
	public ModelAndView search(@ModelAttribute("productQuery") ProductQueryCommand command) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Product> result = null;
		Query<Product> query = new Query<Product>(Product.class);
		if (!command.getName().isEmpty()){
			if (command.getNameMatchMode() == ProductQueryCommand.EXACT_MATCH_MODE){
				query.addFilter(new EqualFilter("name", command.getName()));
			}
			else{
				query.addFilter(new LikeFilter("name", command.getName()));
			}
		}
		if (!command.getVersion().isEmpty()){
			if (command.getVersionMatchMode() == ProductQueryCommand.EXACT_MATCH_MODE){
				query.addFilter(new EqualFilter("version", command.getVersion()));
			}
			else{
				query.addFilter(new LikeFilter("version", command.getVersion()));
			}
		}
		result = productDAO.filter(query);
		map.put("result", result);
		return new ModelAndView("product/result", map);
	}

	/**
	 * Mapping for result page
	 */
	@RequestMapping(value = "product/result")
	public void resultPage() {
	}
}