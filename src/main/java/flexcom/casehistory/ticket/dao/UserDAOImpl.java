package flexcom.casehistory.ticket.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import flexcom.casehistory.ticket.entity.Ticket;
import flexcom.casehistory.ticket.entity.User;

/**
 * User DAO implementation
 * @author philippe
 *
 */
@Transactional
@Repository
public class UserDAOImpl extends JPAGenericDAO<User, Long> implements UserDAO {

	/**
	 * Default constructor
	 */
	public UserDAOImpl() {
		super(User.class);
	}

	/**
	 * {@inheritDoc}
	 * This method hashes the password before making the user persistant.
	 */
	@Override
	public void createUser(User user) {
		String pwd = user.getPassword();
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		String hash = encoder.encodePassword(pwd, null);
		user.setPassword(hash);
		create(user);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteAll() {
		for (User u : findAll()) {
			entityManager.remove(u);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<User> findAll() {
		return entityManager.createQuery("from User", User.class).getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User findByLogin(String login) {
		TypedQuery<User> q = entityManager.createNamedQuery("user.findByLogin", User.class);
		q.setParameter("login", login);
		return q.getSingleResult();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateUser(User user) {
		user.setLastUpdate(new Date());
		update(user);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void changePassword(User user, String pwd){
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		String hash = encoder.encodePassword(pwd, null);
		user.setPassword(hash);
		update(user);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean checkPassword(User user, String pwd){
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		return encoder.isPasswordValid(user.getPassword(), pwd, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<User> findByAssignedTicket(Ticket ticket) {
		Query q = entityManager.createNamedQuery("user.findByAssignedTicket");
		q.setParameter("ticket", ticket);
		return (List<User>) q.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteUser(User user) {
		delete(entityManager.getReference(User.class, user.getId()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User findById(long id) {
		return read(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<User> findByAccessLevel(int accessLevel) {
		TypedQuery<User> q = entityManager.createNamedQuery("user.findByAccessLevel", User.class);
		q.setParameter("accessLevel", accessLevel);
		return q.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User findByEmail(String email) {
		TypedQuery<User> q = entityManager.createNamedQuery("user.findByEmail", User.class);
		q.setParameter("email", email);
		return q.getSingleResult();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<User> findByName(String name) {
		TypedQuery<User> q = entityManager.createNamedQuery("user.findByName", User.class);
		q.setParameter("name", name);
		return q.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<User> findByTitle(String title) {
		TypedQuery<User> q = entityManager.createNamedQuery("user.findByTitle", User.class);
		q.setParameter("title", title);
		return q.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long count() {
		return entityManager.createNamedQuery("user.count", Long.class).getSingleResult();
	}

	@Override
	public List<User> findAllEnabled() {
		return entityManager.createQuery("select u from User u where u.enabled = true", User.class).getResultList();
	}
}
