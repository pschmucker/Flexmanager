package flexcom.casehistory.mvc.editor;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import flexcom.casehistory.ticket.dao.ProductDAO;
import flexcom.casehistory.ticket.entity.Product;

/**
 * Editor for {@link Product} objects
 * 
 * @author philippe
 * 
 */
@Component
public class ProductEditor extends PropertyEditorSupport {

	/**
	 * Product DAO
	 */
	@Autowired
	private ProductDAO productDAO;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getAsText() {
		Product p = (Product) getValue();
		return (p == null) ? null : "" + p.getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setAsText(String text) {
		setValue((text == null) ? null : productDAO.findById(Long.parseLong(text)));
	}
}
