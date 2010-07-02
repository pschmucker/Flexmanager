package flexcom.casehistory.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import flexcom.casehistory.ticket.dao.ClientDAO;
import flexcom.casehistory.ticket.dao.ContactDAO;
import flexcom.casehistory.ticket.entity.Contact;

/**
 * {@link Contact} data set
 * 
 * @author philippe
 * 
 */
@Component
public class ContactDataSet extends Data {

	/**
	 * Contact DAO
	 */
	@Autowired
	private ContactDAO contactDAO;
	
	@Autowired
	private ClientDAO clientDAO;
	
	@Autowired
	private ClientDataSet clientDataSet;

	/**
	 * Set up all contacts
	 */
	public void setup() {
		if (clientDAO.count() == 0){
			clientDataSet.setup();
		}
		
		if (contactDAO.count() == 0) {
			CONTACT_1 = new Contact();
			CONTACT_1.setName("Mme. X");
			CONTACT_1.setCompany(CLIENT_1);
			contactDAO.createContact(CONTACT_1);
			CONTACT_2 = new Contact();
			CONTACT_2.setName("M. Y");
			CONTACT_2.setCompany(CLIENT_1);
			contactDAO.createContact(CONTACT_2);
		}
	}

	/**
	 * Clear all contacts
	 */
	public void clear() {
		contactDAO.deleteAll();
		clientDAO.deleteAll();
	}

}
