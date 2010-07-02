package flexcom.casehistory.mvc.editor;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import flexcom.casehistory.ticket.dao.ContactDAO;
import flexcom.casehistory.ticket.entity.Contact;

/**
 * Editor for {@link Contact} objects
 * 
 * @author philippe
 * 
 */
@Component
public class ContactEditor extends PropertyEditorSupport {

	/**
	 * Contact DAO
	 */
	@Autowired
	private ContactDAO contactDAO;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getAsText() {
		Contact c = (Contact) getValue();
		return (c == null) ? null : "" + c.getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setAsText(String text) {
		setValue((text == null) ? null : contactDAO.findById(Long.parseLong(text)));
	}
}
