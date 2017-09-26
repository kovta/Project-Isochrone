package com.kota.stratagem.ejbservice.decorator;

import java.util.HashSet;
import java.util.Set;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.inject.Inject;

import com.kota.stratagem.ejbservice.converter.delegation.AssignmentConverter;
import com.kota.stratagem.ejbservice.dispatch.LifecycleOverseer;
import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbservice.protocol.TeamAssignmentProtocol;
import com.kota.stratagem.ejbserviceclient.domain.AppUserObjectiveAssignmentRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TeamObjectiveAssignmentRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TeamProjectAssignmentRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TeamSubmoduleAssignmentRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TeamTaskAssignmentRepresentor;

@Decorator
public class TeamAssignmentProtocolDispatchDecorator implements TeamAssignmentProtocol {

	@Inject
	@Delegate
	private TeamAssignmentProtocol protocol;

	@Inject
	private AssignmentConverter converter;

	@Inject
	private LifecycleOverseer overseer;

	@Override
	public TeamObjectiveAssignmentRepresentor[] saveObjectiveAssignments(Long[] recipients, Long objective) throws AdaptorException {
		Set<AppUserObjectiveAssignmentRepresentor> representors = new HashSet<>();
		TeamObjectiveAssignmentRepresentor[] assignments = this.protocol.saveObjectiveAssignments(recipients, objective);
		for(TeamObjectiveAssignmentRepresentor assignment : assignments) {
			representors.addAll(this.converter.toAppUserObjectiveAssignmentSet(assignment));
		}
		for(AppUserObjectiveAssignmentRepresentor representor : representors) {
			//this.overseer.assigned(representor.toTextMessage());
		}
		return assignments;
	}

	@Override
	public void removeObjectiveAssignment(Long id) throws AdaptorException {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeProjectAssignment(Long id) throws AdaptorException {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeSubmoduleAssignment(Long id) throws AdaptorException {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeTaskAssignment(Long id) throws AdaptorException {
		// TODO Auto-generated method stub

	}

	@Override
	public TeamProjectAssignmentRepresentor[] saveProjectAssignments(Long[] recipients, Long project) throws AdaptorException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TeamSubmoduleAssignmentRepresentor[] saveSubmoduleAssignments(Long[] recipients, Long submodule) throws AdaptorException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TeamTaskAssignmentRepresentor[] saveTaskAssignments(Long[] recipients, Long task) throws AdaptorException {
		// TODO Auto-generated method stub
		return null;
	}



}
