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

import flexcom.casehistory.mvc.editor.UserEditor;
import flexcom.casehistory.ticket.dao.UserDAO;
import flexcom.casehistory.ticket.entity.User;

/**
 * Test class for {@link UserEditor}
 * 
 * @author philippe
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class UserEditorTest {

	/**
	 * A User DAO
	 */
	@Autowired
	private UserDAO userDAO;

	/**
	 * A {@link User}
	 */
	private User user;

	/**
	 * The ID of the {@link User}
	 */
	private long userId;

	/**
	 * User editor
	 */
	@Autowired
	private UserEditor editor;

	/**
	 * Create and persist a {@link User}
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		user = new User();
		user.setLogin("user");
		user.setPassword("pwd");
		user.setName("name");
		user.setEmail("user@yopmail.com");
		userDAO.createUser(user);
		userId = user.getId();
	}

	/**
	 * Clear the entity manager and the created {@link User}. Put a
	 * <code>null</code> value for the editor
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		editor.setValue(null);
		userDAO.deleteAll();
		user = null;
	}

	/**
	 * Test if the editor return the correct {@link String} after a call to the
	 * setAsText() method
	 */
	@Test
	public void testGetAsText() {
		String id = "" + userId;
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
	 * Test if the editor return the correct {@link User} after a call to the
	 * setAsText() method
	 */
	@Test
	public void testSetAsTextString() {
		editor.setAsText("" + userId);
		assertEquals(user, editor.getValue());
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
