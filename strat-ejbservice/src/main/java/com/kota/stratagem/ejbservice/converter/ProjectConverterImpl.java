package com.kota.stratagem.ejbservice.converter;

import java.util.HashSet;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.kota.stratagem.ejbserviceclient.domain.ProjectRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.catalog.ProjectStatusRepresentor;
import com.kota.stratagem.persistence.entity.AppUserProjectAssignment;
import com.kota.stratagem.persistence.entity.Project;
import com.kota.stratagem.persistence.entity.Submodule;
import com.kota.stratagem.persistence.entity.Task;
import com.kota.stratagem.persistence.entity.TeamProjectAssignment;

@Stateless
public class ProjectConverterImpl implements ProjectConverter {

	@EJB
	private ObjectiveConverter objectiveConverter;

	@EJB
	private SubmoduleConverter submoduleConverter;

	@EJB
	private TaskConverter taskConverter;

	@EJB
	private AppUserConverter appUserConverter;

	// @EJB
	// private ImpedimentConverter impedimentConverter;

	@EJB
	private AssignmentConverter assignmentConverter;

	@Override
	public ProjectRepresentor toElementary(Project project) {
		final ProjectStatusRepresentor status = ProjectStatusRepresentor.valueOf(project.getStatus().toString());
		final ProjectRepresentor representor = project.getId() != null
				? new ProjectRepresentor(project.getId(), project.getName(), project.getDescription().trim(), status, project.getDeadline(),
						project.getConfidential(), this.appUserConverter.toElementary(project.getCreator()), project.getCreationDate(),
						this.appUserConverter.toElementary(project.getModifier()), project.getModificationDate(),
						project.getObjective() != null ? this.objectiveConverter.toElementary(project.getObjective()) : null)
				: new ProjectRepresentor(project.getName(), project.getDescription().trim(), status, project.getDeadline(), project.getConfidential(),
						this.appUserConverter.toElementary(project.getCreator()), project.getCreationDate(),
						this.appUserConverter.toElementary(project.getModifier()), project.getModificationDate(),
						this.objectiveConverter.toElementary(project.getObjective()));
		return representor;
	}

	@Override
	public ProjectRepresentor toSimplified(Project project) {
		final ProjectRepresentor representor = this.toElementary(project);
		if (project.getSubmodules() != null) {
			for (final Submodule submodule : project.getSubmodules()) {
				representor.addSubmodules(this.submoduleConverter.toSimplified(submodule));
			}
		}
		if (project.getTasks() != null) {
			for (final Task task : project.getTasks()) {
				representor.addTask(this.taskConverter.toElementary(task));
			}
		}
		// if (project.getImpediments() != null) {
		// for (final Impediment impediment : project.getImpediments()) {
		// representor.addImpediment(this.impedimentConverter.to(impediment));
		// }
		// }
		return representor;
	}

	@Override
	public ProjectRepresentor toComplete(Project project) {
		final ProjectRepresentor representor = this.toSimplified(project);
		if (project.getAssignedTeams() != null) {
			for (final TeamProjectAssignment team : project.getAssignedTeams()) {
				representor.addTeam(this.assignmentConverter.to(team));
			}
		}
		if (project.getAssignedUsers() != null) {
			for (final AppUserProjectAssignment user : project.getAssignedUsers()) {
				representor.addUser(this.assignmentConverter.to(user));
			}
		}
		return representor;
	}

	@Override
	public Set<ProjectRepresentor> toElementary(Set<Project> projects) {
		final Set<ProjectRepresentor> representors = new HashSet<ProjectRepresentor>();
		for (final Project project : projects) {
			representors.add(this.toElementary(project));
		}
		return representors;
	}

	@Override
	public Set<ProjectRepresentor> toSimplified(Set<Project> projects) {
		final Set<ProjectRepresentor> representors = new HashSet<ProjectRepresentor>();
		for (final Project project : projects) {
			representors.add(this.toSimplified(project));
		}
		return representors;
	}

	@Override
	public Set<ProjectRepresentor> toComplete(Set<Project> projects) {
		final Set<ProjectRepresentor> representors = new HashSet<ProjectRepresentor>();
		for (final Project project : projects) {
			representors.add(this.toComplete(project));
		}
		return representors;
	}

}