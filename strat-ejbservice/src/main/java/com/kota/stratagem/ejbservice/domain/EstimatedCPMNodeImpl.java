package com.kota.stratagem.ejbservice.domain;

import com.kota.stratagem.ejbserviceclient.domain.designation.EstimatedCPMNode;

public class EstimatedCPMNodeImpl extends AbstractCPMNode implements EstimatedCPMNode {

	Double expectedDuration;
	Double variance;

	public EstimatedCPMNodeImpl(String id, Double expectedDuration, Double variance) {
		super(id);
		this.expectedDuration = expectedDuration;
		this.variance = variance;
	}

	@Override
	public Double getExpectedDuration() {
		return this.expectedDuration;
	}

	public void setExpectedDuration(Double expectedDuration) {
		this.expectedDuration = expectedDuration;
	}

	@Override
	public Double getVariance() {
		return this.variance;
	}

	public void setVariance(Double variance) {
		this.variance = variance;
	}

	@Override
	public String toString() {
		return "EstimatedCPMNodeImpl [variance=" + this.variance + ", expectedDuration=" + this.expectedDuration + ", id=" + this.id + ", criticalDuration="
				+ this.criticalDuration + ", dependencies=" + this.dependencies + ", dependants=" + this.dependants + "]";
	}

}
