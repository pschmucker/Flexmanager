package flexcom.casehistory.mvc.controller.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses(value = { 
	ClientControllerTest.class, ProductControllerTest.class, TicketControllerTest.class, 
	UserControllerTest.class, LicenceControllerTest.class
})
public class ControllerSuite {

}
