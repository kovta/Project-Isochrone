package com.kota.stratagem.ejbservice.protocol;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbserviceclient.domain.TaskRepresentor;

@Local
public interface TaskProtocol {

	TaskRepresentor getTask(Long id) throws AdaptorException;

	List<TaskRepresentor> getCompliantDependencyConfigurations(TaskRepresentor task) throws AdaptorException;

	List<TaskRepresentor> getAllTasks() throws AdaptorException;

	TaskRepresentor saveTask(Long id, String name, String description, int priority, double completion, Date deadline, Boolean admittance, String operator, Long objective, Long project,
			Long submodule, Double duration, Double pessimistic, Double realistic, Double optimistic) throws AdaptorException;

	TaskRepresentor moveTask(Long id, Long submodule) throws AdaptorException;

	void removeTask(Long id) throws AdaptorException;

	void saveTaskDependencies(Long source, Long[] dependencies) throws AdaptorException;

	void saveTaskDependants(Long source, Long[] dependants) throws AdaptorException;

	void removeTaskDependency(Long dependency, Long dependant) throws AdaptorException;

}