package com.kota.stratagem.ejbservice.evaluation;

import java.util.List;

import com.kota.stratagem.ejbservice.domain.CPMResult;
import com.kota.stratagem.ejbservice.exception.CyclicDependencyException;
import com.kota.stratagem.ejbservice.exception.InvalidNodeTypeException;
import com.kota.stratagem.ejbserviceclient.domain.designation.CPMNode;

public interface DependencyNetworkEvaluator {

	CPMResult evaluate(List<CPMNode> network) throws InvalidNodeTypeException, CyclicDependencyException;

}
