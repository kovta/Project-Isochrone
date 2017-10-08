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
		for (final SubmoduleRepresentor submodule : submodules) {
			nodes.add(this.to(submodule));
		}
		for (final SubmoduleRepresentor submodule : submodules) {
			final CPMNode correspondingNode = this.findCorrespondingNode(submodule, nodes);
			for (final CPMNode dependency : submodule.getDependencies()) {
				final SubmoduleRepresentor dependencyNode = (SubmoduleRepresentor) dependency;
				for (final CPMNode node : nodes) {
					final AbstractCPMNode proxy = (AbstractCPMNode) node;
					if (proxy.getId().equals(dependencyNode.getId())) {
						correspondingNode.addDependency(node);
					}
				}
			}
			for (final CPMNode dependant : submodule.getDependants()) {
				final SubmoduleRepresentor dependantNode = (SubmoduleRepresentor) dependant;
				for (final CPMNode node : nodes) {
					final AbstractCPMNode proxy = (AbstractCPMNode) node;
					if (proxy.getId().equals(dependantNode.getId())) {
						correspondingNode.addDependant(node);
					}
				}
			}
		}
		return nodes;
	}

	private CPMNode findCorrespondingNode(SubmoduleRepresentor submodule, List<CPMNode> nodes) {
		for (final CPMNode node : nodes) {
			final AbstractCPMNode element = (AbstractCPMNode) node;
			if (submodule.getId().equals(element.getId())) {
				return node;
			}
		}
		return null;
	}

}
