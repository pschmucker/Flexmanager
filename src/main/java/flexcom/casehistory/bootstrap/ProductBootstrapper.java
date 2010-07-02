package flexcom.casehistory.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import flexcom.casehistory.ticket.dao.ProductDAO;
import flexcom.casehistory.ticket.entity.Product;


@Component
public class ProductBootstrapper implements Bootstrapper {
	
	@Autowired
	private ProductDAO productDAO;
	
	@Override
	public void bootstrap() {
		if (productDAO.count() == 0){
			Product product = new Product();
			product.setName("Calltax");
			product.setVersion("3.0");
			product.setBuild("2635");
			productDAO.createProduct(product);
			
			product = new Product();
			product.setName("CaseHistory");
			product.setVersion("0.1");
			product.setBuild("328");
			productDAO.createProduct(product);
		}
	}
	
}
