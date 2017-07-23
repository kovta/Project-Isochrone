package com.kota.stratagem.persistence.service;

import java.util.Date;
import java.util.Set;

import javax.ejb.Local;

import com.kota.stratagem.persistence.entity.AppUser;
import com.kota.stratagem.persistence.entity.Submodule;
import com.kota.stratagem.persistence.entity.Task;
import com.kota.stratagem.persistence.entity.Team;
import com.kota.stratagem.persistence.exception.PersistenceServiceException;

@Local
public interface SubmoduleService {

	Submodule create(String name, String description, Date deadline, AppUser creator, Date creationDate, Set<Task> tasks, Set<Team> assignedTeams,
			Set<AppUser> assignedUsers, Long project) throws PersistenceServiceException;

	Submodule readElementary(Long id) throws PersistenceServiceException;

	Submodule readWithTasks(Long id) throws PersistenceServiceException;

	Set<Submodule> readAll() throws PersistenceServiceException;

	Submodule update(Long id, String name, String description, Date deadline, AppUser modifier, Date modificationDate, Set<Task> tasks, Set<Team> assignedTeams,
			Set<AppUser> assignedUsers) throws PersistenceServiceException;

	void delete(Long id) throws PersistenceServiceException;

	boolean exists(Long id) throws PersistenceServiceException;

}
