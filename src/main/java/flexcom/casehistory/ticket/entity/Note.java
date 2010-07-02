package flexcom.casehistory.ticket.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

/**
 * Note entity class
 * 
 * @author philippe
 * 
 */
@Entity
@Table(name = "Notes")
@NamedQueries(value = {
	@NamedQuery(name = "note.findById", query = "select n from Note n where n.id = :id"),
	@NamedQuery(name = "note.findByNote", query = "select n from Note n where n.note = :note"),
	@NamedQuery(name = "note.count", query = "select count(*) from Note n"),
	@NamedQuery(name = "note.findByAuthor", query = "select n from Note n where n.author = :author"),
	@NamedQuery(name = "note.findByTicket", query = "select n from Note n where n.ticket = :ticket")
})
public class Note {
	
	/**
	 * Note's ID
	 */
	private long id;
	
	/**
	 * The note
	 */
	private String note;
	
	/**
	 * Path to an attachment file
	 */
	private String attachment;
	
	/**
	 * {@link Ticket} concerned by this note
	 */
	private Ticket ticket;
	
	/**
	 * Author of this note
	 */
	private User author;

	/**
	 * Note's creation date
	 */
	private Date creationDate;

	/**
	 * Note's last update date
	 */
	private Date lastUpdate;
	
	private boolean enabled;
	
	/**
	 * Default constructor
	 */
	public Note() {
		creationDate = new Date();
		enabled = true;
	}

	/**
	 * Gets the ID
	 * @return the id
	 */
	@Id
	@Column(name = "key_")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getId() {
		return id;
	}

	/**
	 * Sets the ID
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Gets the note
	 * @return the note
	 */
	@Lob
	@NotNull
	public String getNote() {
		return note;
	}

	/**
	 * Sets the note
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * Gets the attachment path
	 * @return the attachment
	 */
	@Column(length = 255)
	@Length(max = 255)
	public String getAttachment() {
		return attachment;
	}

	/**
	 * Sets the attachment path
	 * @param attachment the attachment to set
	 */
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	/**
	 * Gets the ticket concerned by this note
	 * @return the ticket
	 */
	@ManyToOne
	@NotNull
	@Valid
	public Ticket getTicket() {
		return ticket;
	}

	/**
	 * Sets the ticket concerned by this note
	 * @param ticket the ticket to set
	 */
	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	/**
	 * Gets the author of this note
	 * @return the author
	 */
	@ManyToOne
	@NotNull
	@Valid
	public User getAuthor() {
		return author;
	}

	/**
	 * Sets the author of this note
	 * @param author the author to set
	 */
	public void setAuthor(User author) {
		this.author = author;
	}

	/**
	 * Gets the creation date
	 * 
	 * @return The note's creation date
	 */
	@Column(name = "creationdate", nullable = false, updatable = false)
	@Temporal(value = TemporalType.TIMESTAMP)
	@NotNull
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * Sets the date creation
	 * 
	 * @param creationDate
	 *            The note's date creation
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * Gets the last update date
	 * 
	 * @return the last update date
	 */
	@Column(name = "lastupdate")
	@Temporal(value = TemporalType.TIMESTAMP)	
	public Date getLastUpdate(){
		return lastUpdate;
	}
	
	/**
	 * Sets the last update date
	 * 
	 * @param lastUpdate the last update date to set
	 */
	public void setLastUpdate(Date lastUpdate){
		this.lastUpdate = lastUpdate;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	/**
	 * {@inheritDoc} Compare two notes using their id
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj instanceof Note) {
			Note note = (Note) obj;
			return (note.getId() == this.id) ? true : false;
		}
		return false;
	}

	/**
	 * {@inheritDoc} Calculates the hashCode using the note's id
	 */
	@Override
	public int hashCode() {
		return ((Long) id).hashCode();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return The note
	 */
	@Override
	public String toString() {
		return note;
	}

}
