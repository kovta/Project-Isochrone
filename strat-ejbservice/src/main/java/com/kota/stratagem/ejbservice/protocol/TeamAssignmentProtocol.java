package com.kota.stratagem.ejbservice.protocol;

import javax.ejb.Local;

import com.kota.stratagem.ejbservice.exception.AdaptorException;

@Local
public interface TeamAssignmentProtocol extends AssignmentProtocol {

	void saveObjectiveAssignments(Long[] recipients, Long objective) throws AdaptorException;

	void saveProjectAssignments(Long[] recipients, Long project) throws AdaptorException;

	void saveSubmoduleAssignments(Long[] recipients, Long submodule) throws AdaptorException;

	void saveTaskAssignments(Long[] recipients, Long task) throws AdaptorException;

}
