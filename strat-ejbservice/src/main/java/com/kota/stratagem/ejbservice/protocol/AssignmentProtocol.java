package com.kota.stratagem.ejbservice.protocol;

import com.kota.stratagem.ejbservice.exception.AdaptorException;

public interface AssignmentProtocol {

	void removeObjectiveAssignment(Long id) throws AdaptorException;

	void removeProjectAssignment(Long id) throws AdaptorException;

	void removeSubmoduleAssignment(Long id) throws AdaptorException;

	void removeTaskAssignment(Long id) throws AdaptorException;

}
