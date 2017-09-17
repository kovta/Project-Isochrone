package com.kota.stratagem.ejbservice.evaluation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.kota.stratagem.ejbservice.domain.CPMResult;
import com.kota.stratagem.ejbservice.domain.DefinitiveCPMNodeImpl;
import com.kota.stratagem.ejbservice.domain.EstimatedCPMNodeImpl;
import com.kota.stratagem.ejbservice.exception.CyclicDependencyException;
import com.kota.stratagem.ejbservice.exception.InvalidNodeTypeException;
import com.kota.stratagem.ejbserviceclient.domain.designation.CPMNode;

public abstract class AbstractDependencyNetworkEvaluator implements DependencyNetworkEvaluator {

	@Override
	public CPMResult evaluate(List<CPMNode> network) throws InvalidNodeTypeException, CyclicDependencyException {
		final List<EstimatedCPMNodeImpl> nodes = new ArrayList<>();
		final EstimatedCPMNodeImpl start = new EstimatedCPMNodeImpl((double) 0, (double) 0, (double) 0);
		final EstimatedCPMNodeImpl end = new EstimatedCPMNodeImpl((double) 0, (double) 0, (double) 0);
		for (final CPMNode node : network) {
			if (node.getDependencies().isEmpty()) {
				start.addDependant(node);
			}
			if (node.getDependants().isEmpty()) {
				end.addDependency(node);
			}
			if (node instanceof EstimatedCPMNodeImpl) {
				nodes.add((EstimatedCPMNodeImpl) node);
			} else if (node instanceof DefinitiveCPMNodeImpl) {
				final DefinitiveCPMNodeImpl element = (DefinitiveCPMNodeImpl) node;
				nodes.add(new EstimatedCPMNodeImpl(element.getDuration(), element.getDuration(), element.getDuration()));
			} else {
				throw new InvalidNodeTypeException();
			}
		}
		nodes.add(start);
		nodes.add(end);
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
							variance = dependency.getVariance();
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
		double expectedDuration = 0, variance = 0;
		for (final EstimatedCPMNodeImpl element : inspected) {
			if (element.getCriticalDuration() > expectedDuration) {
				expectedDuration = element.getCriticalDuration();
				variance = element.getVariance();
			}
		}
		return new CPMResult(expectedDuration, Math.sqrt(variance));
	}

	protected abstract double calculateExpectedDuration(CPMNode node) throws InvalidNodeTypeException;

	protected abstract double calculateVariance(CPMNode node) throws InvalidNodeTypeException;

}
