package com.kota.stratagem.ejbserviceclient.domain;

import java.io.Serializable;
import java.util.Date;

public class AppUserObjectiveAssignmentRepresentor extends AbstractAssignmentRepresentor implements Serializable {

	private static final long serialVersionUID = -6842707197339241183L;

	private final AppUserRepresentor recipient;
	private final ObjectiveRepresentor objective;

	public AppUserObjectiveAssignmentRepresentor(Long id, AppUserRepresentor entrustor, AppUserRepresentor recipient, ObjectiveRepresentor objective,
			Date creationDate) {
		super(id, entrustor, creationDate);
		this.recipient = recipient;
		this.objective = objective;
	}

	public AppUserObjectiveAssignmentRepresentor(AppUserRepresentor entrustor, AppUserRepresentor recipient, ObjectiveRepresentor objective,
			Date creationDate) {
		super(entrustor, creationDate);
		this.recipient = recipient;
		this.objective = objective;
	}

	public AppUserRepresentor getRecipient() {
		return this.recipient;
	}

	public ObjectiveRepresentor getObjective() {
		return this.objective;
	}

	@Override
	public String toString() {
		return "UserObjectiveAssignmentRepresentor [recipient=" + this.recipient + ", objective=" + this.objective + "]";
	}

}
