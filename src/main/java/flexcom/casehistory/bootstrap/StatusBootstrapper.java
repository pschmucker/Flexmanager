package flexcom.casehistory.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import flexcom.casehistory.ticket.dao.StatusDAO;
import flexcom.casehistory.ticket.entity.Status;


@Component
public class StatusBootstrapper implements Bootstrapper {
	
	@Autowired
	private StatusDAO statusDAO;
	
	@Override
	public void bootstrap() {
		if (statusDAO.count() == 0){
			statusDAO.createStatus(Status.getStatus(Status.NEW));
			statusDAO.createStatus(Status.getStatus(Status.PENDING));
			statusDAO.createStatus(Status.getStatus(Status.ASSIGNED));
			statusDAO.createStatus(Status.getStatus(Status.REOPEN));
			statusDAO.createStatus(Status.getStatus(Status.RESOLVED));
			statusDAO.createStatus(Status.getStatus(Status.CUSTOMER_FEEDBACK));
			statusDAO.createStatus(Status.getStatus(Status.FLEXCOM_FEEDBACK));
			statusDAO.createStatus(Status.getStatus(Status.CLOSED));
		}
	}
	
}
