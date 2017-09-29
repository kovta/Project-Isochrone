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
public abstract class TeamAssignmentProtocolDispatchDecorator implements TeamAssignmentProtocol {

	@Inject
	@Delegate
	private TeamAssignmentProtocol protocol;

	@Inject
	private AssignmentConverter converter;

	@Inject
	private LifecycleOverseer overseer;

	@Override
	public TeamObjectiveAssignmentRepresentor[] saveObjectiveAssignments(Long[] recipients, Long objective) throws AdaptorException {
		final Set<AppUserObjectiveAssignmentRepresentor> representors = new HashSet<>();
		final TeamObjectiveAssignmentRepresentor[] assignments = this.protocol.saveObjectiveAssignments(recipients, objective);
		for (final TeamObjectiveAssignmentRepresentor assignment : assignments) {
			representors.addAll(this.converter.toAppUserObjectiveAssignmentSet(assignment));
		}
		for (final AppUserObjectiveAssignmentRepresentor representor : representors) {
			this.overseer.assigned(representor.toTextMessage());
		}
		return assignments;
	}

	@Override
	public TeamProjectAssignmentRepresentor[] saveProjectAssignments(Long[] recipients, Long project) throws AdaptorException {
		return this.protocol.saveProjectAssignments(recipients, project);
	}

	@Override
	public TeamSubmoduleAssignmentRepresentor[] saveSubmoduleAssignments(Long[] recipients, Long submodule) throws AdaptorException {
		return this.protocol.saveSubmoduleAssignments(recipients, submodule);
	}

	@Override
	public TeamTaskAssignmentRepresentor[] saveTaskAssignments(Long[] recipients, Long task) throws AdaptorException {
		return this.protocol.saveTaskAssignments(recipients, task);
	}

	@Override
	public void removeObjectiveAssignment(Long id) throws AdaptorException {
		this.protocol.removeObjectiveAssignment(id);
	}

	@Override
	public void removeProjectAssignment(Long id) throws AdaptorException {
		this.protocol.removeProjectAssignment(id);
	}

	@Override
	public void removeSubmoduleAssignment(Long id) throws AdaptorException {
		this.protocol.removeSubmoduleAssignment(id);
	}

	@Override
	public void removeTaskAssignment(Long id) throws AdaptorException {
		this.protocol.removeTaskAssignment(id);
	}

}
