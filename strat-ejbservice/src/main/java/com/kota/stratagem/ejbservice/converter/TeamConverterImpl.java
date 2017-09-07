package com.kota.stratagem.ejbservice.converter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.kota.stratagem.ejbserviceclient.domain.TeamRepresentor;
import com.kota.stratagem.persistence.entity.AppUser;
import com.kota.stratagem.persistence.entity.Objective;
import com.kota.stratagem.persistence.entity.Project;
import com.kota.stratagem.persistence.entity.Task;
import com.kota.stratagem.persistence.entity.Team;

public class TeamConverterImpl implements TeamConverter {

	@Inject
	private AppUserConverter appUserConverter;

	@Inject
	private ObjectiveConverter objectiveConverter;

	@Inject
	private ProjectConverter projectConverter;

	@Inject
	private TaskConverter taskConverter;

	@Override
	public TeamRepresentor to(Team team) {
		final TeamRepresentor representor = this.toElementary(team);
		if (team.getMembers() != null) {
			for (final AppUser user : team.getMembers()) {
				representor.addMember(this.appUserConverter.toElementary(user));
			}
		}
		if (team.getObjectives() != null) {
			for (final Objective objective : team.getObjectives()) {
				representor.addObjective(this.objectiveConverter.toElementary(objective));
			}
		}
		if (team.getProjects() != null) {
			for (final Project project : team.getProjects()) {
				representor.addProject(this.projectConverter.toElementary(project));
			}
		}
		if (team.getTasks() != null) {
			for (final Task task : team.getTasks()) {
				representor.addTask(this.taskConverter.toElementary(task));
			}
		}
		return representor;
	}

	@Override
	public TeamRepresentor toElementary(Team team) {
		final TeamRepresentor representor = team.getId() != null
				? new TeamRepresentor(team.getId(), team.getName(), this.appUserConverter.toElementary(team.getLeader()),
						this.appUserConverter.toElementary(team.getCreator()), team.getCreationDate(), this.appUserConverter.toElementary(team.getModifier()),
						team.getModificationDate())
				: new TeamRepresentor(team.getName(), this.appUserConverter.toElementary(team.getLeader()),
						this.appUserConverter.toElementary(team.getCreator()), team.getCreationDate(), this.appUserConverter.toElementary(team.getModifier()),
						team.getModificationDate());
		return representor;
	}

	@Override
	public List<TeamRepresentor> to(List<Team> teams) {
		final List<TeamRepresentor> representors = new ArrayList<>();
		for (final Team team : teams) {
			representors.add(this.to(team));
		}
		return representors;
	}

}
