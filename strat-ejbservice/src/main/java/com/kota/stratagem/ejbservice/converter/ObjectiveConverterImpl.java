package com.kota.stratagem.ejbservice.converter;

import java.util.HashSet;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.kota.stratagem.ejbserviceclient.domain.ObjectiveRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ObjectiveStatusRepresentor;
import com.kota.stratagem.persistence.entity.Objective;
import com.kota.stratagem.persistence.entity.Project;
import com.kota.stratagem.persistence.entity.Task;

@Stateless
public class ObjectiveConverterImpl implements ObjectiveConverter {

	@EJB
	private AppUserConverter appUserConverter;

	@EJB
	private TeamConverter teamConverter;

	@EJB
	private ProjectConverter projectConverter;

	@EJB
	private TaskConverter taskConverter;

	@Override
	public ObjectiveRepresentor to(Objective objective) {
		final ObjectiveRepresentor representor = this.toElementary(objective);
		if (objective.getProjects() != null) {
			for (final Project project : objective.getProjects()) {
				representor.addProject(this.projectConverter.toElementary(project));
			}
		}
		if (objective.getTasks() != null) {
			for (final Task task : objective.getTasks()) {
				representor.addTask(this.taskConverter.to(task));
			}
		}
		// if (objective.getAssignedTeams() != null) {
		// for (final Team team : objective.getAssignedTeams()) {
		// representor.addTeam(this.teamConverter.to(team));
		// }
		// }
		// if (objective.getAssignedUsers() != null) {
		// for (final AppUser user : objective.getAssignedUsers()) {
		// representor.addUser(this.appUserConverter.to(user));
		// }
		// }
		return representor;
	}

	@Override
	public ObjectiveRepresentor toElementary(Objective objective) {
		final ObjectiveStatusRepresentor status = ObjectiveStatusRepresentor.valueOf(objective.getStatus().toString());
		final ObjectiveRepresentor representor = objective.getId() != null
				? new ObjectiveRepresentor(objective.getId(), objective.getName(), objective.getDescription(), objective.getPriority(), status)
				: new ObjectiveRepresentor(objective.getName(), objective.getDescription(), objective.getPriority(), status);
		return representor;
	}

	@Override
	public Set<ObjectiveRepresentor> to(Set<Objective> objectives) {
		final Set<ObjectiveRepresentor> representors = new HashSet<ObjectiveRepresentor>();
		for (final Objective objective : objectives) {
			representors.add(this.to(objective));
		}
		return representors;
	}

}
