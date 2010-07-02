package flexcom.casehistory.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import flexcom.casehistory.ticket.dao.ClientDAO;
import flexcom.casehistory.ticket.entity.Client;

/**
 * {@link Client} data set
 * 
 * @author philippe
 * 
 */
@Component
public class ClientDataSet extends Data {

	/**
	 * Client DAO
	 */
	@Autowired
	private ClientDAO clientDAO;

	/**
	 * Set up all clients
	 */
	public void setup() {
		if (clientDAO.count() == 0) {
			CLIENT_1 = new Client();
			CLIENT_1.setName("C1");
			clientDAO.createClient(CLIENT_1);
			CLIENT_2 = new Client();
			CLIENT_2.setName("C2");
			clientDAO.createClient(CLIENT_2);
		}
	}

	/**
	 * Clear all clients
	 */
	public void clear() {
		clientDAO.deleteAll();
	}

}
