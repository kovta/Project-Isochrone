package com.kota.stratagem.ejbserviceclient.domain;

import java.util.Date;

public abstract class AbstractAssignmentRepresentor {

	protected Long id;
	protected final AppUserRepresentor entrustor;
	protected final Date creationDate;

	public AbstractAssignmentRepresentor(Long id, AppUserRepresentor entrustor, Date creationDate) {
		super();
		this.id = id;
		this.entrustor = entrustor;
		this.creationDate = creationDate;
	}

	public AbstractAssignmentRepresentor(AppUserRepresentor entrustor, Date creationDate) {
		super();
		this.entrustor = entrustor;
		this.creationDate = creationDate;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AppUserRepresentor getEntrustor() {
		return this.entrustor;
	}

	public Date getCreationDate() {
		return this.creationDate;
	}

}
