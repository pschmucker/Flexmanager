package flexcom.casehistory.ticket.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import flexcom.casehistory.ticket.entity.Role;

@Transactional
@Repository
public class RoleDAOImpl extends JPAGenericDAO<Role, Long> implements RoleDAO {
	
	/**
	 * Default constructor
	 */
	public RoleDAOImpl(){
		super(Role.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long count() {
		return entityManager.createNamedQuery("role.count", Long.class).getSingleResult();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createRole(Role role) {
		create(role);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteAll() {
		for (Role r : findAll()) {
			entityManager.remove(r);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteRole(Role role) {
		delete(entityManager.getReference(Role.class, role.getId()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Role> findAll() {
		return entityManager.createQuery("from Role", Role.class).getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Role findById(long id) {
		return read(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Role findByName(String name) {
		TypedQuery<Role> q = entityManager.createNamedQuery("role.findByName", Role.class);
		q.setParameter("name", name);
		return q.getSingleResult();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateRole(Role role) {
		update(role);
	}

}
