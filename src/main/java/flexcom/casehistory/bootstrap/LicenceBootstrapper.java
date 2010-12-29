package flexcom.casehistory.bootstrap;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import flexcom.casehistory.ticket.dao.ClientDAO;
import flexcom.casehistory.ticket.dao.LicenceDAO;
import flexcom.casehistory.ticket.dao.MaintenanceDAO;
import flexcom.casehistory.ticket.dao.ProductDAO;
import flexcom.casehistory.ticket.entity.Client;
import flexcom.casehistory.ticket.entity.Licence;
import flexcom.casehistory.ticket.entity.Product;


@Component
public class LicenceBootstrapper implements Bootstrapper {
	
	@Autowired
	private LicenceDAO licenceDAO;
	
	@Autowired
	private ProductDAO productDAO;
	
	@Autowired
	private ClientDAO clientDAO;
	
	@Autowired
	private MaintenanceDAO maintenanceDAO;
	
	@Autowired
	private MaintenanceBootstrapper maintenanceBootstrapper;
	
	@Autowired
	private ClientBootstrapper clientBootstrapper;
	
	@Autowired
	private ProductBootstrapper productBootstrapper;
	
	@Override
	public void bootstrap() {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
		
		if (maintenanceDAO.count() == 0){
			maintenanceBootstrapper.bootstrap();
		}
		
		if (clientDAO.count() == 0){
			clientBootstrapper.bootstrap();
		}
		
		if (productDAO.count() == 0){
			productBootstrapper.bootstrap();
		}
		
		if (licenceDAO.count() == 0){
			Client client = clientDAO.findByName("Flexcom");
			Product product = productDAO.findByName("CaseHistory").get(0);
			
			Licence licence = new Licence();
			licence.setLicenceKey("52326-2302-9232-2309827");
			try {
				licence.setExpirationDate(dateFormatter.parse("31/12/2011"));
			}
			catch (ParseException e) {
				licence.setExpirationDate(null);
			}
			licence.setClient(client);
			licence.setProduct(product);
			licence.setMaintenance(maintenanceDAO.findByName("Full"));
			licenceDAO.createLicence(licence);
		}
	}
	
}
