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

import flexcom.casehistory.ticket.dao.StatusDAO;
import flexcom.casehistory.ticket.entity.Status;

/**
 * Class test for setters of the {@link Status} class. Here we check all
 * constraints on every fields.
 * 
 * @author philippe
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class StatusConstraintsTest {

	/**
	 * Status DAO
	 */
	@Autowired
	private StatusDAO statusDAO;

	/**
	 * A {@link Status}
	 */
	private Status status;

	/**
	 * Set up the context
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		status = Status.getStatus(Status.ASSIGNED);
	}

	/**
	 * Clear the context
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		statusDAO.deleteAll();
		status = null;
	}
	
	/**
	 * Test if a {@link DataIntegrityViolationException} is thrown when we
	 * create 2 statuses with the same name
	 */
	@Test(expected = DataIntegrityViolationException.class)
	public void testSetNameUnique() {
		status.setName("unique");
		statusDAO.createStatus(status);

		Status copy = new Status();
		copy.setName("unique");
		statusDAO.createStatus(copy);
	}

	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * {@link Status} with a <code>null</code> name
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSetNameNull() {
		status.setName(null);
		statusDAO.createStatus(status);
	}

	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * {@link Status} with a too long name
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSetNameMaxSize() {
		status.setName("This status has a too long name");
		statusDAO.createStatus(status);
	}

	/**
	 * Test if a {@link ConstraintViolationException} is thrown when we create a
	 * {@link Status} with an empty name
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSetEmptyName() {
		status.setName("");
		statusDAO.createStatus(status);
	}

}
