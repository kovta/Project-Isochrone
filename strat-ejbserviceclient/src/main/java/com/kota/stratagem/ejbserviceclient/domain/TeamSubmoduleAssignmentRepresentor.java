package com.kota.stratagem.ejbserviceclient.domain;

import java.io.Serializable;
import java.util.Date;

public class TeamSubmoduleAssignmentRepresentor extends AbstractTeamAssignmentRepresentor implements Serializable {

	private static final long serialVersionUID = 838524957773822183L;

	private final SubmoduleRepresentor submodule;

	public TeamSubmoduleAssignmentRepresentor(Long id, AppUserRepresentor entrustor, TeamRepresentor recipient, SubmoduleRepresentor submodule,
			Date creationDate) {
		super(id, entrustor, recipient, creationDate);
		this.submodule = submodule;
	}

	public TeamSubmoduleAssignmentRepresentor(AppUserRepresentor entrustor, TeamRepresentor recipient, SubmoduleRepresentor submodule, Date creationDate) {
		super(entrustor, recipient, creationDate);
		this.submodule = submodule;
	}

	public SubmoduleRepresentor getSubmodule() {
		return this.submodule;
	}

	@Override
	public String toString() {
		return "TeamSubmoduleAssignmentRepresentor [submodule=" + this.submodule + "]";
	}

	@Override
	public String toTextMessage() {
		// TODO Auto-generated method stub
		return null;
	}

}
