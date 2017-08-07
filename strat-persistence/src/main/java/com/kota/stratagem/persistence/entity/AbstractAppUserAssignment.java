package com.kota.stratagem.persistence.entity;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractAppUserAssignment extends AbstractAssignment {

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH, targetEntity = AppUser.class)
	@JoinColumn(name = "assignment_recipient", referencedColumnName = "user_id", nullable = false)
	protected AppUser recipient;

	public AppUser getRecipient() {
		return this.recipient;
	}

	public void setRecipient(AppUser recipient) {
		this.recipient = recipient;
	}

}
