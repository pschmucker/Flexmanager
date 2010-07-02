package flexcom.casehistory.ticket.search.filter;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

/**
 * Implementation of a LIKE filter
 * 
 * @author philippe
 * 
 */
public class LikeFilter implements Filter {

	/**
	 * Tested property
	 */
	private String property;

	/**
	 * Tested value
	 */
	private String value;
	
	/**
	 * Case-sensitiveness
	 */
	private boolean caseSensitive;
	
	/**
	 * Match mode
	 */
	private MatchMode matchMode;

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
	public LikeFilter(String property, String value) {
		if (property == null){
			throw new IllegalArgumentException("Property can't be null");
		}
		if (value == null){
			throw new IllegalArgumentException("Value can't be null");
		}
		this.property = property;
		this.value = value;
		this.caseSensitive = true;
		this.matchMode = MatchMode.ANYWHERE;
	}

	/**
	 * Gets the case-sensitiveness
	 * @return the caseSensitive
	 */
	public boolean isCaseSensitive() {
		return caseSensitive;
	}

	/**
	 * Sets the case-sensitiveness
	 * @param caseSensitive the caseSensitive to set
	 */
	public void setCaseSensitive(boolean caseSensitive) {
		this.caseSensitive = caseSensitive;
	}

	/**
	 * Gets the match mode
	 * @return the matchMode
	 */
	public MatchMode getMatchMode() {
		return matchMode;
	}

	/**
	 * Sets the match mode
	 * @param matchMode the matchMode to set
	 */
	public void setMatchMode(MatchMode matchMode) {
		this.matchMode = matchMode;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Criterion getHibernateCriterion() {
		return (caseSensitive ? Restrictions.like(property, value, matchMode) : Restrictions.ilike(property, value, matchMode)); 
	}

}
