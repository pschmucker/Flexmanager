package flexcom.casehistory.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import flexcom.casehistory.ticket.dao.ClientDAO;
import flexcom.casehistory.ticket.entity.Client;


@Component
public class ClientBootstrapper implements Bootstrapper {
	
	@Autowired
	private ClientDAO clientDAO;
	
	@Override
	public void bootstrap() {
		if (clientDAO.count() == 0){
			Client client = new Client();
			client.setName("Flexcom");
			client.setAddress("5 Rue Jean Jaurès, Dudelange");
			client.setCountry("Luxembourg");
			clientDAO.createClient(client);
			
			client = new Client();
			client.setName("Google");
			client.setAddress("Everywhere");
			client.setCountry("World");
			clientDAO.createClient(client);
		}
	}
	
}
