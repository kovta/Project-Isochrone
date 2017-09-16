package com.kota.stratagem.ejbservice.preparation;

import java.util.List;

import com.kota.stratagem.ejbserviceclient.domain.ObjectiveRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ProjectRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.SubmoduleRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TaskRepresentor;

public interface DTOExtensionManager {

	ObjectiveRepresentor prepare(ObjectiveRepresentor representor);

	List<ObjectiveRepresentor> prepareObjectives(List<ObjectiveRepresentor> representors);

	ProjectRepresentor prepare(ProjectRepresentor representor);

	List<ObjectiveRepresentor> prepareProjectClusters(List<ObjectiveRepresentor> representors);

	SubmoduleRepresentor prepare(SubmoduleRepresentor representor);

	TaskRepresentor prepare(TaskRepresentor representor);

}
