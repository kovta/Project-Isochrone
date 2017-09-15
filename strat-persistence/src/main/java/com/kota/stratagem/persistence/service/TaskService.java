package com.kota.stratagem.persistence.service;

import java.util.Date;
import java.util.Set;

import javax.ejb.Local;

import com.kota.stratagem.persistence.entity.AppUser;
import com.kota.stratagem.persistence.entity.Task;

@Local
public interface TaskService {

	Task create(String name, String description, int priority, double completion, Date deadline, Boolean admittance, AppUser creator, Long objective,
			Long project, Long submodule, Double duration, Double pessimistic, Double realistic, Double optimistic);

	Task readElementary(Long id);

	Task readWithMonitoring(Long id);

	Task readWithAssignments(Long id);

	Task readWithDependencies(Long id);

	Task readWithDependants(Long id);

	Task readWithDirectDependencies(Long id);

	Task readComplete(Long id);

	Set<Task> readAll();

	Task update(Long id, String name, String description, int priority, double completion, Date deadline, Boolean admittance, AppUser modifier, Long objective,
			Long project, Long submodule, Double duration, Double pessimistic, Double realistic, Double optimistic);

	void delete(Long id);

	boolean exists(Long id);

	void createDependency(Long dependency, Long dependant, Long operator);

	void deleteDependency(Long dependency, Long dependant, Long operator);

}