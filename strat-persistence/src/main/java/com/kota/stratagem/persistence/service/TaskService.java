package com.kota.stratagem.persistence.service;

import java.util.Date;
import java.util.Set;

import javax.ejb.Local;

import com.kota.stratagem.persistence.entity.AppUser;
import com.kota.stratagem.persistence.entity.Task;
import com.kota.stratagem.persistence.exception.PersistenceServiceException;

@Local
public interface TaskService {

	Task create(String name, String description, int priority, double completion, Date deadline, AppUser creator, Long objective, Long project, Long submodule)
			throws PersistenceServiceException;

	Task readElementary(Long id) throws PersistenceServiceException;

	Task readWithAssignments(Long id) throws PersistenceServiceException;

	Task readComplete(Long id) throws PersistenceServiceException;

	Set<Task> readAll() throws PersistenceServiceException;

	Task update(Long id, String name, String description, int priority, double completion, Date deadline, AppUser modifier, Long objective, Long project,
			Long submodule) throws PersistenceServiceException;

	void delete(Long id) throws PersistenceServiceException;

	boolean exists(Long id) throws PersistenceServiceException;

}