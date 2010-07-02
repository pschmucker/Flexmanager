package flexcom.casehistory.ticket.search.filter;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 * Implementation of a XOR filter
 * 
 * @author philippe
 * 
 */
public class XorFilter implements Filter {

	/**
	 * First {@link Filter} for XOR operator
	 */
	private Filter firstFilter;

	/**
	 * Second {@link Filter} for XOR operator
	 */
	private Filter secondFilter;

	/**
	 * Constructor requiring 2 filters
	 * 
	 * @param firstFilter
	 *            First {@link Filter} for XOR operator
	 * @param secondFilter
	 *            Second {@link Filter} for XOR operator
	 * @throws IllegalArgumentException
	 *             when one of the {@link Filter} arguments is <code>null</code>
	 * 
	 */
	public XorFilter(Filter firstFilter, Filter secondFilter) {
		if (firstFilter == null || secondFilter == null) {
			throw new IllegalArgumentException("Arguments for this constructor can't be null");
		}
		this.firstFilter = firstFilter;
		this.secondFilter = secondFilter;
	}

	/**
	 * {@inheritDoc} The XOR {@link Criterion} doesn't exist, that's why we
	 * construct the {@link Criterion} according to this equality : A XOR B = (A
	 * AND NOT B) OR (NOT A AND B)
	 */
	@Override
	public Criterion getHibernateCriterion() {
		Criterion left = firstFilter.getHibernateCriterion();
		Criterion right = secondFilter.getHibernateCriterion();
		Criterion notLeft = Restrictions.not(left);
		Criterion notRight = Restrictions.not(right);
		return Restrictions.or(Restrictions.and(left, notRight), Restrictions.and(notLeft, right));
	}

}
