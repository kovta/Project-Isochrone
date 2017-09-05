package com.kota.stratagem.ejbserviceclient.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.kota.stratagem.ejbserviceclient.domain.catalog.RoleRepresentor;

public class AppUserRepresentor implements Serializable {

	private static final long serialVersionUID = -6915593956157741427L;

	private Long id;
	private final String name;
	private final String email;
	private final RoleRepresentor role;
	private final Date registrationDate;
	private AppUserRepresentor accountModifier;
	private final Date acountModificationDate;
	private final int notificationViewCount;
	private final int imageSelector;
	private final List<AppUserObjectiveAssignmentRepresentor> objectives;
	private final List<AppUserProjectAssignmentRepresentor> projects;
	private final List<AppUserSubmoduleAssignmentRepresentor> submodules;
	private final List<AppUserTaskAssignmentRepresentor> tasks;
	private final List<ImpedimentRepresentor> reportedImpediments;
	private final List<ImpedimentRepresentor> processedImpediments;
	private final List<TeamRepresentor> supervisedTeams;
	private final List<TeamRepresentor> teamMemberships;
	private final List<NotificationRepresentor> notifications;

	public AppUserRepresentor() {
		this(null, "", "", RoleRepresentor.PRISTINE_USER, new Date(), new Date(), 0, 0);
	}

	public AppUserRepresentor(Long id, String name, String email, RoleRepresentor role, Date registrationDate, Date acountModificationDate,
			int notificationViewCount, int imageSeletor) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.role = role;
		this.registrationDate = registrationDate;
		this.acountModificationDate = acountModificationDate;
		this.notificationViewCount = notificationViewCount;
		this.imageSelector = imageSeletor;
		this.objectives = new ArrayList<>();
		this.projects = new ArrayList<>();
		this.submodules = new ArrayList<>();
		this.tasks = new ArrayList<>();
		this.reportedImpediments = new ArrayList<>();
		this.processedImpediments = new ArrayList<>();
		this.supervisedTeams = new ArrayList<>();
		this.teamMemberships = new ArrayList<>();
		this.notifications = new ArrayList<>();
	}

	public AppUserRepresentor(String name, String email, RoleRepresentor role, Date registrationDate, Date acountModificationDate, int notificationViewCount,
			int imageSeletor) {
		super();
		this.name = name;
		this.email = email;
		this.role = role;
		this.registrationDate = registrationDate;
		this.acountModificationDate = acountModificationDate;
		this.notificationViewCount = notificationViewCount;
		this.imageSelector = imageSeletor;
		this.objectives = new ArrayList<>();
		this.projects = new ArrayList<>();
		this.submodules = new ArrayList<>();
		this.tasks = new ArrayList<>();
		this.reportedImpediments = new ArrayList<>();
		this.processedImpediments = new ArrayList<>();
		this.supervisedTeams = new ArrayList<>();
		this.teamMemberships = new ArrayList<>();
		this.notifications = new ArrayList<>();
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public String getEmail() {
		return this.email;
	}

	public RoleRepresentor getRole() {
		return this.role;
	}

	public Date getRegistrationDate() {
		return this.registrationDate;
	}

	public AppUserRepresentor getAccountModifier() {
		return this.accountModifier;
	}

	public void setAccountModifier(AppUserRepresentor accountModifier) {
		this.accountModifier = accountModifier;
	}

	public Date getAcountModificationDate() {
		return this.acountModificationDate;
	}

	public int getNotificationViewCount() {
		return this.notificationViewCount;
	}

	public int getImageSelector() {
		return this.imageSelector;
	}

	public List<AppUserObjectiveAssignmentRepresentor> getObjectives() {
		return this.objectives;
	}

	public List<AppUserProjectAssignmentRepresentor> getProjects() {
		return this.projects;
	}

	public List<AppUserSubmoduleAssignmentRepresentor> getSubmodules() {
		return this.submodules;
	}

	public List<AppUserTaskAssignmentRepresentor> getTasks() {
		return this.tasks;
	}

	public List<ImpedimentRepresentor> getReportedImpediments() {
		return this.reportedImpediments;
	}

	public List<ImpedimentRepresentor> getProcessedImpediments() {
		return this.processedImpediments;
	}

	public List<TeamRepresentor> getSupervisedTeams() {
		return this.supervisedTeams;
	}

	public List<TeamRepresentor> getTeamMemberships() {
		return this.teamMemberships;
	}

	public List<NotificationRepresentor> getNotifications() {
		return this.notifications;
	}

	@Override
	public String toString() {
		return "AppUserRepresentor [id=" + this.id + ", name=" + this.name + ", email=" + this.email + ", role=" + this.role + ", registrationDate="
				+ this.registrationDate + ", accountModifier=" + this.accountModifier + ", acountModificationDate=" + this.acountModificationDate
				+ ", notificationViewCount=" + this.notificationViewCount + ", imageSelector=" + this.imageSelector + ", objectives=" + this.objectives
				+ ", projects=" + this.projects + ", submodules=" + this.submodules + ", tasks=" + this.tasks + ", reportedImpediments="
				+ this.reportedImpediments + ", processedImpediments=" + this.processedImpediments + ", supervisedTeams=" + this.supervisedTeams
				+ ", teamMemberships=" + this.teamMemberships + ", notifications=" + this.notifications + "]";
	}

	public void addObjectiveAssignment(AppUserObjectiveAssignmentRepresentor objective) {
		this.objectives.add(objective);
	}

	public void addProjectAssignment(AppUserProjectAssignmentRepresentor project) {
		this.projects.add(project);
	}

	public void addSubmoduleAssignment(AppUserSubmoduleAssignmentRepresentor submodule) {
		this.submodules.add(submodule);
	}

	public void addTaskAssignment(AppUserTaskAssignmentRepresentor task) {
		this.tasks.add(task);
	}

	public void addNotification(NotificationRepresentor notification) {
		this.notifications.add(notification);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.id == null) ? 0 : this.id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final AppUserRepresentor other = (AppUserRepresentor) obj;
		if (this.id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!this.id.equals(other.id)) {
			return false;
		}
		return true;
	}

}
