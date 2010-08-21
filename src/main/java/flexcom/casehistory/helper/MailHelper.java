package flexcom.casehistory.helper;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessagePreparator;

import flexcom.casehistory.ticket.entity.Ticket;
import flexcom.casehistory.ticket.entity.User;

/**
 * @author philippe
 *
 */
public class MailHelper {
	
	public static void sendNotificationForNewTicket(final User user, final Ticket ticket){
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
//		sender.setHost("smtp.gmail.com");
//		sender.setHost("outgoing.verizon.net");
//		sender.setHost("mail.flexcom.lu");
		sender.setHost("87.98.164.155");
//		sender.setHost("smtp.laposte.net");

//		String[] emailAdresses = new String[users.length];
//		for (int i = 0; i < users.length; i++){
//			emailAdresses[i] = users[i].getEmail();
//		}

//		MimeMessage mail = sender.createMimeMessage();
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			
			@Override
			public void prepare(MimeMessage mimeMessage) throws MessagingException {
				mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
				mimeMessage.setFrom(new InternetAddress("noreply@flexcom.lu"));
				mimeMessage.setSubject("A ticket has been assigned to you");
				mimeMessage.setText(
					"#" + ticket.getId() + " " + ticket.getTitle() + "\n\n" 
					+ "Priority : " + ticket.getPriority() + "\n"
					+ "Status : " + ticket.getStatus() + "\n"
					+ "Client : " + ticket.getClient() + "\n"
					+ "Product : " + ticket.getProduct() + "\n"
					+ "Build : " + ticket.getBuild() + "\n\n"
					+ ticket.getDescription()
				);
				
			}
		};
		
		try {
//			helper.setFrom("noreply@flexcom.lu");
//			helper.setSubject("Ticket assigned : " + ticket.getTitle());
//			helper.setTo(emailAdresses);
//			helper.setText("A ticket has been assigned to you : \n" 
//					+ "#" + ticket.getId() + " " + ticket.getTitle() + "\n\n" 
//					+ "Priority" + ticket.getPriority() + "\n"
//					+ "Status" + ticket.getStatus() + "\n"
//					+ "Client" + ticket.getClient() + "\n"
//					+ "Product" + ticket.getProduct() + "\n"
//					+ "Build" + ticket.getBuild() + "\n\n"
//					+ ticket.getDescription() + "\n"
//				);
			sender.send(preparator);
		} catch (MailException e) {
			e.printStackTrace();
		}
	}
	
}
