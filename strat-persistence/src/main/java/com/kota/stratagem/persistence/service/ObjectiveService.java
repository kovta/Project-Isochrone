package com.kota.stratagem.persistence.service;

import java.util.Date;
import java.util.Set;

import javax.ejb.Local;

import com.kota.stratagem.persistence.entity.Objective;
import com.kota.stratagem.persistence.entity.trunk.ObjectiveStatus;
import com.kota.stratagem.persistence.exception.CoherentPersistenceServiceException;

@Local
public interface ObjectiveService {

	Objective create(String name, String description, int priority, ObjectiveStatus status, Date deadline, Boolean confidentiality, String creator);

	Objective readElementary(Long id);

	Objective readWithMonitoring(Long id);

	Objective readWithAssignments(Long id);

	Objective readWithTasks(Long id);

	Objective readWithProjects(Long id);

	Objective readWithProjectsAndTasks(Long id);

	Objective readComplete(Long id);

	Set<Objective> readAll();

	Objective update(Long id, String name, String description, int priority, ObjectiveStatus status, Date deadline, Boolean confidentiality, String modifier);

	void delete(Long id) throws CoherentPersistenceServiceException;

	boolean exists(Long id);

}
