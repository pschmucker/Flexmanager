package flexcom.casehistory.ticket.search.filter;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 * Implementation of an AND filter
 * 
 * @author philippe
 * 
 */
public class AndFilter implements Filter {

	/**
	 * First {@link Filter} for AND operator
	 */
	private Filter firstFilter;

	/**
	 * Second {@link Filter} for AND operator
	 */
	private Filter secondFilter;

	/**
	 * Constructor requiring 2 filters
	 * 
	 * @param firstFilter
	 *            First {@link Filter} for AND operator
	 * @param secondFilter
	 *            Second {@link Filter} for AND operator
	 * @throws IllegalArgumentException
	 *             when one of the {@link Filter} arguments is <code>null</code>
	 */
	public AndFilter(Filter firstFilter, Filter secondFilter) {
		if (firstFilter == null || secondFilter == null) {
			throw new IllegalArgumentException("Arguments for this constructor can't be null");
		}
		this.firstFilter = firstFilter;
		this.secondFilter = secondFilter;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Criterion getHibernateCriterion() {
		return Restrictions.and(firstFilter.getHibernateCriterion(), secondFilter.getHibernateCriterion());
	}

}
