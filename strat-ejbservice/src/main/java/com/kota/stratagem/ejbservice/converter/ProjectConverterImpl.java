package com.kota.stratagem.ejbservice.converter;

import java.util.HashSet;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.kota.stratagem.ejbserviceclient.domain.ProjectRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ProjectStatusRepresentor;
import com.kota.stratagem.persistence.entity.Project;
import com.kota.stratagem.persistence.entity.Submodule;
import com.kota.stratagem.persistence.entity.Task;

@Stateless
public class ProjectConverterImpl implements ProjectConverter {

	@EJB
	private ObjectiveConverter objectiveConverter;

	@EJB
	private SubmoduleConverter submoduleConverter;

	@EJB
	private TaskConverter taskConverter;

	@EJB
	private TeamConverter teamConverter;

	@EJB
	private AppUserConverter appUserConverter;

	@EJB
	private ImpedimentConverter impedimentConverter;

	@Override
	public ProjectRepresentor to(Project project) {
		final ProjectRepresentor representor = this.toElementary(project);
		if (project.getSubmodules() != null) {
			for (final Submodule submodule : project.getSubmodules()) {
				representor.addSubmodules(this.submoduleConverter.to(submodule));
			}
		}
		if (project.getTasks() != null) {
			for (final Task task : project.getTasks()) {
				representor.addTask(this.taskConverter.toElementary(task));
			}
		}
		// if (project.getAssignedTeams() != null) {
		// for (final Team team : project.getAssignedTeams()) {
		// representor.addTeam(this.teamConverter.to(team));
		// }
		// }
		// if (project.getAssignedUsers() != null) {
		// for (final AppUser user : project.getAssignedUsers()) {
		// representor.addUser(this.appUserConverter.to(user));
		// }
		// }
		// if (project.getImpediments() != null) {
		// for (final Impediment impediment : project.getImpediments()) {
		// representor.addImpediment(this.impedimentConverter.to(impediment));
		// }
		// }
		return representor;
	}

	@Override
	public ProjectRepresentor toElementary(Project project) {
		final ProjectStatusRepresentor status = ProjectStatusRepresentor.valueOf(project.getStatus().toString());
		final ProjectRepresentor representor = project.getId() != null
				? new ProjectRepresentor(project.getId(), project.getName(), project.getDescription(), status, project.getDeadline(), project.getConfidential(),
						this.appUserConverter.toElementary(project.getCreator()), project.getCreationDate(),
						this.appUserConverter.toElementary(project.getModifier()), project.getModificationDate(),
						project.getObjective() != null ? this.objectiveConverter.toElementary(project.getObjective()) : null)
				: new ProjectRepresentor(project.getName(), project.getDescription(), status, project.getDeadline(), project.getConfidential(),
						this.appUserConverter.toElementary(project.getCreator()), project.getCreationDate(),
						this.appUserConverter.toElementary(project.getModifier()), project.getModificationDate(),
						this.objectiveConverter.toElementary(project.getObjective()));
		return representor;
	}

	@Override
	public Set<ProjectRepresentor> to(final Set<Project> projects) {
		final Set<ProjectRepresentor> representors = new HashSet<ProjectRepresentor>();
		for (final Project project : projects) {
			representors.add(this.to(project));
		}
		return representors;
	}

}