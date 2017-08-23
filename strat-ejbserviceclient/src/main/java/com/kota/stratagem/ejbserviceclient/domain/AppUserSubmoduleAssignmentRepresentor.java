package com.kota.stratagem.ejbserviceclient.domain;

import java.io.Serializable;
import java.util.Date;

public class AppUserSubmoduleAssignmentRepresentor extends AbstractAppUserAssignmentRepresentor implements Serializable {

	private static final long serialVersionUID = -303587943046632622L;

	private final SubmoduleRepresentor submodule;

	public AppUserSubmoduleAssignmentRepresentor(Long id, AppUserRepresentor entrustor, AppUserRepresentor recipient, SubmoduleRepresentor submodule,
			Date creationDate) {
		super(id, entrustor, recipient, creationDate);
		this.submodule = submodule;
	}

	public AppUserSubmoduleAssignmentRepresentor(AppUserRepresentor entrustor, AppUserRepresentor recipient, SubmoduleRepresentor submodule,
			Date creationDate) {
		super(entrustor, recipient, creationDate);
		this.submodule = submodule;
	}

	public SubmoduleRepresentor getSubmodule() {
		return this.submodule;
	}

	@Override
	public String toString() {
		return "AppUserSubmoduleAssignmentRepresentor [recipient=" + this.recipient + ", submodule=" + this.submodule + "]";
	}

	public String toTextMessage() {
		return "AppUserSubmoduleAssignmentRepresentor | [id=" + this.id + ", submodule_id=" + this.submodule.getId() + ", submodule_name="
				+ this.submodule.getName() + ", recipient_id=" + this.recipient.getId() + ", entrustor_id=" + this.entrustor.getId() + ", creationDate="
				+ this.creationDate + "]";
	}

}
