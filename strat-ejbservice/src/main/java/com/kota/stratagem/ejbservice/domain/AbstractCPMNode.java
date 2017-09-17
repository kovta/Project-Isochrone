package com.kota.stratagem.ejbservice.domain;

import java.util.ArrayList;
import java.util.List;

import com.kota.stratagem.ejbserviceclient.domain.designation.CPMNode;

public class AbstractCPMNode implements CPMNode {

	double criticalDuration;

	List<CPMNode> dependencies;
	List<CPMNode> dependants;

	public AbstractCPMNode() {
		this.dependencies = new ArrayList<>();
		this.dependants = new ArrayList<>();
	}

	public double getCriticalDuration() {
		return this.criticalDuration;
	}

	public void setCriticalDuration(double criticalDuration) {
		this.criticalDuration = criticalDuration;
	}

	@Override
	public List<CPMNode> getDependencies() {
		return new ArrayList<>(this.dependencies);
	}

	@Override
	public List<CPMNode> getDependants() {
		return new ArrayList<>(this.dependencies);
	}

	@Override
	public void addDependency(CPMNode node) {
		this.dependencies.add(node);
	}

	@Override
	public void addDependant(CPMNode node) {
		this.dependants.add(node);
	}

}
