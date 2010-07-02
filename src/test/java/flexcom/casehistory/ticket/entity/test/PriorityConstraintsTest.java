package flexcom.casehistory.ticket.entity.test;

import javax.validation.ConstraintViolationException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import flexcom.casehistory.ticket.dao.PriorityDAO;
import flexcom.casehistory.ticket.entity.Priority;

/**
 * Class test for setters of the {@link Priority} class. Here we check all
 * constraints on every fields.
 * 
 * @author philippe
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class PriorityConstraintsTest {

	/**
	 * Priority DAO
	 */
	@Autowired
	private PriorityDAO priorityDAO;

	/**
	 * A {@link Priority}
	 */
	private Priority priority;

	/**
	 * Set up the context
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		priority = Priority.getPriority(Priority.HIGH);
	}

	/**
	 * Clear the context
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		priorityDAO.deleteAll();
		priority = null;
	}
	
	/**
	 * Test if a {@link DataIntegrityViolationException} is thrown when we
	 * create 2 priorities with the same name
	 */
	@Test(expected = DataIntegrityViolationException.class)
	public void testSetNameUnique() {
		priority.setName("unique");
		priority.setLevel(234);
		priorityDAO.createPriority(priority);

		Priority copy = new Priority();
		copy.setName("unique");
		copy.setLevel(567);
		priorityDAO.createPriority(copy);
	}

	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * {@link Priority} with a <code>null</code> name
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSetNameNull() {
		priority.setName(null);
		priorityDAO.createPriority(priority);
	}

	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * {@link Priority} with a too long name
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSetNameMaxSize() {
		priority.setName("This priority has a too long name");
		priorityDAO.createPriority(priority);
	}

	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * {@link Priority} with an empty name
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSetEmptyName() {
		priority.setName("");
		priorityDAO.createPriority(priority);
	}

	/**
	 * Test if a {@link NullPointerException} is thrown when we create a
	 * {@link Priority} with a <code>null</code> level
	 */
	@Test(expected = NullPointerException.class)
	public void testSetVersionNull() {
		Integer level = null;
		priority.setLevel(level);
		priorityDAO.createPriority(priority);
	}

	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * {@link Priority} with a negative level
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSetNegativeLevel() {
		priority.setLevel(-45);
		priorityDAO.createPriority(priority);
	}

}
