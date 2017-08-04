package com.kota.stratagem.ejbservice.protocol;

import javax.ejb.Local;

import com.kota.stratagem.ejbservice.exception.AdaptorException;

@Local
public interface AppUserAssignmentProtocol {

	void saveObjectiveAssignments(String[] recipients, Long objective) throws AdaptorException;

	void saveProjectAssignments(String[] recipients, Long objective) throws AdaptorException;

	void saveSubmoduleAssignments(String[] recipients, Long objective) throws AdaptorException;

	void saveTaskAssignments(String[] recipients, Long objective) throws AdaptorException;

	void removeObjectiveAssignment(Long id) throws AdaptorException;

	void removeProjectAssignment(Long id) throws AdaptorException;

	void removeSubmoduleAssignment(Long id) throws AdaptorException;

	void removeTaskAssignment(Long id) throws AdaptorException;

}
