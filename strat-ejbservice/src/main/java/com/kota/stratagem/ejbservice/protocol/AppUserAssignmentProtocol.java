package com.kota.stratagem.ejbservice.protocol;

import javax.ejb.Local;

import com.kota.stratagem.ejbservice.exception.AdaptorException;

@Local
public interface AppUserAssignmentProtocol extends AssignmentProtocol {

	void saveObjectiveAssignments(String[] recipients, Long objective) throws AdaptorException;

	void saveProjectAssignments(String[] recipients, Long project) throws AdaptorException;

	void saveSubmoduleAssignments(String[] recipients, Long submodule) throws AdaptorException;

	void saveTaskAssignments(String[] recipients, Long task) throws AdaptorException;

}
