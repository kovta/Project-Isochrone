package com.kota.stratagem.persistence.service;

import java.util.Date;
import java.util.Set;

import javax.ejb.Local;

import com.kota.stratagem.persistence.entity.Objective;
import com.kota.stratagem.persistence.entity.trunk.ObjectiveStatus;
import com.kota.stratagem.persistence.exception.PersistenceServiceException;

@Local
public interface ObjectiveService {

	Objective create(String name, String description, int priority, ObjectiveStatus status, Date deadline, Boolean confidentiality, String creator)
			throws PersistenceServiceException;

	Objective readElementary(Long id) throws PersistenceServiceException;

	Objective readWithTasks(Long id) throws PersistenceServiceException;

	Objective readWithProjects(Long id) throws PersistenceServiceException;

	Objective readWithProjectsAndTasks(Long id) throws PersistenceServiceException;

	Objective readComplete(Long id) throws PersistenceServiceException;

	Set<Objective> readAll() throws PersistenceServiceException;

	Objective update(Long id, String name, String description, int priority, ObjectiveStatus status, Date deadline, Boolean confidentiality, String modifier)
			throws PersistenceServiceException;

	void delete(Long id) throws PersistenceServiceException;

	boolean exists(Long id) throws PersistenceServiceException;

}
