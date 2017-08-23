package com.kota.stratagem.ejbserviceclient.domain;

import java.io.Serializable;
import java.util.Date;

public class AppUserProjectAssignmentRepresentor extends AbstractAppUserAssignmentRepresentor implements Serializable {

	private static final long serialVersionUID = -4732660193181661667L;

	private final ProjectRepresentor project;

	public AppUserProjectAssignmentRepresentor(Long id, AppUserRepresentor entrustor, AppUserRepresentor recipient, ProjectRepresentor project,
			Date creationDate) {
		super(id, entrustor, recipient, creationDate);
		this.project = project;
	}

	public AppUserProjectAssignmentRepresentor(AppUserRepresentor entrustor, AppUserRepresentor recipient, ProjectRepresentor project, Date creationDate) {
		super(entrustor, recipient, creationDate);
		this.project = project;
	}

	public ProjectRepresentor getProject() {
		return this.project;
	}

	@Override
	public String toString() {
		return "AppUserProjectAssignmentRepresentor [recipient=" + this.recipient + ", project=" + this.project + "]";
	}

	public String toTextMessage() {
		return "AppUserProjectAssignmentRepresentor | [id=" + this.id + ", project_id=" + this.project.getId() + ", project_name=" + this.project.getName()
				+ ", recipient_id=" + this.recipient.getId() + ", entrustor_id=" + this.entrustor.getId() + ", creationDate=" + this.creationDate + "]";
	}

}