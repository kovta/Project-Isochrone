package com.kota.stratagem.ejbserviceclient.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AppUserRepresentor implements Serializable {

	private static final long serialVersionUID = -6915593956157741427L;

	private Long id;
	private final String name;
	private final String passwordHash;
	private final String email;
	private final RoleRepresentor role;
	private final Date registrationDate;
	private AppUserRepresentor accountModifier;
	private final Date acountModificationDate;
	private final List<ObjectiveRepresentor> objectives;
	private final List<ProjectRepresentor> projects;
	private final List<TaskRepresentor> tasks;
	private final List<ImpedimentRepresentor> reportedImpediments;
	private final List<ImpedimentRepresentor> processedImpediments;
	private final List<TeamRepresentor> supervisedTeams;
	private final List<TeamRepresentor> teamMemberships;

	public AppUserRepresentor() {
		this(null, "", "", "", RoleRepresentor.PRISTINE_USER, new Date(), new Date());
	}

	public AppUserRepresentor(Long id, String name, String passwordHash, String email, RoleRepresentor role, Date registrationDate,
			Date acountModificationDate) {
		super();
		this.id = id;
		this.name = name;
		this.passwordHash = passwordHash;
		this.email = email;
		this.role = role;
		this.registrationDate = registrationDate;
		this.acountModificationDate = acountModificationDate;
		this.objectives = new ArrayList<>();
		this.projects = new ArrayList<>();
		this.tasks = new ArrayList<>();
		this.reportedImpediments = new ArrayList<>();
		this.processedImpediments = new ArrayList<>();
		this.supervisedTeams = new ArrayList<>();
		this.teamMemberships = new ArrayList<>();
	}

	public AppUserRepresentor(String name, String passwordHash, String email, RoleRepresentor role, Date registrationDate, Date acountModificationDate) {
		super();
		this.name = name;
		this.passwordHash = passwordHash;
		this.email = email;
		this.role = role;
		this.registrationDate = registrationDate;
		this.acountModificationDate = acountModificationDate;
		this.objectives = new ArrayList<>();
		this.projects = new ArrayList<>();
		this.tasks = new ArrayList<>();
		this.reportedImpediments = new ArrayList<>();
		this.processedImpediments = new ArrayList<>();
		this.supervisedTeams = new ArrayList<>();
		this.teamMemberships = new ArrayList<>();
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

	public String getPasswordHash() {
		return this.passwordHash;
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

	public List<ObjectiveRepresentor> getObjectives() {
		return this.objectives;
	}

	public List<ProjectRepresentor> getProjects() {
		return this.projects;
	}

	public List<TaskRepresentor> getTasks() {
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

	@Override
	public String toString() {
		return "\nAppUserRepresentor [id=" + this.id + ", name=" + this.name + ", passwordHash=" + this.passwordHash + ", email=" + this.email + ", role="
				+ this.role + ", registrationDate=" + this.registrationDate + ", accountModifier=" + this.accountModifier + ", acountModificationDate="
				+ this.acountModificationDate + ", objectives=" + this.objectives + ", projects=" + this.projects + ", tasks=" + this.tasks
				+ ", reportedImpediments=" + this.reportedImpediments + ", processedImpediments=" + this.processedImpediments + ", supervisedTeams="
				+ this.supervisedTeams + ", teamMemberships=" + this.teamMemberships + "]\n";
	}

}
