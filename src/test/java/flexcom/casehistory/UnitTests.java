package flexcom.casehistory;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import flexcom.casehistory.mvc.controller.test.ControllerSuite;
import flexcom.casehistory.mvc.editor.test.EditorSuite;
import flexcom.casehistory.ticket.dao.test.DaoSuite;
import flexcom.casehistory.ticket.entity.test.EntitySuite;
import flexcom.casehistory.ticket.search.test.SearchSuite;

@RunWith(Suite.class)
@SuiteClasses(value = { 
	SearchSuite.class, EntitySuite.class, DaoSuite.class, EditorSuite.class,
	ControllerSuite.class// TODO, AspectSuite.class
})
public class UnitTests {

}
