package flexcom.casehistory.mvc.editor;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import flexcom.casehistory.ticket.dao.MaintenanceDAO;
import flexcom.casehistory.ticket.entity.Maintenance;

/**
 * Editor for {@link Maintenance} objects
 * 
 * @author philippe
 * 
 */
@Component
public class MaintenanceEditor extends PropertyEditorSupport {

	/**
	 * Maintenance DAO
	 */
	@Autowired
	private MaintenanceDAO maintenanceDAO;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getAsText() {
		Maintenance m = (Maintenance) getValue();
		return (m == null) ? null : "" + m.getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setAsText(String text) {
		setValue((text == null) ? null : maintenanceDAO.findById(Long.parseLong(text)));
	}
}
