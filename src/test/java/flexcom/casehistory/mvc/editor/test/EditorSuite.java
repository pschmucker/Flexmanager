package flexcom.casehistory.mvc.editor.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses(value = { 
	ProductEditorTest.class, CompanyEditorTest.class, TicketEditorTest.class, UserEditorTest.class, 
	PriorityEditorTest.class, StatusEditorTest.class
})
public class EditorSuite {

}
