package com.kota.stratagem.ejbserviceclient.domain;

import java.util.Date;

public abstract class AbstractTimeConstraintRepresentor extends AbstractIdentityObscuror {

	private final int urgencyLevel;

	public AbstractTimeConstraintRepresentor(Date deadline, Long id) {
		super(id);
		final Date now = new Date();
		final long diff = (deadline.getTime() - now.getTime()) / (1000 * 60 * 60 * 24);
		if (diff < 0) {
			this.urgencyLevel = 3;
		} else if (diff < 1) {
			this.urgencyLevel = 2;
		} else if (diff <= 7) {
			this.urgencyLevel = 1;
		} else {
			this.urgencyLevel = 0;
		}

	}

	public int getUrgencyLevel() {
		return this.urgencyLevel;
	}

}
