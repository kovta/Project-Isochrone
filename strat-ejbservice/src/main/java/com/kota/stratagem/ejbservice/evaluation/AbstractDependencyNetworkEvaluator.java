package com.kota.stratagem.ejbservice.evaluation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.kota.stratagem.ejbservice.domain.CPMResult;
import com.kota.stratagem.ejbservice.domain.EstimatedCPMNodeImpl;
import com.kota.stratagem.ejbservice.exception.CyclicDependencyException;
import com.kota.stratagem.ejbservice.exception.InvalidNodeTypeException;
import com.kota.stratagem.ejbserviceclient.domain.designation.CPMNode;

public abstract class AbstractDependencyNetworkEvaluator implements DependencyNetworkEvaluator {

	protected static final String START_NODE_ID = "START_NODE";
	protected static final String END_NODE_ID = "END_NODE";

	@Override
	public CPMResult evaluate(List<CPMNode> network) throws InvalidNodeTypeException, CyclicDependencyException {
		final List<EstimatedCPMNodeImpl> nodes = new ArrayList<>();
		final EstimatedCPMNodeImpl startNode = new EstimatedCPMNodeImpl(START_NODE_ID, 0.0, 0.0);
		final EstimatedCPMNodeImpl endNode = new EstimatedCPMNodeImpl(END_NODE_ID, 0.0, 0.0);
		for (final CPMNode node : network) {
			if (node.getDependencies().isEmpty()) {
				startNode.addDependant(node);
				node.addDependency(startNode);
			}
			if (node.getDependants().isEmpty()) {
				endNode.addDependency(node);
				node.addDependant(endNode);
			}
			if (node instanceof EstimatedCPMNodeImpl) {
				nodes.add((EstimatedCPMNodeImpl) node);
			} else {
				throw new InvalidNodeTypeException();
			}
		}
		nodes.add(startNode);
		nodes.add(endNode);
		final List<EstimatedCPMNodeImpl> inspected = new ArrayList<EstimatedCPMNodeImpl>();
		final List<EstimatedCPMNodeImpl> remaining = new ArrayList<EstimatedCPMNodeImpl>(nodes);
		while (!remaining.isEmpty()) {
			boolean progress = false;
			for (final Iterator<EstimatedCPMNodeImpl> it = remaining.iterator(); it.hasNext();) {
				final EstimatedCPMNodeImpl node = it.next();
				if (inspected.containsAll(node.getDependencies())) {
					double critical = 0, variance = 0;
					for (final CPMNode n : node.getDependencies()) {
						final EstimatedCPMNodeImpl dependency = (EstimatedCPMNodeImpl) n;
						if (dependency.getCriticalDuration() > critical) {
							critical = dependency.getCriticalDuration();
							variance = dependency.getVariance() != null ? dependency.getVariance() : 0;
						}
					}
					node.setCriticalDuration(critical + this.calculateExpectedDuration(node));
					node.setVariance(variance + this.calculateVariance(node));
					inspected.add(node);
					it.remove();
					progress = true;
				}
			}
			if (!progress) {
				throw new CyclicDependencyException();
			}
		}
		return new CPMResult(endNode.getCriticalDuration(), Math.sqrt(endNode.getVariance()));
	}

	protected abstract double calculateExpectedDuration(CPMNode node) throws InvalidNodeTypeException;

	protected abstract double calculateVariance(CPMNode node) throws InvalidNodeTypeException;

}
