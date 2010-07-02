package flexcom.casehistory.ticket.search.filter;

import org.hibernate.criterion.Criterion;

/**
 * Filter interface 
 * @author philippe
 *
 */
public interface Filter {

	/**
	 * Return a Hibernate {@link Criterion} which correspond to this filter
	 * @return a Hibernate {@link Criterion} corresponding to a filter
	 */
	public Criterion getHibernateCriterion();

}
