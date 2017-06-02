package com.kota.stratagem.persistence.service;

import java.util.Set;

import javax.ejb.Local;

import com.kota.stratagem.persistence.entity.AppUser;
import com.kota.stratagem.persistence.entity.Impediment;
import com.kota.stratagem.persistence.entity.Objective;
import com.kota.stratagem.persistence.entity.Project;
import com.kota.stratagem.persistence.entity.Task;
import com.kota.stratagem.persistence.entity.Team;
import com.kota.stratagem.persistence.entity.trunk.Role;
import com.kota.stratagem.persistence.exception.PersistenceServiceException;

@Local
public interface AppUserService {

	AppUser create(String name, String passwordHash, String email, Role role, Set<Objective> objectives, Set<Project> projects, Set<Task> tasks,
			Set<Impediment> reportedImpediments, Set<Impediment> processedImpediments, Set<Team> supervisedTeams, Set<Team> teamMemberships)
			throws PersistenceServiceException;

	AppUser read(Long id) throws PersistenceServiceException;

	Set<AppUser> readAll() throws PersistenceServiceException;

	AppUser update(Long id, String name, String passwordHash, String email, Role role, Set<Objective> objectives, Set<Project> projects, Set<Task> tasks,
			Set<Impediment> reportedImpediments, Set<Impediment> processedImpediments, Set<Team> supervisedTeams, Set<Team> teamMemberships)
			throws PersistenceServiceException;

	void delete(Long id) throws PersistenceServiceException;

	boolean exists(Long id) throws PersistenceServiceException;

}
