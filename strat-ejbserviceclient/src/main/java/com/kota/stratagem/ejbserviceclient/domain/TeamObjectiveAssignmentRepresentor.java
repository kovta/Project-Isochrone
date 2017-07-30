package com.kota.stratagem.ejbserviceclient.domain;

import java.io.Serializable;
import java.util.Date;

public class TeamObjectiveAssignmentRepresentor extends AbstractAssignmentRepresentor implements Serializable {

	private static final long serialVersionUID = 5059019199128505192L;

	private final TeamRepresentor recipient;
	private final ObjectiveRepresentor objective;

	public TeamObjectiveAssignmentRepresentor(Long id, AppUserRepresentor entrustor, TeamRepresentor recipient, ObjectiveRepresentor objective,
			Date creationDate) {
		super(id, entrustor, creationDate);
		this.recipient = recipient;
		this.objective = objective;
	}

	public TeamObjectiveAssignmentRepresentor(AppUserRepresentor entrustor, TeamRepresentor recipient, ObjectiveRepresentor objective, Date creationDate) {
		super(entrustor, creationDate);
		this.recipient = recipient;
		this.objective = objective;
	}

	public TeamRepresentor getRecipient() {
		return this.recipient;
	}

	public ObjectiveRepresentor getObjective() {
		return this.objective;
	}

	@Override
	public String toString() {
		return "TeamObjectiveAssignmentRepresentor [recipient=" + this.recipient + ", objective=" + this.objective + "]";
	}

}
