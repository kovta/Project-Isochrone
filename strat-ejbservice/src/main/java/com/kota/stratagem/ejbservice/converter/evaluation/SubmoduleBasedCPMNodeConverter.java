package com.kota.stratagem.ejbservice.converter.evaluation;

import java.util.ArrayList;
import java.util.List;

import com.kota.stratagem.ejbservice.domain.AbstractCPMNode;
import com.kota.stratagem.ejbservice.domain.EstimatedCPMNodeImpl;
import com.kota.stratagem.ejbservice.interceptor.Certified;
import com.kota.stratagem.ejbserviceclient.domain.SubmoduleRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.designation.CPMNode;
import com.kota.stratagem.persistence.qualifier.SubmoduleFocused;
import com.kota.stratagem.persistence.util.Constants;

@SubmoduleFocused
public class SubmoduleBasedCPMNodeConverter extends AbstractCPMNodeConverter implements CPMNodeConverter {

	private CPMNode to(SubmoduleRepresentor submodule) {
		return new EstimatedCPMNodeImpl(this.buildNodeId(submodule.getId(), Constants.SUBMODULE_REPRESENTOR_DATA_LABEL), submodule.getExpectedDuration(), submodule.getVariance());
	}

	@Override
	@Certified(SubmoduleRepresentor.class)
	public List<CPMNode> to(List<?> components) {
		final List<CPMNode> nodes = new ArrayList<>();
		final List<SubmoduleRepresentor> submodules = (List<SubmoduleRepresentor>) components;
		for(final SubmoduleRepresentor submodule : submodules) {
			nodes.add(this.to(submodule));
		}
		for(final SubmoduleRepresentor submodule : submodules) {
			final CPMNode correspondingNode = this.findCorrespondingNode(submodule, nodes);
			for(final CPMNode dependency : submodule.getDependencies()) {
				CPMNode node = this.findCorrespondingNode((SubmoduleRepresentor) dependency, nodes);
				if(node != null) {
					correspondingNode.addDependency(node);
				}
			}
			for(final CPMNode dependant : submodule.getDependants()) {
				CPMNode node = this.findCorrespondingNode((SubmoduleRepresentor) dependant, nodes);
				if(node != null) {
					correspondingNode.addDependant(node);
				}
			}
		}
		return nodes;
	}

	private CPMNode findCorrespondingNode(SubmoduleRepresentor submodule, List<CPMNode> nodes) {
		for(final CPMNode node : nodes) {
			final AbstractCPMNode element = (AbstractCPMNode) node;
			if(isCorresponding(submodule.getId(), element.getId(), Constants.SUBMODULE_REPRESENTOR_DATA_LABEL)) {
				return node;
			}
		}
		return null;
	}

}
