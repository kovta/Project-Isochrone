package com.kota.stratagem.ejbserviceclient.domain;

import java.io.Serializable;
import java.util.Date;

public class AppUserTaskAssignmentRepresentor extends AbstractAppUserAssignmentRepresentor implements Serializable {

	private static final long serialVersionUID = -7930701516714196957L;

	private final TaskRepresentor task;

	public AppUserTaskAssignmentRepresentor(Long id, AppUserRepresentor entrustor, AppUserRepresentor recipient, TaskRepresentor task, Date creationDate) {
		super(id, entrustor, recipient, creationDate);
		this.task = task;
	}

	public AppUserTaskAssignmentRepresentor(AppUserRepresentor entrustor, AppUserRepresentor recipient, TaskRepresentor task, Date creationDate) {
		super(entrustor, recipient, creationDate);
		this.task = task;
	}

	public TaskRepresentor getTask() {
		return this.task;
	}

	@Override
	public String toString() {
		return "AppUserTaskAssignmentRepresentor [recipient=" + this.recipient + ", task=" + this.task + "]";
	}

}
