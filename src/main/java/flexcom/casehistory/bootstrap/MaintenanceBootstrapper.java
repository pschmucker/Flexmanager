package flexcom.casehistory.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import flexcom.casehistory.ticket.dao.MaintenanceDAO;
import flexcom.casehistory.ticket.entity.Maintenance;


@Component
public class MaintenanceBootstrapper implements Bootstrapper {
	
	@Autowired
	private MaintenanceDAO maintenanceDAO;
	
	@Override
	public void bootstrap() {
		if (maintenanceDAO.count() == 0){
			maintenanceDAO.createMaintenance(Maintenance.getMaintenance(Maintenance.FULL));
			maintenanceDAO.createMaintenance(Maintenance.getMaintenance(Maintenance.TEST));
			maintenanceDAO.createMaintenance(Maintenance.getMaintenance(Maintenance.NONE));
		}
	}
	
}
