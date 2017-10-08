package com.kota.stratagem.ejbserviceclient.domain;

import java.util.Date;

public abstract class AbstractMonitoredRepresentor {

	protected AppUserRepresentor creator;
	protected Date creationDate;
	protected AppUserRepresentor modifier;
	protected Date modificationDate;

	public AppUserRepresentor getCreator() {
		return this.creator;
	}

	public void setCreator(AppUserRepresentor creator) {
		this.creator = creator;
	}

	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public AppUserRepresentor getModifier() {
		return this.modifier;
	}

	public void setModifier(AppUserRepresentor modifier) {
		this.modifier = modifier;
	}

	public Date getModificationDate() {
		return this.modificationDate;
	}

	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}

}
