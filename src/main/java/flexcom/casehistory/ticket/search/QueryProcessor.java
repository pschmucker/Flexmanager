package flexcom.casehistory.ticket.search;

import java.util.List;

/**
 * Interface providing a single method execute({@link Query})
 * 
 * @author philippe
 * 
 * @param <T>
 *            Type of entities processed by this QueryProcessor
 */
public interface QueryProcessor<T> {

	/**
	 * Execute a {@link Query}
	 * 
	 * @param query
	 *            The {@link Query} to execute
	 * @return The result of the query execution
	 */
	public List<T> execute(Query<T> query);

}
