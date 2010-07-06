package flexcom.casehistory.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import flexcom.casehistory.ticket.dao.ProductDAO;
import flexcom.casehistory.ticket.entity.Product;

/**
 * {@link Product} data set
 * 
 * @author philippe
 * 
 */
@Component
public class ProductDataSet extends Data {

	/**
	 * Product DAO
	 */
	@Autowired
	private ProductDAO productDAO;

	/**
	 * Set up all products
	 */
	public void setup() {
		if (productDAO.count() == 0) {
			PRODUCT_1 = new Product();
			PRODUCT_1.setName("P1");
			PRODUCT_1.setVersion("1.0");
			productDAO.createProduct(PRODUCT_1);
			PRODUCT_2 = new Product();
			PRODUCT_2.setName("P2");
			PRODUCT_2.setVersion("1.0");
			productDAO.createProduct(PRODUCT_2);
		}
	}

	/**
	 * Clear all products
	 */
	public void clear() {
		productDAO.deleteAll();
	}

}
