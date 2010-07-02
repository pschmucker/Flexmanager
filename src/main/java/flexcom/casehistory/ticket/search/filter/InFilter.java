package flexcom.casehistory.ticket.search.filter;

import java.util.Arrays;
import java.util.Collection;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 * Implementation of an IN filter
 * 
 * @author philippe
 * 
 */
public class InFilter implements Filter {
	
	/**
	 * Tested property
	 */
	private String property;
	
	/**
	 * Collection of tested values
	 */
	private Collection<Object> collection;
	
	/**
	 * Constructor requiring a property and a collection of values
	 * 
	 * @param property
	 *            Tested property
	 * @param collection
	 *            Collection of tested values
	 * @throws IllegalArgumentException
	 *             when the property or collection is <code>null</code>
	 */
	public InFilter(String property, Collection<Object> collection){
		if (property == null){
			throw new IllegalArgumentException("Property can't be null");
		}
		if (collection == null){
			throw new IllegalArgumentException("Collection can't be null");
		}
		this.property = property;
		this.collection = collection;
	}
	
	/**
	 * Constructor requiring a property and an array of values
	 * 
	 * @param property
	 *            Tested property
	 * @param array
	 *            Array of tested values
	 * @throws IllegalArgumentException
	 *             when the property or array is <code>null</code>
	 */
	public InFilter(String property, Object[] array){
		if (property == null){
			throw new IllegalArgumentException("Property can't be null");
		}
		if (array == null){
			throw new IllegalArgumentException("Array can't be null");
		}
		this.property = property;
		this.collection = Arrays.asList(array);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Criterion getHibernateCriterion() {
		if (collection.size() == 0){
			return Restrictions.idEq(null);
		}
		return Restrictions.in(property, collection);
	}

}
