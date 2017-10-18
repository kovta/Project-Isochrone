package com.kota.stratagem.ejbservice.evaluation;

import java.util.ArrayList;
import java.util.List;

import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.kota.stratagem.ejbservice.core.AbstractMockTest;
import com.kota.stratagem.ejbservice.domain.CPMResult;
import com.kota.stratagem.ejbservice.domain.EstimatedCPMNodeImpl;
import com.kota.stratagem.ejbservice.exception.CyclicDependencyException;
import com.kota.stratagem.ejbservice.exception.InvalidNodeTypeException;
import com.kota.stratagem.ejbserviceclient.domain.designation.CPMNode;

public class DependencyNetworkEvaluatorTest extends AbstractMockTest {

	protected static final String NODE_ID_PREFIX = "TST-TSK-";

	protected static final String START_NODE_ID = "START_NODE";
	protected static final String END_NODE_ID = "END_NODE";

	@Test
	public void produceCPMResultFromDependencyNetwork() throws InvalidNodeTypeException, CyclicDependencyException {
		final EstimatedDependencyNetworkEvaluatorImpl networkEvaluator = Mockito.spy(new EstimatedDependencyNetworkEvaluatorImpl());
		final List<CPMNode> networkParameter = new ArrayList<>();
		final EstimatedCPMNodeImpl nodeA = new EstimatedCPMNodeImpl(NODE_ID_PREFIX + 1, 2.83, 0.50); // C
		final EstimatedCPMNodeImpl nodeB = new EstimatedCPMNodeImpl(NODE_ID_PREFIX + 2, 7.33, 0.66); // C
		final EstimatedCPMNodeImpl nodeC = new EstimatedCPMNodeImpl(NODE_ID_PREFIX + 3, 2.33, 0.66);
		final EstimatedCPMNodeImpl nodeD = new EstimatedCPMNodeImpl(NODE_ID_PREFIX + 4, 3.50, 0.83); // C
		final EstimatedCPMNodeImpl nodeE = new EstimatedCPMNodeImpl(NODE_ID_PREFIX + 5, 1.00, 0.33); // C
		final EstimatedCPMNodeImpl nodeF = new EstimatedCPMNodeImpl(NODE_ID_PREFIX + 6, 8.83, 1.16);
		final EstimatedCPMNodeImpl nodeG = new EstimatedCPMNodeImpl(NODE_ID_PREFIX + 7, 3.83, 0.83); // C
		nodeA.addDependant(nodeB);
		nodeA.addDependant(nodeC);
		nodeB.addDependency(nodeA);
		nodeB.addDependant(nodeD);
		nodeC.addDependency(nodeA);
		nodeC.addDependant(nodeD);
		nodeD.addDependency(nodeB);
		nodeD.addDependency(nodeC);
		nodeD.addDependant(nodeE);
		nodeD.addDependant(nodeF);
		nodeE.addDependency(nodeD);
		nodeE.addDependant(nodeG);
		nodeF.addDependency(nodeD);
		nodeF.addDependant(nodeG);
		nodeG.addDependency(nodeE);
		nodeG.addDependency(nodeF);
		final EstimatedCPMNodeImpl startNode = new EstimatedCPMNodeImpl(START_NODE_ID, 0.0, 0.0);
		final EstimatedCPMNodeImpl endNode = new EstimatedCPMNodeImpl(END_NODE_ID, 0.0, 0.0);
		startNode.addDependant(nodeA);
		nodeA.addDependency(startNode);
		endNode.addDependency(nodeG);
		nodeG.addDependant(endNode);
		networkParameter.add(startNode);
		networkParameter.add(nodeA);
		networkParameter.add(nodeB);
		networkParameter.add(nodeC);
		networkParameter.add(nodeD);
		networkParameter.add(nodeE);
		networkParameter.add(nodeF);
		networkParameter.add(nodeG);
		networkParameter.add(endNode);
		Mockito.when(networkEvaluator.calculateExpectedDuration(startNode)).thenReturn(startNode.getExpectedDuration());
		Mockito.when(networkEvaluator.calculateVariance(startNode)).thenReturn(startNode.getVariance());
		Mockito.when(networkEvaluator.calculateExpectedDuration(nodeA)).thenReturn(nodeA.getExpectedDuration());
		Mockito.when(networkEvaluator.calculateVariance(nodeA)).thenReturn(nodeA.getVariance());
		Mockito.when(networkEvaluator.calculateExpectedDuration(nodeB)).thenReturn(nodeB.getExpectedDuration());
		Mockito.when(networkEvaluator.calculateVariance(nodeB)).thenReturn(nodeB.getVariance());
		Mockito.when(networkEvaluator.calculateExpectedDuration(nodeC)).thenReturn(nodeC.getExpectedDuration());
		Mockito.when(networkEvaluator.calculateVariance(nodeC)).thenReturn(nodeC.getVariance());
		Mockito.when(networkEvaluator.calculateExpectedDuration(nodeD)).thenReturn(nodeD.getExpectedDuration());
		Mockito.when(networkEvaluator.calculateVariance(nodeD)).thenReturn(nodeD.getVariance());
		Mockito.when(networkEvaluator.calculateExpectedDuration(nodeE)).thenReturn(nodeE.getExpectedDuration());
		Mockito.when(networkEvaluator.calculateVariance(nodeE)).thenReturn(nodeE.getVariance());
		Mockito.when(networkEvaluator.calculateExpectedDuration(nodeF)).thenReturn(nodeF.getExpectedDuration());
		Mockito.when(networkEvaluator.calculateVariance(nodeF)).thenReturn(nodeF.getVariance());
		Mockito.when(networkEvaluator.calculateExpectedDuration(nodeG)).thenReturn(nodeG.getExpectedDuration());
		Mockito.when(networkEvaluator.calculateVariance(nodeG)).thenReturn(nodeG.getVariance());
		Mockito.when(networkEvaluator.calculateExpectedDuration(endNode)).thenReturn(endNode.getExpectedDuration());
		Mockito.when(networkEvaluator.calculateVariance(endNode)).thenReturn(endNode.getVariance());
		final double resultDuration = nodeA.getExpectedDuration() + nodeB.getExpectedDuration() + nodeD.getExpectedDuration() + nodeE.getExpectedDuration()
				+ nodeG.getExpectedDuration();
		final double resultVariance = nodeA.getVariance() + nodeB.getVariance() + nodeD.getVariance() + nodeE.getVariance() + nodeG.getVariance();
		final CPMResult expectedResult = new CPMResult(resultDuration, Math.sqrt(resultVariance));
		Mockito.when(networkEvaluator.evaluate(networkParameter)).thenReturn(expectedResult);

		final CPMResult actualResult = networkEvaluator.evaluate(networkParameter);

		Assert.assertEquals(actualResult, expectedResult);
	}

}
