package com.kota.stratagem.ejbserviceclient.domain;

import java.io.Serializable;
import java.util.Date;

public class TeamTaskAssignmentRepresentor extends AbstractTeamAssignmentRepresentor implements Serializable {

	private static final long serialVersionUID = 362388974304407634L;

	private final TaskRepresentor task;

	public TeamTaskAssignmentRepresentor(Long id, AppUserRepresentor entrustor, TeamRepresentor recipient, TaskRepresentor task, Date creationDate) {
		super(id, entrustor, recipient, creationDate);
		this.task = task;
	}

	public TeamTaskAssignmentRepresentor(AppUserRepresentor entrustor, TeamRepresentor recipient, TaskRepresentor task, Date creationDate) {
		super(entrustor, recipient, creationDate);
		this.task = task;
	}

	public TaskRepresentor getTask() {
		return this.task;
	}

	@Override
	public String toString() {
		return "TeamTaskAssignmentRepresentor [task=" + this.task + "]";
	}

	@Override
	public String toTextMessage() {
		// TODO Auto-generated method stub
		return null;
	}

}
