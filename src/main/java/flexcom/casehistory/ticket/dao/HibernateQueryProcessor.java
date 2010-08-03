package flexcom.casehistory.ticket.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import flexcom.casehistory.ticket.search.Query;
import flexcom.casehistory.ticket.search.QueryProcessor;
import flexcom.casehistory.ticket.search.filter.Filter;

/**
 * Implementation class of {@link QueryProcessor} with Hibernate
 * 
 * @author philippe
 * 
 * @param <T>
 *            Type of entities processed by this QueryProcessor
 */
@Repository
public class HibernateQueryProcessor<T> implements QueryProcessor<T> {

	/**
	 * {@link EntityManager}
	 */
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * {@inheritDoc} This method use the Criteria API from Hibernate, which
	 * allow us to make complex query using filters.
	 * 
	 * @param query
	 *            The {@link Query} to execute
	 * @return The result of the query execution
	 * @throws IllegalArgumentException
	 *             thrown if the {@link Query} argument is null
	 */
	@Transactional
	@SuppressWarnings("unchecked")
	@Override
	public List<T> execute(Query<T> query) {
		if (query == null) {
			throw new IllegalArgumentException("Can't execute a null query");
		}
		Session session = (Session) entityManager.getDelegate();
		Criteria c = session.createCriteria(query.getType());
		for (Filter f : query.getFilters()) {
			c.add(f.getHibernateCriterion());
		}
		c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return c.list();
	}

}
