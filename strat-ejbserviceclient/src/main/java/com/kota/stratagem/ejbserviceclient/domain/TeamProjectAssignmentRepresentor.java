package com.kota.stratagem.ejbserviceclient.domain;

import java.io.Serializable;
import java.util.Date;

public class TeamProjectAssignmentRepresentor extends AbstractTeamAssignmentRepresentor implements Serializable {

	private static final long serialVersionUID = 3372250187190490652L;

	private final ProjectRepresentor project;

	public TeamProjectAssignmentRepresentor(Long id, AppUserRepresentor entrustor, TeamRepresentor recipient, ProjectRepresentor project, Date creationDate) {
		super(id, entrustor, recipient, creationDate);
		this.project = project;
	}

	public TeamProjectAssignmentRepresentor(AppUserRepresentor entrustor, TeamRepresentor recipient, ProjectRepresentor project, Date creationDate) {
		super(entrustor, recipient, creationDate);
		this.project = project;
	}

	public ProjectRepresentor getProject() {
		return this.project;
	}

	@Override
	public String toString() {
		return "TeamProjectAssignmentRepresentor [project=" + this.project + "]";
	}

}
