package flexcom.casehistory.ticket.search;

import java.util.ArrayList;
import java.util.List;

import flexcom.casehistory.ticket.search.filter.Filter;

/**
 * Query class used by {@link QueryProcessor}
 * @author philippe
 *
 * @param <T> Type of entities
 */
public class Query<T> {
	
	/**
	 * Type of entities
	 */
	private Class<? extends T> type;
	
	/**
	 * Filters list
	 */
	private List<Filter> filters;
	
	/**
	 * Constructor requiring an entity type in argument
	 * @param type Type of entities
	 */
	public Query(Class<? extends T> type){
		if (type == null){
			throw new IllegalArgumentException("Type for query can't be null");
		}
		this.type = type;
		filters = new ArrayList<Filter>();
	}

	/**
	 * Add a {@link Filter} to this query
	 * @param filter
	 */
	public void addFilter(Filter filter) {
		if (filter != null){
			filters.add(filter);
		}
	}

	/**
	 * Gets the entities type
	 * @return The entities type
	 */
	public Class<? extends T> getType() {
		return type;
	}

	/**
	 * Gets the list of filters
	 * @return The list of filters
	 */
	public List<Filter> getFilters() {
		return filters;
	}

}
