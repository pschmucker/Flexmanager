package flexcom.casehistory.mvc.editor;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import flexcom.casehistory.ticket.dao.ClientDAO;
import flexcom.casehistory.ticket.dao.PartnerDAO;
import flexcom.casehistory.ticket.entity.Client;
import flexcom.casehistory.ticket.entity.Company;

/**
 * Editor for {@link Client} objects
 * 
 * @author philippe
 * 
 */
@Component
public class CompanyEditor extends PropertyEditorSupport {

	/**
	 * Client DAO
	 */
	@Autowired
	private ClientDAO clientDAO;

	@Autowired
	private PartnerDAO partnerDAO;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getAsText() {
		Company c = (Company) getValue();
		return (c == null) ? null : "" + c.getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setAsText(String text) {
		if (text == null){
			setValue(null);
		}
		else{
			Company c = clientDAO.findById(Long.parseLong(text));
			if (c == null){
				c = partnerDAO.findById(Long.parseLong(text));
			}
			setValue(c);
		}
	}
}
