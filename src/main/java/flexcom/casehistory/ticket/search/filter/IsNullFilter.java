package flexcom.casehistory.ticket.search.filter;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 * Implementation of an IS NULL filter
 * 
 * @author philippe
 * 
 */
public class IsNullFilter implements Filter {
	
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
	public IsNullFilter(String property) {
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
		return Restrictions.isNull(property);
	}

}
