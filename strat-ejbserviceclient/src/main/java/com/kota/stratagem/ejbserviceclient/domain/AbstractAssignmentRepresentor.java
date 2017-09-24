package com.kota.stratagem.ejbserviceclient.domain;

import java.util.Date;

import com.kota.stratagem.ejbserviceclient.domain.designation.Dispatchable;

public abstract class AbstractAssignmentRepresentor implements Dispatchable {

	protected Long id;
	protected final AppUserRepresentor entrustor;
	protected final Date creationDate;

	public AbstractAssignmentRepresentor(Long id, AppUserRepresentor entrustor, Date creationDate) {
		super();
		this.id = id;
		this.entrustor = entrustor;
		this.creationDate = creationDate;
	}

	public AbstractAssignmentRepresentor(AppUserRepresentor entrustor, Date creationDate) {
		super();
		this.entrustor = entrustor;
		this.creationDate = creationDate;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AppUserRepresentor getEntrustor() {
		return this.entrustor;
	}

	public Date getCreationDate() {
		return this.creationDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.id == null) ? 0 : this.id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final AbstractAssignmentRepresentor other = (AbstractAssignmentRepresentor) obj;
		if (this.id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!this.id.equals(other.id)) {
			return false;
		}
		return true;
	}

}
