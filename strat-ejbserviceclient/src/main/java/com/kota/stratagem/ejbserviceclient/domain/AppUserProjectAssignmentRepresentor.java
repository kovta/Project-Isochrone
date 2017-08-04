package com.kota.stratagem.ejbserviceclient.domain;

import java.io.Serializable;
import java.util.Date;

public class AppUserProjectAssignmentRepresentor extends AbstractAssignmentRepresentor implements Serializable {

	private static final long serialVersionUID = -4732660193181661667L;

	private final AppUserRepresentor recipient;
	private final ProjectRepresentor project;

	public AppUserProjectAssignmentRepresentor(Long id, AppUserRepresentor entrustor, AppUserRepresentor recipient, ProjectRepresentor project,
			Date creationDate) {
		super(id, entrustor, creationDate);
		this.recipient = recipient;
		this.project = project;
	}

	public AppUserProjectAssignmentRepresentor(AppUserRepresentor entrustor, AppUserRepresentor recipient, ProjectRepresentor project, Date creationDate) {
		super(entrustor, creationDate);
		this.recipient = recipient;
		this.project = project;
	}

	public AppUserRepresentor getRecipient() {
		return this.recipient;
	}

	public ProjectRepresentor getProject() {
		return this.project;
	}

	@Override
	public String toString() {
		return "AppUserProjectAssignmentRepresentor [recipient=" + this.recipient + ", project=" + this.project + "]";
	}

}