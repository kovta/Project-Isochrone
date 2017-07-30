package com.kota.stratagem.ejbservice.protocol;

import javax.ejb.Local;

import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbserviceclient.domain.AppUserObjectiveAssignmentRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ObjectiveRepresentor;

@Local
public interface AppUserAssignmentProtocol {

	AppUserObjectiveAssignmentRepresentor saveObjectiveAssignment(String entrustor, String recipient, ObjectiveRepresentor objective) throws AdaptorException;

	void removeObjectiveAssignment(Long id) throws AdaptorException;

}
