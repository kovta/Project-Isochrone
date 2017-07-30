package com.kota.stratagem.ejbservice.protocol;

import javax.ejb.Local;

import com.kota.stratagem.ejbservice.exception.AdaptorException;

@Local
public interface AppUserAssignmentProtocol {

	void saveObjectiveAssignments(String[] recipients, Long objective) throws AdaptorException;

	void removeObjectiveAssignment(Long id) throws AdaptorException;

}
