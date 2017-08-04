package com.kota.stratagem.ejbserviceclient.domain;

import java.io.Serializable;
import java.util.Date;

public class AppUserSubmoduleAssignmentRepresentor extends AbstractAssignmentRepresentor implements Serializable {

	private static final long serialVersionUID = -303587943046632622L;

	private final AppUserRepresentor recipient;
	private final SubmoduleRepresentor submodule;

	public AppUserSubmoduleAssignmentRepresentor(Long id, AppUserRepresentor entrustor, AppUserRepresentor recipient, SubmoduleRepresentor submodule,
			Date creationDate) {
		super(id, entrustor, creationDate);
		this.recipient = recipient;
		this.submodule = submodule;
	}

	public AppUserSubmoduleAssignmentRepresentor(AppUserRepresentor entrustor, AppUserRepresentor recipient, SubmoduleRepresentor submodule,
			Date creationDate) {
		super(entrustor, creationDate);
		this.recipient = recipient;
		this.submodule = submodule;
	}

	public AppUserRepresentor getRecipient() {
		return this.recipient;
	}

	public SubmoduleRepresentor getSubmodule() {
		return this.submodule;
	}

	@Override
	public String toString() {
		return "AppUserSubmoduleAssignmentRepresentor [recipient=" + this.recipient + ", submodule=" + this.submodule + "]";
	}

}
