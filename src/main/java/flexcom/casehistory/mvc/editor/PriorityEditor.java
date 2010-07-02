package flexcom.casehistory.mvc.editor;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import flexcom.casehistory.ticket.dao.PriorityDAO;
import flexcom.casehistory.ticket.entity.Priority;

/**
 * Editor for {@link Priority} objects
 * 
 * @author philippe
 * 
 */
@Component
public class PriorityEditor extends PropertyEditorSupport {

	/**
	 * Priority DAO
	 */
	@Autowired
	private PriorityDAO priorityDAO;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getAsText() {
		Priority p = (Priority) getValue();
		return (p == null) ? null : "" + p.getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setAsText(String text) {
		setValue((text == null) ? null : priorityDAO.findById(Long.parseLong(text)));
	}
}
