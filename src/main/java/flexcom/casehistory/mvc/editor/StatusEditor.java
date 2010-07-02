package flexcom.casehistory.mvc.editor;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import flexcom.casehistory.ticket.dao.StatusDAO;
import flexcom.casehistory.ticket.entity.Status;

/**
 * Editor for {@link Status} objects
 * 
 * @author philippe
 * 
 */
@Component
public class StatusEditor extends PropertyEditorSupport {

	/**
	 * Status DAO
	 */
	@Autowired
	private StatusDAO statusDAO;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getAsText() {
		Status s = (Status) getValue();
		return (s == null) ? null : "" + s.getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setAsText(String text) {
		setValue((text == null) ? null : statusDAO.findById(Long.parseLong(text)));
	}
}
