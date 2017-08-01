package com.kota.stratagem.persistence.service;

import javax.ejb.Local;

import com.kota.stratagem.persistence.entity.AppUserObjectiveAssignment;
import com.kota.stratagem.persistence.entity.AppUserProjectAssignment;
import com.kota.stratagem.persistence.entity.AppUserSubmoduleAssignment;
import com.kota.stratagem.persistence.entity.AppUserTaskAssignment;
import com.kota.stratagem.persistence.exception.PersistenceServiceException;

@Local
public interface AppUserAssignmentService {

	AppUserObjectiveAssignment createObjectiveAssignment(Long entrustor, Long recipient, Long objective) throws PersistenceServiceException;

	AppUserProjectAssignment createProjectAssignment(Long entrustor, Long recipient, Long project) throws PersistenceServiceException;

	AppUserSubmoduleAssignment createSubmoduleAssignment(Long entrustor, Long recipient, Long submodule) throws PersistenceServiceException;

	AppUserTaskAssignment createTaskAssignment(Long entrustor, Long recipient, Long task) throws PersistenceServiceException;

	void deleteObjectiveAssignment(Long id) throws PersistenceServiceException;

	void deleteProjectAssignment(Long id) throws PersistenceServiceException;

	void deleteSubmoduleAssignment(Long id) throws PersistenceServiceException;

	void deleteTaskAssignment(Long id) throws PersistenceServiceException;

}
