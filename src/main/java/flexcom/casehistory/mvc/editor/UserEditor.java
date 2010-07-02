package flexcom.casehistory.mvc.editor;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import flexcom.casehistory.ticket.dao.UserDAO;
import flexcom.casehistory.ticket.entity.User;

/**
 * Editor for {@link User} objects
 * 
 * @author philippe
 * 
 */
@Component
public class UserEditor extends PropertyEditorSupport {

	/**
	 * User DAO
	 */
	@Autowired
	private UserDAO userDAO;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getAsText() {
		User u = (User) getValue();
		return (u == null) ? null : "" + u.getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setAsText(String text) {
		setValue((text == null) ? null : userDAO.findById(Long.parseLong(text)));
	}
}
