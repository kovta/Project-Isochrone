package com.kota.stratagem.ejbservice.domain;

import com.kota.stratagem.ejbserviceclient.domain.designation.EstimatedCPMNode;

public class EstimatedCPMNodeImpl extends AbstractCPMNode implements EstimatedCPMNode {

	Double variance;

	Double pessimistic;
	Double realistic;
	Double optimisitc;

	public EstimatedCPMNodeImpl(Double pessimistic, Double realistic, Double optimisitc) {
		super();
		this.pessimistic = pessimistic;
		this.realistic = realistic;
		this.optimisitc = optimisitc;
	}

	public Double getVariance() {
		return this.variance;
	}

	public void setVariance(Double variance) {
		this.variance = variance;
	}

	@Override
	public Double getPessimistic() {
		return this.pessimistic;
	}

	@Override
	public Double getRealistic() {
		return this.realistic;
	}

	@Override
	public Double getOptimistic() {
		return this.optimisitc;
	}

}
