package com.kota.stratagem.ejbservice.preparation;

import java.util.List;

import com.kota.stratagem.ejbservice.domain.CPMResult;
import com.kota.stratagem.ejbserviceclient.domain.AbstractProgressionRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TaskRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.designation.CPMNode;

public interface ExtensionProvider {

	void provideBaseEstimationDetails(TaskRepresentor baseComponent);

	void addCompletionAdaptedComponent(List<CPMNode> components, TaskRepresentor task);

	TaskRepresentor adaptEstimationsToCompletion(TaskRepresentor baseComponent);

	CPMResult evaluateDependencyNetwork(List<CPMNode> network, boolean estimated);

	void provideEstimations(CPMResult result, AbstractProgressionRepresentor representor);

	void provideBlankEstimations(AbstractProgressionRepresentor representor);

}
