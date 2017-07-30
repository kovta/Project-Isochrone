package com.kota.stratagem.persistence.service;

import javax.ejb.Local;

import com.kota.stratagem.persistence.entity.AppUser;
import com.kota.stratagem.persistence.entity.AppUserObjectiveAssignment;
import com.kota.stratagem.persistence.entity.Objective;
import com.kota.stratagem.persistence.exception.PersistenceServiceException;

@Local
public interface AppUserAssignmentService {

	AppUserObjectiveAssignment create(AppUser entrustor, AppUser recipient, Objective objective) throws PersistenceServiceException;

	// Objective create(Project project, AppUser recipient, AppUser entrustor) throws PersistenceServiceException;
	//
	// Objective create(Submodule submodule, AppUser recipient, AppUser entrustor) throws PersistenceServiceException;
	//
	// Objective create(Task task, AppUser recipient, AppUser entrustor) throws PersistenceServiceException;

	void deleteObjectiveAssignment(Long id) throws PersistenceServiceException;

	// void deleteProjectAssignment(Long id) throws PersistenceServiceException;
	//
	// void deleteSubmoduleAssignment(Long id) throws PersistenceServiceException;
	//
	// void deleteTaskAssignment(Long id) throws PersistenceServiceException;

}
