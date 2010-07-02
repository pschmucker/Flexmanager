package flexcom.casehistory.ticket.search.filter;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 * Implementation of a NOT filter
 * 
 * @author philippe
 * 
 */
public class NotFilter implements Filter {

	/**
	 * {@link Filter} for NOT operator
	 */
	private Filter filter;

	/**
	 * Constructor requiring 2 filters
	 * 
	 * @param filter
	 *            {@link Filter} for NOT operator
	 * @throws IllegalArgumentException
	 *             the {@link Filter} argument is <code>null</code>
	 */
	public NotFilter(Filter filter) {
		if (filter == null) {
			throw new IllegalArgumentException("Argument for this constructor can't be null");
		}
		this.filter = filter;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Criterion getHibernateCriterion() {
		return Restrictions.not(filter.getHibernateCriterion());
	}

}
