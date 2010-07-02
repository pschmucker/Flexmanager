package flexcom.casehistory.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import flexcom.casehistory.ticket.dao.ClientDAO;
import flexcom.casehistory.ticket.dao.ContactDAO;
import flexcom.casehistory.ticket.entity.Company;
import flexcom.casehistory.ticket.entity.Contact;


@Component
public class ContactBootstrapper implements Bootstrapper {
	
	@Autowired
	private ContactDAO contactDAO;
	
	@Autowired
	private ClientDAO clientDAO;
	
	@Autowired
	private ClientBootstrapper clientBootstrapper;
	
	@Override
	public void bootstrap() {
		if (clientDAO.count() == 0){
			clientBootstrapper.bootstrap();
		}
		
		if (contactDAO.count() == 0){
			Company company = clientDAO.findByName("Flexcom");
			
			Contact contact = new Contact();
			contact.setName("M. Y");
			contact.setCompany(company);
			contactDAO.createContact(contact);
			
			contact = new Contact();
			contact.setName("Mme. X");
			contact.setCompany(company);
			contactDAO.createContact(contact);
		}
	}
	
}
