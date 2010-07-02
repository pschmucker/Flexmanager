package flexcom.casehistory.mvc.editor;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import flexcom.casehistory.ticket.dao.RoleDAO;
import flexcom.casehistory.ticket.entity.Role;

/**
 * Editor for {@link Role} objects
 * 
 * @author philippe
 * 
 */
@Component
public class RoleEditor extends PropertyEditorSupport {

	/**
	 * Role DAO
	 */
	@Autowired
	private RoleDAO roleDAO;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getAsText() {
		Role r = (Role) getValue();
		return (r == null) ? null : "" + r.getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setAsText(String text) {
		setValue((text == null) ? null : roleDAO.findById(Long.parseLong(text)));
	}
}
