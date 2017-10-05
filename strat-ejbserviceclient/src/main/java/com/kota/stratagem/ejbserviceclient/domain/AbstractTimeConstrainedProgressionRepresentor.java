package com.kota.stratagem.ejbserviceclient.domain;

import java.util.Date;

import com.kota.stratagem.ejbserviceclient.domain.designation.Dispatchable;

public abstract class AbstractTimeConstrainedProgressionRepresentor extends AbstractMonitoredRepresentor implements Dispatchable {

	private double completion;
	private final int urgencyLevel;

	public AbstractTimeConstrainedProgressionRepresentor(Date deadline, Long id) {
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

	public double getCompletion() {
		return this.completion;
	}

	public void setCompletion(double completion) {
		this.completion = completion;
	}

	public int getUrgencyLevel() {
		return this.urgencyLevel;
	}

	public Boolean isCompleted() {
		return this.getCompletion() == 100;
	}

	public Boolean isOngoing() {
		return (this.getCompletion() < 100) && (this.getCompletion() > 0);
	}

	public Boolean isUnstarted() {
		return this.getCompletion() == 0;
	}

}
