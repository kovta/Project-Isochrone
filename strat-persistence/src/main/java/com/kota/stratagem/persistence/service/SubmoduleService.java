package com.kota.stratagem.persistence.service;

import java.util.Date;
import java.util.Set;

import javax.ejb.Local;

import com.kota.stratagem.persistence.entity.Submodule;
import com.kota.stratagem.persistence.exception.CoherentPersistenceServiceException;

@Local
public interface SubmoduleService {

	Submodule create(String name, String description, Date deadline, String creator, Long project);

	Submodule readElementary(Long id);

	Submodule readWithMonitoring(Long id);

	Submodule readWithAssignments(Long id);

	Submodule readWithTasks(Long id);

	Submodule readWithDependencies(Long id);

	Submodule readWithDependants(Long id);

	Submodule readWithDirectDependencies(Long id);

	Submodule readWithTasksAndDirectDependencies(Long id);

	Submodule readComplete(Long id);

	Set<Submodule> readAll();

	Submodule update(Long id, String name, String description, Date deadline, String modifier);

	void delete(Long id) throws CoherentPersistenceServiceException;

	boolean exists(Long id);

	void createDependency(Long dependency, Long dependant, Long operator);

	void deleteDependency(Long dependency, Long dependant, Long operator);

}
