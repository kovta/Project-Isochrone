package com.kota.stratagem.persistence.service;

import java.util.Date;
import java.util.Set;

import javax.ejb.Local;

import com.kota.stratagem.persistence.entity.Project;
import com.kota.stratagem.persistence.entity.trunk.ProjectStatus;
import com.kota.stratagem.persistence.exception.CoherentPersistenceServiceException;

@Local
public interface ProjectService {

	Project create(String name, String description, ProjectStatus status, Date deadline, Boolean confidentiality, Long creator, Long objective);

	Project readElementary(Long id);

	Project readWithAssignments(Long id);

	Project readWithTasks(Long id);

	Project readWithSubmodules(Long id);

	Project readWithSubmodulesAndTasks(Long id);

	Project readComplete(Long id);

	Set<Project> readByStatus(ProjectStatus status);

	Set<Project> readAll();

	Project update(Long id, String name, String description, ProjectStatus status, Date deadline, Boolean confidentiality, String modifier);

	void delete(Long id) throws CoherentPersistenceServiceException;

	boolean exists(Long id);

}