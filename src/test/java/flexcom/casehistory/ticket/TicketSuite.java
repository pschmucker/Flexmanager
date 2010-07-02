package flexcom.casehistory.ticket;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses(value = { TicketCreationTest.class, TicketAssignmentTest.class, TicketSearchTest.class })
public class TicketSuite {

}
