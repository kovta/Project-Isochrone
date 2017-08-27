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

	TaskRepresentor saveTask(Long id, String name, String description, int priority, double completion, Date deadline, String operator, Long objective,
			Long project, Long submodule) throws AdaptorException;

	void removeTask(Long id) throws AdaptorException;

	void saveTaskDependencies(Long source, Long[] dependencies) throws AdaptorException;

}