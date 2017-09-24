package com.kota.stratagem.ejbservice.protocol;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import com.kota.stratagem.ejbservice.access.SessionContextAccessor;
import com.kota.stratagem.ejbservice.context.EJBServiceConfiguration;
import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbservice.interceptor.Regulated;
import com.kota.stratagem.persistence.qualifier.ObjectiveFocused;
import com.kota.stratagem.persistence.qualifier.ProjectFocused;
import com.kota.stratagem.persistence.qualifier.SubmoduleFocused;
import com.kota.stratagem.persistence.qualifier.TaskFocused;
import com.kota.stratagem.persistence.service.AppUserService;
import com.kota.stratagem.persistence.service.delegation.group.TeamAssingmentService;

@Regulated
@Stateless(mappedName = EJBServiceConfiguration.TEAM_ASSIGNMENT_PROTOCOL_SIGNATURE)
public class TeamAssignmentProtocolImpl implements TeamAssignmentProtocol {

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
	private SessionContextAccessor sessionContextAccessor;

	@EJB
	private AppUserService appUserService;

	@Override
	public void saveObjectiveAssignments(Long[] recipients, Long objective) throws AdaptorException {
		for (final Long recipient : recipients) {
			this.objectiveAssignmentService.create(
					this.appUserService.readElementary(this.sessionContextAccessor.getSessionContext().getCallerPrincipal().getName()).getId(), recipient,
					objective);
		}
	}

	@Override
	public void saveProjectAssignments(Long[] recipients, Long project) throws AdaptorException {
		for (final Long recipient : recipients) {
			this.projectAssignmentService.create(
					this.appUserService.readElementary(this.sessionContextAccessor.getSessionContext().getCallerPrincipal().getName()).getId(), recipient,
					project);
		}
	}

	@Override
	public void saveSubmoduleAssignments(Long[] recipients, Long submodule) throws AdaptorException {
		for (final Long recipient : recipients) {
			this.submoduleAssignmentService.create(
					this.appUserService.readElementary(this.sessionContextAccessor.getSessionContext().getCallerPrincipal().getName()).getId(), recipient,
					submodule);
		}
	}

	@Override
	public void saveTaskAssignments(Long[] recipients, Long task) throws AdaptorException {
		for (final Long recipient : recipients) {
			this.taskAssignmentService.create(
					this.appUserService.readElementary(this.sessionContextAccessor.getSessionContext().getCallerPrincipal().getName()).getId(), recipient,
					task);
		}
	}

	@Override
	public void removeObjectiveAssignment(Long id) throws AdaptorException {
		this.objectiveAssignmentService.delete(id);
	}

	@Override
	public void removeProjectAssignment(Long id) throws AdaptorException {
		this.projectAssignmentService.delete(id);
	}

	@Override
	public void removeSubmoduleAssignment(Long id) throws AdaptorException {
		this.submoduleAssignmentService.delete(id);
	}

	@Override
	public void removeTaskAssignment(Long id) throws AdaptorException {
		this.taskAssignmentService.delete(id);
	}

}
