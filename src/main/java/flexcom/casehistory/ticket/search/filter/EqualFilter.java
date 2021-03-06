package flexcom.casehistory.ticket.search.filter;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 * Implementation of an EQUAL filter
 * 
 * @author philippe
 * 
 */
public class EqualFilter implements Filter {

	/**
	 * Tested property
	 */
	private String property;

	/**
	 * Tested value
	 */
	private Object value;

	/**
	 * Constructor requiring a property and a value
	 * 
	 * @param property
	 *            Tested property
	 * @param value
	 *            Tested value
	 * @throws IllegalArgumentException
	 *             when the property is <code>null</code>
	 */
	public EqualFilter(String property, Object value) {
		if (property == null) {
			throw new IllegalArgumentException("Property can't be null");
		}
		this.property = property;
		this.value = value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Criterion getHibernateCriterion() {
		if (value == null) {
			return Restrictions.isNull(property);
		}
		return Restrictions.eq(property, value);
	}

}
