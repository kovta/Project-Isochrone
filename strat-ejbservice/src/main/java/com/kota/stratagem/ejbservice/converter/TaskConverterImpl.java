package com.kota.stratagem.ejbservice.converter;

import java.util.HashSet;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.kota.stratagem.ejbservice.converter.delegation.AssignmentConverter;
import com.kota.stratagem.ejbserviceclient.domain.TaskRepresentor;
import com.kota.stratagem.persistence.entity.AppUserTaskAssignment;
import com.kota.stratagem.persistence.entity.Task;
import com.kota.stratagem.persistence.entity.TeamTaskAssignment;

@Stateless
public class TaskConverterImpl extends AbstractMonitoredEntityConverter implements TaskConverter {

	@EJB
	private ObjectiveConverter objectiveConverter;

	@EJB
	private ProjectConverter projectConverter;

	@EJB
	private SubmoduleConverter submoduleConverter;

	// @EJB
	// private ImpedimentConverter impedimentConverter;

	@EJB
	private AssignmentConverter assignmentConverter;

	@Override
	public TaskRepresentor toElementary(Task task) {
		final TaskRepresentor representor = task.getId() != null
				? new TaskRepresentor(task.getId(), task.getName(), task.getDescription().trim(), task.getPriority(), task.getCompletion(), task.getDeadline(),
						task.getDuration(), task.getAdmittance())
				: new TaskRepresentor(task.getName(), task.getDescription().trim(), task.getPriority(), task.getCompletion(), task.getDeadline(),
						task.getDuration(), task.getAdmittance());
		if (task.getEstimation() != null) {
			representor.setPessimistic(task.getEstimation().getPessimistic());
			representor.setRealistic(task.getEstimation().getRealistic());
			representor.setOptimistic(task.getEstimation().getOptimistic());
		}
		return representor;
	}

	@Override
	public TaskRepresentor toDispatchable(Task task) {
		final TaskRepresentor representor = this.toElementary(task);
		if (task.getObjective() != null) {
			representor.setObjective(this.objectiveConverter.toElementary(task.getObjective()));
		}
		if (task.getProject() != null) {
			representor.setProject(this.projectConverter.toElementary(task.getProject()));
		}
		if (task.getSubmodule() != null) {
			representor.setSubmodule(this.submoduleConverter.toElementary(task.getSubmodule()));
		}
		return this.inculdeMonitoringFields(representor, task);
	}

	@Override
	public TaskRepresentor toSimplified(Task task) {
		final TaskRepresentor representor = this.toElementary(task);
		// if (task.getImpediments() != null) {
		// for (final Impediment impediment : task.getImpediments()) {
		// representor.addImpediment(this.impedimentConverter.to(impediment));
		// }
		// }
		if (task.getDependantTasks() != null) {
			for (final Task dependant : task.getDependantTasks()) {
				representor.addDependant(this.toElementary(dependant));
			}
		}
		if (task.getTaskDependencies() != null) {
			for (final Task dependency : task.getTaskDependencies()) {
				representor.addDependency(this.toElementary(dependency));
			}
		}
		if (task.getObjective() != null) {
			representor.setObjective(this.objectiveConverter.toElementary(task.getObjective()));
		}
		if (task.getProject() != null) {
			representor.setProject(this.projectConverter.toElementary(task.getProject()));
		}
		if (task.getSubmodule() != null) {
			representor.setSubmodule(this.submoduleConverter.toElementary(task.getSubmodule()));
		}
		return representor;
	}

	@Override
	public TaskRepresentor toComplete(Task task) {
		final TaskRepresentor representor = this.toSimplified(task);
		if (task.getAssignedTeams() != null) {
			for (final TeamTaskAssignment team : task.getAssignedTeams()) {
				representor.addTeam(this.assignmentConverter.to(team));
			}
		}
		if (task.getAssignedUsers() != null) {
			for (final AppUserTaskAssignment user : task.getAssignedUsers()) {
				representor.addUser(this.assignmentConverter.to(user));
			}
		}
		return this.inculdeMonitoringFields(representor, task);
	}

	@Override
	public Set<TaskRepresentor> toElementary(Set<Task> tasks) {
		final Set<TaskRepresentor> representors = new HashSet<>();
		for (final Task task : tasks) {
			representors.add(this.toElementary(task));
		}
		return representors;
	}

	@Override
	public Set<TaskRepresentor> toDispatchable(Set<Task> tasks) {
		final Set<TaskRepresentor> representors = new HashSet<>();
		for (final Task task : tasks) {
			representors.add(this.toDispatchable(task));
		}
		return representors;
	}

	@Override
	public Set<TaskRepresentor> toSimplified(Set<Task> tasks) {
		final Set<TaskRepresentor> representors = new HashSet<>();
		for (final Task task : tasks) {
			representors.add(this.toSimplified(task));
		}
		return representors;
	}

	@Override
	public Set<TaskRepresentor> toComplete(Set<Task> tasks) {
		final Set<TaskRepresentor> representors = new HashSet<>();
		for (final Task task : tasks) {
			representors.add(this.toComplete(task));
		}
		return representors;
	}

}