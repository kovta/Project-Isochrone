package com.kota.stratagem.ejbservice.domain;

import com.kota.stratagem.ejbserviceclient.domain.designation.EstimatedCPMNode;

public class EstimatedCPMNodeImpl extends AbstractCPMNode implements EstimatedCPMNode {

	Double variance;

	Double pessimistic;
	Double realistic;
	Double optimisitc;

	public EstimatedCPMNodeImpl(Long id, Double pessimistic, Double realistic, Double optimisitc) {
		super(id);
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

	@Override
	public String toString() {
		return "EstimatedCPMNodeImpl [id=" + this.id + "variance=" + this.variance + ", pessimistic=" + this.pessimistic + ", realistic=" + this.realistic
				+ ", optimisitc=" + this.optimisitc + ", dependencies=" + this.dependencies + "]";
	}

}
