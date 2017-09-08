package com.kota.stratagem.ejbserviceclient.domain;

import java.io.Serializable;
import java.util.Date;

public class TeamObjectiveAssignmentRepresentor extends AbstractTeamAssignmentRepresentor implements Serializable {

	private static final long serialVersionUID = 5059019199128505192L;

	private final ObjectiveRepresentor objective;

	public TeamObjectiveAssignmentRepresentor(Long id, AppUserRepresentor entrustor, TeamRepresentor recipient, ObjectiveRepresentor objective,
			Date creationDate) {
		super(id, entrustor, recipient, creationDate);
		this.objective = objective;
	}

	public TeamObjectiveAssignmentRepresentor(AppUserRepresentor entrustor, TeamRepresentor recipient, ObjectiveRepresentor objective, Date creationDate) {
		super(entrustor, recipient, creationDate);
		this.objective = objective;
	}

	@Override
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

	@Override
	public String toTextMessage() {
		// TODO Auto-generated method stub
		return null;
	}

}
