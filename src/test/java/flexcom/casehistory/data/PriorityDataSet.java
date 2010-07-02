package flexcom.casehistory.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import flexcom.casehistory.ticket.dao.PriorityDAO;
import flexcom.casehistory.ticket.entity.Priority;

/**
 * {@link Priority} data set
 * 
 * @author philippe
 * 
 */
@Component
public class PriorityDataSet extends Data {

	/**
	 * Priority DAO
	 */
	@Autowired
	private PriorityDAO priorityDAO;
	
	/**
	 * Set up all priorities
	 */
	public void setup() {
		if (priorityDAO.count() == 0) {
			PRIORITY_HIGH = Priority.getPriority(Priority.HIGH);
			PRIORITY_IMMEDIATE = Priority.getPriority(Priority.IMMEDIATE);
			PRIORITY_LOW = Priority.getPriority(Priority.LOW);
			PRIORITY_MEDIUM = Priority.getPriority(Priority.MEDIUM);
			PRIORITY_NONE = Priority.getPriority(Priority.NONE);
			PRIORITY_URGENT = Priority.getPriority(Priority.URGENT);
			priorityDAO.createPriority(PRIORITY_HIGH);
			priorityDAO.createPriority(PRIORITY_IMMEDIATE);
			priorityDAO.createPriority(PRIORITY_LOW);
			priorityDAO.createPriority(PRIORITY_MEDIUM);
			priorityDAO.createPriority(PRIORITY_NONE);
			priorityDAO.createPriority(PRIORITY_URGENT);
		}
	}

	/**
	 * Clear all priorities
	 */
	public void clear() {
		priorityDAO.deleteAll();
	}

}
