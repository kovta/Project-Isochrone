package com.kota.stratagem.ejbserviceclient.domain;

import java.io.Serializable;
import java.util.Date;

public class NotificationRepresentor implements Serializable {

	private static final long serialVersionUID = 5718437117122104722L;

	private final Long id;
	private final String message;
	private final Date creationDate;
	private final AppUserRepresentor inducer;

	public NotificationRepresentor(Long id, String message, Date creationDate, AppUserRepresentor inducer) {
		super();
		this.id = id;
		this.message = message;
		this.creationDate = creationDate;
		this.inducer = inducer;
	}

	public Long getId() {
		return this.id;
	}

	public String getMessage() {
		return this.message;
	}

	public Date getCreationDate() {
		return this.creationDate;
	}

	public AppUserRepresentor getInducer() {
		return this.inducer;
	}

	@Override
	public String toString() {
		return "NotificationRepresentor [id=" + this.id + ", message=" + this.message + ", creationDate=" + this.creationDate + ", inducer=" + this.inducer
				+ "]";
	}

}
