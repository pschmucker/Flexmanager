package flexcom.casehistory.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseBootstrapper implements Bootstrapper {
	
	@Autowired
	private UserBootstrapper userBootstrapper;
	
	@Autowired
	private LicenceBootstrapper licenceBootstrapper;
	
	@Autowired
	private TicketBootstrapper ticketBootstrapper;

	@Override
	public void bootstrap() {
		userBootstrapper.bootstrap();
		licenceBootstrapper.bootstrap();
		ticketBootstrapper.bootstrap();
	}

}
