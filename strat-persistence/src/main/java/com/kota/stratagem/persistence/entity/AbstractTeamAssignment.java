package com.kota.stratagem.persistence.entity;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractTeamAssignment extends AbstractAssignment {

	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, targetEntity = Team.class)
	@JoinColumn(name = "assignment_recipient", referencedColumnName = "team_id", nullable = false)
	protected Team recipient;

	public Team getRecipient() {
		return this.recipient;
	}

	public void setRecipient(Team recipient) {
		this.recipient = recipient;
	}

}
