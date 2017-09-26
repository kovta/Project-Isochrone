package com.kota.stratagem.ejbservice.converter;

import java.util.HashSet;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.kota.stratagem.ejbservice.converter.delegation.AssignmentConverter;
import com.kota.stratagem.ejbserviceclient.domain.ObjectiveRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.catalog.ObjectiveStatusRepresentor;
import com.kota.stratagem.persistence.entity.AppUserObjectiveAssignment;
import com.kota.stratagem.persistence.entity.Objective;
import com.kota.stratagem.persistence.entity.Project;
import com.kota.stratagem.persistence.entity.Task;
import com.kota.stratagem.persistence.entity.TeamObjectiveAssignment;

@Stateless
public class ObjectiveConverterImpl extends AbstractMonitoredEntityConverter implements ObjectiveConverter {

	@EJB
	private AppUserConverter appUserConverter;

	@EJB
	private ProjectConverter projectConverter;

	@EJB
	private TaskConverter taskConverter;

	@EJB
	private AssignmentConverter assignmentConverter;

	@Override
	public ObjectiveRepresentor toElementary(Objective objective) {
		final ObjectiveStatusRepresentor status = ObjectiveStatusRepresentor.valueOf(objective.getStatus().toString());
		final ObjectiveRepresentor representor = objective.getId() != null
				? new ObjectiveRepresentor(objective.getId(), objective.getName(), objective.getDescription().trim(), objective.getPriority(), status,
						objective.getDeadline(), objective.getConfidential())
				: new ObjectiveRepresentor(objective.getName(), objective.getDescription().trim(), objective.getPriority(), status, objective.getDeadline(),
						objective.getConfidential());
		return representor;
	}

	@Override
	public ObjectiveRepresentor toDispatchable(Objective objective) {
		return this.inculdeMonitoringFields(this.toElementary(objective), objective);
	}

	@Override
	public ObjectiveRepresentor toSimplified(Objective objective) {
		final ObjectiveRepresentor representor = this.toElementary(objective);
		if (objective.getProjects() != null) {
			for (final Project project : objective.getProjects()) {
				representor.addProject(this.projectConverter.toElementary(project));
			}
		}
		if (objective.getTasks() != null) {
			for (final Task task : objective.getTasks()) {
				representor.addTask(this.taskConverter.toElementary(task));
			}
		}
		return representor;
	}

	@Override
	public ObjectiveRepresentor toComplete(Objective objective) {
		final ObjectiveRepresentor representor = this.toElementary(objective);
		if (objective.getProjects() != null) {
			for (final Project project : objective.getProjects()) {
				representor.addProject(this.projectConverter.toSimplified(project));
			}
		}
		if (objective.getTasks() != null) {
			for (final Task task : objective.getTasks()) {
				representor.addTask(this.taskConverter.toElementary(task));
			}
		}
		if (objective.getAssignedTeams() != null) {
			for (final TeamObjectiveAssignment teamAssignment : objective.getAssignedTeams()) {
				representor.addTeamAssignment(this.assignmentConverter.to(teamAssignment));
			}
		}
		if (objective.getAssignedUsers() != null) {
			for (final AppUserObjectiveAssignment userAssignment : objective.getAssignedUsers()) {
				representor.addUserAssignment(this.assignmentConverter.to(userAssignment));
			}
		}
		return this.inculdeMonitoringFields(representor, objective);
	}

	@Override
	public Set<ObjectiveRepresentor> toElementary(Set<Objective> objectives) {
		final Set<ObjectiveRepresentor> representors = new HashSet<ObjectiveRepresentor>();
		for (final Objective objective : objectives) {
			representors.add(this.toElementary(objective));
		}
		return representors;
	}

	@Override
	public Set<ObjectiveRepresentor> toSimplified(Set<Objective> objectives) {
		final Set<ObjectiveRepresentor> representors = new HashSet<ObjectiveRepresentor>();
		for (final Objective objective : objectives) {
			representors.add(this.toSimplified(objective));
		}
		return representors;
	}

	@Override
	public Set<ObjectiveRepresentor> toComplete(Set<Objective> objectives) {
		final Set<ObjectiveRepresentor> representors = new HashSet<ObjectiveRepresentor>();
		for (final Objective objective : objectives) {
			representors.add(this.toComplete(objective));
		}
		return representors;
	}

}
