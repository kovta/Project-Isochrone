package com.kota.stratagem.ejbservice.converter.evaluation;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import com.kota.stratagem.ejbservice.domain.DefinitiveCPMNodeImpl;
import com.kota.stratagem.ejbservice.domain.EstimatedCPMNodeImpl;
import com.kota.stratagem.ejbserviceclient.domain.TaskRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.designation.CPMNode;

@Stateless
public class CPMNodeConverterImpl implements CPMNodeConverter {

	@Override
	public CPMNode to(TaskRepresentor task) {
		CPMNode node;
		if (task.isEstimated()) {
			node = new EstimatedCPMNodeImpl(task.getPessimistic(), task.getRealistic(), task.getOptimistic());
		} else {
			node = new DefinitiveCPMNodeImpl(task.getDuration());
		}
		return node;
	}

	@Override
	public List<CPMNode> to(List<TaskRepresentor> tasks) {
		final List<CPMNode> nodes = new ArrayList<>();
		for (final TaskRepresentor task : tasks) {
			nodes.add(this.to(task));
		}
		return nodes;
	}

}
