package com.kota.stratagem.ejbserviceclient.domain;

import java.io.Serializable;
import java.util.Date;

public abstract class AbstractAppUserAssignmentRepresentor extends AbstractAssignmentRepresentor implements Serializable {

	private static final long serialVersionUID = -3286792703224468467L;

	protected final AppUserRepresentor recipient;

	public AbstractAppUserAssignmentRepresentor(Long id, AppUserRepresentor entrustor, AppUserRepresentor recipient, Date creationDate) {
		super(id, entrustor, creationDate);
		this.recipient = recipient;
	}

	public AbstractAppUserAssignmentRepresentor(AppUserRepresentor entrustor, AppUserRepresentor recipient, Date creationDate) {
		super(entrustor, creationDate);
		this.recipient = recipient;
	}

	public AppUserRepresentor getRecipient() {
		return this.recipient;
	}

}
