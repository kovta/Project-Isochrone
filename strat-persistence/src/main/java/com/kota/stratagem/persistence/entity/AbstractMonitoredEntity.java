package com.kota.stratagem.persistence.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public abstract class AbstractMonitoredEntity {

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, targetEntity = AppUser.class)
	protected AppUser creator;

	@Temporal(TemporalType.TIMESTAMP)
	protected Date creationDate;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE, targetEntity = AppUser.class)
	protected AppUser modifier;

	@Temporal(TemporalType.TIMESTAMP)
	protected Date modificationDate;

	public AppUser getCreator() {
		return this.creator;
	}

	public void setCreator(AppUser creator) {
		this.creator = creator;
	}

	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public AppUser getModifier() {
		return this.modifier;
	}

	public void setModifier(AppUser modifier) {
		this.modifier = modifier;
	}

	public Date getModificationDate() {
		return this.modificationDate;
	}

	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}

}
