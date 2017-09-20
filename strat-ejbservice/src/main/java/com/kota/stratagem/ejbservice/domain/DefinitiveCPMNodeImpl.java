package com.kota.stratagem.ejbservice.domain;

import com.kota.stratagem.ejbserviceclient.domain.designation.DefinitiveCPMNode;

public class DefinitiveCPMNodeImpl extends AbstractCPMNode implements DefinitiveCPMNode {

	Double duration;

	public DefinitiveCPMNodeImpl(Long id, Double duration) {
		super(id);
		this.duration = duration;
	}

	@Override
	public Double getDuration() {
		return this.duration;
	}

	@Override
	public String toString() {
		return "DefinitiveCPMNodeImpl [id=" + this.id + "duration=" + this.duration + ", dependencies=" + this.dependencies + "]";
	}

}
