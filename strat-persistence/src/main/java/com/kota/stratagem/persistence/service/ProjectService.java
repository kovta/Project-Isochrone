package com.kota.stratagem.persistence.service;

import java.util.Date;
import java.util.Set;

import javax.ejb.Local;

import com.kota.stratagem.persistence.entity.Project;
import com.kota.stratagem.persistence.entity.trunk.ProjectStatus;
import com.kota.stratagem.persistence.exception.PersistenceServiceException;

@Local
public interface ProjectService {

	Project create(String name, String description, ProjectStatus status, Date deadline, Boolean confidentiality, Long creator, Long objective)
			throws PersistenceServiceException;

	Project readElementary(Long id) throws PersistenceServiceException;

	Project readWithAssignments(Long id) throws PersistenceServiceException;

	Project readWithTasks(Long id) throws PersistenceServiceException;

	Project readWithSubmodules(Long id) throws PersistenceServiceException;

	Project readWithSubmodulesAndTasks(Long id) throws PersistenceServiceException;

	Project readComplete(Long id) throws PersistenceServiceException;

	Set<Project> readByStatus(ProjectStatus status) throws PersistenceServiceException;

	Set<Project> readAll() throws PersistenceServiceException;

	Project update(Long id, String name, String description, ProjectStatus status, Date deadline, Boolean confidentiality, String modifier)
			throws PersistenceServiceException;

	void delete(Long id) throws PersistenceServiceException;

	boolean exists(Long id) throws PersistenceServiceException;

}