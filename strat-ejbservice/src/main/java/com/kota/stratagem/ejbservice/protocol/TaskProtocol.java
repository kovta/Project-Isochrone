package com.kota.stratagem.ejbservice.protocol;

import java.util.List;
import java.util.Set;

import javax.ejb.Local;

import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbserviceclient.domain.AppUserRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ImpedimentRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ObjectiveRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ProjectRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TaskRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TeamRepresentor;

@Local
public interface TaskProtocol {

	TaskRepresentor getTask(Long id) throws AdaptorException;

	List<TaskRepresentor> getAllTasks() throws AdaptorException;

	TaskRepresentor saveTask(Long id, String name, String description, double completion, Set<TeamRepresentor> assignedTeams,
			Set<AppUserRepresentor> assignedUsers, Set<ImpedimentRepresentor> impediments, Set<TaskRepresentor> dependantTasks,
			Set<TaskRepresentor> taskDependencies, ObjectiveRepresentor objective, ProjectRepresentor project) throws AdaptorException;

	void removeTask(Long id) throws AdaptorException;

}