package flexcom.casehistory.ticket.search.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import flexcom.casehistory.ticket.search.filter.test.FilterSuite;

@RunWith(Suite.class)
@SuiteClasses(value = { FilterSuite.class, QueryTest.class })
public class SearchSuite {

}
