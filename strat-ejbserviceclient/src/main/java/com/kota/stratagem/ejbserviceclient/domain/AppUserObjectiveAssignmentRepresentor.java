package com.kota.stratagem.ejbserviceclient.domain;

import java.io.Serializable;
import java.util.Date;

public class AppUserObjectiveAssignmentRepresentor extends AbstractAppUserAssignmentRepresentor implements Serializable {

	private static final long serialVersionUID = -6842707197339241183L;

	private final ObjectiveRepresentor objective;

	public AppUserObjectiveAssignmentRepresentor(Long id, AppUserRepresentor entrustor, AppUserRepresentor recipient, ObjectiveRepresentor objective,
			Date creationDate) {
		super(id, entrustor, recipient, creationDate);
		this.objective = objective;
	}

	public AppUserObjectiveAssignmentRepresentor(AppUserRepresentor entrustor, AppUserRepresentor recipient, ObjectiveRepresentor objective,
			Date creationDate) {
		super(entrustor, recipient, creationDate);
		this.objective = objective;
	}

	public ObjectiveRepresentor getObjective() {
		return this.objective;
	}

	@Override
	public String toString() {
		return "UserObjectiveAssignmentRepresentor [recipient=" + this.recipient + ", objective=" + this.objective + "]";
	}

	@Override
	public String toTextMessage() {
		return "AppUserObjectiveAssignmentRepresentor | [id=" + this.id + ", objective_id=" + this.objective.getId() + ", objective_name="
				+ this.objective.getName() + ", recipient_id=" + this.recipient.getId() + ", entrustor_id=" + this.entrustor.getId() + ", creationDate="
				+ this.creationDate + "]";
	}

}
