package com.kota.stratagem.ejbservice.converter;

import java.util.HashSet;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.kota.stratagem.ejbserviceclient.domain.AppUserRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.RoleRepresentor;
import com.kota.stratagem.persistence.entity.AppUser;

@Stateless
public class AppUserConverterImpl implements AppUserConverter {

	@EJB
	private ObjectiveConverter objectiveConverter;

	@EJB
	private ProjectConverter projectConverter;

	@EJB
	private TaskConverter taskConverter;

	@EJB
	private ImpedimentConverter impedimentConverter;

	@EJB
	private TeamConverter teamConverter;

	@Override
	public AppUserRepresentor to(AppUser user) {
		final RoleRepresentor role = RoleRepresentor.valueOf(user.getRole().toString());
		final AppUserRepresentor representor = user.getId() != null
				? new AppUserRepresentor(user.getId(), user.getName(), user.getPasswordHash(), user.getEmail(), role)
				: new AppUserRepresentor(user.getName(), user.getPasswordHash(), user.getEmail(), role);
		// if (user.getObjectives() != null) {
		// for (final Objective objective : user.getObjectives()) {
		// representor.addObjective(this.objectiveConverter.to(objective));
		// }
		// }
		// if (user.getProjects() != null) {
		// for (final Project project : user.getProjects()) {
		// representor.addProject(this.projectConverter.to(project));
		// }
		// }
		// if (user.getTasks() != null) {
		// for (final Task task : user.getTasks()) {
		// representor.addTask(this.taskConverter.to(task));
		// }
		// }
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
		return representor;
	}

	@Override
	public Set<AppUserRepresentor> to(Set<AppUser> users) {
		final Set<AppUserRepresentor> representors = new HashSet<>();
		for (final AppUser user : users) {
			representors.add(this.to(user));
		}
		return representors;
	}

}
