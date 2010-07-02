package flexcom.casehistory.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import flexcom.casehistory.ticket.dao.ClientDAO;
import flexcom.casehistory.ticket.dao.LicenceDAO;
import flexcom.casehistory.ticket.dao.MaintenanceDAO;
import flexcom.casehistory.ticket.dao.ProductDAO;
import flexcom.casehistory.ticket.entity.Licence;

/**
 * {@link Licence} data set
 * 
 * @author philippe
 * 
 */
@Component
public class LicenceDataSet extends Data {

	/**
	 * Licence DAO
	 */
	@Autowired
	private LicenceDAO licenceDAO;
	
	@Autowired
	private MaintenanceDAO maintenanceDAO;
	
	@Autowired
	private ClientDAO clientDAO;
	
	@Autowired
	private ProductDAO productDAO;
	
	@Autowired
	private MaintenanceDataSet maintenanceDataSet;

	@Autowired
	private ClientDataSet clientDataSet;

	@Autowired
	private ProductDataSet productDataSet;

	/**
	 * Set up all licences
	 */
	public void setup() {
		if (maintenanceDAO.count() == 0){
			maintenanceDataSet.setup();
		}
		
		if (clientDAO.count() == 0){
			clientDataSet.setup();
		}
		
		if (productDAO.count() == 0){
			productDataSet.setup();
		}
		
		if (licenceDAO.count() == 0) {
			LICENCE_1 = new Licence();
			LICENCE_1.setLicenceKey("2957-920-283-7889");
			LICENCE_1.setClient(CLIENT_1);
			LICENCE_1.setProduct(PRODUCT_1);
			LICENCE_1.setMaintenance(MAINTENANCE_NONE);
			licenceDAO.createLicence(LICENCE_1);
			LICENCE_2 = new Licence();
			LICENCE_2.setLicenceKey("key");
			LICENCE_2.setClient(CLIENT_1);
			LICENCE_2.setProduct(PRODUCT_1);
			LICENCE_2.setMaintenance(MAINTENANCE_NONE);
			licenceDAO.createLicence(LICENCE_2);
		}
	}

	/**
	 * Clear all licences
	 */
	public void clear() {
		licenceDAO.deleteAll();
		productDAO.deleteAll();
		clientDAO.deleteAll();
		maintenanceDAO.deleteAll();
	}

}
