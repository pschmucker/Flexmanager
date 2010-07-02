package flexcom.casehistory.ticket.search.filter.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses(value = { 
	EqualFilterTest.class, IsEmptyFilterTest.class, LessThanFilterTest.class, 
	LessOrEqualFilterTest.class, GreaterThanFilterTest.class, GreaterOrEqualFilterTest.class, 
	BetweenFilterTest.class, IsNotEmptyFilterTest.class, IsNotNullFilterTest.class,
	IsNullFilterTest.class, OrFilterTest.class, NotFilterTest.class, 
	AndFilterTest.class, XorFilterTest.class, InFilterTest.class,
	NotEqualFilterTest.class, LikeFilterTest.class
})
public class FilterSuite {

}
