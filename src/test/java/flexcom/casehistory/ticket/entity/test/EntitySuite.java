package flexcom.casehistory.ticket.entity.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses(value = { 
	PriorityTest.class, StatusTest.class, ProductTest.class,
	UserTest.class, TicketTest.class, ClientTest.class, 
	NoteTest.class, EventTest.class, 
	UserConstraintsTest.class, ClientConstraintsTest.class,
	ProductConstraintsTest.class, TicketConstraintsTest.class, 
	LicenceTest.class, LicenceConstraintsTest.class, 
	NoteConstraintsTest.class, EventConstraintsTest.class, 
	PriorityConstraintsTest.class, StatusConstraintsTest.class, 
	MaintenanceTest.class, MaintenanceConstraintsTest.class
})
public class EntitySuite {

}
