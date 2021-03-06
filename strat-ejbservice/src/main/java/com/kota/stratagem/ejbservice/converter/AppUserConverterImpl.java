package com.kota.stratagem.ejbservice.converter;

import java.util.HashSet;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.kota.stratagem.ejbservice.converter.delegation.AssignmentConverter;
import com.kota.stratagem.ejbserviceclient.domain.AppUserRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.catalog.RoleRepresentor;
import com.kota.stratagem.persistence.entity.AppUser;
import com.kota.stratagem.persistence.entity.AppUserObjectiveAssignment;
import com.kota.stratagem.persistence.entity.AppUserProjectAssignment;
import com.kota.stratagem.persistence.entity.AppUserSubmoduleAssignment;
import com.kota.stratagem.persistence.entity.AppUserTaskAssignment;
import com.kota.stratagem.persistence.entity.Notification;
import com.kota.stratagem.persistence.entity.Team;
import com.kota.stratagem.persistence.service.TeamService;

@Stateless
public class AppUserConverterImpl implements AppUserConverter {

	// @EJB
	// private ImpedimentConverter impedimentConverter;

	@EJB
	private TeamService teamService;

	@EJB
	private TeamConverter teamConverter;

	@EJB
	private AssignmentConverter assignmentConverter;

	@EJB
	private NotificationConverter notificationConverter;

	@Override
	public AppUserRepresentor toElementary(AppUser user) {
		final RoleRepresentor role = RoleRepresentor.valueOf(user.getRole().toString());
		final AppUserRepresentor representor = user.getId() != null
				? new AppUserRepresentor(user.getId(), user.getName(), user.getEmail(), role, user.getRegistrationDate(), user.getAccountModificationDate(),
						user.getNotificationViewCount(), user.getImageSelector())
				: new AppUserRepresentor(user.getName(), user.getEmail(), role, user.getRegistrationDate(), user.getAccountModificationDate(),
						user.getNotificationViewCount(), user.getImageSelector());
		return representor;
	}

	@Override
	public AppUserRepresentor toSimplified(AppUser user) {
		final AppUserRepresentor representor = this.toElementary(user);
		if ((user.getNotifications() != null) && !user.getNotifications().isEmpty()) {
			for (final Notification notification : user.getNotifications()) {
				representor.addNotification(this.notificationConverter.to(notification));
			}
		}
		return representor;
	}

	@Override
	public AppUserRepresentor toSubComplete(AppUser user) {
		final AppUserRepresentor representorProxy = this.toElementary(user);
		final AppUserRepresentor representor = this.includeAssignments(representorProxy, user);
		return representor;
	}

	@Override
	public AppUserRepresentor toComplete(AppUser user) {
		final AppUserRepresentor representorProxy = this.toSimplified(user);
		final AppUserRepresentor representor = this.includeAssignments(representorProxy, user);
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
		if (user.getSupervisedTeams() != null) {
			for (final Team team : user.getSupervisedTeams()) {
				representor.addSupervisedTeam(this.teamConverter.toElementary(this.teamService.readWithLeader(team.getId())));
			}
		}
		if (user.getTeamMemberships() != null) {
			for (final Team team : user.getTeamMemberships()) {
				representor.addTeamMembership(this.teamConverter.toElementary(this.teamService.readWithLeader(team.getId())));
			}
		}
		if (user.getAccountModifier() != null) {
			representor.setAccountModifier(this.toElementary(user.getAccountModifier()));
		}
		return representor;
	}

	private AppUserRepresentor includeAssignments(AppUserRepresentor representor, AppUser user) {
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
	public Set<AppUserRepresentor> toSubComplete(Set<AppUser> users) {
		final Set<AppUserRepresentor> representors = new HashSet<>();
		for (final AppUser user : users) {
			representors.add(this.toSubComplete(user));
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
