package com.kota.stratagem.ejbserviceclient.domain;

import java.io.Serializable;
import java.util.Date;

public class AbstractTeamAssignmentRepresentor extends AbstractAssignmentRepresentor implements Serializable {

	private static final long serialVersionUID = 5065695607623054548L;

	protected final TeamRepresentor recipient;

	public AbstractTeamAssignmentRepresentor(Long id, AppUserRepresentor entrustor, TeamRepresentor recipient, Date creationDate) {
		super(id, entrustor, creationDate);
		this.recipient = recipient;
	}

	public AbstractTeamAssignmentRepresentor(AppUserRepresentor entrustor, TeamRepresentor recipient, Date creationDate) {
		super(entrustor, creationDate);
		this.recipient = recipient;
	}

	public TeamRepresentor getRecipient() {
		return this.recipient;
	}

}
