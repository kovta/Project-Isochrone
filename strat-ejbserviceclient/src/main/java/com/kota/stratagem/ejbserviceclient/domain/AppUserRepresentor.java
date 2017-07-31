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
	private final String passwordHash;
	private final String email;
	private final RoleRepresentor role;
	private final Date registrationDate;
	private AppUserRepresentor accountModifier;
	private final Date acountModificationDate;
	private final List<ObjectiveRepresentor> objectives;
	private final List<ProjectRepresentor> projects;
	private final List<SubmoduleRepresentor> submodules;
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
		this.submodules = new ArrayList<>();
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
		this.submodules = new ArrayList<>();
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

	public List<SubmoduleRepresentor> getSubmodules() {
		return this.submodules;
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
