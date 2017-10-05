package com.kota.stratagem.ejbservice.evaluation;

import java.util.List;

import com.kota.stratagem.ejbservice.domain.CPMResult;
import com.kota.stratagem.ejbservice.exception.CyclicDependencyException;
import com.kota.stratagem.ejbservice.exception.InvalidNodeTypeException;
import com.kota.stratagem.ejbservice.qualifier.Definitive;
import com.kota.stratagem.ejbserviceclient.domain.designation.CPMNode;
import com.kota.stratagem.ejbserviceclient.domain.designation.DefinitiveCPMNode;
import com.kota.stratagem.ejbserviceclient.domain.designation.EstimatedCPMNode;

@Definitive
public class DefinitiveDependencyNetworkEvaluator extends AbstractDependencyNetworkEvaluator implements DependencyNetworkEvaluator {

	@Override
	public CPMResult evaluate(List<CPMNode> network) throws InvalidNodeTypeException, CyclicDependencyException {
		return super.evaluate(network);
	}

	@Override
	protected double calculateExpectedDuration(CPMNode node) throws InvalidNodeTypeException {
		if (node instanceof DefinitiveCPMNode) {
			final DefinitiveCPMNode element = (DefinitiveCPMNode) node;
			return element.getDuration();
		} else if (node instanceof EstimatedCPMNode) {
			final EstimatedCPMNode element = (EstimatedCPMNode) node;
			return element.getExpectedDuration();
		} else {
			throw new InvalidNodeTypeException();
		}
	}

	@Override
	protected double calculateVariance(CPMNode node) {
		return 0;
	}

}
