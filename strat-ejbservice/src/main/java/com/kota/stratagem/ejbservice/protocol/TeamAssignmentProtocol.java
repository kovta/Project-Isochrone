package com.kota.stratagem.ejbservice.protocol;

import javax.ejb.Local;

import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbserviceclient.domain.TeamObjectiveAssignmentRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TeamProjectAssignmentRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TeamSubmoduleAssignmentRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TeamTaskAssignmentRepresentor;

@Local
public interface TeamAssignmentProtocol extends AssignmentProtocol {

	TeamObjectiveAssignmentRepresentor[] saveObjectiveAssignments(Long[] recipients, Long objective) throws AdaptorException;

	TeamProjectAssignmentRepresentor[] saveProjectAssignments(Long[] recipients, Long project) throws AdaptorException;

	TeamSubmoduleAssignmentRepresentor[] saveSubmoduleAssignments(Long[] recipients, Long submodule) throws AdaptorException;

	TeamTaskAssignmentRepresentor[] saveTaskAssignments(Long[] recipients, Long task) throws AdaptorException;

}
