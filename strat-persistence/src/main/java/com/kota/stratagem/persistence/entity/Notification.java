package com.kota.stratagem.persistence.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "notifications")
@SequenceGenerator(name = "notificationGenerator", sequenceName = "notifications_notification_id_seq", allocationSize = 1)
public class Notification implements Serializable {

	private static final long serialVersionUID = 1925579286192016849L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notificationGenerator")
	@Column(name = "notification_id", nullable = false, updatable = false, insertable = false)
	private Long id;

	@Column(name = "notification_message", nullable = false)
	private String message;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "notification_creation_date", nullable = false)
	private Date creationDate;

	@ManyToOne(fetch = FetchType.EAGER, targetEntity = AppUser.class)
	@JoinColumn(name = "notification_inducer", referencedColumnName = "user_id", nullable = false)
	protected AppUser inducer;

	public Notification() {
		super();
	}

	public Notification(String message, Date creationDate, AppUser inducer) {
		super();
		this.message = message;
		this.creationDate = creationDate;
		this.inducer = inducer;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public AppUser getInducer() {
		return this.inducer;
	}

	public void setInducer(AppUser inducer) {
		this.inducer = inducer;
	}

	@Override
	public String toString() {
		return "Notification [id=" + this.id + ", message=" + this.message + ", creationDate=" + this.creationDate + ", inducer=" + this.inducer + "]";
	}

}
