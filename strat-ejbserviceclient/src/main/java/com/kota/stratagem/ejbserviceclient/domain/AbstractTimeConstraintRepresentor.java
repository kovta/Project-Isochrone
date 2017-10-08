package com.kota.stratagem.ejbserviceclient.domain;

import java.util.Date;

import com.kota.stratagem.ejbserviceclient.domain.designation.Dispatchable;

public abstract class AbstractTimeConstraintRepresentor extends AbstractMonitoredRepresentor implements Dispatchable {

	protected final Date deadline;
	private final int urgencyLevel;

	public AbstractTimeConstraintRepresentor(Date deadline) {
		this.deadline = deadline;
		final Date target = deadline != null ? deadline : new Date();
		final Date now = new Date();
		final long diff = (target.getTime() - now.getTime()) / (1000 * 60 * 60 * 24);
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

	public Date getDeadline() {
		return this.deadline;
	}

	public Boolean isDeadlineProvided() {
		return this.getDeadline() != null;
	}

}
