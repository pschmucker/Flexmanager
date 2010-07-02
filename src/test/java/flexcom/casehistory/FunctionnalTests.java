package flexcom.casehistory;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import flexcom.casehistory.ticket.TicketSuite;

@RunWith(Suite.class)
@SuiteClasses(value = { TicketSuite.class })
public class FunctionnalTests {

}
