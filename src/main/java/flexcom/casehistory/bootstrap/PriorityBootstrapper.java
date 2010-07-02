package flexcom.casehistory.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import flexcom.casehistory.ticket.dao.PriorityDAO;
import flexcom.casehistory.ticket.entity.Priority;


@Component
public class PriorityBootstrapper implements Bootstrapper {
	
	@Autowired
	private PriorityDAO priorityDAO;
	
	@Override
	public void bootstrap() {
		if (priorityDAO.count() == 0){
			priorityDAO.createPriority(Priority.getPriority(Priority.IMMEDIATE));
			priorityDAO.createPriority(Priority.getPriority(Priority.URGENT));
			priorityDAO.createPriority(Priority.getPriority(Priority.HIGH));
			priorityDAO.createPriority(Priority.getPriority(Priority.MEDIUM));
			priorityDAO.createPriority(Priority.getPriority(Priority.LOW));
			priorityDAO.createPriority(Priority.getPriority(Priority.NONE));
		}
	}
	
}
