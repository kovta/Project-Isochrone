package com.kota.stratagem.ejbservice.converter.evaluation;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import com.kota.stratagem.ejbservice.domain.AbstractCPMNode;
import com.kota.stratagem.ejbservice.domain.DefinitiveCPMNodeImpl;
import com.kota.stratagem.ejbservice.domain.EstimatedCPMNodeImpl;
import com.kota.stratagem.ejbserviceclient.domain.TaskRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.designation.CPMNode;

@Stateless
public class CPMNodeConverterImpl implements CPMNodeConverter {

	private CPMNode to(TaskRepresentor task) {
		CPMNode node;
		if (task.isEstimated()) {
			node = new EstimatedCPMNodeImpl(task.getId(), task.getPessimistic(), task.getRealistic(), task.getOptimistic());
		} else {
			node = new DefinitiveCPMNodeImpl(task.getId(), task.getDuration());
		}
		return node;
	}

	@Override
	public List<CPMNode> to(List<TaskRepresentor> tasks) {
		final List<CPMNode> nodes = new ArrayList<>();
		for (final TaskRepresentor task : tasks) {
			nodes.add(this.to(task));
		}
		for (final TaskRepresentor task : tasks) {
			final CPMNode correspondingNode = this.findCorrespondingNode(task, nodes);
			for (final CPMNode dependency : task.getDependencies()) {
				final TaskRepresentor dependencyNode = (TaskRepresentor) dependency;
				for (final CPMNode node : nodes) {
					final AbstractCPMNode proxy = (AbstractCPMNode) node;
					if (proxy.getId() == dependencyNode.getId()) {
						correspondingNode.addDependency(node);
					}
				}
			}
			for (final CPMNode dependant : task.getDependants()) {
				final TaskRepresentor dependantNode = (TaskRepresentor) dependant;
				for (final CPMNode node : nodes) {
					final AbstractCPMNode proxy = (AbstractCPMNode) node;
					if (proxy.getId() == dependantNode.getId()) {
						correspondingNode.addDependant(node);
					}
				}
			}
		}
		return nodes;
	}

	private CPMNode findCorrespondingNode(TaskRepresentor task, List<CPMNode> nodes) {
		for (final CPMNode node : nodes) {
			final AbstractCPMNode element = (AbstractCPMNode) node;
			if (task.getId() == element.getId()) {
				return node;
			}
		}
		return null;
	}

}
