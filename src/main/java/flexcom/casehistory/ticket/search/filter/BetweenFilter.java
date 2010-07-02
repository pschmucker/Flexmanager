package flexcom.casehistory.ticket.search.filter;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 * Implementation of a BETWEEN filter
 * 
 * @author philippe
 * 
 */
public class BetweenFilter implements Filter {

	/**
	 * Tested property
	 */
	private String property;

	/**
	 * Tested low value
	 */
	private Object lowValue;

	/**
	 * Tested high value
	 */
	private Object highValue;

	/**
	 * Constructor requiring a property and a value
	 * 
	 * @param property
	 *            Tested property
	 * @param lowValue
	 *            Tested low value
	 * @param highValue
	 *            Tested high value
	 * @throws IllegalArgumentException
	 *             when the property or one of the value is <code>null</code>
	 */
	public BetweenFilter(String property, Object lowValue, Object highValue) {
		if (property == null){
			throw new IllegalArgumentException("Property can't be null");
		}
		if (lowValue == null || highValue == null){
			throw new IllegalArgumentException("Values can't be null");
		}
		this.property = property;
		this.lowValue = lowValue;
		this.highValue = highValue;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public Criterion getHibernateCriterion() {
		return Restrictions.between(property, lowValue, highValue);
	}

}
