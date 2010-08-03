package flexcom.casehistory.ticket.dao.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses(value = { 
	UserDAOImplTest.class, TicketDAOImplTest.class, ClientDAOImplTest.class, ProductDAOImplTest.class, 
	LicenceDAOImplTest.class, NoteDAOImplTest.class, EventDAOImplTest.class, PriorityDAOImplTest.class, 
	StatusDAOImplTest.class, MaintenanceDAOImplTest.class, HibernateQueryProcessorTest.class
})
public class DaoSuite {

}
