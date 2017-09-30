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
import com.kota.stratagem.ejbserviceclient.domain.AppUserProjectAssignmentRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.AppUserSubmoduleAssignmentRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.AppUserTaskAssignmentRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TeamObjectiveAssignmentRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TeamProjectAssignmentRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TeamSubmoduleAssignmentRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TeamTaskAssignmentRepresentor;
import com.kota.stratagem.persistence.entity.TeamObjectiveAssignment;
import com.kota.stratagem.persistence.entity.TeamProjectAssignment;
import com.kota.stratagem.persistence.entity.TeamSubmoduleAssignment;
import com.kota.stratagem.persistence.entity.TeamTaskAssignment;
import com.kota.stratagem.persistence.qualifier.ObjectiveFocused;
import com.kota.stratagem.persistence.qualifier.ProjectFocused;
import com.kota.stratagem.persistence.qualifier.SubmoduleFocused;
import com.kota.stratagem.persistence.qualifier.TaskFocused;
import com.kota.stratagem.persistence.service.delegation.group.TeamAssingmentService;

@Decorator
public abstract class TeamAssignmentProtocolDispatchDecorator implements TeamAssignmentProtocol {

	@Inject
	@ObjectiveFocused
	private TeamAssingmentService objectiveAssignmentService;

	@Inject
	@ProjectFocused
	private TeamAssingmentService projectAssignmentService;

	@Inject
	@SubmoduleFocused
	private TeamAssingmentService submoduleAssignmentService;

	@Inject
	@TaskFocused
	private TeamAssingmentService taskAssignmentService;

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
		final Set<AppUserProjectAssignmentRepresentor> representors = new HashSet<>();
		final TeamProjectAssignmentRepresentor[] assignments = this.protocol.saveProjectAssignments(recipients, project);
		for (final TeamProjectAssignmentRepresentor assignment : assignments) {
			representors.addAll(this.converter.toAppUserProjectAssignmentSet(assignment));
		}
		for (final AppUserProjectAssignmentRepresentor representor : representors) {
			this.overseer.assigned(representor.toTextMessage());
		}
		return assignments;
	}

	@Override
	public TeamSubmoduleAssignmentRepresentor[] saveSubmoduleAssignments(Long[] recipients, Long submodule) throws AdaptorException {
		final Set<AppUserSubmoduleAssignmentRepresentor> representors = new HashSet<>();
		final TeamSubmoduleAssignmentRepresentor[] assignments = this.protocol.saveSubmoduleAssignments(recipients, submodule);
		for (final TeamSubmoduleAssignmentRepresentor assignment : assignments) {
			representors.addAll(this.converter.toAppUserSubmoduleAssignmentSet(assignment));
		}
		for (final AppUserSubmoduleAssignmentRepresentor representor : representors) {
			this.overseer.assigned(representor.toTextMessage());
		}
		return assignments;
	}

	@Override
	public TeamTaskAssignmentRepresentor[] saveTaskAssignments(Long[] recipients, Long task) throws AdaptorException {
		final Set<AppUserTaskAssignmentRepresentor> representors = new HashSet<>();
		final TeamTaskAssignmentRepresentor[] assignments = this.protocol.saveTaskAssignments(recipients, task);
		for (final TeamTaskAssignmentRepresentor assignment : assignments) {
			representors.addAll(this.converter.toAppUserTaskAssignmentSet(assignment));
		}
		for (final AppUserTaskAssignmentRepresentor representor : representors) {
			this.overseer.assigned(representor.toTextMessage());
		}
		return assignments;
	}

	@Override
	public void removeObjectiveAssignment(Long id) throws AdaptorException {
		final Set<AppUserObjectiveAssignmentRepresentor> assignments = this.converter
				.toAppUserObjectiveAssignmentSet(this.converter.to((TeamObjectiveAssignment) this.objectiveAssignmentService.read(id)));
		this.protocol.removeObjectiveAssignment(id);
		for (final AppUserObjectiveAssignmentRepresentor assignment : assignments) {
			this.overseer.dissociated(assignment.toTextMessage());
		}
	}

	@Override
	public void removeProjectAssignment(Long id) throws AdaptorException {
		final Set<AppUserProjectAssignmentRepresentor> assignments = this.converter
				.toAppUserProjectAssignmentSet(this.converter.to((TeamProjectAssignment) this.projectAssignmentService.read(id)));
		this.protocol.removeObjectiveAssignment(id);
		for (final AppUserProjectAssignmentRepresentor assignment : assignments) {
			this.overseer.dissociated(assignment.toTextMessage());
		}
	}

	@Override
	public void removeSubmoduleAssignment(Long id) throws AdaptorException {
		final Set<AppUserSubmoduleAssignmentRepresentor> assignments = this.converter
				.toAppUserSubmoduleAssignmentSet(this.converter.to((TeamSubmoduleAssignment) this.submoduleAssignmentService.read(id)));
		this.protocol.removeObjectiveAssignment(id);
		for (final AppUserSubmoduleAssignmentRepresentor assignment : assignments) {
			this.overseer.dissociated(assignment.toTextMessage());
		}
	}

	@Override
	public void removeTaskAssignment(Long id) throws AdaptorException {
		final Set<AppUserTaskAssignmentRepresentor> assignments = this.converter
				.toAppUserTaskAssignmentSet(this.converter.to((TeamTaskAssignment) this.taskAssignmentService.read(id)));
		this.protocol.removeObjectiveAssignment(id);
		for (final AppUserTaskAssignmentRepresentor assignment : assignments) {
			this.overseer.dissociated(assignment.toTextMessage());
		}
	}
}
