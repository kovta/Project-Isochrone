package com.kota.stratagem.ejbserviceclient.domain;

import java.util.Date;

public abstract class AbstractProgressionRepresentor extends AbstractTimeConstraintRepresentor {

	private double completion;
	private Double durationSum;
	private Double completedDurationSum;
	private Boolean estimated;
	private Date expectedCompletionDate;
	private Date estimatedCompletionDate;
	private Double targetDeviation;
	private Double earlyFinishEstimation;

	public AbstractProgressionRepresentor(Date deadline, Long id) {
		super(deadline, id);
	}

	public double getCompletion() {
		return this.completion;
	}

	public void setCompletion(double completion) {
		this.completion = completion;
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

	public Double getDurationSum() {
		return this.durationSum;
	}

	public void setDurationSum(Double durationSum) {
		this.durationSum = durationSum;
	}

	public Double getCompletedDurationSum() {
		return this.completedDurationSum;
	}

	public void setCompletedDurationSum(Double completedDurationSum) {
		this.completedDurationSum = completedDurationSum;
	}

	public Boolean getEstimated() {
		return this.estimated;
	}

	public void setEstimated(Boolean estimated) {
		this.estimated = estimated;
	}

	public Date getExpectedCompletionDate() {
		return this.expectedCompletionDate;
	}

	public void setExpectedCompletionDate(Date expectedCompletionDate) {
		this.expectedCompletionDate = expectedCompletionDate;
	}

	public Date getEstimatedCompletionDate() {
		return this.estimatedCompletionDate;
	}

	public void setEstimatedCompletionDate(Date estimatedCompletionDate) {
		this.estimatedCompletionDate = estimatedCompletionDate;
	}

	public Double getTargetDeviation() {
		return this.targetDeviation;
	}

	public void setTargetDeviation(Double targetDeviation) {
		this.targetDeviation = targetDeviation;
	}

	public Double getEarlyFinishEstimation() {
		return this.earlyFinishEstimation;
	}

	public void setEarlyFinishEstimation(Double earlyFinishEstimation) {
		this.earlyFinishEstimation = earlyFinishEstimation;
	}

}
