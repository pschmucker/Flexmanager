package flexcom.casehistory.ticket.search.filter;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 * Implementation of an IS NOT EMPTY filter
 * 
 * @author philippe
 * 
 */
public class IsNotEmptyFilter implements Filter {
	
	/**
	 * Tested property
	 */
	private String property;

	/**
	 * Constructor requiring a property
	 * 
	 * @param property
	 *            Tested property
	 * @throws IllegalArgumentException
	 *             when the property is <code>null</code>
	 */
	public IsNotEmptyFilter(String property) {
		if (property == null){
			throw new IllegalArgumentException("Property can't be null");
		}
		this.property = property;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Criterion getHibernateCriterion() {
		return Restrictions.isNotEmpty(property);
	}

}
