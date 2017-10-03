package com.kota.stratagem.ejbservice.converter.evaluation;

import java.util.List;

import javax.ejb.Local;

import com.kota.stratagem.ejbserviceclient.domain.TaskRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.designation.CPMNode;

@Local
public interface CPMNodeConverter {

	List<CPMNode> to(List<TaskRepresentor> tasks);

	Double calculateExpectedDuration(Double pessimistic, Double realistic, Double optimistic);

	Double calculateVariance(Double pessimistic, Double realistic, Double optimistic);

}
