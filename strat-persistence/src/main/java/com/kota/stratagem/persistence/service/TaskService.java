package com.kota.stratagem.persistence.service;

import java.util.Date;
import java.util.Set;

import javax.ejb.Local;

import com.kota.stratagem.persistence.entity.AppUser;
import com.kota.stratagem.persistence.entity.Task;
import com.kota.stratagem.persistence.exception.PersistenceServiceException;

@Local
public interface TaskService {

	Task create(String name, String description, int priority, double completion, Date deadline, Boolean admittance, AppUser creator, Long objective,
			Long project, Long submodule, Double duration, Double pessimistic, Double realistic, Double optimistic) throws PersistenceServiceException;

	Task readElementary(Long id) throws PersistenceServiceException;

	Task readWithAssignments(Long id) throws PersistenceServiceException;

	Task readWithDependencies(Long id) throws PersistenceServiceException;

	Task readWithDependants(Long id) throws PersistenceServiceException;

	Task readWithDirectDependencies(Long id) throws PersistenceServiceException;

	Task readComplete(Long id) throws PersistenceServiceException;

	Set<Task> readAll() throws PersistenceServiceException;

	Task update(Long id, String name, String description, int priority, double completion, Date deadline, Boolean admittance, AppUser modifier, Long objective,
			Long project, Long submodule, Double duration, Double pessimistic, Double realistic, Double optimistic) throws PersistenceServiceException;

	void delete(Long id) throws PersistenceServiceException;

	boolean exists(Long id) throws PersistenceServiceException;

	void createDependency(Long dependency, Long dependant, Long operator) throws PersistenceServiceException;

	void deleteDependency(Long dependency, Long dependant, Long operator) throws PersistenceServiceException;

}