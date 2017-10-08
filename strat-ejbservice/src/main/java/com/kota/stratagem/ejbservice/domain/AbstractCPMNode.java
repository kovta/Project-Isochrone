package com.kota.stratagem.ejbservice.domain;

import java.util.ArrayList;
import java.util.List;

import com.kota.stratagem.ejbserviceclient.domain.designation.CPMNode;

public class AbstractCPMNode implements CPMNode {

	String id;
	double criticalDuration;

	List<CPMNode> dependencies;
	List<CPMNode> dependants;

	public AbstractCPMNode(String id) {
		this.id = id;
		this.dependencies = new ArrayList<>();
		this.dependants = new ArrayList<>();
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.id == null) ? 0 : this.id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(obj == null) {
			return false;
		}
		if(this.getClass() != obj.getClass()) {
			return false;
		}
		AbstractCPMNode other = (AbstractCPMNode) obj;
		if(this.id == null) {
			if(other.id != null) {
				return false;
			}
		} else if(!this.id.equals(other.id)) {
			return false;
		}
		return true;
	}

}
