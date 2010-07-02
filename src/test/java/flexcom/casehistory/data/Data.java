package flexcom.casehistory.data;

import flexcom.casehistory.ticket.entity.Client;
import flexcom.casehistory.ticket.entity.Contact;
import flexcom.casehistory.ticket.entity.Licence;
import flexcom.casehistory.ticket.entity.Maintenance;
import flexcom.casehistory.ticket.entity.Priority;
import flexcom.casehistory.ticket.entity.Product;
import flexcom.casehistory.ticket.entity.Role;
import flexcom.casehistory.ticket.entity.Status;
import flexcom.casehistory.ticket.entity.Ticket;
import flexcom.casehistory.ticket.entity.User;

public abstract class Data {

	public static Maintenance MAINTENANCE_FULL;
	public static Maintenance MAINTENANCE_TEST;
	public static Maintenance MAINTENANCE_NONE;
	
	public static Status STATUS_ASSIGNED;
	public static Status STATUS_CLOSED;
	public static Status STATUS_CUSTOMER_FEEDBACK;
	public static Status STATUS_FLEXCOM_FEEDBACK;
	public static Status STATUS_NEW;
	public static Status STATUS_PENDING;
	public static Status STATUS_REOPEN;
	public static Status STATUS_RESOLVED;

	public static Priority PRIORITY_HIGH;
	public static Priority PRIORITY_IMMEDIATE;
	public static Priority PRIORITY_LOW;
	public static Priority PRIORITY_MEDIUM;
	public static Priority PRIORITY_NONE;
	public static Priority PRIORITY_URGENT;
	
	public static Role ROLE_ADMIN;
	public static Role ROLE_USER;
	
	public static User USER_1;
	public static User USER_2;
	public static User USER_3;
	
	public static Client CLIENT_1;
	public static Client CLIENT_2;
	
	public static Product PRODUCT_1;
	public static Product PRODUCT_2;
	
	public static Contact CONTACT_1;
	public static Contact CONTACT_2;
	
	public static Ticket TICKET_1;
	public static Ticket TICKET_2;
	public static Ticket TICKET_3;
	public static Ticket TICKET_4;
	public static Ticket TICKET_5;
	
	public static Licence LICENCE_1;
	public static Licence LICENCE_2;

}
