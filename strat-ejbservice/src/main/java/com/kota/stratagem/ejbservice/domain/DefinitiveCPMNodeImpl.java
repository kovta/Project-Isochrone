package com.kota.stratagem.ejbservice.domain;

import com.kota.stratagem.ejbserviceclient.domain.designation.DefinitiveCPMNode;

public class DefinitiveCPMNodeImpl extends AbstractCPMNode implements DefinitiveCPMNode {

	Double duration;

	public DefinitiveCPMNodeImpl(Double duration) {
		super();
		this.duration = duration;
	}

	@Override
	public Double getDuration() {
		return this.duration;
	}

}
