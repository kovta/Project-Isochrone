package com.kota.stratagem.ejbserviceclient.domain;

import java.io.Serializable;
import java.util.Date;

public abstract class AbstractAppUserAssignmentRepresentor extends AbstractAssignmentRepresentor implements Serializable {

	private static final long serialVersionUID = -3286792703224468467L;

	protected final AppUserRepresentor recipient;

	public AbstractAppUserAssignmentRepresentor(Long id, AppUserRepresentor entrustor, AppUserRepresentor recipient, Date creationDate) {
		super(id, entrustor, creationDate);
		this.recipient = recipient;
	}

	public AbstractAppUserAssignmentRepresentor(AppUserRepresentor entrustor, AppUserRepresentor recipient, Date creationDate) {
		super(entrustor, creationDate);
		this.recipient = recipient;
	}

	public AppUserRepresentor getRecipient() {
		return this.recipient;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = (prime * result) + ((this.recipient == null) ? 0 : this.recipient.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final AbstractAppUserAssignmentRepresentor other = (AbstractAppUserAssignmentRepresentor) obj;
		if (this.recipient == null) {
			if (other.recipient != null) {
				return false;
			}
		} else if (!this.recipient.equals(other.recipient)) {
			return false;
		}
		return true;
	}

}
