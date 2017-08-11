package com.kota.stratagem.persistence.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public abstract class AbstractAssignment {

	@ManyToOne(fetch = FetchType.EAGER, targetEntity = AppUser.class)
	@JoinColumn(name = "assignment_entrustor", referencedColumnName = "user_id", nullable = false)
	protected AppUser entrustor;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "assignment_creation_date", nullable = false)
	protected Date creationDate;

	public AppUser getEntrustor() {
		return this.entrustor;
	}

	public void setEntrustor(AppUser entrustor) {
		this.entrustor = entrustor;
	}

	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

}
