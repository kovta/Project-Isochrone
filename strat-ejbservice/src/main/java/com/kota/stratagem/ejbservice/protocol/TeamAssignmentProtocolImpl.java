package com.kota.stratagem.ejbservice.protocol;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import com.kota.stratagem.ejbservice.access.SessionContextAccessor;
import com.kota.stratagem.ejbservice.context.EJBServiceConfiguration;
import com.kota.stratagem.ejbservice.converter.delegation.AssignmentConverter;
import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbservice.interceptor.Regulated;
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

	@Inject
	private AssignmentConverter converter;

	@Override
	public TeamObjectiveAssignmentRepresentor[] saveObjectiveAssignments(Long[] recipients, Long objective) throws AdaptorException {
		final TeamObjectiveAssignmentRepresentor[] assignments = new TeamObjectiveAssignmentRepresentor[recipients.length];
		for (int i = 0; i < recipients.length; i++) {
			assignments[i] = this.converter.to((TeamObjectiveAssignment) this.objectiveAssignmentService.create(
					this.appUserService.readElementary(this.sessionContextAccessor.getSessionContext().getCallerPrincipal().getName()).getId(), recipients[i],
					objective));
		}
		return assignments;
	}

	@Override
	public TeamProjectAssignmentRepresentor[] saveProjectAssignments(Long[] recipients, Long project) throws AdaptorException {
		final TeamProjectAssignmentRepresentor[] assignments = new TeamProjectAssignmentRepresentor[recipients.length];
		for (int i = 0; i < recipients.length; i++) {
			assignments[i] = this.converter.to((TeamProjectAssignment) this.projectAssignmentService.create(
					this.appUserService.readElementary(this.sessionContextAccessor.getSessionContext().getCallerPrincipal().getName()).getId(), recipients[i],
					project));
		}
		return assignments;
	}

	@Override
	public TeamSubmoduleAssignmentRepresentor[] saveSubmoduleAssignments(Long[] recipients, Long submodule) throws AdaptorException {
		final TeamSubmoduleAssignmentRepresentor[] assignments = new TeamSubmoduleAssignmentRepresentor[recipients.length];
		for (int i = 0; i < recipients.length; i++) {
			assignments[i] = this.converter.to((TeamSubmoduleAssignment) this.submoduleAssignmentService.create(
					this.appUserService.readElementary(this.sessionContextAccessor.getSessionContext().getCallerPrincipal().getName()).getId(), recipients[i],
					submodule));
		}
		return assignments;
	}

	@Override
	public TeamTaskAssignmentRepresentor[] saveTaskAssignments(Long[] recipients, Long task) throws AdaptorException {
		final TeamTaskAssignmentRepresentor[] assignments = new TeamTaskAssignmentRepresentor[recipients.length];
		for (int i = 0; i < recipients.length; i++) {
			assignments[i] = this.converter.to((TeamTaskAssignment) this.taskAssignmentService.create(
					this.appUserService.readElementary(this.sessionContextAccessor.getSessionContext().getCallerPrincipal().getName()).getId(), recipients[i],
					task));
		}
		return assignments;
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
