package flexcom.casehistory.mvc.editor.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import flexcom.casehistory.mvc.editor.PriorityEditor;
import flexcom.casehistory.ticket.dao.PriorityDAO;
import flexcom.casehistory.ticket.entity.Priority;

/**
 * Test class for {@link PriorityEditor}
 * 
 * @author philippe
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class PriorityEditorTest {

	/**
	 * A {@link Priority}
	 */
	private Priority priority;

	/**
	 * The ID of the {@link Priority}
	 */
	private long priorityId;

	/**
	 * Priority DAO
	 */
	@Autowired
	private PriorityDAO priorityDAO;

	/**
	 * Priority editor
	 */
	@Autowired
	private PriorityEditor editor;

	/**
	 * Create and persist a {@link Priority}
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		priority = Priority.getPriority(Priority.HIGH);
		priorityDAO.createPriority(priority);
		priorityId = priority.getId();
	}

	/**
	 * Clear the entity manager and the created {@link Priority}. Put a
	 * <code>null</code> value for the editor
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		editor.setValue(null);
		priorityDAO.deleteAll();
		priority = null;
	}

	/**
	 * Test if the editor return the correct {@link String} after a call to the
	 * setAsText() method
	 */
	@Test
	public void testGetAsText() {
		String id = "" + priorityId;
		editor.setAsText(id);
		assertEquals(id, editor.getAsText());
	}

	/**
	 * Test if the editor return <code>null</code> without a call to the
	 * setAsText() method
	 */
	@Test
	public void testGetAsTextNullValue() {
		assertNull(editor.getAsText());
	}

	/**
	 * Test if the editor return the correct {@link Priority} after a call to the
	 * setAsText() method
	 */
	@Test
	public void testSetAsTextString() {
		editor.setAsText("" + priorityId);
		assertEquals(priority, editor.getValue());
	}

	/**
	 * Test if the editor return <code>null</code> after a call to the
	 * setAsText() method with an invalid ID
	 */
	@Test
	public void testSetAsTextInvalidString() {
		editor.setAsText("" + Long.MAX_VALUE);
		assertNull(editor.getValue());
	}

}
