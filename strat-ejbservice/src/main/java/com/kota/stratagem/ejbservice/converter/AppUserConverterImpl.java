package com.kota.stratagem.ejbservice.converter;

import java.util.HashSet;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.kota.stratagem.ejbserviceclient.domain.AppUserRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.catalog.RoleRepresentor;
import com.kota.stratagem.persistence.entity.AppUser;
import com.kota.stratagem.persistence.entity.AppUserObjectiveAssignment;
import com.kota.stratagem.persistence.entity.AppUserProjectAssignment;
import com.kota.stratagem.persistence.entity.AppUserSubmoduleAssignment;
import com.kota.stratagem.persistence.entity.AppUserTaskAssignment;

@Stateless
public class AppUserConverterImpl implements AppUserConverter {

	@EJB
	private ObjectiveConverter objectiveConverter;

	@EJB
	private ProjectConverter projectConverter;

	@EJB
	private TaskConverter taskConverter;

	@EJB
	private SubmoduleConverter submoduleConverter;

	@EJB
	private ImpedimentConverter impedimentConverter;

	@EJB
	private TeamConverter teamConverter;

	@EJB
	private AssignmentConverter assignmentConverter;

	@Override
	public AppUserRepresentor toElementary(AppUser user) {
		final RoleRepresentor role = RoleRepresentor.valueOf(user.getRole().toString());
		final AppUserRepresentor representor = user.getId() != null
				? new AppUserRepresentor(user.getId(), user.getName(), user.getPasswordHash(), user.getEmail(), role, user.getRegistrationDate(),
						user.getAcountModificationDate())
				: new AppUserRepresentor(user.getName(), user.getPasswordHash(), user.getEmail(), role, user.getRegistrationDate(),
						user.getAcountModificationDate());
		return representor;
	}

	@Override
	public AppUserRepresentor toComplete(AppUser user) {
		final AppUserRepresentor representor = this.toElementary(user);
		if (user.getObjectives() != null) {
			for (final AppUserObjectiveAssignment objective : user.getObjectives()) {
				representor.addObjectiveAssignment(this.assignmentConverter.to(objective));
			}
		}
		if (user.getProjects() != null) {
			for (final AppUserProjectAssignment project : user.getProjects()) {
				representor.addProjectAssignment(this.assignmentConverter.to(project));
			}
		}
		if (user.getSubmodules() != null) {
			for (final AppUserSubmoduleAssignment submodule : user.getSubmodules()) {
				representor.addSubmoduleAssignment(this.assignmentConverter.to(submodule));
			}
		}
		if (user.getTasks() != null) {
			for (final AppUserTaskAssignment task : user.getTasks()) {
				representor.addTaskAssignment(this.assignmentConverter.to(task));
			}
		}
		// if (user.getReportedImpediments() != null) {
		// for (final Impediment impediment : user.getReportedImpediments()) {
		// representor.addReportedImpediment(this.impedimentConverter.to(impediment));
		// }
		// }
		// if (user.getProcessedImpediments() != null) {
		// for (final Impediment impediment : user.getProcessedImpediments()) {
		// representor.addProcessingImpediment(this.impedimentConverter.to(impediment));
		// }
		// }
		// if (user.getSupervisedTeams() != null) {
		// for (final Team team : user.getSupervisedTeams()) {
		// representor.addSupervisedTeam(this.teamConverter.to(team));
		// }
		// }
		// if (user.getTeamMemberships() != null) {
		// for (final Team team : user.getTeamMemberships()) {
		// representor.addSupervisedTeam(this.teamConverter.to(team));
		// }
		// }
		if (user.getAccountModifier() != null) {
			representor.setAccountModifier(this.toElementary(user.getAccountModifier()));
		}
		return representor;
	}

	@Override
	public Set<AppUserRepresentor> toElementary(Set<AppUser> users) {
		final Set<AppUserRepresentor> representors = new HashSet<>();
		for (final AppUser user : users) {
			representors.add(this.toElementary(user));
		}
		return representors;
	}

	@Override
	public Set<AppUserRepresentor> toComplete(Set<AppUser> users) {
		final Set<AppUserRepresentor> representors = new HashSet<>();
		for (final AppUser user : users) {
			representors.add(this.toComplete(user));
		}
		return representors;
	}

}
