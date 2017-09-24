package com.kota.stratagem.ejbservice.converter;

import java.util.HashSet;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.kota.stratagem.ejbserviceclient.domain.AppUserObjectiveAssignmentRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.AppUserProjectAssignmentRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.AppUserSubmoduleAssignmentRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.AppUserTaskAssignmentRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TeamObjectiveAssignmentRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TeamProjectAssignmentRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TeamSubmoduleAssignmentRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TeamTaskAssignmentRepresentor;
import com.kota.stratagem.persistence.entity.AppUserObjectiveAssignment;
import com.kota.stratagem.persistence.entity.AppUserProjectAssignment;
import com.kota.stratagem.persistence.entity.AppUserSubmoduleAssignment;
import com.kota.stratagem.persistence.entity.AppUserTaskAssignment;
import com.kota.stratagem.persistence.entity.TeamObjectiveAssignment;
import com.kota.stratagem.persistence.entity.TeamProjectAssignment;
import com.kota.stratagem.persistence.entity.TeamSubmoduleAssignment;
import com.kota.stratagem.persistence.entity.TeamTaskAssignment;
import com.kota.stratagem.persistence.service.TeamService;

@Stateless
public class AssignmentConverterImpl implements AssignmentConverter {

	@EJB
	private TeamService teamService;

	@EJB
	private AppUserConverter appUserConverter;

	@EJB
	private TeamConverter teamConverter;

	@EJB
	private ObjectiveConverter objectiveConverter;

	@EJB
	private ProjectConverter projectConverter;

	@EJB
	private SubmoduleConverter submoduleConverter;

	@EJB
	private TaskConverter taskConverter;

	@Override
	public AppUserObjectiveAssignmentRepresentor to(AppUserObjectiveAssignment assignment) {
		final AppUserObjectiveAssignmentRepresentor representor = assignment.getId() != null
				? new AppUserObjectiveAssignmentRepresentor(assignment.getId(), this.appUserConverter.toElementary(assignment.getEntrustor()),
						this.appUserConverter.toElementary(assignment.getRecipient()), this.objectiveConverter.toElementary(assignment.getObjective()),
						assignment.getCreationDate())
				: new AppUserObjectiveAssignmentRepresentor(this.appUserConverter.toElementary(assignment.getEntrustor()),
						this.appUserConverter.toElementary(assignment.getRecipient()), this.objectiveConverter.toElementary(assignment.getObjective()),
						assignment.getCreationDate());
		return representor;
	}

	@Override
	public AppUserProjectAssignmentRepresentor to(AppUserProjectAssignment assignment) {
		final AppUserProjectAssignmentRepresentor representor = assignment.getId() != null
				? new AppUserProjectAssignmentRepresentor(assignment.getId(), this.appUserConverter.toElementary(assignment.getEntrustor()),
						this.appUserConverter.toElementary(assignment.getRecipient()), this.projectConverter.toElementary(assignment.getProject()),
						assignment.getCreationDate())
				: new AppUserProjectAssignmentRepresentor(this.appUserConverter.toElementary(assignment.getEntrustor()),
						this.appUserConverter.toElementary(assignment.getRecipient()), this.projectConverter.toElementary(assignment.getProject()),
						assignment.getCreationDate());
		return representor;
	}

	@Override
	public AppUserSubmoduleAssignmentRepresentor to(AppUserSubmoduleAssignment assignment) {
		final AppUserSubmoduleAssignmentRepresentor representor = assignment.getId() != null
				? new AppUserSubmoduleAssignmentRepresentor(assignment.getId(), this.appUserConverter.toElementary(assignment.getEntrustor()),
						this.appUserConverter.toElementary(assignment.getRecipient()), this.submoduleConverter.toElementary(assignment.getSubmodule()),
						assignment.getCreationDate())
				: new AppUserSubmoduleAssignmentRepresentor(this.appUserConverter.toElementary(assignment.getEntrustor()),
						this.appUserConverter.toElementary(assignment.getRecipient()), this.submoduleConverter.toElementary(assignment.getSubmodule()),
						assignment.getCreationDate());
		return representor;
	}

	@Override
	public AppUserTaskAssignmentRepresentor to(AppUserTaskAssignment assignment) {
		final AppUserTaskAssignmentRepresentor representor = assignment.getId() != null
				? new AppUserTaskAssignmentRepresentor(assignment.getId(), this.appUserConverter.toElementary(assignment.getEntrustor()),
						this.appUserConverter.toElementary(assignment.getRecipient()), this.taskConverter.toElementary(assignment.getTask()),
						assignment.getCreationDate())
				: new AppUserTaskAssignmentRepresentor(this.appUserConverter.toElementary(assignment.getEntrustor()),
						this.appUserConverter.toElementary(assignment.getRecipient()), this.taskConverter.toElementary(assignment.getTask()),
						assignment.getCreationDate());
		return representor;
	}

	@Override
	public TeamObjectiveAssignmentRepresentor to(TeamObjectiveAssignment assignment) {
		final TeamObjectiveAssignmentRepresentor representor = assignment.getId() != null
				? new TeamObjectiveAssignmentRepresentor(assignment.getId(), this.appUserConverter.toElementary(assignment.getEntrustor()),
						this.teamConverter.toElementary(this.teamService.readWithLeader(assignment.getRecipient().getId())),
						this.objectiveConverter.toElementary(assignment.getObjective()), assignment.getCreationDate())
				: new TeamObjectiveAssignmentRepresentor(this.appUserConverter.toElementary(assignment.getEntrustor()),
						this.teamConverter.toElementary(this.teamService.readWithLeader(assignment.getRecipient().getId())),
						this.objectiveConverter.toElementary(assignment.getObjective()), assignment.getCreationDate());
		return representor;
	}

	@Override
	public TeamProjectAssignmentRepresentor to(TeamProjectAssignment assignment) {
		final TeamProjectAssignmentRepresentor representor = assignment.getId() != null
				? new TeamProjectAssignmentRepresentor(assignment.getId(), this.appUserConverter.toElementary(assignment.getEntrustor()),
						this.teamConverter.toElementary(this.teamService.readWithLeader(assignment.getRecipient().getId())),
						this.projectConverter.toElementary(assignment.getProject()), assignment.getCreationDate())
				: new TeamProjectAssignmentRepresentor(this.appUserConverter.toElementary(assignment.getEntrustor()),
						this.teamConverter.toElementary(this.teamService.readWithLeader(assignment.getRecipient().getId())),
						this.projectConverter.toElementary(assignment.getProject()), assignment.getCreationDate());
		return representor;
	}

	@Override
	public TeamSubmoduleAssignmentRepresentor to(TeamSubmoduleAssignment assignment) {
		final TeamSubmoduleAssignmentRepresentor representor = assignment.getId() != null
				? new TeamSubmoduleAssignmentRepresentor(assignment.getId(), this.appUserConverter.toElementary(assignment.getEntrustor()),
						this.teamConverter.toElementary(this.teamService.readWithLeader(assignment.getRecipient().getId())),
						this.submoduleConverter.toElementary(assignment.getSubmodule()), assignment.getCreationDate())
				: new TeamSubmoduleAssignmentRepresentor(this.appUserConverter.toElementary(assignment.getEntrustor()),
						this.teamConverter.toElementary(this.teamService.readWithLeader(assignment.getRecipient().getId())),
						this.submoduleConverter.toElementary(assignment.getSubmodule()), assignment.getCreationDate());
		return representor;
	}

	@Override
	public TeamTaskAssignmentRepresentor to(TeamTaskAssignment assignment) {
		final TeamTaskAssignmentRepresentor representor = assignment.getId() != null
				? new TeamTaskAssignmentRepresentor(assignment.getId(), this.appUserConverter.toElementary(assignment.getEntrustor()),
						this.teamConverter.toElementary(this.teamService.readWithLeader(assignment.getRecipient().getId())),
						this.taskConverter.toElementary(assignment.getTask()), assignment.getCreationDate())
				: new TeamTaskAssignmentRepresentor(this.appUserConverter.toElementary(assignment.getEntrustor()),
						this.teamConverter.toElementary(this.teamService.readWithLeader(assignment.getRecipient().getId())),
						this.taskConverter.toElementary(assignment.getTask()), assignment.getCreationDate());
		return representor;
	}

	@Override
	public Set<AppUserObjectiveAssignmentRepresentor> toAppUserObjectiveAssignmentSet(Set<AppUserObjectiveAssignment> assignments) {
		final Set<AppUserObjectiveAssignmentRepresentor> representors = new HashSet<>();
		for (final AppUserObjectiveAssignment assignment : assignments) {
			representors.add(this.to(assignment));
		}
		return representors;
	}

	@Override
	public Set<AppUserProjectAssignmentRepresentor> toAppUserProjectAssignmentSet(Set<AppUserProjectAssignment> assignments) {
		final Set<AppUserProjectAssignmentRepresentor> representors = new HashSet<>();
		for (final AppUserProjectAssignment assignment : assignments) {
			representors.add(this.to(assignment));
		}
		return representors;
	}

	@Override
	public Set<AppUserSubmoduleAssignmentRepresentor> toAppUserSubmoduleAssignmentSet(Set<AppUserSubmoduleAssignment> assignments) {
		final Set<AppUserSubmoduleAssignmentRepresentor> representors = new HashSet<>();
		for (final AppUserSubmoduleAssignment assignment : assignments) {
			representors.add(this.to(assignment));
		}
		return representors;
	}

	@Override
	public Set<AppUserTaskAssignmentRepresentor> toAppUserTaskAssignmentSet(Set<AppUserTaskAssignment> assignments) {
		final Set<AppUserTaskAssignmentRepresentor> representors = new HashSet<>();
		for (final AppUserTaskAssignment assignment : assignments) {
			representors.add(this.to(assignment));
		}
		return representors;
	}

	@Override
	public Set<TeamObjectiveAssignmentRepresentor> toTeamObjectiveAssignmentSet(Set<TeamObjectiveAssignment> assignments) {
		final Set<TeamObjectiveAssignmentRepresentor> representors = new HashSet<>();
		for (final TeamObjectiveAssignment assignment : assignments) {
			representors.add(this.to(assignment));
		}
		return representors;
	}

	@Override
	public Set<TeamProjectAssignmentRepresentor> toTeamProjectAssignmentSet(Set<TeamProjectAssignment> assignments) {
		final Set<TeamProjectAssignmentRepresentor> representors = new HashSet<>();
		for (final TeamProjectAssignment assignment : assignments) {
			representors.add(this.to(assignment));
		}
		return representors;
	}

	@Override
	public Set<TeamSubmoduleAssignmentRepresentor> toTeamSubmoduleAssignmentSet(Set<TeamSubmoduleAssignment> assignments) {
		final Set<TeamSubmoduleAssignmentRepresentor> representors = new HashSet<>();
		for (final TeamSubmoduleAssignment assignment : assignments) {
			representors.add(this.to(assignment));
		}
		return representors;
	}

	@Override
	public Set<TeamTaskAssignmentRepresentor> toTeamTaskAssignmentSet(Set<TeamTaskAssignment> assignments) {
		final Set<TeamTaskAssignmentRepresentor> representors = new HashSet<>();
		for (final TeamTaskAssignment assignment : assignments) {
			representors.add(this.to(assignment));
		}
		return representors;
	}

}
