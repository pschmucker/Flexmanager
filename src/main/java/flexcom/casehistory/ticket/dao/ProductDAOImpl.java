package flexcom.casehistory.ticket.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import flexcom.casehistory.ticket.entity.Product;

/**
 * Product DAO implementation
 * @author philippe
 *
 */
@Transactional
@Repository
public class ProductDAOImpl extends JPAGenericDAO<Product, Long> implements ProductDAO {

	/**
	 * Default constructor
	 */
	public ProductDAOImpl() {
		super(Product.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Product> findAll() {
		return entityManager.createQuery("from Product", Product.class).getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createProduct(Product product) {
		create(product);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Product> findByName(String name) {
		TypedQuery<Product> q = entityManager.createNamedQuery("product.findByName", Product.class);
		q.setParameter("name", name);
		return q.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteProduct(Product product) {
		delete(entityManager.getReference(Product.class, product.getId()));
	}

	/**
	 * {@inheritDoc} Before removing the {@link Product} from the
	 * {@link EntityManager}, we remove this {@link Product} from all clients who
	 * own this {@link Product}
	 */
	@Override
	public void deleteAll() {
		for (Product p : findAll()) {
			entityManager.remove(p);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Product findById(long id) {
		return read(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateProduct(Product product) {
		update(product);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long count() {
		return entityManager.createNamedQuery("product.count", Long.class).getSingleResult();
	}

	@Override
	public List<Product> findAllEnabled() {
		return entityManager.createQuery("select p from Product p where p.enabled = true", Product.class).getResultList();
	}
}
