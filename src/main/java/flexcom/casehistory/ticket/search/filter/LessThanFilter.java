package flexcom.casehistory.ticket.search.filter;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 * Implementation of a LESS THAN filter
 * 
 * @author philippe
 * 
 */
public class LessThanFilter implements Filter {

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
	 *             when the property or value is <code>null</code>
	 */
	public LessThanFilter(String property, Object value) {
		if (property == null){
			throw new IllegalArgumentException("Property can't be null");
		}
		if (value == null){
			throw new IllegalArgumentException("Value can't be null");
		}
		this.property = property;
		this.value = value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Criterion getHibernateCriterion() {
		return Restrictions.lt(property, value);
	}

}
