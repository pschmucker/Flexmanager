package flexcom.casehistory.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import flexcom.casehistory.ticket.dao.StatusDAO;
import flexcom.casehistory.ticket.entity.Status;

/**
 * {@link Status} data set
 * 
 * @author philippe
 * 
 */
@Component
public class StatusDataSet extends Data {

	/**
	 * Status DAO
	 */
	@Autowired
	private StatusDAO statusDAO;
	
	/**
	 * Set up all statuses
	 */
	public void setup() {
		if (statusDAO.count() == 0) {
			STATUS_ASSIGNED = Status.getStatus(Status.ASSIGNED);
			STATUS_CLOSED = Status.getStatus(Status.CLOSED);
			STATUS_CUSTOMER_FEEDBACK = Status
					.getStatus(Status.CUSTOMER_FEEDBACK);
			STATUS_FLEXCOM_FEEDBACK = Status.getStatus(Status.FLEXCOM_FEEDBACK);
			STATUS_NEW = Status.getStatus(Status.NEW);
			STATUS_PENDING = Status.getStatus(Status.PENDING);
			STATUS_REOPEN = Status.getStatus(Status.REOPEN);
			STATUS_RESOLVED = Status.getStatus(Status.RESOLVED);
			statusDAO.createStatus(STATUS_ASSIGNED);
			statusDAO.createStatus(STATUS_CLOSED);
			statusDAO.createStatus(STATUS_CUSTOMER_FEEDBACK);
			statusDAO.createStatus(STATUS_FLEXCOM_FEEDBACK);
			statusDAO.createStatus(STATUS_NEW);
			statusDAO.createStatus(STATUS_PENDING);
			statusDAO.createStatus(STATUS_REOPEN);
			statusDAO.createStatus(STATUS_RESOLVED);
		}
	}

	/**
	 * Clear all statuses
	 */
	public void clear() {
		statusDAO.deleteAll();
	}

}
