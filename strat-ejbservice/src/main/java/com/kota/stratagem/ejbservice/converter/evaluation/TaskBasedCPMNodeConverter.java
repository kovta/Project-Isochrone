package com.kota.stratagem.ejbservice.converter.evaluation;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.kota.stratagem.ejbservice.domain.AbstractCPMNode;
import com.kota.stratagem.ejbservice.domain.EstimatedCPMNodeImpl;
import com.kota.stratagem.ejbservice.interceptor.Certified;
import com.kota.stratagem.ejbservice.qualifier.NormalDistributionBased;
import com.kota.stratagem.ejbservice.statistics.ProbabilityCalculator;
import com.kota.stratagem.ejbserviceclient.domain.TaskRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.designation.CPMNode;
import com.kota.stratagem.persistence.qualifier.TaskFocused;
import com.kota.stratagem.persistence.util.Constants;

@TaskFocused
public class TaskBasedCPMNodeConverter extends AbstractCPMNodeConverter implements CPMNodeConverter {

	@Inject
	@NormalDistributionBased
	private ProbabilityCalculator calculator;

	private CPMNode to(TaskRepresentor task) {
		CPMNode node;
		if (task.isEstimated()) {
			node = new EstimatedCPMNodeImpl(this.buildNodeId(task.getId(), Constants.TASK_REPRESENTOR_DATA_LABEL),
					this.calculator.calculateExpectedDuration(task.getPessimistic(), task.getRealistic(), task.getOptimistic()),
					this.calculator.calculateVariance(task.getPessimistic(), task.getRealistic(), task.getOptimistic()));
		} else {
			node = new EstimatedCPMNodeImpl(this.buildNodeId(task.getId(), Constants.TASK_REPRESENTOR_DATA_LABEL), task.getDuration(), 0.0);
		}
		return node;
	}

	@Override
	@Certified(TaskRepresentor.class)
	public List<CPMNode> to(List<?> components) {
		final List<CPMNode> nodes = new ArrayList<>();
		final List<TaskRepresentor> tasks = (List<TaskRepresentor>) components;
		for (final TaskRepresentor task : tasks) {
			nodes.add(this.to(task));
		}
		for (final TaskRepresentor task : tasks) {
			final CPMNode correspondingNode = this.findCorrespondingNode(task, nodes);
			for (final CPMNode dependency : task.getDependencies()) {
				final CPMNode node = this.findCorrespondingNode((TaskRepresentor) dependency, nodes);
				if (node != null) {
					correspondingNode.addDependency(node);
				}
			}
			for (final CPMNode dependant : task.getDependants()) {
				final CPMNode node = this.findCorrespondingNode((TaskRepresentor) dependant, nodes);
				if (node != null) {
					correspondingNode.addDependant(node);
				}
			}
		}
		return nodes;
	}

	private CPMNode findCorrespondingNode(TaskRepresentor task, List<CPMNode> nodes) {
		for (final CPMNode node : nodes) {
			final AbstractCPMNode element = (AbstractCPMNode) node;
			if (this.isCorresponding(task.getId(), element.getId(), Constants.TASK_REPRESENTOR_DATA_LABEL)) {
				return node;
			}
		}
		return null;
	}

}
