package flexcom.casehistory.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import flexcom.casehistory.ticket.dao.MaintenanceDAO;
import flexcom.casehistory.ticket.entity.Maintenance;

/**
 * {@link Maintenance} data set
 * 
 * @author philippe
 * 
 */
@Component
public class MaintenanceDataSet extends Data {
	
	@Autowired
	private MaintenanceDAO maintenanceDAO;
	
	/**
	 * Set up all maintenances
	 */
	public void setup() {
		if (maintenanceDAO.count() == 0) {
			MAINTENANCE_FULL = Maintenance.getMaintenance(Maintenance.FULL);
			MAINTENANCE_TEST = Maintenance.getMaintenance(Maintenance.TEST);
			MAINTENANCE_NONE = Maintenance.getMaintenance(Maintenance.NONE);
			maintenanceDAO.createMaintenance(MAINTENANCE_FULL);
			maintenanceDAO.createMaintenance(MAINTENANCE_TEST);
			maintenanceDAO.createMaintenance(MAINTENANCE_NONE);
		}
	}

	/**
	 * Clear all maintenances
	 */
	public void clear() {
		maintenanceDAO.deleteAll();
	}

}
