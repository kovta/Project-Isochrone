package com.kota.stratagem.ejbservice.converter;

import java.util.HashSet;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import com.kota.stratagem.ejbservice.converter.delegation.AssignmentConverter;
import com.kota.stratagem.ejbserviceclient.domain.TeamRepresentor;
import com.kota.stratagem.persistence.entity.AppUser;
import com.kota.stratagem.persistence.entity.Team;
import com.kota.stratagem.persistence.entity.TeamObjectiveAssignment;
import com.kota.stratagem.persistence.entity.TeamProjectAssignment;
import com.kota.stratagem.persistence.entity.TeamSubmoduleAssignment;
import com.kota.stratagem.persistence.entity.TeamTaskAssignment;

@Stateless
public class TeamConverterImpl extends AbstractMonitoredEntityConverter implements TeamConverter {

	@EJB
	private AppUserConverter appUserConverter;

	@EJB
	private ObjectiveConverter objectiveConverter;

	@EJB
	private ProjectConverter projectConverter;

	@EJB
	private TaskConverter taskConverter;

	@Inject
	private AssignmentConverter assignmentConverter;

	@Override
	public TeamRepresentor toElementary(Team team) {
		final TeamRepresentor representor = team.getId() != null
				? new TeamRepresentor(team.getId(), team.getName(), this.appUserConverter.toElementary(team.getLeader()))
				: new TeamRepresentor(team.getName(), this.appUserConverter.toElementary(team.getLeader()));
		return representor;
	}

	@Override
	public TeamRepresentor toSubSimplified(Team team) {
		final TeamRepresentor representor = this.toElementary(team);
		if (team.getMembers() != null) {
			for (final AppUser member : team.getMembers()) {
				representor.addMember(this.appUserConverter.toElementary(member));
			}
		}
		return representor;
	}

	@Override
	public TeamRepresentor toSimplified(Team team) {
		final TeamRepresentor representor = this.toSubSimplified(team);
		if (team.getObjectives() != null) {
			for (final TeamObjectiveAssignment objective : team.getObjectives()) {
				representor.addObjectiveAssignment(this.assignmentConverter.to(objective));
			}
		}
		if (team.getProjects() != null) {
			for (final TeamProjectAssignment project : team.getProjects()) {
				representor.addProjectAssignment(this.assignmentConverter.to(project));
			}
		}
		if (team.getSubmodules() != null) {
			for (final TeamSubmoduleAssignment submodule : team.getSubmodules()) {
				representor.addSubmoduleAssignment(this.assignmentConverter.to(submodule));
			}
		}
		if (team.getTasks() != null) {
			for (final TeamTaskAssignment task : team.getTasks()) {
				representor.addTaskAssignment(this.assignmentConverter.to(task));
			}
		}
		return representor;
	}

	@Override
	public TeamRepresentor toComplete(Team team) {
		return this.inculdeMonitoringFields(this.toSimplified(team), team);
	}

	@Override
	public Set<TeamRepresentor> toElementary(Set<Team> teams) {
		final Set<TeamRepresentor> representors = new HashSet<>();
		for (final Team team : teams) {
			representors.add(this.toElementary(team));
		}
		return representors;
	}

	@Override
	public Set<TeamRepresentor> toSimplified(Set<Team> teams) {
		final Set<TeamRepresentor> representors = new HashSet<>();
		for (final Team team : teams) {
			representors.add(this.toSimplified(team));
		}
		return representors;
	}

	@Override
	public Set<TeamRepresentor> toComplete(Set<Team> teams) {
		final Set<TeamRepresentor> representors = new HashSet<>();
		for (final Team team : teams) {
			representors.add(this.toComplete(team));
		}
		return representors;
	}

}
