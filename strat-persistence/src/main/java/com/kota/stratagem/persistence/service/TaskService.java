package com.kota.stratagem.persistence.service;

import java.util.Date;
import java.util.Set;

import javax.ejb.Local;

import com.kota.stratagem.persistence.entity.AppUser;
import com.kota.stratagem.persistence.entity.Impediment;
import com.kota.stratagem.persistence.entity.Task;
import com.kota.stratagem.persistence.entity.Team;
import com.kota.stratagem.persistence.exception.PersistenceServiceException;

@Local
public interface TaskService {

	Task create(String name, String description, int priority, double completion, Date deadline, AppUser creator, Set<Team> assignedTeams,
			Set<AppUser> assignedUsers, Set<Impediment> impediments, Set<Task> dependantTasks, Set<Task> taskDependencies, Long objective, Long project,
			Long submodule) throws PersistenceServiceException;

	Task read(Long id) throws PersistenceServiceException;

	Set<Task> readAll() throws PersistenceServiceException;

	Task update(Long id, String name, String description, int priority, double completion, Date deadline, AppUser modifier, Set<Team> assignedTeams,
			Set<AppUser> assignedUsers, Set<Impediment> impediments, Set<Task> dependantTasks, Set<Task> taskDependencies, Long objective, Long project,
			Long submodule) throws PersistenceServiceException;

	void delete(Long id) throws PersistenceServiceException;

	boolean exists(Long id) throws PersistenceServiceException;

}