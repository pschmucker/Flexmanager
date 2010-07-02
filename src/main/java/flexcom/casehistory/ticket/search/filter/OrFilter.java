package flexcom.casehistory.ticket.search.filter;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 * Implementation of an OR filter
 * 
 * @author philippe
 * 
 */
public class OrFilter implements Filter {

	/**
	 * First {@link Filter} for OR operator
	 */
	private Filter firstFilter;

	/**
	 * Second {@link Filter} for OR operator
	 */
	private Filter secondFilter;

	/**
	 * Constructor requiring 2 filters
	 * 
	 * @param firstFilter
	 *            First {@link Filter} for OR operator
	 * @param secondFilter
	 *            Second {@link Filter} for OR operator
	 * @throws IllegalArgumentException
	 *             when one of the {@link Filter} arguments is <code>null</code>
	 */
	public OrFilter(Filter firstFilter, Filter secondFilter) {
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
		return Restrictions.or(firstFilter.getHibernateCriterion(), secondFilter.getHibernateCriterion());
	}

}
