package com.kota.stratagem.persistence.service;

import java.util.Date;
import java.util.Set;

import javax.ejb.Local;

import com.kota.stratagem.persistence.entity.AppUser;
import com.kota.stratagem.persistence.entity.Impediment;
import com.kota.stratagem.persistence.entity.Project;
import com.kota.stratagem.persistence.entity.Task;
import com.kota.stratagem.persistence.entity.Team;
import com.kota.stratagem.persistence.entity.trunk.ProjectStatus;
import com.kota.stratagem.persistence.exception.PersistenceServiceException;

@Local
public interface ProjectService {

	Project create(String name, String description, ProjectStatus status, Date deadline, Boolean confidentiality, AppUser creator, Set<Task> tasks,
			Set<Team> assignedTeams, Set<AppUser> assignedUsers, Set<Impediment> impediments, Long objective) throws PersistenceServiceException;

	Project readElementary(Long id) throws PersistenceServiceException;

	Project readWithTasks(Long id) throws PersistenceServiceException;

	Set<Project> readByStatus(ProjectStatus status) throws PersistenceServiceException;

	Set<Project> readAll() throws PersistenceServiceException;

	Project update(Long id, String name, String description, ProjectStatus status, Date deadline, Boolean confidentiality, AppUser modifier, Set<Task> tasks,
			Set<Team> assignedTeams, Set<AppUser> assignedUsers, Set<Impediment> impediments, Long objective) throws PersistenceServiceException;

	void delete(Long id) throws PersistenceServiceException;

	boolean exists(Long id) throws PersistenceServiceException;

}