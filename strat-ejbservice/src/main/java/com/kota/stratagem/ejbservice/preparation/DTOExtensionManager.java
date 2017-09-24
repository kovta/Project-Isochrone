package com.kota.stratagem.ejbservice.preparation;

import java.util.List;

import com.kota.stratagem.ejbserviceclient.domain.AppUserRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ObjectiveRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ProjectRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.SubmoduleRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TaskRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TeamRepresentor;

public interface DTOExtensionManager {

	AppUserRepresentor prepare(AppUserRepresentor representor);

	List<AppUserRepresentor> prepareAppUsers(List<AppUserRepresentor> representors);

	TeamRepresentor prepare(TeamRepresentor representor);

	List<TeamRepresentor> prepareTeams(List<TeamRepresentor> representors);

	ObjectiveRepresentor prepare(ObjectiveRepresentor representor);

	List<ObjectiveRepresentor> prepareObjectives(List<ObjectiveRepresentor> representors);

	ProjectRepresentor prepare(ProjectRepresentor representor);

	List<ObjectiveRepresentor> prepareProjectClusters(List<ObjectiveRepresentor> representors);

	SubmoduleRepresentor prepare(SubmoduleRepresentor representor);

	TaskRepresentor prepare(TaskRepresentor representor);

	List<TaskRepresentor> prepareCompliantTasks(List<TaskRepresentor> representors);

}
